package ua.kharkiv.epam.dereza.chain;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Utility class that contains static methods
 * 
 * @author Eduard_Dereza
 * 
 */
public class Utility {

	/**
	 * Returns list of files with given dir according filter's rules
	 * 
	 * @param dir
	 * @return ArrayList<File>
	 */
	public static ArrayList<File> findFilesInDir(String path, FileFilter filter) {
		if (path == null)
			throw new NullPointerException();
		if (!new File(path).exists())
			throw new IllegalArgumentException();
		
		File dir = new File(path);
		
		ArrayList<File> files = new ArrayList<File>();
		
		File[] filesDirs = dir.listFiles();

		for (File file : filesDirs) {
			if (!file.isDirectory()) {
				if(filter != null && filter.check(file) || filter == null)
					files.add(file);
			} else {
				files.addAll(findFilesInDir(file.getAbsolutePath(), filter));
			}
		}
		return files;
	}
	
	/**
	 * Parses date
	 * 
	 * @param String
	 *            startTime
	 * @return Date
	 * @throws ParseException 
	 */
	public static Date parseDate(String startTime) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = dateFormat.parse(startTime);
		} catch (ParseException ex) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			date = dateFormat.parse(startTime);
		}
		return date;
	}
}
