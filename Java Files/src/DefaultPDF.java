import java.io.IOException;

import com.itextpdf.text.DocumentException;

public abstract class DefaultPDF {
	public abstract void createPdf(String dest) throws IOException, DocumentException;
}
