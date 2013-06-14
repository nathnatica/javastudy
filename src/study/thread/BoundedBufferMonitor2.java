package study.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBufferMonitor2 {

	private static String[] buffer;
	private static int capacity;

	private static int front;
	private static int rear;
	private static int count;

	private static final Lock lock = new ReentrantLock();

	private static final Condition notFull = lock.newCondition();
	private static final Condition notEmpty = lock.newCondition();

	public static void set(int capacity) {
		BoundedBufferMonitor2.capacity = capacity;
		buffer = new String[capacity];
	}

	public static void deposit(String data) throws InterruptedException {
		lock.lock();
		try {
			while (count == capacity) {
				notFull.await();
			}
			buffer[rear] = data;
			rear = (rear+1) % capacity;
			count++;

			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public static String fetch() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0) {
				notEmpty.await();
			}
			String result = buffer[front];
			front = (front+1) % capacity;
			count--;

			notFull.signal();
			return result;
		} finally {
			lock.unlock();
		}
	}


}
