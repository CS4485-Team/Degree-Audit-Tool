package src;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public class PDFwriter {

    public static void main(String[] args) throws IOException {

        File file = new File("C://Users/David/Desktop/Updated-DP-Cyber-Security.pdf/");
        PDDocument doc1 = Loader.loadPDF(file);

        PDDocumentCatalog docCatalog = doc1.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();

        if (acroForm != null) {
            PDField field = (PDField) acroForm.getField("Name of Student");
            acroForm.getField("Date Submitted").setValue("David");
        }

        doc1.save("new.pdf");
        doc1.close();

    }

}
