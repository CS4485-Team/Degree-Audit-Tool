import javafx.util.Pair;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class StudentParser {

    // Information from Student
    private String name;
    private String ID;
    private String startDate;
    private String major;
    private String track;
    private String gradDate;
    private boolean fastTrack;
    private boolean thesis;

    Pair<Boolean, StudentCourse>[] thesisCourses = new Pair[3];

    private List<StudentCourse> courseList;
    private final List<StudentCourse> transcriptList;

    public StudentParser() {
        this.name = "";
        this.ID = "";
        this.startDate = "";
        this.major = "";
        fastTrack = false;
        thesis = false;

        courseList = new ArrayList<>();
        transcriptList = new ArrayList<>();
    }

    public StudentParser(String name, String ID, String startDate, String major) {

        this.name = name;
        this.ID = ID;
        this.startDate = startDate;
        this.major = major;

        courseList = new ArrayList<>();
        transcriptList = new ArrayList<>();
    }

    public void addCourse(StudentCourse newCourse) {
        courseList.add(newCourse);
    }

    public void addCourse(String line, String semester, String transfer) {
        StudentCourse newCourse = new StudentCourse(line, semester, transfer);
        transcriptList.add(newCourse);
    }

    public void fillFromTranscript() {
        HashMap<String, List<String>> filledCourses = new HashMap<>();
        for (StudentCourse trans : transcriptList) {
            if (!filledCourses.getOrDefault(trans.getCourseNumber(), new ArrayList<>()).contains(trans.getSemester())) {
                fillCourse(filledCourses, trans);
                filledCourses.compute(trans.getCourseNumber(), (id, sem) -> sem != null ? sem : new ArrayList<>())
                        .add(trans.getSemester());
            }
        }
    }

    private void fillCourse(HashMap<String, List<String>> filledCourses, StudentCourse trans) {
        boolean found = false;
        for (StudentCourse course : courseList) {
            if (course.getCourseNumber().equals(trans.getCourseNumber()) && !filledCourses
                    .getOrDefault(course.getCourseNumber(), new ArrayList<>()).contains(course.getSemester())) {
                course.setCourseVariables(trans);
                found = true;
                break;
            }
        }
        if (!found) {
            courseList.add(trans);
        }
    }

    public List<StudentCourse> getCourseType(CourseScanner.CourseType type) {
        List<StudentCourse> course = new ArrayList<>();
        for (StudentCourse current : courseList)
            if (current.type == type)
                course.add(current);
        return course;
    }

    public List<StudentCourse> getCleanCourseList() {
        List<StudentCourse> cleanCourses = new ArrayList<>();
        for (StudentCourse course : courseList)
            if (!course.isEmpty() && !course.getSemester().isEmpty())
                cleanCourses.add(course);
        return cleanCourses;
    }

    public void dropEmpty() {
        List<StudentCourse> emptyCourses = new ArrayList<>();
        for (StudentCourse course : courseList)
            if (course.isEmpty())
                emptyCourses.add(course);
        courseList.removeAll(emptyCourses);
    }

    public void printStudentInformation() {
        System.out.println(name);
        System.out.println(courseList);
        System.out.println(transcriptList);
        System.out.println(ID);
        System.out.println(startDate);
        System.out.println(major);
    }

    public List<StudentCourse> getThesisCourses() {
        List<StudentCourse> thesisList = new ArrayList<>();
        for (StudentCourse course : courseList) {
            if (course.getCourseNumber().contains("6V81") || course.getCourseNumber().contains("6V98")) {
                thesisList.add(course);
            }
        }
        return thesisList;
    }

    public List<StudentCourse> getCourseList() {
        return courseList;
    }

    public List<StudentCourse> getTranscriptList() {
        return transcriptList;
    }

    public String getStudentName() {
        return name;
    }

    public String getStudentId() {
        return ID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getCurrentMajor() {
        return major;
    }

    public String getGraduation() {
        return gradDate;
    }

    public boolean isFastTrack() {
        return fastTrack;
    }

    public boolean isThesis() {
        return thesis;
    }

    public String getSimpleName() {
        String names[] = name.split(" ");

        if (names.length < 2)
            return name;
        else
            return names[0] + names[names.length - 1];

    }

    public void setStudentName(String name) {
        this.name = name;
    }

    public void setStudentId(String ID) {
        this.ID = ID;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setCurrentMajor(String major) {
        this.major = major;
    }

    public void setGraduation(String gradDate) {
        this.gradDate = gradDate;
    }

    // Set thesis from transcript
    public void setThesis(boolean thesis) {
        this.thesis = thesis;
    }

    public void setFastTrack(boolean fastTrack) {
        this.fastTrack = fastTrack;
    }

}