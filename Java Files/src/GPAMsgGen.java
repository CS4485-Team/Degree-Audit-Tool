
public class GPAMsgGen {
	
	public static final double MIN_CORE_GPA = 3.19;
	public static final double MIN_ELEC_GPA = 3.00;
	public static final double MIN_CUML_GPA = 3.00;
	
	
// Main class used as a test class in case errors arise.
//	public static void main(String[] args) {
//		String[] classes = {"CS1000", "CS1001", "CS1002", "CS1003", "CS1004"};
//		String[] grades = {"A", "A", "A", "", ""};
//		System.out.println(msgGen(classes, grades, MIN_CORE_GPA));
//	}
	
	public static double gpaGen(String[] classes, String[] grades) {
		int numClassesFinished = 0;
		double currPts = 0, currGPA = 0;
		
		// We must assume that classes and grades have the same length.
		for (int i = 0; i < classes.length; i++) {
			if (grades[i].trim().equals("")) {
				continue;
			} else if (grades[i].trim().equals("I") || grades[i].trim().equals("P")) {
				continue;
			} else {
				numClassesFinished++;
				currPts += (gradeToGPA(grades[i].trim()));
			}
		}
		
		currGPA = currPts / ((double) numClassesFinished);
		
		return currGPA;
	}
	
	public static String msgGen(String[] classes, String[] grades, double minGPA) {
		
		int numClassesFinished = 0;
		int numClassesLeft = 0;
		double currPts = 0, /*currGPA = 0,*/ reqRemMinGPA = 0;
		
		// We must assume that classes and grades have the same length.
		for (int i = 0; i < classes.length; i++) {
			if (grades[i].trim().equals("")) {
				numClassesLeft++;
			} else if (grades[i].trim().equals("I") || grades[i].trim().equals("P")) {
				continue;
			} else {
				numClassesFinished++;
				currPts += (gradeToGPA(grades[i].trim()));
			}
		}
		
		// Current GPA is not actually needed for any of these calculations.
		/*currGPA = currPts / ((double) numClassesFinished);*/
		
		reqRemMinGPA = calcReqRemMinGPA(currPts, minGPA, numClassesFinished, numClassesLeft);
		
		if (numClassesLeft == 0) {
			
			return "      -The student has completed all courses in this category.";
		
		} else if (reqRemMinGPA <= 2) {
			String toReturn = "      -The student must pass: ";
			for (int i = 0; i < classes.length; i++) {
				if (grades[i].trim().equals("")) {
					toReturn += classes[i];
					toReturn += " ";
				}
			}
			
			return toReturn;
			
		} else if (reqRemMinGPA > 2 && numClassesLeft > 1) {
			String toReturn = "      -The student must achieve a GPA of at least ";
			toReturn += String.format("%.3f", reqRemMinGPA);
			toReturn += " in: ";
			for (int i = 0; i < classes.length; i++) {
				if (grades[i].trim().equals("")) {
					toReturn += classes[i];
					toReturn += " ";
				}
			}
			
			return toReturn;
			
		} else if (numClassesLeft == 1)  {
			String toReturn = "      -The student must achieve the grade ";
			toReturn += minGradeFromGPA(reqRemMinGPA);
			toReturn += " in: ";			
			for (int i = 0; i < classes.length; i++) {
				if (grades[i].trim().equals("")) {
					toReturn += classes[i];
					toReturn += " ";
				}
			}
			
			return toReturn;
		}
		
		return "      The student has met all requirements.";
	}
	
	private static double gradeToGPA(String grade) {
		if (grade.equals("A+") || grade.equals("A")) return 4;
		if (grade.equals("A-")) return 3.67;
		if (grade.equals("B+")) return 3.33;
		if (grade.equals("B")) return 3;
		if (grade.equals("B-")) return 2.67;
		if (grade.equals("C+")) return 2.33;
		if (grade.equals("C")) return 2;
		if (grade.equals("F")) return 0;
		return 0;
	}
	
	// Handles the case in which only one class remains.
	private static String minGradeFromGPA(double gpa) {
		if (gpa > 4)
			return "higher than an A";
		else if (gpa > 3.67) 
			return "A or higher";
		else if (gpa > 3.33)
			return "A- or higher";
		else if (gpa > 3)
			return "B+ or higher";
		else if (gpa > 2.67)
			return "B or higher";
		else if (gpa > 2.33)
			return "B- or higher";
		else if (gpa > 2.00)
			return "C+ or higher";
		else
			return "Error somewhere.";
	}
	
	// Func name stands for Calculate required minimum GPA for remaining classes.
	private static double calcReqRemMinGPA(double currPts, double minGPA, int numClassesFinished, int numClassesLeft) {
		return (minGPA * ((double)(numClassesFinished + numClassesLeft)) - currPts) / numClassesLeft;
	}
}
