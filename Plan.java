public class Plan {
        private int numOptional = 0;
        private concentration currentPlan;
        private CourseScanner[] requiredCore = new CourseScanner[] {};
        private CourseScanner[] optionalCore = new CourseScanner[] {};
        private CourseScanner[] admissionPrerequisites = new CourseScanner[] {};
        private CourseScanner[] trackPrerequisites = new CourseScanner[] {};
        private String[] excludedElectives = new String[] {};
        private CourseScanner[] additional = new CourseScanner[] {};
        private CourseScanner[] electives = new CourseScanner[] {};
        //Track types
        public enum concentration {
                TRADITIONAL,
                NETWORKS,
                INTEL,
                INTERACTIVE,
                SYSTEMS,
                DATA,
                CYBER,
                SOFTWARE;
        }

        public Plan(String degreePlan) {
                setConcentration(degreePlan);
        }

        public void setDegreePlan(String degreePlan) {
                setConcentration(degreePlan);
        }
      
        private void setConcentration(String degreePlan) {
                switch (degreePlan) {
                        case "Traditional":
                                currentPlan = concentration.TRADITIONAL;
                                traditionalComputerScience();
                                break;
                        case "Networks and Telecommunications":
                                currentPlan = concentration.NETWORKS;
                                networksAndTelecommunication();
                                break;
                        case "Intelligent Systems":
                                currentPlan = concentration.INTEL;
                                intelligentSystems();
                                break;
                        case "Interactive Computing":
                                currentPlan = concentration.INTERACTIVE;
                                interactiveComputing();
                                break;
                        case "Systems":
                                currentPlan = concentration.SYSTEMS;
                                systemsPlan();
                                break;
                        case "Data Science":
                                currentPlan = concentration.DATA;
                                dataScience();
                                break;
                        case "Cyber Security":
                                currentPlan = concentration.CYBER;
                                cyberSecurity();
                                break;
                        case "Software Engineering":
                                currentPlan = concentration.SOFTWARE;
                                softwareEngineering();
                                break;
                }
        }
    //If the transcript is for traditionalComputerScience then match the course numbers obtained and saved the course name.
        private void traditionalComputerScience() {
                numOptional = 2;
                excludedElectives = new String[] {};
                requiredCore = new CourseScanner[] {
                                new CourseScanner("CS 6363", "Design and Analysis of Computer Algorithms", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6378", "Advanced Operating Systems", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6390", "Advanced Computer Networks", "3",
                                                CourseScanner.CourseType.CORE)
                };
                optionalCore = new CourseScanner[] {
                                new CourseScanner("CS 6353", "Compiler Construction", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6360", "Database Design", "3", CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6371", "Advanced Programming Languages", "3",
                                                CourseScanner.CourseType.OPTIONAL)
                };
                trackPrerequisites = new CourseScanner[] {};
                electives = new CourseScanner[] {
                                new CourseScanner("CS 6359", "Object-Orient ANS & DES", "3",
                                                CourseScanner.CourseType.ELECTIVE),
                                new CourseScanner("CS 6364", "Artificial Intelligence", "3",
                                                CourseScanner.CourseType.ELECTIVE),
                                new CourseScanner("CS 6385", "ALG ASP/Telecom NT", "3",
                                                CourseScanner.CourseType.ELECTIVE),
                                new CourseScanner("CS 6314", "Web Programming Languages", "3",
                                                CourseScanner.CourseType.ELECTIVE),
                                new CourseScanner("CS 6350", "Big Data Management And Analysis", "3",
                                                CourseScanner.CourseType.ELECTIVE)
                };
                additional = new CourseScanner[] {
                                new CourseScanner("CS 5333", "Discrete Structures", "3",
                                                CourseScanner.CourseType.ADDITIONAL)
                };
                admissionPrerequisites = new CourseScanner[] {
                                new CourseScanner("CS 5303", "Computer Science I", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5330", "Computer Science II", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5333", "Discrete Structures", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5343", "Algorithm Analysis & Data Structures", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5348", "Operating System Concepts", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5349", "Automata Theory", "3", CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5390", "Computer Networks", "3", CourseScanner.CourseType.PREREQ)
                };
        }

        private void networksAndTelecommunication() {
                requiredCore = new CourseScanner[] {
                                new CourseScanner("CS 6352", "Perf. of Computer Systems and Networks", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6363", "Design and Analysis of Computer Algorithms", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6378", "Advanced Operating Systems", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6385", "Algorithmic Aspects of Telecomm. Networks", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6390", "Advanced Computer Networks", "3",
                                                CourseScanner.CourseType.CORE)
                };
                admissionPrerequisites = new CourseScanner[] {
                                new CourseScanner("CS 5303", "Computer Science I", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5330", "Computer Science II", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5333", "Discrete Structures", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5343", "Algorithm Analysis & Data Structures", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5348", "Operating System Concepts", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5390", "Computer Networks", "3", CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 3341", "Probability & Statistics in CS", "3",
                                                CourseScanner.CourseType.PREREQ)
                };
        }

        private void intelligentSystems() {
                numOptional = 1;
                requiredCore = new CourseScanner[] {
                                new CourseScanner("CS 6320", "Natural Language Processing", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6363", "Design and Analysis of Computer Algorithms", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6364", "Artificial Intelligence", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6375", "Machine Learning", "3", CourseScanner.CourseType.CORE)
                };
                optionalCore = new CourseScanner[] {
                                new CourseScanner("CS 6360", "Database Design", "3", CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6378", "Advanced Operating Systems", "3",
                                                CourseScanner.CourseType.OPTIONAL)
                };
                trackPrerequisites = new CourseScanner[] {
                                new CourseScanner("CS 5303", "Computer Science I", "3", CourseScanner.CourseType.TRACK),
                                new CourseScanner("CS 5330", "Computer Science II", "3",
                                                CourseScanner.CourseType.TRACK),
                                new CourseScanner("CS 5333", "Discrete Structures", "3",
                                                CourseScanner.CourseType.TRACK),
                                new CourseScanner("CS 5343", "Algorithm Analysis & Data Structures", "3",
                                                CourseScanner.CourseType.TRACK),
                                new CourseScanner("CS 5348", "Operating System Concepts", "3",
                                                CourseScanner.CourseType.TRACK)
                };
                admissionPrerequisites = new CourseScanner[] {
                                new CourseScanner("CS 3341", "Probability & Statistics in CS", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5349", "Automata Theory", "3", CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5354", "Software Engineering", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5390", "Computer Networks", "3", CourseScanner.CourseType.PREREQ)
                };
        }

        private void interactiveComputing() {
                numOptional = 3;
                requiredCore = new CourseScanner[] {
                                new CourseScanner("CS 6326", "Human Computer Interaction", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6363", "Design and Analysis of Computer Algorithms", "3",
                                                CourseScanner.CourseType.CORE)
                };
                optionalCore = new CourseScanner[] {
                                new CourseScanner("CS 6323", "Computer Animation and Gaming", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6328", "Modeling and Simulation", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6331", "Multimedia Systems", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6334", "Virtual Reality", "3", CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6366", "Computer Graphics", "3",
                                                CourseScanner.CourseType.OPTIONAL)
                };
                admissionPrerequisites = new CourseScanner[] {
                                new CourseScanner("CS 5303", "Computer Science I", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5330", "Computer Science II", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5333", "Discrete Structures", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5343", "Algorithm Analysis & Data Structures", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5348", "Operating System Concepts", "3",
                                                CourseScanner.CourseType.PREREQ)
                };
        }

        private void systemsPlan() {
                numOptional = 1;
                requiredCore = new CourseScanner[] {
                                new CourseScanner("CS 6304", "Computer Architecture", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6363", "Design and Analysis of Computer Algorithms", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6378", "Advanced Operating Systems", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6396", "Real-Time Systems", "3", CourseScanner.CourseType.CORE)
                };
                optionalCore = new CourseScanner[] {
                                new CourseScanner("CS 6349", "Network Security", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6376", "Parallel Processing", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6380", "Distributed Computing", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6397", "Synthesis and Opt. of High-Perf. Systems", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6399", "Parallel Architectures and Systems", "3",
                                                CourseScanner.CourseType.OPTIONAL)
                };
                admissionPrerequisites = new CourseScanner[] {
                                new CourseScanner("CS 5303", "Computer Science I", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5330", "Computer Science II", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5333", "Discrete Structures", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5343", "Algorithm Analysis & Data Structures", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5348", "Operating System Concepts", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5390", "Computer Networks", "3", CourseScanner.CourseType.PREREQ)
                };
        }

        private void dataScience() {
                numOptional = 1;
                requiredCore = new CourseScanner[] {
                                new CourseScanner("CS 6313", "Statistical Methods for Data Sciences", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6350", "Big Data Management and Analytics", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6363", "Design and Analysis of Computer Algorithms", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6375", "Machine Learning", "3", CourseScanner.CourseType.CORE)
                };
                optionalCore = new CourseScanner[] {
                                new CourseScanner("CS 6301", "Social Network Analytics", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6320", "Natural Language Processing", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6327", "Video Analytics", "3", CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6347", "Statistics for Machine Learning", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6360", "Database Design", "3", CourseScanner.CourseType.OPTIONAL)
                };
                admissionPrerequisites = new CourseScanner[] {
                                new CourseScanner("CS 5303", "Computer Science I", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5330", "Computer Science II", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5333", "Discrete Structures", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5343", "Algorithm Analysis & Data Structures", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5348", "Operating System Concepts", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 3341", "Probability & Statistics in CS", "3",
                                                CourseScanner.CourseType.PREREQ)
                };
        }

        private void cyberSecurity() {
                numOptional = 2;
                requiredCore = new CourseScanner[] {
                                new CourseScanner("CS 6324", "Information Security", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6363", "Design and Analysis of Computer Algorithms", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("CS 6378", "Advanced Operating Systems", "3",
                                                CourseScanner.CourseType.CORE)
                };
                optionalCore = new CourseScanner[] {
                                new CourseScanner("CS 6332", "System Security & Malicious Code Analysis", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6348", "Data and Applications Security", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6349", "Network Security", "3",
                                                CourseScanner.CourseType.OPTIONAL),
                                new CourseScanner("CS 6377", "Introduction To Cryptography", "3",
                                                CourseScanner.CourseType.OPTIONAL)
                };
                trackPrerequisites = new CourseScanner[] {
                                new CourseScanner("CS 5303", "Computer Science I", "3", CourseScanner.CourseType.TRACK),
                                new CourseScanner("CS 5330", "Computer Science II", "3",
                                                CourseScanner.CourseType.TRACK),
                                new CourseScanner("CS 5333", "Discrete Structures", "3",
                                                CourseScanner.CourseType.TRACK),
                                new CourseScanner("CS 5343", "Algorithm Analysis & Data Structures", "3",
                                                CourseScanner.CourseType.TRACK),
                                new CourseScanner("CS 5348", "Operating System Concepts", "3",
                                                CourseScanner.CourseType.TRACK),
                                new CourseScanner("CS 5390", "Computer Networks", "3", CourseScanner.CourseType.TRACK)
                };
                admissionPrerequisites = new CourseScanner[] {
                                new CourseScanner("CS 5349", "Automata Theory", "3", CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5354", "Software Engineering", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 3341", "Probability & Statistics in CS", "3",
                                                CourseScanner.CourseType.PREREQ)
                };
        }

        private void softwareEngineering() {
                excludedElectives = new String[] { "CS 6359" };
                requiredCore = new CourseScanner[] {
                                new CourseScanner("SE 6329", "Perf. of Computer Systems and Networks", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("SE 6361", "Advanced Requirements Engineering", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("SE 6362", "Adv Software Architecture & Design", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("SE 6367", "Software Testing, Validation, Verification", "3",
                                                CourseScanner.CourseType.CORE),
                                new CourseScanner("SE 6387", "Advanced Software Engineering Project", "3",
                                                CourseScanner.CourseType.CORE)
                };
                admissionPrerequisites = new CourseScanner[] {
                                new CourseScanner("CS 5303", "Computer Science I", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5330", "Computer Science II", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5333", "Discrete Structures", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5343", "Algorithm Analysis & Data Structures", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5348", "Operating System Concepts", "3",
                                                CourseScanner.CourseType.PREREQ),
                                new CourseScanner("CS 5354", "Software Engineering", "3",
                                                CourseScanner.CourseType.PREREQ),
                };
        }
      //Checks if the class is a core class
        public boolean isCore(CourseScanner courseNumber) {
                for (CourseScanner currentClass : requiredCore) {
                        if (courseNumber.equals(currentClass))
                                return true;
                }
                return false;
        }
  // Checks if the class is a optional core class
        public boolean isOpt(CourseScanner courseNumber) {
                for (CourseScanner currentClass : optionalCore) {
                        if (courseNumber.equals(currentClass))
                                return true;
                }
                return false;
        }
//Checks if the class is a track preequisite
        public boolean isTrack(CourseScanner courseNumber) {
                for (CourseScanner currentClass : trackPrerequisites) {
                        if (courseNumber.equals(currentClass))
                                return true;
                }
                return false;
        }
//Checks if the class is a admission prereq
        public boolean isPre(CourseScanner courseNumber) {
                for (CourseScanner currentClass : admissionPrerequisites) {
                        if (courseNumber.equals(currentClass))
                                return true;
                }
                return false;
        }

        public int getNumOptional() {
                return numOptional;
        }

        public CourseScanner[] getCore() {
                return requiredCore;
        }

        public CourseScanner[] getOptionalCore() {
                return optionalCore;
        }

        public CourseScanner[] getAdmissionPrerequisites() {
                return admissionPrerequisites;
        }

        public CourseScanner[] getTrackPrerequisites() {
                return trackPrerequisites;
        }

}
