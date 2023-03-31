import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StudentCourse extends CourseScanner {
    private String letterGrade = "";
    private double attempted = 0;
    private double earned = 0;
    private double points = 0;
    private String transfer = "";
    private boolean isTransfer = false;
    private boolean isWaived = false;
    private boolean fromTranscript = false;
    private String semester = "";

    public StudentCourse() {

    }

    public StudentCourse(StudentCourse another) {
        super((CourseScanner) another);
        this.letterGrade = another.letterGrade;
        this.semester = another.semester;
        this.attempted = another.attempted;
        this.earned = another.earned;
        this.points = another.earned;
        this.transfer = another.transfer;
        this.isTransfer = another.isTransfer;
        this.isWaived = another.isWaived;
        this.fromTranscript = another.fromTranscript;
    }

    public StudentCourse(CourseType type) {
        this.setType(type);
    }

    public StudentCourse(CourseScanner course, CourseType type) {
        setCourseNumber(course.getCourseNumber());
        setCourseName(course.getCourseName());
        setHours(course.getHours());
        this.setType(type);
    }

    public StudentCourse(String id, String name, String hours, CourseType type) {
        setCourseNumber(id);
        setCourseName(name);
        setHours(hours);
        this.type = type;
    }

    public StudentCourse(String input, String semester, String transfer) {
        this.semester = semester;
        this.transfer = transfer;
        this.fromTranscript = true;

        processInput(input);
    }

    public void setCourseVariables(StudentCourse newCourse) {
        this.setCourseNumber(newCourse.getCourseNumber());
        this.setHours(newCourse.getHours());
        this.semester = newCourse.getSemester();
        attempted = newCourse.getAttempted();
        points = newCourse.getPoints();
        earned = newCourse.getEarned();
        letterGrade = newCourse.getLetterGrade();
        isWaived = newCourse.isWaived();
        transfer = newCourse.getTransfer();
        fromTranscript = newCourse.isFromTranscript();
        type = newCourse.getType();
    }

    private void processInput(String input) {

        String[] tokens = input.split("\\s+");
        setCourseNumber(tokens[0] + " " + tokens[1]);

        if (!tokens[tokens.length - 2].matches("[-+]?[0-9]*\\.?[0-9]+")) {
            setCourseName(String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length - 4)));
            letterGrade = tokens[tokens.length - 2];
            tokens = Arrays.copyOfRange(tokens, tokens.length - 4, tokens.length);
        } else {
            setCourseName(String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length - 3)));
            letterGrade = "";
            tokens = Arrays.copyOfRange(tokens, tokens.length - 3, tokens.length);

        }

        attempted = Double.parseDouble(tokens[0]);
        setHours(String.valueOf((int) attempted));
        earned = Double.parseDouble(tokens[1]);
        points = Double.parseDouble(tokens[tokens.length - 1]);
    }

    public String toString() {
        String semesterOutput = (isTransfer) ? "X" + semester : semester + "X";

        return courseNumber + " " + courseName + " " + semesterOutput + " " + letterGrade;
    

    }
    public boolean equals(Object obj) {
        if (obj instanceof CourseScanner) {
            if (obj instanceof StudentCourse) {
                return super.equals(obj) && semester == ((StudentCourse) obj).getSemester();
            } else
                return super.equals(obj);
        }
        return false;
    }

    public int hash() {
        return Objects.hash(getCourseNumber(), semester);
    }

    public boolean isEmpty() {
        return getCourseNumber().isEmpty() && semester.isEmpty();
    }

    public String getSemester() {
        return semester;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public double getAttempted() {
        return attempted;
    }

    public double getEarned() {
        return earned;
    }

    public double getPoints() {
        return points;
    }

    public String getTransfer() {
        return transfer;
    }

    public boolean isFromTranscript() {
        return fromTranscript;
    }

    public boolean isWaived() {
        return isWaived;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public void setWaived(boolean waived) {
        this.isWaived = waived;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
    public void setTransfer(boolean transfer){isTransfer = transfer;}
}
