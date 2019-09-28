package grimpan.core;

public class Utils {

	public static final String DEFAULT_DIR = "C:/Temp/";
	
	public static final int MINPOLYDIST = 6;
	
	static public String getExtension(String fileName) {
		String ext = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			ext = fileName.substring(i+1);
		}
		return ext;
	}

}
