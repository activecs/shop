package ua.kharkiv.epam.dereza.chain;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		FileFilter filter = null;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Please input path to folder for scanning");
		System.out.println("(for win it looks like - C:/Windows, for linux - /data/app)");
		System.out.print(">");
		String path = scanner.next();
		
		System.out.println("Please, select the filtering options.");
		
		System.out.println("Please enter file name, or -1");
		String fileName = scanner.next();
		if(!fileName.equals("-1")){
			if(filter == null)
				filter = new FileFilterName(fileName);
			if(filter != null)
				filter = new FileFilterName(filter, fileName);
		}
		
		System.out.println("Please enter file extention, or -1");
		String fileExtesion = scanner.next();
		if(!fileExtesion.equals("-1")){
			if(filter == null)
				filter = new FileFilterExtention(fileExtesion);
			if(filter != null)
				filter = new FileFilterExtention(filter, fileExtesion);
		}
		
		System.out.println("Please enter the minimum allowable creation date of the file, or -1");
		System.out.println("date format - yyyy-MM-dd");
		String minDate = scanner.next();
		if(!minDate.equals("-1")){
			try {
				Date date = Utility.parseDate(minDate);
				if(filter == null)
					filter = new FileFilterMinModificationDate(date);
				if(filter != null)
					filter = new FileFilterMinModificationDate(filter, date);
			} catch (ParseException e) {
				System.out.println("Can't parse date, filter will be skipped");
			}
		}
		
		System.out.println("Please enter the maximum allowable creation date of the file, or -1");
		System.out.println("date format - yyyy-MM-dd");
		String maxDate = scanner.next();
		if(!maxDate.equals("-1")){
			try {
				Date date = Utility.parseDate(maxDate);
			if(filter == null)
				filter = new FileFilterMaxModificationDate(date);
			if(filter != null)
				filter = new FileFilterMaxModificationDate(filter, date);
			} catch (ParseException e) {
				System.out.println("Can't parse date, filter will be skipped");
			}
		}
		
		System.out.println("Please enter the minimum allowable size of the file, or -1");
		System.out.println("in bytes");
		Long minSize = scanner.nextLong();
		if(!minSize.equals(-1l)){
			if(filter == null)
				filter = new FileFilterMinSize(minSize);
			if(filter != null)
				filter = new FileFilterMinSize(filter, minSize);
		}
		
		System.out.println("Please enter the maximum allowable size of the file, or -1");
		System.out.println("in bytes");
		Long maxSize = scanner.nextLong();
		if(!maxSize.equals(-1l)){
			if(filter == null)
				filter = new FileFilterMaxSize(maxSize);
			if(filter != null)
				filter = new FileFilterMaxSize(filter, maxSize);
		}
		
		ArrayList<File> files = Utility.findFilesInDir(path, filter);
		
		Iterator<File> iter = files.iterator();
		
		System.out.println("Were found : " + files.size() + " files.");
		System.out.println("--Files:--");
		while(iter.hasNext())
			System.out.println(iter.next().getName());
		
		scanner.close();
	}
}
