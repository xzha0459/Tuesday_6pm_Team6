package fit5171.monash.edu;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;



public abstract class Person {
    private static final Map<String, String> ALLOWED_GENDERS;
    static {
        Map<String, String> genders = new LinkedHashMap<>();
        genders.put("Woman", "Woman");
        genders.put("Man", "Man");
        genders.put("Non-Binary", "Non-Binary");
        genders.put("Prefer not to say", "Prefer not to say");
        genders.put("Other", "Other");
        ALLOWED_GENDERS = Collections.unmodifiableMap(genders);


    }

    private String firstName;
    private String lastName;
    private int age;
    private String gender;

    public Person() {}

    /**
     * Constructs a Person object and validates all fields.
     *
     * @param firstName person's first name (letters only)
     * @param lastName person's last name (letters only)
     * @param age person's age (must be > 0)
     * @param gender person's gender (must be in ALLOWED_GENDERS)
     */
    public Person(String firstName, String lastName, int age, String gender) {
        setFirstName(firstName);
        setLastName(lastName);
        setAge(age);
        setGender(gender);
    }

    public String getFirstName() {
        return firstName;
    }

    /**
     * Validates and sets the first name: non-null, letters only
     */
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (!firstName.matches("[A-Za-z]+")) {
            throw new IllegalArgumentException("First name must contain only letters");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Validates and sets the last name: non-null, letters only
     */
    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (!lastName.matches("[A-Za-z]+")) {
            throw new IllegalArgumentException("Last name must contain only letters");
        }
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    /**
     * Validates and sets the age: must be a positive integer
     */
    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be a positive integer");
        }
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    /**
     * Validates and sets the gender: must be one of the predefined options
     */
    public void setGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be empty");
        }
        if (!ALLOWED_GENDERS.containsKey(gender)) {
            throw new IllegalArgumentException("Gender must be one of: " + ALLOWED_GENDERS.keySet());
        }
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person that = (Person) o;
        return age == that.age &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(gender, that.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, gender);
    }
}