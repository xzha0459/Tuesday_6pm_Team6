package fit5171.monash.edu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Person class
 * Since Person is an abstract class, we use a concrete subclass for testing
 */
class PersonTest {

    // Create a concrete subclass of Person for testing
    static class TestPerson extends Person {
        public TestPerson(String firstName, String lastName, int age, String gender) {
            super(firstName, lastName, age, gender);
        }

        // Empty constructor for testing setter methods
        public TestPerson() {
            super();
        }
    }

    @Test
    @DisplayName("Test creating valid Person object")
    void testCreateValidPerson() {
        TestPerson person = new TestPerson("John", "Smith", 30, "Man");

        assertEquals("John", person.getFirstName());
        assertEquals("Smith", person.getLastName());
        assertEquals(30, person.getAge());
        assertEquals("Man", person.getGender());
    }

    // ========== Test constructor validation ==========

    @Test
    @DisplayName("Test empty/null fields in constructor - should throw exceptions")
    void testEmptyFields() {
        // Test empty first name
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new TestPerson("", "Smith", 30, "Man");
        });
        assertTrue(exception1.getMessage().contains("First name cannot be empty"));

        // Test null first name
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new TestPerson(null, "Smith", 30, "Man");
        });
        assertTrue(exception2.getMessage().contains("First name cannot be empty"));

        // Test empty last name
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            new TestPerson("John", "", 30, "Man");
        });
        assertTrue(exception3.getMessage().contains("Last name cannot be empty"));

        // Test null last name
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
            new TestPerson("John", null, 30, "Man");
        });
        assertTrue(exception4.getMessage().contains("Last name cannot be empty"));

        // Test empty gender
        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> {
            new TestPerson("John", "Smith", 30, "");
        });
        assertTrue(exception5.getMessage().contains("Gender cannot be empty"));

        // Test null gender
        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> {
            new TestPerson("John", "Smith", 30, null);
        });
        assertTrue(exception6.getMessage().contains("Gender cannot be empty"));
    }

    @Test
    @DisplayName("Test age validation in constructor")
    void testAgeValidation() {
        // Test zero age
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new TestPerson("John", "Smith", 0, "Man");
        });
        assertTrue(exception1.getMessage().contains("Age must be a positive number"));

        // Test negative age
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new TestPerson("John", "Smith", -25, "Man");
        });
        assertTrue(exception2.getMessage().contains("Age must be a positive number"));
    }

    // ========== Test gender validation ==========

    @ParameterizedTest
    @ValueSource(strings = {"Woman", "Man", "Non-Binary", "Prefer not to say", "Other"})
    @DisplayName("Test valid gender options")
    void testValidGenders(String gender) {
        TestPerson person = new TestPerson("John", "Smith", 30, gender);
        assertEquals(gender, person.getGender());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Male", "Female", "Unknown"})
    @DisplayName("Test invalid gender options")
    void testInvalidGenders(String gender) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new TestPerson("John", "Smith", 30, gender);
        });
        assertTrue(exception.getMessage().contains("Gender must be one of the following options"));
    }

    // ========== Test name format validation ==========

    @ParameterizedTest
    @CsvSource({
            "1John, Smith",
            "John, 2Smith",
            "#John, Smith",
            "John, $Smith"
    })
    @DisplayName("Test names starting with digit or symbol")
    void testNamesStartingWithDigitOrSymbol(String firstName, String lastName) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new TestPerson(firstName, lastName, 30, "Man");
        });
        assertTrue(exception.getMessage().contains("cannot start with a digit or symbol"));
    }

    // ========== Test setter methods ==========

    @Test
    @DisplayName("Test setter validation")
    void testSetterValidation() {
        TestPerson person = new TestPerson("John", "Smith", 30, "Man");

        // Test firstName setter
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> {
            person.setFirstName("");
        });
        assertTrue(ex1.getMessage().contains("First name cannot be empty"));

        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> {
            person.setFirstName("1John");
        });
        assertTrue(ex2.getMessage().contains("First name cannot start with a digit or symbol"));

        // Test lastName setter
        Exception ex3 = assertThrows(IllegalArgumentException.class, () -> {
            person.setLastName("");
        });
        assertTrue(ex3.getMessage().contains("Last name cannot be empty"));

        Exception ex4 = assertThrows(IllegalArgumentException.class, () -> {
            person.setLastName("#Smith");
        });
        assertTrue(ex4.getMessage().contains("Last name cannot start with a digit or symbol"));

        // Test age setter
        Exception ex5 = assertThrows(IllegalArgumentException.class, () -> {
            person.setAge(-5);
        });
        assertTrue(ex5.getMessage().contains("Age must be a positive number"));

        // Test gender setter
        Exception ex6 = assertThrows(IllegalArgumentException.class, () -> {
            person.setGender("");
        });
        assertTrue(ex6.getMessage().contains("Gender cannot be empty"));

        Exception ex7 = assertThrows(IllegalArgumentException.class, () -> {
            person.setGender("Invalid");
        });
        assertTrue(ex7.getMessage().contains("Gender must be one of the following options"));
    }

    @Test
    @DisplayName("Test toString method")
    void testToString() {
        TestPerson person = new TestPerson("John", "Smith", 30, "Man");
        String expected = "Person{firstName='John', lastName='Smith', age=30, gender='Man'}";
        assertEquals(expected, person.toString());
    }
}