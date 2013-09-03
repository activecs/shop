package ua.kharkiv.epam.dereza.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Utility class that contains static methods
 * 
 * @author Eduard_Dereza
 * 
 */
public class Utility {
	
	private final static String TYPE_STRING = "String";
	private final static String TYPE_DOUBLE = "double";
	private final static String TYPE_INTEGER = "int";
	private final static String TYPE_BIGDECIMAL = "BigDecimal";
	private final static String TYPE_BOOLEAN = "boolean";
	
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
	
	/**
	 * Reads object from scanner according to given object type name
	 * 
	 * @param type
	 * @param scanner
	 * @return read Object or null if cannot determine given type
	 */
	public static <T> T readByType(Class<T> type, Scanner scanner) {
		boolean repeat = false;
		do {
			try {
				switch (type.getSimpleName()) {
				case TYPE_STRING: {
					return (T) scanner.next();
				}
				case TYPE_INTEGER: {
					return (T) new Integer(scanner.nextInt());
				}
				case TYPE_DOUBLE: {
					return (T) new Double(scanner.nextDouble());
				}
				case TYPE_BOOLEAN: {
					return (T) new Boolean(scanner.nextBoolean());
				}
				case TYPE_BIGDECIMAL: {
					return (T) scanner.nextBigDecimal();
				}
				default: {
					return null;
				}
				}
			} catch (InputMismatchException e) {
				System.out.println("Would you like to repeat input");
				System.out.println("1 - yes");
				System.out.println("2 - no");
				scanner = new Scanner(System.in);
				if (!scanner.next().equals("1")) {
					throw e;
				} else {
					repeat = true;
				}
			}
		} while (repeat);
		
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param type
	 * @return
	 */
	public static <T> T generateRandObject(Class<T> type) {
		Random rand = new Random();
		switch (type.getSimpleName()) {
		case TYPE_STRING: {
			return (T) ("str" + rand.nextInt(100));
		}
		case TYPE_INTEGER: {
			return (T) new Integer(rand.nextInt(100));
		}
		case TYPE_DOUBLE: {
			return (T) new Double(rand.nextDouble() * 10);
		}
		case TYPE_BOOLEAN: {
			return (T) new Boolean(rand.nextBoolean());
		}
		case TYPE_BIGDECIMAL: {
			return (T) new BigDecimal(rand.nextDouble() * 100);
		}
		default: {
			return null;
		}
		}
	}
	
}
