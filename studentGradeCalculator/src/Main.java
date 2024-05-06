package studentGradeCalculator.src;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("WELCOME TO THE STUDENT GRADE CALCULATOR");
        System.out.println("This helps you calculate the grade of a student based on the average marks of " +
                "subjects inputted.");
        String menu = """
                OPTIONS:
                Press 0 to exit
                Press 1 to add subjects mark
                press 2 to display results""";
        int input;
        int[] subjectAndMarks;
        int totalMark = 0;
        int totalSubject = 0;
        while (true) {
            input = readNumberInput(menu);
            if (input == 0) break;
            if (input == 1) {
                subjectAndMarks = addSubject(totalMark, totalSubject);
                totalMark = subjectAndMarks[0];
                totalSubject = subjectAndMarks[1];
                displayGrade(totalMark, totalSubject);
            } else {
                displayGrade(totalMark, totalSubject);
            }
        }
    }

    public static int readNumberInput(String instructions) {
        int input;
        while(true) {
            try {
                System.out.println(instructions);
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
                scanner.nextLine();
                if ((input < 0) || (input > 2)) throw new InputMismatchException();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Option");
            }
        }
        return input;
    }

    public static int[] addSubject(int totalMark, int totalSubject) {
        String instruction = """
                Input mark for each subject from 0 and 100
                Type 'save' to save values and display grade
                Type 'clear' to clear all previous marks""";
        System.out.println(instruction);
        Scanner scanner = new Scanner(System.in);
        String input;
        int mark;
        while (true) {
            System.out.printf("Subject %d%n Mark: ", (totalSubject + 1));
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("save")) {
                break;
            } else if (input.equalsIgnoreCase("clear")) {
                totalMark = 0;
                totalSubject = 0;
                System.out.println("All previous scores has been erased");
                System.out.println(instruction);
            } else {
                try {
                    mark = Integer.parseInt(input);
                    if ((mark < 0) || (mark > 100)) throw new NumberFormatException();
                    totalMark += mark;
                    totalSubject++;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input");
                }
            }
        }
        return new int[]{totalMark, totalSubject};
    }

    public static void displayGrade(int totalMark, int totalSubject) {
        int avg = (int) Math.round((double) totalMark / totalSubject);
        String result = """
                RESULT SUMMARY
                Total Subject Taken: %d
                Total Marks Obtained: %d
                Average Mark: %d
                Grade: %s""".formatted(totalSubject, totalMark, avg, getGrade(avg));
        System.out.println(result);
    }

    public static String getGrade(int score) {
        if (score >= 97) return "O";
        else if (score >= 87) return "A+";
        else if (score >= 77) return "A";
        else if(score >= 67) return "B+";
        else if(score >= 57) return "B";
        else if(score >= 47) return "C";
        else if(score >= 37) return "P";
        else  return "F";
    }
}
