package filemanager;

import java.io.FileInputStream;

public interface PersistentObject {
	public void retrieveFromFIS(FileInputStream fis);
	public boolean saveToFOS();
}
