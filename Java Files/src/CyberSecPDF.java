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
                                                   + "\n                        CYBER SECURITY                          "
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
 
        PdfPTable reqCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.VIOLET, "CORE COURSES            (15 Credit Hours)            3.19 GPA Required");
        document.add(reqCoursesHeader);
 
        
        // Each list here represents values in each column. Every section will need these values and a for loop will be used to insert them into the pdf.
        Font rcTitleSize[] = { PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE,
                PDFBuilder.FONT_SEVENPTFIVE };
        Font rcOtherSize[] = { PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE };
        String rcTitles[] = { "Information Security",
                "Design and Analysis of Computer Algorithms", "Advanced Operating Systems", PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String rcCourseNums[] = { "   CS6324  ", "   CS6363  ", "   CS6378  ", PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String rcSems[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String rcTransfer[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String rcGrade[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
 
        for (int i = 0; i < 5; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(rcTitleSize[i], rcOtherSize[i], rcTitles[i], rcCourseNums[i], rcSems[i],
                    rcTransfer[i], rcGrade[i]);
            document.add(temp);
        }
 
        // ============================================CORE
        // COURSES=========================================================================================
        PdfPTable coreCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.VIOLET, "                  Two of the following 4 Core Courses                   ");
        document.add(coreCoursesHeader);
 
 
        Font ccTitleSize[] = { PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SIXPTEIGHT, PDFBuilder.FONT_SEVENPTFIVE,
                PDFBuilder.FONT_SEVENPTFIVE };
        Font ccOtherSize[] = { PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE };
        String ccTitles[] = { "System Sec. & Malicious Code Analysis", "Data and Applications Security",
                "Network Security", "Introduction to Cryptography", PDFBuilder.EMPTY };
        String ccCourseNums[] = { "   CS6332  ", "   CS6348  ", "   CS6349  ", "   CS6377  ", PDFBuilder.EMPTY };
        String ccSems[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY};
        String ccTransfer[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String ccGrade[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
 
        for (int i = 0; i < 5; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(ccTitleSize[i], ccOtherSize[i], ccTitles[i], ccCourseNums[i], ccSems[i],
                    ccTransfer[i], ccGrade[i]);
            document.add(temp);
        }
 
        // ============================================ELECTIVE COURSES=========================================================================================
        PdfPTable elecCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_TEN, PDFBuilder.VIOLET, "2 IA* APPROVED 6000 COURSES         (6 Credit Hours)         3.0 GPA Required");
        document.add(elecCoursesHeader);
 
        // Each list here represents values in each column. Every section will need these values and a for loop will be used to insert them into the pdf.
        Font ecTitleSize[] = { PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE };
        Font ecOtherSize[] = { PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE };
        String ecTitles[] = { "1.", "2.", PDFBuilder.EMPTY };
        String ecCourseNums[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String ecSems[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String ecTransfer[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String ecGrade[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
 
        for (int i = 0; i < 3; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(ecTitleSize[i], ecOtherSize[i], ecTitles[i], ecCourseNums[i], ecSems[i],
                    ecTransfer[i], ecGrade[i]);
            document.add(temp);
        }
        
        PdfPTable elecCoursesHeader2 = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.VIOLET, "                Additional Electives (3 Credit Hours Min.)                 ");
        document.add(elecCoursesHeader2);
 
        Font ec2TitleSize[] = { PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE };
        Font ec2OtherSize[] = { PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE };
        String ec2Titles[] = { "3.", "4.", "5.", "6.", PDFBuilder.EMPTY };
        String ec2CourseNums[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String ec2Sems[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String ec2Transfer[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String ec2Grade[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
 
        for (int i = 0; i < 5; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(ec2TitleSize[i], ec2OtherSize[i], ec2Titles[i], ec2CourseNums[i], ec2Sems[i],
                    ec2Transfer[i], ec2Grade[i]);
            document.add(temp);
            System.out.println('a');
        }
        
        // OTHER NON CS REQ'D COURSES
        
        PdfPTable otherReqCoursesHeader = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_ELEVEN, PDFBuilder.VIOLET, 
        		"                            Other Requirements                             ");
        document.add(otherReqCoursesHeader);
 
        Font orcTitleSize[] = { PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE };
        Font orcOtherSize[] = { PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE };
        String orcTitles[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String orcCourseNums[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String orcSems[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String orcTransfer[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String orcGrade[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY };
 
        for (int i = 0; i < 2; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(orcTitleSize[i], orcOtherSize[i], orcTitles[i], orcCourseNums[i], orcSems[i],
                    orcTransfer[i], orcGrade[i]);
            document.add(temp);
        }
        
        
 
        // ===================================================PREREQS=============================================================================
 
        PdfPTable prereqCoursesWarning = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_TEN, PDFBuilder.YELLOW, "                 ***5XXX Courses Cannot be used on this degree plan***                ");
        document.add(prereqCoursesWarning);
 
        PdfPTable prereqHeaders = PDFBuilder.makeDefaultHeader(PDFBuilder.FONT_NINE, PDFBuilder.VIOLET,       "Admission Prerequisites               Course Num    UTD Sem      Waiver       Grade   ");
        document.add(prereqHeaders);
        
        Font prcTitleSize[] = { PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE,
                PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE, PDFBuilder.FONT_SEVENPTFIVE };
        Font prcOtherSize[] = { PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE, PDFBuilder.FONT_NINE };
        String prcTitles[] = { "Computer Science I", "Computer Science II",
                "Discrete Structures", "Algorithm Analysis & Data Structures", "Operating Systems Concepts", "Computer Networks", PDFBuilder.EMPTY };
        String prcCourseNums[] = { "   CS5303   ", "   CS5330    ", "   CS5333   ", "   CS5343   ", "   CS5348   ", "   CS5390   ", PDFBuilder.EMPTY };
        String prcSems[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY};
        String prcTransfer[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
        String prcGrade[] = { PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY, PDFBuilder.EMPTY };
 
        for (int i = 0; i < 7; i++) {
            PdfPTable temp = PDFBuilder.makeDefaultTable(prcTitleSize[i], prcOtherSize[i], prcTitles[i], prcCourseNums[i], prcSems[i],
                prcTransfer[i], prcGrade[i]);
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
}