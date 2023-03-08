import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
 
public class PDFBuilder {
    
    public static final int WIDTH_PERCENTAGE = 90;
    public static final BaseColor BEIGE = new BaseColor(253, 200, 155);
    public static final BaseColor CYAN = new BaseColor(200, 255, 255);
    public static final BaseColor YELLOW = new BaseColor(255, 255, 0);
    public static final BaseColor CANARY = new BaseColor(255, 255, 128);
    public static final BaseColor BLUE = new BaseColor(150, 175, 255);
    public static final BaseColor VIOLET = new BaseColor(255, 144, 255);
    public static final BaseColor GREEN = new BaseColor(0, 255, 0);
    public static final BaseColor ORANGE = new BaseColor(255, 200, 0);
    public static final String DEST = "./src/table3.pdf";
    public static final Font FONT_TWELVE = FontFactory.getFont(FontFactory.COURIER, 12f, Font.BOLD);
    public static final Font FONT_ELEVEN = FontFactory.getFont(FontFactory.COURIER, 11f, Font.BOLD);
    public static final Font FONT_TEN = FontFactory.getFont(FontFactory.COURIER, 10f, Font.BOLD);
    public static final Font FONT_NINE = FontFactory.getFont(FontFactory.COURIER, 9f, Font.BOLD);
    public static final Font FONT_SEVENPTFIVE = FontFactory.getFont(FontFactory.COURIER, 7.5f, Font.BOLD);
    public static final Font FONT_SIXPTEIGHT = FontFactory.getFont(FontFactory.COURIER, 6.8f, Font.BOLD);
    public static final String EMPTY = "           ";
 
    public static void main(String[] args) throws IOException, DocumentException {
    	Scanner input = new Scanner(System.in);
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        DefaultPDF pdf = null;
        System.out.println("1 for datasci, 2 for intelsys");
        String next = input.next();
        if (next.equals("1")) {
        	pdf = new DataSciPDF(strFiller(" ", DataSciPDF.ROWS), strFiller(" ", DataSciPDF.ROWS),
        			strFiller(" ", DataSciPDF.ROWS), strFiller(" ", DataSciPDF.ROWS), strFiller(" ", DataSciPDF.ROWS));
        } else if (next.equals("2")) {
        	pdf = new IntelSysPDF(strFiller(" ", IntelSysPDF.ROWS), strFiller(" ", IntelSysPDF.ROWS),
        			strFiller(" ", IntelSysPDF.ROWS), strFiller(" ", IntelSysPDF.ROWS), strFiller(" ", IntelSysPDF.ROWS));
        } else if (next.equals("3")) {
        	pdf = new SysTrackPDF(strFiller(" ", SysTrackPDF.ROWS), strFiller(" ", SysTrackPDF.ROWS),
        			strFiller(" ", SysTrackPDF.ROWS), strFiller(" ", SysTrackPDF.ROWS), strFiller(" ", SysTrackPDF.ROWS));
        } else if (next.equals("4")) {
        	pdf = new SoftEngPDF(strFiller(" ", SoftEngPDF.ROWS), strFiller(" ", SoftEngPDF.ROWS),
                    strFiller(" ", SoftEngPDF.ROWS), strFiller(" ", SoftEngPDF.ROWS), strFiller(" ", SoftEngPDF.ROWS));
        } else if (next.equals("5")) {
        	pdf = new CyberSecPDF(strFiller(" ", CyberSecPDF.ROWS), strFiller(" ", CyberSecPDF.ROWS),
                    strFiller(" ", CyberSecPDF.ROWS), strFiller(" ", CyberSecPDF.ROWS), strFiller(" ", CyberSecPDF.ROWS));
        } else if (next.equals("6")) {
        	pdf = new NetTelePDF();
        } else if (next.equals("7")) {
        	pdf = new TradCSPDF();
        } else if (next.equals("8")) {
        	pdf = new InterCompPDF();
        }
        
        pdf.createPdf(DEST);
        input.close();
        
    }
 
    /** Makes a title header that is able to be colored.
     * 
     * @param fontSize
     * @param color
     * @param text
     * @return
     */
    public static PdfPTable makeDefaultHeader(Font fontSize, BaseColor color, String text) {
        PdfPTable header = new PdfPTable(1);
        header.setWidthPercentage(WIDTH_PERCENTAGE);
        
        PdfPCell cell = new PdfPCell(new Phrase(10f, text, fontSize));
        System.out.println(color);
        cell.setBackgroundColor(color);
        header.addCell(cell);
        
        return header;
    }
    
    /** Makes a default table containing 1 row, containing course title, number, semester, transfer/waiver, and grade.
     * 
     * @param titleFontSize
     * @param otherFontSize
     * @param courseTitle
     * @param courseNum
     * @param sem
     * @param tsf
     * @param grade
     * @return
     * @throws DocumentException
     */
    public static PdfPTable makeDefaultTable(Font titleFontSize, Font otherFontSize, String courseTitle,
            String courseNum, String sem, String tsf, String grade) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(WIDTH_PERCENTAGE);
        table.setWidths(new int[] { 3, 1, 1, 1, 1 });
        PdfPCell cell;
 
        cell = new PdfPCell(new Phrase(5f, courseTitle, titleFontSize));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(5f, courseNum, otherFontSize));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(5f, sem, otherFontSize));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(5f, tsf, otherFontSize));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(5f, grade, otherFontSize));
        table.addCell(cell);
 
        return table;
 
    }
    
    public static Font[] sizeFiller(Font toFill, int length) {
    	
    	Font[] array = new Font[length];
    	
    	for (int i = 0; i < length; i++) {
    		Font font = new Font(toFill);
    		
    		array[i] = font;
    	}
    	
    	return array;
    }
    
    public static String[] strFiller(String toFill, int length) {
    	
    	String[] array = new String[length];
    	
    	for (int i = 0; i < length; i++) {
    		String str = new String(toFill);
    		
    		array[i] = str;
    	}
    	
    	return array;
    }
    
}