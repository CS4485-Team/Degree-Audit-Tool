import java.io.File;
import java.io.FileWriter;

public class Main {
    public static void main(String args[]) throws Exception {
        String inputFilePath = args[0];
        File inputFile = new File(inputFilePath);
        transcriptParser transcript = new transcriptParser(inputFile);
        StudentParser curr = transcript.getStudent();

        curr.getThesisCourses();

        curr.printStudentInformation();

    }

}
