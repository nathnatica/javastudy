package study.aspectj;

import java.lang.reflect.Field;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.FieldSignature;


@Aspect
public class FieldSetAspect {
	//@Pointcut("args(rhs) && set(java.lang.String study.aspectj.Address.*)")
	@Pointcut("args(rhs) && set(* study.aspectj.Address.*)")
	public void allFields(Object rhs) {
	}

	@Pointcut("!set(* study.aspectj.TrackedObjectMixin.*)")
	public void skipTrackedObject() {
	}

	@Around("allFields(rhs) && skipTrackedObject()")
	public Object reportFieldAssignment(ProceedingJoinPoint thisJoinPoint, Object rhs) throws Throwable {
		FieldSignature fs = (FieldSignature)thisJoinPoint.getSignature();

		Object target = thisJoinPoint.getTarget();
		Field field = fs.getField();
		field.setAccessible(true);
		Object currentValue = field.get(target);

		if (equals(currentValue, rhs)) {
			return null;
		} else {
			((ITrackedObject)target).setChanged(true);
			System.out.printf("Setting %s from %s to %s\n", fs.getName(), currentValue, rhs);
			return thisJoinPoint.proceed();
		}
	}


	private boolean equals(Object lhs, Object rhs) {
		if (lhs == null && rhs == null) {
			return true;
		}
		if (lhs == null && rhs != null) {
			return false;
		}
		if (lhs != null && rhs == null) {
			return false;
		}
		return lhs.equals(rhs);
	}
}
