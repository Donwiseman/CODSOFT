import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentClass implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;
    private String firstName;
    private String lastName;
    private String registrationNumber;
    private String grade;
    private int yearOfBirth;
    private int monthOfBirth;
    private int dayOfBirth;
    private final List<String> courses;

    public StudentClass() {
        this.courses = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getMonthOfBirth() {
        return monthOfBirth;
    }

    public void setMonthOfBirth(int monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(int dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public List<String> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return "%s %s    %s    %s".formatted(lastName, firstName, registrationNumber, grade);
    }
}
