package ua.kharkiv.epam.dereza.serialization;

import java.util.Arrays;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		//		char a = 'a';
		//		char A = 'A';
		//		char z = 'z';
		//		char Z = 'Z';
		//		System.out.println((int)a);
		//		System.out.println((int)A);
		//		System.out.println((int)z);
		//		System.out.println((int)Z);

		int captchaLength = 5;
		int startCharCode = 65;
		int lastCharCode = 90;

		Random rand = new Random();
		char captchaArr[] = new char[captchaLength];
		for(int i=0; i<captchaLength; i++){
			int nextCode = startCharCode + rand.nextInt(lastCharCode - startCharCode);
			System.out.println(nextCode);
			captchaArr[i] = (char)nextCode;
		}

		System.out.println(Arrays.toString(captchaArr));
	}

}
