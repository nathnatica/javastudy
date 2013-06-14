package study.threadInterrupt;

public class BetterRunnable implements Runnable {

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			// heady operation
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// if a thread get interrupt signal while sleeping or waiting
				// the thread become wake-up status 
				// and isInterrupted is set /false/
				// so  Thread.currentThread().interrupt(); is necessary to "interrupt safe"
				Thread.currentThread().interrupt();
			}
		
			// other operation
		}
	}

}
