package study.aspectj;

public class MethodExecutionExample {

	public void noArgMethod() {
		System.out.println("NO AEGUMENT");
	}

	public void methodWithStringParam(String string) {
		System.out.println("STRING PARAM");
	}

	public void methodCallingOtherMethod() {
		System.out.println("CALL OTHER");
		noArgMethod();
	}

	public static void staticMethod() {
		System.out.println("STATIC");
	}

}
