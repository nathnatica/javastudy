package study.aspectj;

public class Die {
	int faceValue;
	
	public Die() {
		roll();
	}
	
	public int roll() {
		int nextValue = (int)((Math.random()*6) +1);
		setFaceValue(nextValue);
		return getFaceValue();
	}
	
	public int getFaceValue() {
		return faceValue;
	}
	
	public void setFaceValue(int faceValue) {
		this.faceValue = faceValue;
	}
}
