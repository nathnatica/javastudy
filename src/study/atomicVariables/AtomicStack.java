package study.atomicVariables;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicStack {
	private final AtomicReference<Element> head = new AtomicReference<Element>(null);

	public void push (String value) {
		Element newElement = new Element(value);

		while (true) {
			Element oldHead = head.get();
			newElement.next = oldHead;
			if (head.compareAndSet(oldHead, newElement)) {
				return;
			}
		}
	}

	public String pop() {
		while (true) {
			Element oldHead = head.get();

			if (oldHead == null) {
				return null;
			}

			Element newHead = oldHead.next;

			if (head.compareAndSet(oldHead, newHead)) {
				return oldHead.value;
			}
		}
	}

	
	private class Element {
		public String value;
		public Element next;

		public Element(String value) {
			this.value = value;
		}
	}

}
