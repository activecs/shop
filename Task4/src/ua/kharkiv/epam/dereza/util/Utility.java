package ua.kharkiv.epam.dereza.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Utility class that contains static methods
 * 
 * @author Eduard_Dereza
 * 
 */
public class Utility {

	/**
	 * Parses date
	 * 
	 * @param String
	 *            startTime
	 * @return Date
	 */
	public static Date parseDate(String startTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = dateFormat.parse(startTime);
		} catch (ParseException ex) {
			try {
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				date = dateFormat.parse(startTime);
			} catch (ParseException e) {
				System.out.print("Can't parse date");
			}
		}
		return date;
	}
	
	/**
	 * Reads int in specified range
	 * 
	 * @throws IllegalArgumentException if inputed value is out of the range
	 * @param from
	 * @param to
	 * @param scanner
	 * @return read value
	 */
	public static int readIntInRange(int from, int to, Scanner scanner){
		int readInt = scanner.nextInt();
		if (readInt > to || readInt < from)
			throw new IllegalArgumentException("Inputed int is out of the range.");
		
		return readInt;
	}
}
