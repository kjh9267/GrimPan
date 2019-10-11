package grimpan.core;

public class Utils {

	public static final String DEFAULT_DIR = "C:/Temp/";

	public static final int MINPOLYDIST = 6;

	public static final String[] SHAPE_NAME = {
			"정다각형", "타원형", "다각형", "선분", "연필", "이동", "삭제", "로딩"
	};

	static public String getExtension(String fileName) {
		String ext = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			ext = fileName.substring(i+1);
		}
		return ext;
	}

}
