import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
 
public class CyberSecPDF extends DefaultPDF {
 
	public static final int ROWS = 27;
	Font[] titleSizes;
	Font[] otherSizes;
	String[] usrData, titles, courseNums, semesters, tsfOrWaivers, grades;
	
	
	
	/** Monster (bad) constructor for presetting certain array values to degree plan defaults. Note: Default values will not be replaced unless this method is edited directly.
	 * 
	 * @param titles
	 * @param courseNums
	 * @param semesters
	 * @param tsfOrWaivers
	 * @param grades
	 * @throws IndexOutOfBoundsException
	 */
	public CyberSecPDF(String[] usrData, String[] titles, String[] courseNums,
	/*=========*/String[] semesters, String[] tsfOrWaivers, String[] grades) throws IndexOutOfBoundsException {
		if (titles.length != ROWS || courseNums.length != ROWS || semesters.length != ROWS || tsfOrWaivers.length != ROWS || grades.length != ROWS) {
			throw new IndexOutOfBoundsException("Illegal Array Length for CyberSecPDF (Must be 27)");
		}
		
		
		this.titleSizes = PDFBuilder.sizeFiller(PDFBuilder.FONT_SEVENPTFIVE, ROWS);
		this.otherSizes = PDFBuilder.sizeFiller(PDFBuilder.FONT_NINE, ROWS);
        this.usrData = usrData;
		this.titles = titles;
		this.courseNums = courseNums;
		this.semesters = semesters;
		this.tsfOrWaivers = tsfOrWaivers;
		this.grades = grades;
		
		this.titles[0] = "Information Security"; 						this.courseNums[0] = "CS6324";
		this.titles[1] = "Design and Analysis of Computer Algorithms";  this.courseNums[1] = "CS6363";
		this.titles[2] = "Advanced Operating Systems";					this.courseNums[2] = "CS6378";
		this.titles[5] = "System Sec. & Malicious Code Analysis";		this.courseNums[5] = "CS6332";
		this.titles[6] = "Data and Applications Security";				this.courseNums[6] = "CS6348";
		this.titles[7] = "Network Security";							this.courseNums[7] = "CS6349";
		this.titles[8] = "Introduction to Cryptography";				this.courseNums[8] = "CS6377";
		this.titles[10] = "1. " + this.titles[10];
		this.titles[11] = "2. " + this.titles[11];
		this.titles[13] = "3. " + this.titles[13];
		this.titles[14] = "4. " + this.titles[14];
		this.titles[15] = "5. " + this.titles[15];
		this.titles[16] = "6. " + this.titles[16];
		this.titles[20] = "Computer Science I";							this.courseNums[20] = "CS5303";
		this.titles[21] = "Computer Science II";						this.courseNums[21] = "CS5330";
		this.titles[22] = "Discrete Structures";						this.courseNums[22] = "CS5333";
		this.titles[23] = "Algorithm Analysis & Data Structures";		this.courseNums[23] = "CS5343";
		this.titles[24] = "Operating Systems Concepts";					this.courseNums[24] = "CS5348";
		this.titles[25] = "Computer Networks";							this.courseNums[25] = "CS5390";
		
		
		
		
	}
 
    // This function puts everything in place
    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        
        // Top of document. Did not PDFBuilder.make a function in order to customize with precision. Will do so later when expandability needs to be implemented
        PdfPTable introTable = new PdfPTable(1);     
        introTable.setWidthPercentage(PDFBuilder.WIDTH_PERCENTAGE);
        PdfPCell cell = new PdfPCell(new Phrase(10f, getHeaderString(usrData), PDFBuilder.FONT_TWELVE)); 
        introTable.addCell(cell);
        document.add(introTable);
 
        PdfPTable headers = PDFBuilder.makeDefaultTable(PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, "           Course Title              ",
                "Course Num ", "  UTD Sem  ", " Transfer  ", "   Grade   ");
        document.add(headers);
 
