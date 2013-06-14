package study.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MethodExecutionAspect {

	@Pointcut("execution(* study.aspectj.MethodExecutionExample.*(..))")
	void allMethods() {
	}

	@Around("allMethods()")
	public Object reportMethodExecution(ProceedingJoinPoint thisJoinPoint) throws Throwable {
		System.out.printf("Entering : %s\n", thisJoinPoint.getSignature());
		try {
			return thisJoinPoint.proceed();
		} finally {
			System.out.printf("Leaving : %s\n", thisJoinPoint.getSignature());
		}
	}
}
