package study.aspectj;

public class MethodExecutionExampleMain {
	
	public static void main(String[] args) {
		// to run
		// make /META-INF/aop.xml under source folder
		// when run application
		// set VM argument "-javaagent:path/to/aspectjweaver.jar"
		
		MethodExecutionExample me = new MethodExecutionExample();
		me.noArgMethod();
		me.methodWithStringParam("Brett");
		me.methodCallingOtherMethod();
		MethodExecutionExample.staticMethod();

	}

	
}
