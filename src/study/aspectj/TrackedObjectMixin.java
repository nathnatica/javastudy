package study.aspectj;

public class TrackedObjectMixin implements ITrackedObject {
	private boolean changed = false;
	
	public TrackedObjectMixin() {
	}
	
	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

}
