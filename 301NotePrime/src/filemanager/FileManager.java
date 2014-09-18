package filemanager;

public class FileManager {
	
	private static FileManager instance;
	private FileManager() { }
	
	public static FileManager getFileManager()
	{
		return instance;
	}
}
