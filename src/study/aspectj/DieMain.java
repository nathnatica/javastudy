package study.aspectj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DieMain {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		Die d = new Die();
		int faceValue = d.getFaceValue();

		ByteArrayOutputStream baos = serializeObject(d);
		Die retrieveDie = deserializeObject(baos);

		if (retrieveDie.getFaceValue() != faceValue) {
			System.out.printf("Expected %d, but found %d\n", faceValue, retrieveDie.getFaceValue());
		} else {
			System.out.printf("Serialization successful\n");
		}
	}

	private static Die deserializeObject(ByteArrayOutputStream baos) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;

		try {
			bais = new ByteArrayInputStream(baos.toByteArray());
			ois = new ObjectInputStream(bais);
			return (Die)ois.readObject();
		} finally {
			close(ois);
			close(bais);
		}
	}

	private static ByteArrayOutputStream serializeObject(Die d) throws IOException {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;

		try {
			baos = new ByteArrayOutputStream(1024);
			oos = new ObjectOutputStream(baos);
			oos.writeObject(d);
		} finally {
			close(oos);
			close(baos);
		}
		return baos;
	}

	private static void close(Closeable os) {
		if (os != null) {
			try {
				os.close();
			} catch (Exception e) {
				// ignore
			}
		}

	}
}
