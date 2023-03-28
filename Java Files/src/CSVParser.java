import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class CSVParser {
	public static String[][] getArray(String filePath) {
		ArrayList<String> data = new ArrayList<String>();
		System.out.println(System.getProperty("user.dir"));
			String filename = "Degree-Audit-Tool/Java Files/src/data.csv";
			try {
				FileReader fileReader = new FileReader(filename);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					data.add(line + "\n");
				}
				bufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (String s : data)
			System.out.println(s);
		

		String[][] toReturn = new String[data.size()][5];

		// Not zero since the first entry of csv is titles.
		for (int i = 1; i < data.size(); i++) {
			String s = data.get(i);

			String[] temp = s.split(", ");
			System.out.println("top");
			System.out.println(temp[1]);
			System.out.println("bottom");
			for (int j = 0; j < 5; j++) {
				temp[j] = temp[j].trim();
				toReturn[i][j] = temp[j];
			}
		}
		
		return toReturn;
	}
}
