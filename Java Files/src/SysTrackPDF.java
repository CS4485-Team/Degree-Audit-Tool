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
 
public class SysTrackPDF extends DefaultPDF {
 
	public static final int ROWS = 28;
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
	public SysTrackPDF(String[] titles, String[] courseNums,
	/*=========*/String[] semesters, String[] tsfOrWaivers, String[] grades) throws IndexOutOfBoundsException {
		if (titles.length != ROWS || courseNums.length != ROWS || semesters.length != ROWS || tsfOrWaivers.length != ROWS || grades.length != ROWS) {
			throw new IndexOutOfBoundsException("Illegal Array Length for SysTrackPDF (Must be 28)");
		}
		
		
		this.titleSizes = PDFBuilder.sizeFiller(PDFBuilder.FONT_SEVENPTFIVE, ROWS);
		this.otherSizes = PDFBuilder.sizeFiller(PDFBuilder.FONT_NINE, ROWS);
		this.titles = titles;
		this.courseNums = courseNums;
		this.semesters = semesters;
		this.tsfOrWaivers = tsfOrWaivers;
		this.grades = grades;
		
		this.titles[0] = "Computer Architecture"; 						this.courseNums[0] = "   CS6304";
		this.titles[1] = "Design and Analysis of Computer Algorithms";  this.courseNums[1] = "   CS6363";
		this.titles[2] = "Advanced Operating Systems";					this.courseNums[2] = "   CS6378";
		this.titles[3] = "Real-time Systems";    						this.courseNums[3] = "   CS6396";
		this.titles[5] = "Network Security";							this.courseNums[5] = "   CS6349";
		this.titles[6] = "Parallel Processing";							this.courseNums[6] = "   CS6376";
		this.titles[7] = "Distributed Computing";						this.courseNums[7] = "   CS6380";
		this.titles[8] = "Synth/Optimization of High Perf. Systems";	this.courseNums[8] = "   CS6397";
		this.titles[9] = "Parallel Architectures and Systems";			this.courseNums[9] = "   CS6399";
		this.titles[11] = "1. " + this.titles[11];
		this.titles[12] = "2. " + this.titles[12];
		this.titles[13] = "3. " + this.titles[13];
		this.titles[14] = "4. " + this.titles[14];
		this.titles[15] = "5. " + this.titles[15];
		this.titles[16] = "6. " + this.titles[16];
		this.titles[17] = "7. " + this.titles[17];
		this.titles[18] = "8. " + this.titles[18];
		this.titles[21] = "Computer Science I";							this.courseNums[21] = "   CS5303";
		this.titles[22] = "Computer Science II";						this.courseNums[22] = "   CS5330";
		this.titles[23] = "Discrete Structures";						this.courseNums[23] = "   CS5333";
		this.titles[24] = "Algorithm Analysis & Data Structures";		this.courseNums[24] = "   CS5343";
		this.titles[25] = "Operating Systems Concepts";					this.courseNums[25] = "   CS5348";
		this.titles[26] = "Probability and Statistics in CS";			this.courseNums[26] = "   CS3341";
		
		
		
		
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
                                                   + "\n                  MASTERS OF COMPUTER SCIENCE       ____________"
                                                   + "\n                                                    ____________"
                                                   + "\n                         SYSTEMS TRACK                         "
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
 
        PdfPTable reqCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.CYAN, "CORE COURSES            (15 Credit Hours)            3.19 GPA Required");
        document.add(reqCoursesHeader);
 
         
        for (int i = 0; i < 5; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
                    tsfOrWaivers[i], grades[i]);
            document.add(temp);
        }
 
        // ============================================CORE
        // COURSES=========================================================================================
        PdfPTable coreCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.CYAN, "                  One of the following 5 Core Courses                   ");
        document.add(coreCoursesHeader);
 
  
 
        for (int i = 5; i < 11; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
                    tsfOrWaivers[i], grades[i]);
            document.add(temp);
        }
 
        // ============================================ELECTIVE COURSES=========================================================================================
        PdfPTable elecCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_TEN, PDFBuilder.CYAN, "5 APPROVED 6000 COURSES         (15* Credit Hours)         3.0 GPA Required");
        document.add(elecCoursesHeader);
  
        for (int i = 11; i < 16; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
                    tsfOrWaivers[i], grades[i]);
            document.add(temp);
        }
        
        PdfPTable elecCoursesHeader2 = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.CYAN, "                Additional Electives (3 Credit Hours Min.)                 ");
        document.add(elecCoursesHeader2);
  
        for (int i = 16; i < 19; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
                    tsfOrWaivers[i], grades[i]);
            document.add(temp);
        }
        
        // OTHER NON CS REQ'D COURSES
        
        PdfPTable otherReqCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.CYAN, 
        		"                            Other Requirements                             ");
        document.add(otherReqCoursesHeader); 
 
        for (int i = 19; i < 21; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(titleSizes[i], otherSizes[i], titles[i], courseNums[i], semesters[i],
                    tsfOrWaivers[i], grades[i]);
            document.add(temp);
        }
        
        
 
        // ===================================================PREREQS=============================================================================
 
        PdfPTable prereqHeaders = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_NINE, PDFBuilder.CYAN, "Admission Prerequisites               Course Num    UTD Sem      Waiver       Grade   ");
        document.add(prereqHeaders);
         
 
        for (int i = 21; i < 28; i++) {
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