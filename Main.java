import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String args[]) throws Exception {
        String inputFilePath = "C://Users/David/Desktop/Swift, Taylor-Transcript-1.pdf/";
        File inputFile = new File(inputFilePath);
        transcriptParser transcript = new transcriptParser(inputFile);
        StudentParser curr = transcript.getStudent();
        curr.fillFromTranscript();
        curr.printStudentInformation();
        curr.getSimpleName();

    }
}