package fit5171.monash.edu;

import java.util.Arrays;
import java.util.List;

public abstract class Person //abstract class Person
{
    private String firstName;
    private String secondName;
    private int age;
    private String gender;

    private static final List<String> VALID_GENDERS = Arrays.asList(
            "Woman", "Man", "Non-Binary", "Prefer not to say", "Other"
    );

    public Person(){}

    public Person(String firstName, String secondName, int age, String gender){
        // Validate all fields
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }

        if (secondName == null || secondName.trim().isEmpty()) {
            throw new IllegalArgumentException("Second name cannot be empty");
        }

        if (age <= 0) {
            throw new IllegalArgumentException("Age must be positive");
        }

        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be empty");
        }

        // Validate if gender is a valid option
        if (!VALID_GENDERS.contains(gender)) {
            throw new IllegalArgumentException("Invalid gender. Valid options are: "
                    + String.join(", ", VALID_GENDERS));
        }

        // Validate that names cannot start with a digit or symbol
        if (!firstName.matches("^[a-zA-Z].*")) {
            throw new IllegalArgumentException("First name must start with a letter");
        }

        if (!secondName.matches("^[a-zA-Z].*")) {
            throw new IllegalArgumentException("Second name must start with a letter");
        }

        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be positive");
        }
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be empty");
        }

        // Validate if gender is a valid option
        if (!VALID_GENDERS.contains(gender)) {
            throw new IllegalArgumentException("Invalid gender. Valid options are: "
                    + String.join(", ", VALID_GENDERS));
        }

        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }

        // Validate that name cannot start with a digit or symbol
        if (!firstName.matches("^[a-zA-Z].*")) {
            throw new IllegalArgumentException("First name must start with a letter");
        }

        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        if (secondName == null || secondName.trim().isEmpty()) {
            throw new IllegalArgumentException("Second name cannot be empty");
        }

        // Validate that name cannot start with a digit or symbol
        if (!secondName.matches("^[a-zA-Z].*")) {
            throw new IllegalArgumentException("Second name must start with a letter");
        }

        this.secondName = secondName;
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}