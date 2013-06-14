package study.threadLocal;

public class BusinessService {

	public void businessMethod() {
		// get context from thread local
		Context context = MyThreadLocal.get();
		System.out.println(context.getTransactionId());
	}

}
