import java.util.Scanner;

public class CSVParser {
	public static String[][] getArray(String filePath) {
		
		Scanner file = new Scanner(filePath);
		
		String data = "";
		while (file.hasNextLine()) {
			data += file.nextLine();
		}
		
		return null;
	}
}
