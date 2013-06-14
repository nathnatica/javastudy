package study.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

@Aspect
public class InnerTypeAspect {
	@DeclareParents(value="study.aspectj.Address", defaultImpl=study.aspectj.TrackedObjectMixin.class)
	ITrackedObject trackedObject;
}
