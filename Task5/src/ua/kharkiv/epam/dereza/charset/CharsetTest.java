package ua.kharkiv.epam.dereza.charset;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;

public class CharsetTest {
	
	/**
	 * Allows you to convert String from utf-8 to cp1251
	 * 
	 * @param str
	 * @return converted String
	 * @throws UnsupportedEncodingException
	 */
	public static String convertUTF8toCP1251(String str) throws UnsupportedEncodingException{
		String converted = new String(str.getBytes("UTF-8"), "windows-1251"); 
		return converted;
	}
	
	/**
	 * Allows you to convert String from cp1251 to utf-8
	 * 
	 * @param str
	 * @return converted String
	 * @throws UnsupportedEncodingException
	 */
	public static String convertCP1251toUTF8(String str) throws UnsupportedEncodingException{
		String converted = new String(str.getBytes("windows-1251"), "UTF-8");
		return converted;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		Map<String, Charset> charsetsMap = Charset.availableCharsets();
		System.out.println("Charsets available: " + charsetsMap.size());
		StringBuffer sb = new StringBuffer();
		for(String name : charsetsMap.keySet()){
			if(name.contains("1251"))
				sb.append(name + " ");
		}
		System.out.println(sb);
		
		Locale[] locales = Locale.getAvailableLocales();
		System.out.println("Locales available: " + locales.length);
		for(int i = 0; i<locales.length; i++){
			if(locales[i].getVariant().length() != 0)
				System.out.println(locales[i]);
		}
		
		System.out.println(convertCP1251toUTF8("Р°Р±РІРіРґРµС‘Р¶Р·РёР№РєР»РјРЅРѕРїСЂСЃС‚СѓС„С…С†С‡С€С‰СЊС‹СЉСЌСЋСЏ"));
	}
}
