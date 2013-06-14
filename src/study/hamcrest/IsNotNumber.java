package study.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

//custom matcher
public class IsNotNumber extends TypeSafeMatcher<Double> {

	@Override
	public boolean matchesSafely(Double number) {
		return number.isNaN();
	}

	public void describeTo(Description description) {
		description.appendText("not a number");
	}

	@Factory
	public static <T> Matcher<Double> notANumber() {
		return new IsNotNumber();
	}
}