
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class CSVParser {
	public static String[][] getArray(String filePath) {
		ArrayList<String> data = new ArrayList<String>();
		System.out.println(System.getProperty("user.dir"));
			String filename = filePath;
			try {
				FileReader fileReader = new FileReader(filename);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					data.add(line);
				}
				bufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	for (String s : data)
		//	System.out.println(s);
		
		
		String[][] toReturn = new String[5][data.size() - 1];
		System.out.println(toReturn.length);

		// Not zero since the first entry of csv is titles.
		for (int i = 1; i < data.size(); i++) {
			String s = data.get(i);
			//System.out.println(s);
			String[] temp = s.split(",");
			for (int j = 0; j < 5; j++) {
				System.out.println(i-1 + "][" + j);
				temp[j] = temp[j].trim();
				if (temp[j].equals("blank")) {
					temp[j] = " ";
				}
				toReturn[j][i - 1] = temp[j];
				System.out.println(toReturn[j][i-1]);
			}
		}
		System.out.println(toReturn.length);
		System.out.println(toReturn[0].length);

		return toReturn;
	}
}
