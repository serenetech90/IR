import java.io.File;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProjectToolkit {
	public static ArrayList<String> listDir(String dirFullPath) {
		ArrayList<String> fileList = new ArrayList<String>();
		File folder = new File(dirFullPath);
		try {
			for(final File fileEntry : folder.listFiles()) {
				if(fileEntry.isDirectory()) {
					fileList.addAll(listDir(fileEntry.getAbsolutePath()));
				} else {
					fileList.add(fileEntry.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileList;
	}
	
	public static String readTextFile(String fileFullPath) {
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get(fileFullPath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
}
