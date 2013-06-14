package test.timingaspect;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TestMethodTimingAspect {

	private Logger logger;

	public TestMethodTimingAspect() {
		try {
			logger = Logger.getLogger(this.getClass());
			logger.addAppender(new ConsoleAppender(new PatternLayout("%m%n")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Pointcut("target(receiver) && execution(@org.junit.Test * *()) && !execution(@test.auto.annotation.DoFirst * *()) && !execution(@test.auto.annotation.DoLast * *())")
	public void annotatedTest(final Object receiver) {
	}

	@Pointcut("target(receiver) && execution(void junit.framework.TestCase+.test*())")
	public void testMethod(final Object receiver) {
	}

	@Pointcut("target(receiver) && execution(@test.auto.annotation.AddToTestSuite * *())")
	public void customAnnotatedTest(final Object receiver) {
	}

	@Around("annotatedTest(receiver) || customAnnotatedTest(receiver)")
	public void around(final ProceedingJoinPoint thisJoinPoint, final Object receiver) throws Throwable {
		final String className = receiver.getClass().getName();
		final String methodName = thisJoinPoint.getSignature().getName();
		final String qualifiedMethodName = String.format("%s.%s", className, methodName);

		final long start = System.currentTimeMillis();
		try {
			thisJoinPoint.proceed(new Object[] { receiver });
		} finally {
			long end = System.currentTimeMillis();
			long gap = end - start;
			if (gap < 1000) {
				logger.debug(String.format("%16s, %s", "0." + gap, qualifiedMethodName));
			} else {
				double doubleGap = (double)gap/1000;
					int minute = (int)doubleGap/60;
					int sec = (int)(doubleGap-(minute*60));
				if (minute > 0) {
					logger.debug(String.format("%3s(min)%3s(sec), %s", minute, sec, qualifiedMethodName));
				} else {
					logger.debug(String.format("%16s(sec), %s",  sec, qualifiedMethodName));
				}
			}
		}
	}
}
