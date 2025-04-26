package fit5171.monash.edu;

import java.util.Arrays;
import java.util.List;

/**
 * Base Person class, provides basic person information
 */
public abstract class Person //abstract class Person
{
    private String firstName;
    private String lastName;
    private int age;
    private String gender;

    // Valid gender options
    private static final List<String> VALID_GENDERS = Arrays.asList(
            "Woman", "Man", "Non-Binary", "Prefer not to say", "Other");

    /**
     * Empty constructor, only for subclass inheritance
     */
    protected Person(){}

    /**
     * Create a person object with all necessary validations
     *
     * @param firstName First name
     * @param lastName Last name
     * @param age Age
     * @param gender Gender
     * @throws IllegalArgumentException If any parameter is invalid
     */
    public Person(String firstName, String lastName, int age, String gender){
        // Validate all fields are not empty
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }

        if (age <= 0) {
            throw new IllegalArgumentException("Age must be a positive number");
        }

        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be empty");
        }

        // Validate first and last names cannot start with a digit or symbol
        if (!isValidName(firstName)) {
            throw new IllegalArgumentException("First name cannot start with a digit or symbol");
        }

        if (!isValidName(lastName)) {
            throw new IllegalArgumentException("Last name cannot start with a digit or symbol");
        }

        // Validate gender option
        if (!isValidGender(gender)) {
            throw new IllegalArgumentException("Gender must be one of the following options: Woman, Man, Non-Binary, Prefer not to say, Other");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    /**
     * Validate if the name format is correct (can only start with a letter)
     *
     * @param name Name to validate
     * @return true if name format is correct, otherwise false
     */
    private boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        // Check if the first character is a letter
        char firstChar = name.charAt(0);
        return Character.isLetter(firstChar);
    }

    /**
     * Validate if the gender is a valid option
     *
     * @param gender Gender to validate
     * @return true if gender is a valid option, otherwise false
     */
    private boolean isValidGender(String gender) {
        return VALID_GENDERS.contains(gender);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be a positive number");
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

        if (!isValidGender(gender)) {
            throw new IllegalArgumentException("Gender must be one of the following options: Woman, Man, Non-Binary, Prefer not to say, Other");
        }

        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }

        if (!isValidName(firstName)) {
            throw new IllegalArgumentException("First name cannot start with a digit or symbol");
        }

        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }

        if (!isValidName(lastName)) {
            throw new IllegalArgumentException("Last name cannot start with a digit or symbol");
        }

        this.lastName = lastName;
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}