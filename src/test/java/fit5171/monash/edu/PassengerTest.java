package fit5171.monash.edu;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class PassengerTest {

    private Passenger passenger;

    @Mock
    private Person mockPerson;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a valid passenger object
        passenger = new Passenger(
                "John", "Doe", 20, "Man",
                "john.doe@example.com", "0412345678",
                "A1234567", "1234-5678-9012-3456", 123
        );
    }

    @Test
    void testDefaultConstructor() {
        Passenger emptyPassenger = new Passenger();
        assertNotNull(emptyPassenger, "Default constructor should create non-null object");
    }

    @Test
    void testParameterizedConstructor() {
        assertEquals("John", passenger.getFirstName());
        assertEquals("Doe", passenger.getSecondName());
        assertEquals(20, passenger.getAge());
        assertEquals("Man", passenger.getGender());
        assertEquals("john.doe@example.com", passenger.getEmail());
        assertEquals("+61412345678", passenger.getPhoneNumber()); // Note: should be standardized
        assertEquals("A1234567", passenger.getPassport());
        assertEquals("1234-5678-9012-3456", passenger.getCardNumber());
        assertEquals(123, passenger.getSecurityCode());
    }

    @Test
    void testMissingFields() {
        // test the email is missing
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 20, "Man",
                    null, "0412345678", "A1234567",
                    "1234-5678-9012-3456", 123);
        });
        assertTrue(exception.getMessage().contains("mandatory"));

        // test the phone number is missing
        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 20, "Man",
                    "john.doe@example.com", null, "A1234567",
                    "1234-5678-9012-3456", 123);
        });
        assertTrue(exception.getMessage().contains("mandatory"));

        // test the passport is missing
        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 20, "Man",
                    "john.doe@example.com", "0412345678", null,
                    "1234-5678-9012-3456", 123);
        });
        assertTrue(exception.getMessage().contains("mandatory"));
    }

    @Test
    void testEmailValidation() {
        // Test valid email
        passenger.setEmail("valid.email@domain.com");
        assertEquals("valid.email@domain.com", passenger.getEmail());

        // Test invalid email
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            passenger.setEmail("invalid-email");
        });
        assertTrue(exception.getMessage().contains("Invalid email format"));

        exception = assertThrows(IllegalArgumentException.class, () -> {
            passenger.setEmail("invalid@");
        });
        assertTrue(exception.getMessage().contains("Invalid email format"));
    }

    @Test
    void testPhoneNumberValidation() {
        // Test the Australian number format
        passenger.setPhoneNumber("0412345678"); // Aus format
        assertEquals("+61412345678", passenger.getPhoneNumber());

        passenger.setPhoneNumber("+61412345678"); // country code included
        assertEquals("+61412345678", passenger.getPhoneNumber());

        // Test the China number format
        passenger.setPhoneNumber("13912345678"); // China format
        assertEquals("+8613912345678", passenger.getPhoneNumber());

        passenger.setPhoneNumber("+8613912345678"); // country code included
        assertEquals("+8613912345678", passenger.getPhoneNumber());

        // Test the US number format
        passenger.setPhoneNumber("212-555-1234"); // US format
        assertEquals("+12125551234", passenger.getPhoneNumber());

        passenger.setPhoneNumber("+1212-555-1234"); // country code included
        assertEquals("+12125551234", passenger.getPhoneNumber());

        // Test invalid number format
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            passenger.setPhoneNumber("123"); // too short
        });
        assertTrue(exception.getMessage().contains("Invalid phone number format"));

        exception = assertThrows(IllegalArgumentException.class, () -> {
            passenger.setPhoneNumber("abcdefghij"); // non-numeric
        });
        assertTrue(exception.getMessage().contains("Invalid phone number format"));
    }

    @Test
    void testPassportValidation() {
        // Test the Australian passport
        passenger.setPassport("A1234567"); // Aus format
        assertEquals("A1234567", passenger.getPassport());

        // Test the China passport
        passenger.setPassport("G12345678"); // China format
        assertEquals("G12345678", passenger.getPassport());

        // Test the US passport
        passenger.setPassport("123456789"); // US format
        assertEquals("123456789", passenger.getPassport());

        // Test invalid passport
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            passenger.setPassport("12345"); // too short
        });
        assertTrue(exception.getMessage().contains("Invalid passport format"));

        exception = assertThrows(IllegalArgumentException.class, () -> {
            passenger.setPassport("ABCDEFGHI"); // doesnt match any format
        });
        assertTrue(exception.getMessage().contains("Invalid passport format"));
    }

    @Test
    void testEmailGetterAndSetter() {
        String newEmail = "new.email@example.com";
        passenger.setEmail(newEmail);
        assertEquals(newEmail, passenger.getEmail(), "Email should be updated");
    }

    @Test
    void testPhoneNumberGetterAndSetter() {
        String newPhoneNumber = "+61487654321";
        passenger.setPhoneNumber(newPhoneNumber);
        assertEquals(newPhoneNumber, passenger.getPhoneNumber(), "Phone number should be updated");
    }

    @Test
    void testCardNumberGetterAndSetter() {
        String newCardNumber = "9876-5432-1098-7654";
        passenger.setCardNumber(newCardNumber);
        assertEquals(newCardNumber, passenger.getCardNumber(), "Card number should be updated");
    }

    @Test
    void testSecurityCodeGetterAndSetter() {
        int newSecurityCode = 456;
        passenger.setSecurityCode(newSecurityCode);
        assertEquals(newSecurityCode, passenger.getSecurityCode(), "Security code should be updated");
    }

    @Test
    void testPassportGetterAndSetter() {
        String newPassport = "A7654321";
        passenger.setPassport(newPassport);
        assertEquals(newPassport, passenger.getPassport(), "Passport should be updated");
    }

    @Test
    void testInheritedGettersAndSetters() {
        // Test inherited methods
        String newFirstName = "James";
        passenger.setFirstName(newFirstName);
        assertEquals(newFirstName, passenger.getFirstName(), "First name should be updated");

        String newSecondName = "Smith";
        passenger.setSecondName(newSecondName);
        assertEquals(newSecondName, passenger.getSecondName(), "Second name should be updated");

        int newAge = 25;
        passenger.setAge(newAge);
        assertEquals(newAge, passenger.getAge(), "Age should be updated");

        String newGender = "Woman";
        passenger.setGender(newGender);
        assertEquals(newGender, passenger.getGender(), "Gender should be updated");
    }

    @Test
    void testToString() {
        String expected = "Passenger{" + " Fullname= "+ passenger.getFirstName() +" "+ passenger.getSecondName() +
                " ,email='" + passenger.getEmail() + '\'' +
                ", phoneNumber='" + passenger.getPhoneNumber() + '\'' +
                ", passport='" + passenger.getPassport() +
                '}';
        assertEquals(expected, passenger.toString(), "toString method should return expected format");
    }

    // Use the simulated object
    // to test the behavior of the passenger object that depends on the Person class
    @Test
    void testPassengerWithMockPerson() {
        // Set the behavior of the simulated object
        when(mockPerson.getFirstName()).thenReturn("Mock");
        when(mockPerson.getSecondName()).thenReturn("Person");
        when(mockPerson.getAge()).thenReturn(30);
        when(mockPerson.getGender()).thenReturn("Non-Binary");

        // Test if the behavior is correct
        assertEquals("Mock", mockPerson.getFirstName());
        assertEquals("Person", mockPerson.getSecondName());
        assertEquals(30, mockPerson.getAge());
        assertEquals("Non-Binary", mockPerson.getGender());
    }
}
