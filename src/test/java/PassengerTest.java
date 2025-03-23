

import static org.junit.jupiter.api.Assertions.*;
import fit5171.monash.edu.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassengerTest {

    private Passenger passenger;


    @BeforeEach
    void setUp() {
        // Initialize a passenger with test data
        passenger = new Passenger(
                "JJ", "G", 20, "Male",
                "JJG@gmail.com", "0123456789",
                "E12345678", "1010101010101010", 123
        );
    }

    @Test
    void testDefaultConstructor() {
        Passenger emptyPassenger = new Passenger();
        assertNotNull(emptyPassenger, "Default constructor should create non-null object");
        assertNull(emptyPassenger.getFirstName());
        assertNull(emptyPassenger.getSecondName());
        assertEquals(0, emptyPassenger.getAge());
        assertNull(emptyPassenger.getGender());
        assertNull(emptyPassenger.getEmail());
        assertNull(emptyPassenger.getPhoneNumber());
        assertNull(emptyPassenger.getPassport());
        assertNull(emptyPassenger.getCardNumber());
        assertEquals(0, emptyPassenger.getSecurityCode());
    }

    @Test
    void testParameterizedConstructor() {
        assertEquals("JJ", passenger.getFirstName());
        assertEquals("G", passenger.getSecondName());
        assertEquals(20, passenger.getAge());
        assertEquals("Male", passenger.getGender());
        assertEquals("JJG@gmail.com", passenger.getEmail());
        assertEquals("0123456789", passenger.getPhoneNumber());
        assertEquals("E12345678", passenger.getPassport());
        assertEquals("1010101010101010", passenger.getCardNumber());
        assertEquals(123, passenger.getSecurityCode());
    }

    @Test
    void testEmailGetterAndSetter() {
        String newEmail = "new.email@example.com";
        passenger.setEmail(newEmail);
        assertEquals(newEmail, passenger.getEmail(), "Email should be updated");
    }

    @Test
    void testPhoneNumberGetterAndSetter() {
        String newPhoneNumber = "+9876543210";
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
        String newPassport = "D654321";
        passenger.setPassport(newPassport);
        assertEquals(newPassport, passenger.getPassport(), "Passport should be updated");
    }

    @Test
    void testInheritedGettersAndSetters() {
        // Test inherited methods
        String newFirstName = "James";
        passenger.setFirstName(newFirstName);
        assertEquals(newFirstName, passenger.getFirstName(), "First name should be updated");

        String newSecondName = "Lebron";
        passenger.setSecondName(newSecondName);
        assertEquals(newSecondName, passenger.getSecondName(), "Second name should be updated");

        int newAge = 25;
        passenger.setAge(newAge);
        assertEquals(newAge, passenger.getAge(), "Age should be updated");

        String newGender = "Female";
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

}
