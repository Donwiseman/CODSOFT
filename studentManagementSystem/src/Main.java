import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Path savedFile = Path.of("students.dat");
        System.out.println("   STUDENT MANAGEMENT SYSTEM ");
        System.out.println("Manage students information easily");
        List<StudentClass> students = readObject(savedFile);
        String mainMenu = """
                OPTIONS:
                Enter 0: Save and exit application
                Enter 1: Display student list
                Enter 2: Add new student
                Enter 3: Search Student""";
        while (true) {
            int choice = readNumberInput(mainMenu, 3);
            if (choice == 0) {
                if (!students.isEmpty()) {
                    writeObject(savedFile, students);
                }
                break;
            } else if (choice == 1) {
                displayStudents(students);
            } else if (choice == 2) {
                addStudent(students);
            } else if (choice == 3) {
                searchStudent(students);
            }
        }
    }

    public static void searchStudent(List<StudentClass> students) {
        List<StudentClass> searchResult = new ArrayList<>();
        String printStr = """
                Search students using:
                1 First Name
                2 Last Name
                3 Registration Number""";
        System.out.println(printStr);
        String inst = "Enter search option or 0 to exit";
        int input = readNumberInput(inst, 3);
        if (input == 0) return;
        else if (input == 1) {
            String firstName = readStringInput("First Name: ");
            for (StudentClass student : students) {
                if (firstName.equalsIgnoreCase(student.getFirstName())) searchResult.add(student);
            }
        } else if (input == 2) {
            String firstName = readStringInput("First Name: ");
            String lastName = readStringInput("Last Name: ");
            for (StudentClass student : students) {
                if ((firstName.equalsIgnoreCase(student.getFirstName())) &&
                        (lastName.equalsIgnoreCase(student.getLastName()))) {
                    searchResult.add(student);
                }
            }
        } else if (input == 3) {
            String RegNo = readStringInput("Registration Number: ");
            for (StudentClass student : students) {
                if (RegNo.equalsIgnoreCase(student.getRegistrationNumber())) searchResult.add(student);
            }
        }

        if (searchResult.isEmpty()) System.out.println("No Result Found");
        else {
            System.out.println("Search Results");
            for (int i = 0; i < searchResult.size(); i++) {
                System.out.printf("%d %s %s    Grade: %s%n", (i + 1), searchResult.get(i).getFirstName(),
                        searchResult.get(i).getLastName(), searchResult.get(i).getGrade());
            }
            input = readNumberInput("Enter number to view and edit student details or 0 to exit",
                    searchResult.size());
            if (input != 0) editStudent(searchResult.get(input - 1), false);
        }
    }

    public static void addStudent(List<StudentClass> students) {
        StudentClass newStudent = new StudentClass();
        editStudent(newStudent, true);
        students.add(newStudent);
    }

    public static void displayStudents(List<StudentClass> students) {
        if (students.isEmpty()) {
            System.out.println("No student information has been added");
            return;
        }

        int input;
        String inst = "Enter any given student number to view more details or 0 to close display";
        while (true) {
            for (int i = 0; i < students.size(); i++) {
                System.out.printf("Student %d: %s%n", (i + 1), students.get(i).toString());
            }
            input = readNumberInput(inst, students.size());
            if (input == 0) break;
            editStudent(students.get(input - 1), false);
        }
    }

    public static void editStudent(StudentClass indexStudent, boolean isNewStudent) {
        int input = 0;
        String inst = "Enter number of student detail to edit or 0 to exit";
        while (true) {
            if (isNewStudent) {
                input++;
            } else {
                String courses = String.join(", ", indexStudent.getCourses());
                String printStr = """
                STUDENT DETAILS
                1. First Name: %s
                2. Last Name: %s
                3. Registration Number: %s
                4. Date of Birth: %d/%d/%d
                5. Grade: %s
                6. Courses: %s""".formatted(indexStudent.getFirstName(), indexStudent.getLastName(),
                        indexStudent.getRegistrationNumber(), indexStudent.getDayOfBirth(),
                        indexStudent.getMonthOfBirth(), indexStudent.getYearOfBirth(), indexStudent.getGrade(), courses);
                System.out.println(printStr);
                input = readNumberInput(inst, 6);
            }

            if ((input == 0) || (input > 7)) break;
            else if (input == 1) {
                indexStudent.setFirstName(readStringInput("First name: "));
            } else if (input == 2) {
                indexStudent.setLastName(readStringInput("Last name: "));
            } else if (input == 3) {
                indexStudent.setRegistrationNumber(readStringInput("New Registration Number: "));
            } else if (input == 4) {
                int day = 0, month = 0, year = 0;
                for (int i = 0; i < 3; i++) {
                    if (i == 0) {
                        day = readNumberInput("Date of Birth in dd/mm/yyyy format\ndd: ", 31);
                    } else if (i == 1) {
                        month = readNumberInput("mm: ", 12);
                    } else {
                        year = readNumberInput("yyyy: ", 2024);
                    }
                }
                indexStudent.setDayOfBirth(day);
                indexStudent.setMonthOfBirth(month);
                indexStudent.setYearOfBirth(year);
            } else if (input == 5) {
                String grade = chooseGrade();
                if (!grade.isEmpty()) {
                    indexStudent.setGrade(grade);
                }
            } else if (input == 6) {
                editCourses(indexStudent.getCourses());
            }
        }
    }

    public static void editCourses(List<String> courses) {
        int input;
        String inst;
        while (true) {
            System.out.println("List of current courses");
            for (int i = 0; i < courses.size(); i++) {
                System.out.printf("%d. %s%n", (i + 1), courses.get(i));
            }
            inst = """
            Enter 0 to exit
            Enter 1 to add a course
            Enter 2 to delete a course""";
            input = readNumberInput(inst, 2);
            if (input == 0) break;
            else if(input == 1) {
                String newCourse = readStringInput("Add Course: ");
                courses.add(newCourse);
            } else if (input == 2) {
                String delInst = """
                        Enter number of a course to delete
                        Enter 0 to exit""";
                int choice;
                while (true) {
                    for (int i = 0; i < courses.size(); i++) {
                        System.out.printf("%d. %s%n", (i + 1), courses.get(i));
                    }
                    choice = readNumberInput(delInst, courses.size());
                    if (choice == 0) break;
                    courses.remove(choice - 1);
                }
            }
        }
    }

    public static String chooseGrade() {
        System.out.println("Select the Grade of the student");
        String grades = """
                1 A+
                2 A
                3 A-
                4 B+
                5 B
                6 B-
                7 C+
                8 C
                9 C-
                10 D+
                11 D
                12 D-
                13 E
                14 F
                15 P
                16 U""";
        System.out.println(grades);
        String inst = "Enter the number that corresponds to the desired grade or 0 to cancel";
        int input = readNumberInput(inst, 16);
        switch (input) {
            case 0 -> {
                return "";
            } case 1 -> {
                return "A+";
            } case 2 -> {
                return "A";
            } case 3 -> {
                return "A-";
            } case 4 -> {
                return "B+";
            } case 5 -> {
                return "B";
            } case 6 -> {
                return "B-";
            } case 7 -> {
                return "C+";
            } case 8 -> {
                return "C";
            } case 9 -> {
                return "C-";
            } case 10 -> {
                return "D+";
            } case 11 -> {
                return "D";
            } case 12 -> {
                return "D-";
            } case 13 -> {
                return "E";
            } case 14 -> {
                return "F";
            } case 15 -> {
                return "P";
            } case 16 -> {
                return "U";
            } default -> {
                return "";
            }
        }
    }

    public static int readNumberInput(String instructions, int maxChoice) {
        int input;
        while(true) {
            try {
                System.out.println(instructions);
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
                scanner.nextLine();
                if ((input < 0) || (input > maxChoice)) throw new InputMismatchException();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Option");
            }
        }
        return input;
    }

    public static String readStringInput(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    private static void writeObject(Path dataFile, List<StudentClass> students) {
        try (ObjectOutputStream objStream =
                     new ObjectOutputStream(Files.newOutputStream(dataFile))
        ) {
            objStream.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error Saving file...");
        }
    }

    private static List<StudentClass> readObject(Path dataFile) {
        try (ObjectInputStream objStream =
                     new ObjectInputStream(Files.newInputStream(dataFile))
        ) {
            return (List<StudentClass>) objStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved student data");
            return new ArrayList<>();
        }
    }
}