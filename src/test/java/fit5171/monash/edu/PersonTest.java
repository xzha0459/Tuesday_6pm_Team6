package fit5171.monash.edu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Person class validating constructor and setter behaviors.
 */
class PersonTest {

    /**
     * A concrete subclass for testing the abstract Person class.
     */
    private static class TestPerson extends Person {
        public TestPerson(String firstName, String lastName, int age, String gender) {
            super(firstName, lastName, age, gender);
        }
    }

    @Nested
    @DisplayName("Constructor and Getter Tests")
    class ConstructorAndGetters {

        @Test
        @DisplayName("Valid constructor should set all fields correctly")
        void validConstructorSetsFields() {
            TestPerson person = new TestPerson("Alice", "Smith", 25, "Woman");
            assertEquals("Alice", person.getFirstName());
            assertEquals("Smith", person.getLastName());
            assertEquals(25, person.getAge());
            assertEquals("Woman", person.getGender());
        }

        @Test
        @DisplayName("toString should include all fields")
        void toStringIncludesFields() {
            TestPerson person = new TestPerson("Bob", "Brown", 40, "Man");
            String str = person.toString();
            assertTrue(str.contains("firstName='Bob'"));
            assertTrue(str.contains("lastName='Brown'"));
            assertTrue(str.contains("age=40"));
            assertTrue(str.contains("gender='Man'"));
        }
    }

    @Nested
    @DisplayName("Validation Tests for First and Last Name")
    class NameValidation {

        @Test
        @DisplayName("Null first name should throw exception")
        void nullFirstNameThrows() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new TestPerson(null, "Doe", 30, "Other"));
            assertEquals("First name cannot be empty", ex.getMessage());
        }

        @Test
        @DisplayName("First name with non-letter characters should throw")
        void invalidFirstNameThrows() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new TestPerson("123John", "Doe", 30, "Other"));
            assertEquals("First name must contain only letters", ex.getMessage());
        }

        @Test
        @DisplayName("Null last name should throw exception")
        void nullLastNameThrows() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new TestPerson("John", null, 30, "Other"));
            assertEquals("Last name cannot be empty", ex.getMessage());
        }

        @Test
        @DisplayName("Last name with non-letter characters should throw")
        void invalidLastNameThrows() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new TestPerson("John", "Doe!", 30, "Other"));
            assertEquals("Last name must contain only letters", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("Age Validation Tests")
    class AgeValidation {

        @Test
        @DisplayName("Zero age should throw exception")
        void zeroAgeThrows() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new TestPerson("John", "Doe", 0, "Non-Binary"));
            assertEquals("Age must be a positive integer", ex.getMessage());
        }

        @Test
        @DisplayName("Negative age should throw exception")
        void negativeAgeThrows() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new TestPerson("John", "Doe", -5, "Non-Binary"));
            assertEquals("Age must be a positive integer", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("Gender Validation Tests")
    class GenderValidation {

        @Test
        @DisplayName("Null gender should throw exception")
        void nullGenderThrows() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new TestPerson("John", "Doe", 30, null));
            assertEquals("Gender cannot be empty", ex.getMessage());
        }

        @Test
        @DisplayName("Blank gender should throw exception")
        void blankGenderThrows() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new TestPerson("John", "Doe", 30, "  "));
            assertEquals("Gender cannot be empty", ex.getMessage());
        }

        @Test
        @DisplayName("Unsupported gender should throw exception")
        void invalidGenderThrows() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> new TestPerson("John", "Doe", 30, "Alien"));
            assertTrue(ex.getMessage().startsWith("Gender must be one of:"));
        }
    }
}