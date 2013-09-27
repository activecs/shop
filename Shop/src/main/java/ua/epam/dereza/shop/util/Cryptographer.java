package ua.epam.dereza.shop.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Cryptographer that allow you to encode string
 * 
 * @author Eduard_Dereza
 *
 */
public class Cryptographer {

	private static final Logger log = Logger.getLogger(Cryptographer.class);

	private static final String ALGORYTHM = "MD5";
	private static final String CHARSET = "utf-8";

	/**
	 * encrypts string in md5
	 * 
	 * @param input
	 * @return encoded string
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String input) {
		MessageDigest md5;
		String md5StrWithoutZerous;
		StringBuilder md5StrWithZerous = new StringBuilder(32);

		try {
			md5 = MessageDigest.getInstance(ALGORYTHM);
			md5.reset();

			// sends byte-code of string to MessageDigest
			md5.update(input.getBytes(CHARSET));

			// gets MD5-hash without zeros in front
			md5StrWithoutZerous = new BigInteger(1, md5.digest()).toString(16);

			// adds zeros in front if it is necessary
			for (int i = 0, count = 32 - md5StrWithoutZerous.length(); i < count; i++) {
				md5StrWithZerous.append('0');
			}

			md5StrWithZerous.append(md5StrWithoutZerous);

			if(log.isEnabledFor(Level.TRACE)){
				log.trace("Input string for encoding --> " + input);
				log.trace("Output encoded string --> " + md5StrWithZerous.toString());
			}
		} catch (NoSuchAlgorithmException e) {
			log.error("Cannot get instance of MessageDigest", e);
		} catch (UnsupportedEncodingException e) {
			log.error("Cannot generate byte-code of string", e);
		}
		return md5StrWithZerous.toString();
	}
}
