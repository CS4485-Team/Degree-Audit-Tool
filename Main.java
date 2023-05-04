import java.io.File;
import java.io.FileWriter;

public class Main {
    public static void main(String args[]) throws Exception {
        //Accepts first argument when Jar file is ran
        String inputFilePath = args[0];
        File inputFile = new File(inputFilePath);
        transcriptParser transcript = new transcriptParser(inputFile);
        //Calls getStudent command to get Student information
        StudentParser curr = transcript.getStudent();
        //Calls Get thesis course to get courses
        curr.getThesisCourses();
        //Calls printStudentInformation which generates a CSV with student info and all class description
        curr.printStudentInformation();

    }

}
