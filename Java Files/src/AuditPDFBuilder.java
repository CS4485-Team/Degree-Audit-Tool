import java.io.FileOutputStream;

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

	public static void createAudRep(String dest, String[][] classes, int coreStartIndex, int coreEndIndex, int elecStartIndex, int elecEndIndex, int preStartIndex, int preEndIndex) {
		try {

			double coreGPA = 2.40;//restGpaCalc(classes, coreStartIndex, coreEndIndex);
			//float elecGPA = restGpaCalc(classes, coreEndIndex + 1, elecEndIndex);
			//float overallGPA = restGpaCalc(classes, 0, elecEndIndex);


	        Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(dest));
	        document.open();
	        
	        // Top of document. Did not PDFBuilder.make a function in order to customize with precision. Will do so later when expandability needs to be implemented
	        PdfPTable headerTable = new PdfPTable(1);     
	        headerTable.setWidthPercentage(PDFBuilder.WIDTH_PERCENTAGE);
	        
	        String title = 				   "                   AUDIT REPORT                   ";
	        String line1 = String.format("\nNAME: %-25s          ID: %-25d", "Test N. Ame", 2021490138);
	        String line2 = String.format("\nPLAN: %-25s          MAJOR: %-22s", "Master", "Computer Science");
	        String line3 = String.format("\n                                         TRACK: %-22s", "Data Science");
	        String line4 = String.format("\nCORE GPA: %-57.3f", 3.755);
	        String line5 = String.format("\nELECTIVE GPA: %-53.3f", 3.755);
	        String line6 = String.format("\nCOMBINED GPA: %-53.3f", 3.755);
	        String line7 = 				   "CORE ELECTIVES: ";
	        String line8 = 				   "ELECTIVE COURSES: ";
	        String line9 = 				   "LEVELING COURSES AND PRE-REQ'S FROM ADMISSION LETTER:";
			String line10 =                "OUTSTANDING REQUIREMENTS: ";
	        

			for (int i = 0; i <= coreEndIndex - 1; i++) {
				line7 += classes[1][i] + ", ";
			}
	        line7 += classes[1][coreEndIndex] + "\n";
	        
	        for (int i = elecStartIndex; i <= elecEndIndex - 1; i++) {
	        	line8 += classes[1][i] + ", ";
	        }
	        line8 += classes[1][elecEndIndex] + "\n";
			
			line9 += "\n\n";
			for (int i = preStartIndex; i <= preEndIndex; i++) {
				line9 += classes[1][i] + ": ";
				if (classes[3][i].trim().equals("")) {
					if (classes[4][i].trim().equals("")) {
						line9 += "Not yet completed.\n";
					} else {
						line9 += "Completed: " + classes[2][i] + ": " + classes[4][i] + "\n";
					}
				} else {
					line9 += "Waived\n";
				}
			}
	        
			line10 += "\n\nIn order to maintain a 3.19 core GPA:\n";
			int hasGradeCounter = 0;
			int noGradeCounter = 0;
			for (int i = coreStartIndex - 1; i <= coreEndIndex - 1; i++) {
				if (!classes[4][i].trim().equals("")) hasGradeCounter++;
				else noGradeCounter++;
			}


			if (noGradeCounter != 0) {
				line10 += "\n\nIn order to maintain a 3.19 core GPA:\n";
				double graded = hasGradeCounter * coreGPA;
				double minAvg = (3.19 * ((double)(hasGradeCounter + noGradeCounter) - graded)) / noGradeCounter;
				System.out.println(hasGradeCounter);
				if (minAvg < 2) {
					line10 += "The student must pass: ";
				} else {
					line10 += "The student must maintain an average GPA of " + minAvg + " in: ";
				}
				for (int i = coreStartIndex - 1; i <= coreEndIndex - 1; i++) {
				//	System.out.println("TESTING: " + classes[1][i] + ", " + classes[4][i]);
					if (classes[4][i].trim().equals(""))
						line10 += classes[1][i] + ", ";
				} 
				line10 += "\n";
			} 

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

	public static float restGpaCalc(String[][] classes, int startIndex, int endIndex) {
		int counter = 0;
		float cumAvg = 0;
		for (int i = startIndex; i <= endIndex; i++) {
			String tempGrade = classes[4][i];
			if (!tempGrade.trim().equals("")) {
				counter++;
				if (tempGrade.trim().equals("A+")) {
					cumAvg += 4;
				} else if (tempGrade.trim().equals("A")) {
					cumAvg += 4;
				} else if (tempGrade.trim().equals("A-")) {
					cumAvg += 3.67;
				} else if (tempGrade.trim().equals("B+")) {
					cumAvg += 3.33;
				} else if (tempGrade.trim().equals("B")) {
					cumAvg += 3;
				} else if (tempGrade.trim().equals("B-")) {
					cumAvg += 2.67;
				} else if (tempGrade.trim().equals("C+")) {
					cumAvg += 2.33;
				} else if (tempGrade.trim().equals("C")) {
					cumAvg += 2;
				} else if (tempGrade.trim().equals("C-")) {
					cumAvg += 1.67;
				} else if (tempGrade.trim().equals("D+")) {
					cumAvg += 1.33;
				} else if (tempGrade.trim().equals("D")) {
					cumAvg += 1;
				} else if (tempGrade.trim().equals("D-")) {
					cumAvg += 0.67;
				} else if (tempGrade.trim().equals("F")) {
					cumAvg += 0;
				} else {
					counter--;
				}
			}
		}

		cumAvg = cumAvg / ((float)counter);

		return cumAvg;
	}
}