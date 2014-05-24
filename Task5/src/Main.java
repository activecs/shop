import java.io.IOException;
import java.util.Locale;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		String availableLoc = "en,ru";

		System.out.println("ava loc ->" + availableLoc);

		StringTokenizer token = new StringTokenizer(availableLoc, ",");
		while(token.hasMoreTokens()){
			new Locale(token.nextToken());
		}
	}

}