        PdfPTable reqCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.VIOLET, "CORE COURSES            (15 Credit Hours)            3.19 GPA Required");
        document.add(reqCoursesHeader);
 
        for (int i = 0; i < 5; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
            tsfOrWaivers[i], grades[i]);
            document.add(temp);
        }
 
        // ============================================CORE
        // COURSES=========================================================================================
        PdfPTable coreCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.VIOLET, "                  Two of the following 4 Core Courses                   ");
        document.add(coreCoursesHeader);
 
        for (int i = 5; i < 10; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
            tsfOrWaivers[i], grades[i]);
            document.add(temp);
        }
 
        // ============================================ELECTIVE COURSES=========================================================================================
        PdfPTable elecCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_TEN, PDFBuilder.VIOLET, "2 IA* APPROVED 6000 COURSES         (6 Credit Hours)         3.0 GPA Required");
        document.add(elecCoursesHeader);
 
        for (int i = 10; i < 13; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
            tsfOrWaivers[i], grades[i]);
            document.add(temp);
        }
        
        PdfPTable elecCoursesHeader2 = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.VIOLET, "                Additional Electives (3 Credit Hours Min.)                 ");
        document.add(elecCoursesHeader2);
 
        for (int i = 13; i < 18; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
            tsfOrWaivers[i], grades[i]);
            document.add(temp);
        }
        
        // OTHER NON CS REQ'D COURSES
        
        PdfPTable otherReqCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.VIOLET, 
        		"                            Other Requirements                             ");
        document.add(otherReqCoursesHeader);
 
        for (int i = 18; i < 20; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
            tsfOrWaivers[i], grades[i]);
            document.add(temp);
        }
        
        
 
        // ===================================================PREREQS=============================================================================
 
        PdfPTable prereqCoursesWarning = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_TEN, PDFBuilder.YELLOW, "                 ***5XXX Courses Cannot be used on this degree plan***                ");
        document.add(prereqCoursesWarning);
 
        PdfPTable prereqHeaders = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_NINE, PDFBuilder.VIOLET,       "Admission Prerequisites               Course Num    UTD Sem      Waiver       Grade   ");
        document.add(prereqHeaders);
        
        for (int i = 20; i < 27; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
            tsfOrWaivers[i], grades[i]);
            document.add(temp);
        }
 
 
 
        // Signature page also highly customizable.
        
        PdfPTable sigTable = new PdfPTable(1); 
        sigTable.setWidthPercentage(PDFBuilder.WIDTH_PERCENTAGE);
        cell = new PdfPCell(new Phrase(10f,            "*Check with a CS grad advisor"
                                                   + "\n                                                             "
                                                   + "\n                                                             "
                                                   + "\n                                                             "
                                                   + "\n                                                             "
                                                   + "\nAdvisor:__________________________          Date Submitted:___________"
                                                   + "\n                                                             "
                                                   , FontFactory.getFont(FontFactory.COURIER, 11f, Font.BOLD)));
        sigTable.addCell(cell);
        document.add(sigTable);

        document.close();
    }

    private String getHeaderString(String[] usrData) {
        String line1 =   "                          DEGREE PLAN               ____________";
        String line2 = "\n                 UNIVERSITY OF TEXAS AT DALLAS      ____________";
        String line3 = "\n                  MASTERS OF COMPUTER SCIENCE       ____________";
        String line4 = "\n                                                    ____________";
        String line5 = "\n                        CYBER SECURITY                          ";
        String line6 = "\n                                                               ";
        String line7 = String.format(" \nName: %-36s FT: %-15s ", usrData[0], usrData[3].charAt(0));
        String line8 = String.format("\nID: %-34s Thesis: %-15s ", usrData[1], usrData[3].charAt(1));
        String line9 = String.format("\nApplied In: %s", usrData[2]);
        String line10 = String.format("\n                         Anticipated Graduation: %-13s ", usrData[4]);
        String line11 = "\n                                                               ";

        return line1 + line2 + line3 + line4 + line5 + line6 + line7 + line8 + line9 + line10 + line11;
    }
}