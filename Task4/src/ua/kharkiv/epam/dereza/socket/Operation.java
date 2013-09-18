package ua.kharkiv.epam.dereza.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculator abstract class// pattern command
 * 
 * @author Eduard_Dereza
 *
 */
public abstract class Operation {

	/**
	 * Core of operation, must be implemented
	 * 
	 * @param operands
	 * @return result of arithmetic operation
	 */
	protected abstract String calculate(List<String> operands);

	/**
	 * Common method for performing operation
	 * 
	 * @param writer
	 * @param operands
	 * @throws IOException
	 */
	public void operate(BufferedWriter writer, List<String> operands) throws IOException{
		if(operands.size() < 2)
			throw new IllegalArgumentException("Not enough operands");

		String message = calculate(operands);

		// writes response for client
		writer.write("HTTP/1.1 200 OK\n");
		DateFormat df = DateFormat.getTimeInstance();
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		writer.write("Date: " + df.format(new Date()) + "\n");
		writer.write("Server: simpleTCPserver\n");
		writer.write("Connection: close\n");
		writer.write("Content-Type: text/html; charset=UTF-8\n");
		writer.write("Content-Length: " + message.length() + "\n\n");
		writer.write(message + "\n");
		writer.flush();
	}

	/**
	 * Allows you to parse value of  from String.
	 * From String like b12=7 will be returned  7
	 * 
	 * @param operands
	 * @return parsed operand's value
	 */
	protected List<Double> parseOperandsValues(List<String> operands){
		List<Double> operParsed = new ArrayList<Double>();
		Pattern pattern = Pattern.compile("\\d{1,}(\\.\\d{1,}|)$");
		for(String oper : operands){
			Matcher matcher = pattern.matcher(oper);
			if(matcher.find()){
				operParsed.add(new Double(matcher.group()));
			}else{
				throw new IllegalStateException("No match found");
			}
		}

		return operParsed;
	}

	/**
	 * Allows you to parse operand's name from String.
	 * From String like b12=7 will be returned  b12
	 * 
	 * @param operands
	 * @return parsed operand's name
	 */
	protected List<String> parseOperandsNames(List<String> operands){
		List<String> operParsed = new ArrayList<String>();
		Pattern pattern = Pattern.compile("[\\s[^=]]{1,}");
		for(String oper : operands){
			Matcher matcher = pattern.matcher(oper);
			if(matcher.find()){
				operParsed.add(matcher.group());
			}else{
				throw new IllegalStateException("No match found");
			}
		}

		return operParsed;
	}

}
