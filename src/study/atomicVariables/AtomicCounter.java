package study.atomicVariables;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
	private final AtomicInteger value = new AtomicInteger(0);
	
	public int getValue() {
		return value.get();
	}
	
	public int getNextValue() {
		return value.incrementAndGet();
	}
	
	public int getPrevousValue() {
		return value.decrementAndGet();
	}
}
