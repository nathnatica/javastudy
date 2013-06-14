package study.thread;


public class MonitorTest {

	public static void main(String[] args) {
		BoundedBufferMonitor2.set(10);
		new Thread(newFetch()).start();
		new Thread(newDeposit()).start();
	}

	private static Runnable newDeposit() {
		return new Runnable() {
			@Override
			public void run() {
				for (int i=0; i< 10; i++) {
					try {
						Thread.sleep(3000);
						BoundedBufferMonitor2.deposit(""+i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		};
	}

	private static Runnable newFetch() {
		return new Runnable() {
			@Override
			public void run() {
				for (int i=0; i< 10; i++) {
					try {
						Thread.sleep(10);
						System.out.println(BoundedBufferMonitor2.fetch());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		};
	}

}
