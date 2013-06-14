package study.aspectj;

import java.io.Serializable;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

@Aspect
public class SerializableIntroductionAspect {
	@DeclareParents(value="study.aspectj.Die")
	Serializable serializable;
}
