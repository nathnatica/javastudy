package study.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SaveMethodAspect {
	@Pointcut("execution(* study.aspectj.Dao.save(..))")
	public void daoSaveMethod() {
	}
	
	@Around("daoSaveMethod()")
	public Object skipSaveIfUnchanged(ProceedingJoinPoint thisJoinPoint) throws Throwable {
		Object param = thisJoinPoint.getArgs()[0];
		ITrackedObject tracked = (ITrackedObject)param;
		
		try {
			if (tracked.isChanged()) {
				return thisJoinPoint.proceed();
			} else {
				System.out.printf("Not saving : %s, it is unchanged\n", param.getClass());
				return null;
			}
		} finally {
			tracked.setChanged(false);
		}
	}
}	

