package study.thread;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	
	private int value = 0;
	
	private final Semaphore mutex = new Semaphore(1);
	Semaphore available = new Semaphore(200);
	
	public int getNextValue() throws InterruptedException {
		try {
			mutex.acquire();
			return value++;
		} finally {
			mutex.release();
		}
	}

	// if you need only mutual exclusion, synchronized block is a better solution
}
