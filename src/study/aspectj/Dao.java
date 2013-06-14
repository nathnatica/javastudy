package study.aspectj;

public class Dao {
	public static void save(Object o) {
		if (o != null) {
			System.out.println("Saving " + o.getClass());
		}
	}
}
