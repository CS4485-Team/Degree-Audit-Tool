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
 
public class SoftEngPDF extends DefaultPDF {
 
	public static final int ROWS = 26;
	Font[] titleSizes;
	Font[] otherSizes;
	String[] titles, courseNums, semesters, tsfOrWaivers, grades;

        /** Monster (bad) constructor for presetting certain array values to degree plan defaults. Note: Default values will not be replaced unless this method is edited directly.
	 * 
	 * @param titles
	 * @param courseNums
	 * @param semesters
	 * @param tsfOrWaivers
	 * @param grades
	 * @throws IndexOutOfBoundsException
	 */
	public SoftEngPDF(String[] titles, String[] courseNums,
	/*=========*/String[] semesters, String[] tsfOrWaivers, String[] grades) throws IndexOutOfBoundsException {
		if (titles.length != ROWS || courseNums.length != ROWS || semesters.length != ROWS || tsfOrWaivers.length != ROWS || grades.length != ROWS) {
			throw new IndexOutOfBoundsException("Illegal Array Length for DataSciPDF (Must be 28)");
		}
		
		
		this.titleSizes = PDFBuilder.sizeFiller(PDFBuilder.FONT_SEVENPTFIVE, ROWS);
		this.otherSizes = PDFBuilder.sizeFiller(PDFBuilder.FONT_NINE, ROWS);
		this.titles = titles;
		this.courseNums = courseNums;
		this.semesters = semesters;
		this.tsfOrWaivers = tsfOrWaivers;
		this.grades = grades;
		
		this.titles[0] = "Object Oriented Software Engineering"; 		this.courseNums[0] = "   SE6329";
		this.titles[1] = "Advanced Requirements Engineering";    		this.courseNums[1] = "   SE6361";
		this.titles[2] = "Adv. Software Architecture and Design";               this.courseNums[2] = "   SE6362";
		this.titles[3] = "Sw. Testing, Validation, Verification";		this.courseNums[3] = "   SE6367";
		this.titles[4] = "Adv. Software Engineering Project";		        this.courseNums[4] = "   SE6387";
		this.titles[6] = "1. " + this.titles[6];
		this.titles[7] = "2. " + this.titles[7];
		this.titles[8] = "3. " + this.titles[8];
		this.titles[9] = "4. " + this.titles[9];
		this.titles[10] = "5. " + this.titles[10];
		this.titles[11] = "6. " + this.titles[11];
		this.titles[12] = "7. " + this.titles[12];
		this.titles[13] = "8. " + this.titles[13];
		this.titles[16] = "Computer Science I";					this.courseNums[16] = "   CS5303";
		this.titles[17] = "Computer Science II";				this.courseNums[17] = "   CS5330";
		this.titles[18] = "Discrete Structures";				this.courseNums[18] = "   CS5333";
		this.titles[19] = "Algorithm Analysis & Data Structures";		this.courseNums[19] = "   CS5343";
		this.titles[20] = "Operating Systems Concepts";				this.courseNums[20] = "   CS5348";
		this.titles[21] = "Software Engineering";			        this.courseNums[21] = "   CS5354";
		
		
		
		
	}
 
    // This function puts everything in place
    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        
        // Top of document. Did not PDFBuilder.make a function in order to customize with precision. Will do so later when expandability needs to be implemented
        PdfPTable introTable = new PdfPTable(1);     
        introTable.setWidthPercentage(PDFBuilder.WIDTH_PERCENTAGE);
        PdfPCell cell = new PdfPCell(new Phrase(10f,   "                          DEGREE PLAN               ____________"
                                                   + "\n                 UNIVERSITY OF TEXAS AT DALLAS      ____________"
                                                   + "\n                MASTERS OF SOFTWARE ENGINEERING     ____________"
                                                   + "\n                                                    ____________"
                                                   + "\n                      SOFTWARE ENGINEERING                     "
                                                   + "\n                                                               "
                                                   + "\nName:                                      FT:                 "
                                                   + "\nID:                                    Thesis:                 "
                                                   + "\nApplied In:                                                    "
                                                   + "\n                         Anticipated Graduation:               "
                                                   + "\n                                                               "
                                                   , PDFBuilder.FONT_TWELVE)); 
        introTable.addCell(cell);
        document.add(introTable);
 
        PdfPTable headers = PDFBuilder.makeDefaultTable(PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, "           Course Title              ",
                "Course Num ", "  UTD Sem  ", " Transfer  ", "   Grade   ");
        document.add(headers);
 
        PdfPTable reqCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.BLUE, "CORE COURSES            (15 Credit Hours)            3.19 GPA Required");
        document.add(reqCoursesHeader);
 
        for (int i = 0; i < 6; i++) {
                PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
                tsfOrWaivers[i], grades[i]);
                document.add(temp);
        }
 

 
        // ============================================ELECTIVE COURSES=========================================================================================
        PdfPTable elecCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_TEN, PDFBuilder.BLUE,    "5 APPROVED 6000 COURSES         (15* Credit Hours)         3.0 GPA Required");
        document.add(elecCoursesHeader);
        																										
        PdfPTable elecCoursesWarning = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_TEN, PDFBuilder.YELLOW, "              ***CS6359 Cannot be used on this degree plan***              ");
        document.add(elecCoursesWarning);

 
        for (int i = 6; i < 11; i++) {
                PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
                tsfOrWaivers[i], grades[i]);
                document.add(temp);
        }
        
        PdfPTable elecCoursesHeader2 = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.BLUE, "                Additional Electives (3 Credit Hours Min.)                 ");
        document.add(elecCoursesHeader2);
 
 
        for (int i = 11; i < 14; i++) {
                PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
                tsfOrWaivers[i], grades[i]);
                document.add(temp);
        }
        
        // OTHER NON CS REQ'D COURSES
        
        PdfPTable otherReqCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.BLUE, 
        		"                            Other Requirements                             ");
        document.add(otherReqCoursesHeader);
 
        for (int i = 14; i < 16; i++) {
                PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
                tsfOrWaivers[i], grades[i]);
                document.add(temp);
        }
        
        
 
        // ===================================================PREREQS=============================================================================
 
        PdfPTable prereqHeaders = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_NINE, PDFBuilder.BLUE, "Admission Prerequisites               Course Num    UTD Sem      Waiver       Grade   ");
        document.add(prereqHeaders);
        
        for (int i = 16; i < 26; i++) {
                PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
                tsfOrWaivers[i], grades[i]);
                document.add(temp);
        }
 
 
 
        // Signature page also highly customizable.
        
        PdfPTable sigTable = new PdfPTable(1); 
        sigTable.setWidthPercentage(PDFBuilder.WIDTH_PERCENTAGE);
        cell = new PdfPCell(new Phrase(10f,            "*May include any 6000/7000 CS Course Without Prior Permission"
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
}