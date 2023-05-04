import java.io.FileOutputStream;

import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class AuditPDFBuilder {
	//public static void main(String[] args) {
	//	String[][] temp = new String[8][8];
	//	createAudRep("./src/audtable.pdf", temp, 0, 0, 0,0,0,0);
	//}

// usrData[0] = name
// usrData[1] = ID
// usrData[2] = applySem
// usrData[3] = tsfOrWaiver
// usrData[4] = expGrad

	public static void createAudRep(String dest, String[][] classes, String[] usrData, int csOrSe, String track, int coreStartIndex, int coreEndIndex, int coreChoiceStartIndex, int coreChoiceEndIndex,
			int elecStartIndex, int elecEndIndex, int preStartIndex, int preEndIndex) {
		try {
			String major;

	        Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(dest));
	        document.open();
	        
	        // Top of document. Did not PDFBuilder.make a function in order to customize with precision. Will do so later when expandability needs to be implemented
	        PdfPTable headerTable = new PdfPTable(1);     
	        headerTable.setWidthPercentage(PDFBuilder.WIDTH_PERCENTAGE);
	        
			if (csOrSe == 0) {
				major = "Computer Science";
			} else if (csOrSe == 1) {
				major = "Software Engineering";
			} else {
				major = "error";
			}

	        String title = 				   "                   AUDIT REPORT                   ";
	        String line1 = String.format("\nNAME: %-25s          ID: %-25s", usrData[0], usrData[1]);
	        String line2 = String.format("\nPLAN: %-25s          MAJOR: %-22s", "Master", major);
	        String line3 = String.format("\n                                         TRACK: %-22s", track);
	        String line4;// Generated below 
	        String line5;// Generated below 
	        String line6;// Generated below 
	        String line7 = 				   "CORE COURSES: ";
	        String line8 = 				   "ELECTIVE COURSES: ";
	        String line9 = 				   "LEVELING COURSES AND PRE-REQ'S FROM ADMISSION LETTER:";
			String line10 =                "OUTSTANDING REQUIREMENTS: ";
	        
	        
	        for (int i = elecStartIndex; i <= elecEndIndex - 1; i++) {
				if (!classes[1][i].trim().equals(""))
	        		line8 += classes[1][i] + ", ";
	        }

			if (!classes[1][elecEndIndex].trim().equals(""))
	        	line8 += classes[1][elecEndIndex];

			line8 = line8.substring(0, line8.length() - 2);

			line8 += "\n";
			
			line9 += "\n\n";
			for (int i = preStartIndex; i <= preEndIndex; i++) {
				if (!classes[1][i].trim().equals(""))
					line9 += classes[1][i] + ": ";
				if (classes[3][i].trim().equals("") && !classes[0][i].trim().equals("")) {
					if (classes[4][i].trim().equals("")) {
						line9 += "Not yet completed.\n";
					} else {
						line9 += "Completed: " + classes[2][i] + ": " + classes[4][i] + "\n";
					}
				} else if (!classes[0][i].trim().equals("")){
					line9 += "Waived\n";
				}
			}
	        
			line10 += "\n\nIn order to maintain a 3.19 core GPA:\n";
			
			String[] tempClasses1 = genArray(classes, 1, coreStartIndex, coreEndIndex);
			String[] tempGrades1 = genArray(classes, 4, coreStartIndex, coreEndIndex);

			if (coreChoiceStartIndex != 0) {
				ArrayList<Integer> indices = genArrayWithGradeOnly(classes, coreChoiceStartIndex, coreChoiceEndIndex);

				String[] tempCoreChoiceClasses = new String[indices.size()];
				String[] tempCoreChoiceGrades = new String[indices.size()];

				System.out.println("Indices size: " + indices.size());

				for (int i = 0; i < indices.size(); i++) {
					tempCoreChoiceClasses[i] = classes[1][indices.get(i)];
					System.out.println(tempCoreChoiceClasses[i]);
					tempCoreChoiceGrades[i] = classes[4][indices.get(i)];
				}

				System.out.println(tempCoreChoiceClasses.length);

				tempClasses1 = combArray(tempClasses1, tempCoreChoiceClasses);
				tempGrades1 = combArray(tempGrades1, tempCoreChoiceGrades);
			}

			for (int i = 0; i < tempClasses1.length; i++) {
				line7 += tempClasses1[i] + ", ";
			}
			
			line7 = line7.substring(0, line7.length() - 2);

			line7 +=  "\n";

			line10 += GPAMsgGen.msgGen(tempClasses1, tempGrades1, GPAMsgGen.MIN_CORE_GPA);
			
			// Generating 4-6 now
			line4 = String.format("\nCORE GPA: %-57.3f", GPAMsgGen.gpaGen(tempClasses1, tempGrades1));
			
			line10 += "\n\nIn order to maintain a 3.00 elective GPA:\n";
			
			String[] tempClasses2 = genArray(classes, 1, elecStartIndex, elecEndIndex);
			String[] tempGrades2 = genArray(classes, 4, elecStartIndex, elecEndIndex);
			int count = 0;

			for (int i = 0; i < tempClasses2.length; i++) {
				if (!tempClasses2[i].trim().equals("")) count++;
			}

			// Calculating the number of electives student still has to take
			if (count >= 6) {
				line10 += GPAMsgGen.msgGen(tempClasses2, tempGrades2, GPAMsgGen.MIN_ELEC_GPA);
			} else {
				line10 += GPAMsgGen.elecMsgGen(count, tempClasses2, tempGrades2, GPAMsgGen.MIN_ELEC_GPA);
			}
			
			line5 = String.format("\nELECTIVE GPA: %-53.3f", GPAMsgGen.gpaGen(tempClasses2, tempGrades2));
			
			line10 += "\n\nIn order to maintain a 3.00 overall GPA:\n";
			
			String[] tempClassesComb = combArray(tempClasses1, tempClasses2);
			String[] tempGradesComb = combArray(tempGrades1, tempGrades2);
			
			line10 += GPAMsgGen.msgGen(tempClassesComb, tempGradesComb, GPAMsgGen.MIN_CUML_GPA);
			
			line6 = String.format("\nCOMBINED GPA: %-53.3f", GPAMsgGen.gpaGen(tempClassesComb, tempGradesComb));

	        String toInsert = "\n\n" + line1 + line2 + line3 + "\n" + line4 + line5 + line6 + "\n\n" + line7 + line8 + "\n" + line9 + "\n\n" + line10; 
	        
	        PdfPCell cell = new PdfPCell(new Phrase(10f, title, PDFBuilder.FONT_FIFTEEN)); 
	        
	        cell.setBorderColor(PDFBuilder.WHITE);
	        
	        
	        headerTable.addCell(cell);      
	        document.add(headerTable);
	        
	        PdfPTable bodyTable = new PdfPTable(1);     
	        bodyTable.setWidthPercentage(PDFBuilder.WIDTH_PERCENTAGE);
	        
	        cell = new PdfPCell(new Phrase(10f, toInsert, PDFBuilder.FONT_TEN));
	        cell.setBorderColor(PDFBuilder.WHITE);
	        
	        bodyTable.addCell(cell);
	        document.add(bodyTable);
	        
			document.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Generates an array out of a small section of the big data array
	private static String[] genArray(String[][] fullArray, int ind, int startInd, int endInd) {
		String[] toReturn = new String[endInd - startInd + 1];
		System.out.println(endInd - startInd + 1);
		for (int i = startInd; i <= endInd; i++) {
			toReturn[i - startInd] = fullArray[ind][i-1];
			System.out.println(toReturn[i - startInd]);
		}
		
		return toReturn;
	}

	private static ArrayList<Integer> genArrayWithGradeOnly(String[][] fullArray, int startInd, int endInd) {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for (int i = startInd; i <= endInd; i++) {
			System.out.println("{CLASS CODE: " + fullArray[1][i] + "}");
			System.out.println("{GRADE: " + fullArray[4][i] + "}");
			if (!fullArray[1][i].trim().equals("") && !fullArray[4][i].trim().equals("")) {
				System.out.println("If the above 2 are populated, this should print.");
				indices.add(i);
			} else {
				continue;
			}
		}
		
		return indices;
	}

	// Combines 2 Arrays
	private static String[] combArray(String[] arr1, String[] arr2) {
		String[] combined = new String[arr1.length + arr2.length];
		for (int i = 0; i < arr1.length; i++) {
			combined[i] = arr1[i];
		}
		for (int i = 0; i < arr2.length; i++) {
			combined[i + arr1.length] = arr2[i];
		}
		
		return combined;
	}
	
}