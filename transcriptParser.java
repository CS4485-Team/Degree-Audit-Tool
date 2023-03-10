import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class transcriptParser {

    StudentParser currentStudent;

    public transcriptParser(File inputFile) throws IOException {
        String transcript[] = readPDF(inputFile);

        this.currentStudent = createStudent(transcript);

        fillCourseInformation(currentStudent, transcript);
        setSemesterAdmit();

        if (currentStudent.getTranscriptList().size() == 0)
            throw new IOException();
    }

    private String[] readPDF(File inputFile) throws IOException {
        PDDocument document = Loader.loadPDF(inputFile);

        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);

        document.close();

        return text.split("\n");
    }

    private boolean checkRegex(String input, String regex) {
        Pattern stringPattern = Pattern.compile(regex);
        Matcher m = stringPattern.matcher(input);
        return m.find();
    }

    private StudentParser createStudent(String transcript[]) {
        String studentName = "";
        String ID = "";
        String startDate = "";
        String Major = "";

        for (int i = 0; i < transcript.length; i++) {
            if (!studentName.equals("") && !ID.equals("") && !startDate.equals(""))
                break;

            if (checkRegex(transcript[i], "Name:")) {
                String temp = transcript[i].substring("Name:".length());
                studentName = temp.trim();
                continue;
            }

            if (checkRegex(transcript[i], "Student ID:")) {
                ID = transcript[i].substring("Student ID:".length());
                ID = ID.trim();

                if (!ID.matches("^[0-9]{10}$")) {
                    ID = "xxxxxxxxxx";
                }
            }

            if (checkRegex(transcript[i], "Active in Program") && checkRegex(transcript[i - 1], "Master")) {
                startDate = transcript[i].substring(0, 10);
                if (transcript[i + 1].contains("Computer Science"))
                    Major = "Computer Science";
                else if (transcript[i + 1].contains("Software Engineering"))
                    Major = "Software Engineering";
            }
        }
        return new StudentParser(studentName, ID, startDate, Major);
    }

    private void fillCourseInformation(StudentParser currentStudent, String[] transcript) {
        boolean transfer = false;
        boolean fastTrack = false;
        boolean thesis = false;
        boolean graduateCourse = false;
        String transfer_text = "";
        String semester = "";

        for (int i = 0; i < transcript.length; i++) {

            if (transcript[i].contains("Transfer Credits") && !transcript[i].contains("Fast Track")) {
                if (transcript[i + 1].contains("Master Program"))
                    graduateCourse = true;
                if (graduateCourse) {
                    transfer = true;
                    fastTrack = false;
                    transfer_text = "T";
                }
                continue;
            } else if (transcript[i].contains("Transfer Credit from UT Dallas Fast Track")) {
                graduateCourse = true;
                transfer = true;
                fastTrack = true;
                transfer_text = "F/T";
                currentStudent.setFastTrack(true);

                continue;
            } else if (transcript[i].contains("Beginning of Graduate Record")) {
                graduateCourse = true;
                transfer = false;
                fastTrack = false;
                transfer_text = "";
                continue;
            }
            if (!graduateCourse)
                continue;

            if (checkRegex(transcript[i], "([0-9]{4}.+Spring)|([0-9]{4}.+Summer)|([0-9]{4}.+Fall)")) {
                semester = transcript[i].substring(2, 4);
                if (transcript[i].contains("Spring"))
                    semester += "S";
                else if (transcript[i].contains("Summer"))
                    semester += "U";
                else if (transcript[i].contains("Fall"))
                    semester += "F";
                continue;
            }

            if (checkRegex(transcript[i], "(\\s[0-9]{4}\\s)|(\\s[0-9][vV]([0-9]{2})\\s)|(\\s[0-9]-{3}\\s)")) {
                if (transcript[i].contains("6V98") && transcript[i].contains("THESIS"))
                    currentStudent.setThesis(true);
                currentStudent.addCourse(transcript[i], semester, transfer_text);
            }
        }
    }

    private void setSemesterAdmit() {
        List<StudentCourse> courseList = currentStudent.getTranscriptList();
        String semester = courseList.get(0).getSemester();
        for (StudentCourse current : courseList) {
            if (isEarlier(semester, current.getSemester()))
                semester = current.getSemester();
        }
        currentStudent.setStartDate(semester);
    }

    private boolean isEarlier(String current, String target) {
        int currentYear = Integer.parseInt(current.substring(0, 2));
        int newYear = Integer.parseInt(target.substring(0, 2));

        if (newYear < currentYear)
            return true;

        int currentSem = classify(current.substring(2));
        int newSem = classify(target.substring(2));
        if (newSem < currentSem && newSem != 0)
            return true;

        return false;
    }

    private int classify(String str) {
        if (str.equals("S")) {
            return 1;
        } else if (str.equals("U")) {
            return 2;
        } else if (str.equals("F")) {
            return 3;
        }
        return 0;
    }

    public StudentParser getStudent() {
        return currentStudent;
    }
}
