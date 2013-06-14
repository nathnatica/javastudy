package study.lombok;

import java.io.IOException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.ToString;



@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
//@Log	fix it later
public @Data class LombokTest {
	private String variable;

	//@Getter(lazy=true) private final double[] cached = expensive();	//study later
	private double[] expensive() {
		double[] result = new double[1000000];
		for (int i=0; i < result.length; i++) {
			result[i] = Math.asin(i);
		}
		return result;
	}

	@Synchronized
	public int answerToLife() {
		return 42;
	}


	@SneakyThrows(IOException.class)
	public void run() throws Throwable {
		throw new Throwable();
	}

	public static void main(String[] args) throws IOException{
		System.out.println("test");
		//@Cleanup InputStream in = new FileInputStream("");	//study later
		//log.debug("log test");
	}
}