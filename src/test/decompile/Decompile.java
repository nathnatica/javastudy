package test.decompile;

/**
 * decompile local classes in a directory recursively
 * */
import java.io.File;
import java.io.IOException;

public class Decompile {

	public static void main(String[] args) throws IOException {

		String rootPath = "C:\\jad";

		//File file1 = new File(rootPath + "\\sp-basestd-1.1.0_05");
		//File file2 = new File(rootPath + "\\sp-conncom-1.1.0_05");
		//File file3 = new File(rootPath + "\\sp-connsvr-1.1.0_05");
		//File file4 = new File(rootPath + "\\sp-ecservice-1.1.5_01");
		//File file5 = new File(rootPath + "\\sp-ecservice-vo-1.1.5_01");
		//retrieve(file1);
		//retrieve(file2);
		//retrieve(file3);
		//retrieve(file4);
		//retrieve(file5);

		//File file6 = new File(rootPath + "\\sp-connclt-1.1.0_05");
		File file6 = new File(rootPath + "\\stax-api-1.0.1");
		File file7 = new File(rootPath + "\\geronimo-stax-api_1.0_spec-1.0.1");
		retrieve(file6);
		retrieve(file7);

	}

	public static void retrieve(File f) throws IOException {
		decompile(f);
		rename(f);
		delete(f);
	}

	public static void decompile(File f) throws IOException {
		if (f.isDirectory() ) {
			File[] fileList = f.listFiles();
			for (File file : fileList) {
				decompile(file);
			}
		} else {
			String filePath = f.getAbsolutePath();
			if (f.getName().endsWith(".class")) {
				String outputDir = filePath.substring(0, filePath.lastIndexOf("\\"));
				@SuppressWarnings("unused")
				Process p = Runtime.getRuntime().exec("C:\\jad\\jad.exe -o -d " + outputDir + " " + filePath);
			}
		}
	}

	public static void rename (File f) {
		if (f.isDirectory() ) {
			File[] fileList = f.listFiles();
			for (File file : fileList) {
				rename(file);
			}
		} else {
			if (f.getName().endsWith(".jad")) {
				f.renameTo(new File(f.getAbsolutePath().replace(".jad", ".java")));
			}
		}
	}

	public static void delete(File f) {
		if (f.isDirectory() ) {
			File[] fileList = f.listFiles();
			for (File file : fileList) {
				delete(file);
			}
		} else {
			if (f.getName().endsWith(".class")) {
				f.delete();
			}
		}
	}
}
