import com.itextpdf.text.*;

public class AuditPDFBuilder {
	public void createAudRep(String dest) {
		Phrase toInsert = new Phrase(10f,   "                          DEGREE PLAN               ____________"
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
                , PDFBuilder.FONT_TWELVE)
	}
}