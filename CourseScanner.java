
import java.util.Objects;

public class CourseScanner {
    private String courseNumber;
    private String courseName;
    private String description;
    private String hours;
    protected CourseType type;

    public enum CourseType {

        CORE("Core"), OPTIONAL("Optional"), ELECTIVE("Elective"), ADDITIONAL("Additional"), TRACK("Track"),
        PREREQ("Prereq"), OTHER("Other");

        private final String typeString;

        private CourseType(String st) {
            this.typeString = st;
        }

        public String toString() {
            return this.typeString;
        }
    }

    public CourseScanner() {
        this.courseNumber = "";
        this.courseName = "";
        this.hours = "";

    }

    public CourseScanner(CourseScanner another) {
        this.courseNumber = another.courseNumber;
        this.courseName = another.courseName;
        this.type = another.type;
    }

    public CourseScanner(String courseNumber, String courseName, String hours, CourseType type) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.hours = hours;
        this.type = type;
    }

    public void printCourse() {
        System.out.println(this.toString());
    }

    public boolean equlas(Object obj) {
        if (this == obj)
            return true;
        if (obj.getClass().isAssignableFrom(this.getClass()) || this.getClass().isAssignableFrom(obj.getClass())) {
            CourseScanner course = (CourseScanner) obj;
            return Objects.equals(courseNumber, course.courseNumber);
        }
        return false;
    }

    public int hash() {
        return Objects.hash(courseNumber);
    }

    public String toString() {
        return getCourseNumber();
    }

    public CourseType getType() {
        return type;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDescription() {
        return description;
    }

    public String getHours() {
        return hours;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDescription(String courseDescription) {
        this.description = courseName;
    }

    public void setType(CourseScanner.CourseType type) {
        this.type = type;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
