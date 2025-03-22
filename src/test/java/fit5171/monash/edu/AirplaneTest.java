package fit5171.monash.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AirplaneTest {

    private Airplane airplane;
    private static final int AIRPLANE_ID = 123;
    private static final String AIRPLANE_MODEL = "Boeing 737";
    private static final int BUSINESS_SEATS = 20;
    private static final int ECONOMY_SEATS = 150;
    private static final int CREW_SEATS = 10;

    @BeforeEach
    public void setUp() {
        // Create a new Airplane object for each test
        airplane = new Airplane(AIRPLANE_ID, AIRPLANE_MODEL, BUSINESS_SEATS, ECONOMY_SEATS, CREW_SEATS);
    }


    //01 Basic Creation and Properties
    @Test
    public void testConstructorAndGetters() {
        // Test if the constructor and getters correctly set and return values
        assertEquals(AIRPLANE_ID, airplane.getAirplaneID());
        assertEquals(AIRPLANE_MODEL, airplane.getAirplaneModel());
        assertEquals(BUSINESS_SEATS, airplane.getBusinessSitsNumber());
        assertEquals(ECONOMY_SEATS, airplane.getEconomySitsNumber());
        assertEquals(CREW_SEATS, airplane.getCrewSitsNumber());
    }

    //02 Changing Airplane Properties
    @Test
    public void testSetAirplaneID() {
        // Test the setAirplaneID method
        int newID = 456;
        airplane.setAirplaneID(newID);
        assertEquals(newID, airplane.getAirplaneID());
    }

    @Test
    public void testSetAirplaneModel() {
        // Test the setAirplaneModel method
        String newModel = "Airbus A320";
        airplane.setAirplaneModel(newModel);
        assertEquals(newModel, airplane.getAirplaneModel());
    }

    @Test
    public void testSetBusinessSitsNumber() {
        // Test setting business class seats
        int newBusinessSeats = 30;
        airplane.setBusinessSitsNumber(newBusinessSeats);
        assertEquals(newBusinessSeats, airplane.getBusinessSitsNumber());
    }

    @Test
    public void testSetEconomySitsNumber() {
        // Test setting economy class seats
        int newEconomySeats = 200;
        airplane.setEconomySitsNumber(newEconomySeats);
        assertEquals(newEconomySeats, airplane.getEconomySitsNumber());
    }

    @Test
    public void testSetCrewSitsNumber() {
        // Test setting crew seats
        int newCrewSeats = 15;
        airplane.setCrewSitsNumber(newCrewSeats);
        assertEquals(newCrewSeats, airplane.getCrewSitsNumber());
    }

    //03 String Representation
    @Test
    public void testToString() {
        // Test if toString method contains all necessary information
        String airplaneString = airplane.toString();

        assertTrue(airplaneString.contains(AIRPLANE_MODEL));
        assertTrue(airplaneString.contains(String.valueOf(BUSINESS_SEATS)));
        assertTrue(airplaneString.contains(String.valueOf(ECONOMY_SEATS)));
        assertTrue(airplaneString.contains(String.valueOf(CREW_SEATS)));
    }

    //04 Booking Simulation
    @Test
    public void testReduceBusinessSeats() {
        // Test reducing business class seats scenario
        int seatsToReduce = 5;
        int expectedSeats = BUSINESS_SEATS - seatsToReduce;

        airplane.setBusinessSitsNumber(expectedSeats);
        assertEquals(expectedSeats, airplane.getBusinessSitsNumber());
    }

    @Test
    public void testReduceEconomySeats() {
        // Test reducing economy class seats scenario
        int seatsToReduce = 10;
        int expectedSeats = ECONOMY_SEATS - seatsToReduce;

        airplane.setEconomySitsNumber(expectedSeats);
        assertEquals(expectedSeats, airplane.getEconomySitsNumber());
    }

    //05 Empty Seat Scenario
    @Test
    public void testZeroSeats() {
        // Test edge case with zero seats
        airplane.setBusinessSitsNumber(0);
        airplane.setEconomySitsNumber(0);
        airplane.setCrewSitsNumber(0);

        assertEquals(0, airplane.getBusinessSitsNumber());
        assertEquals(0, airplane.getEconomySitsNumber());
        assertEquals(0, airplane.getCrewSitsNumber());
    }

    //06 Handling Bad Input
    @Test
    public void testNegativeSeats() {
        // Test setting negative number of seats (should be avoided in practice, but testing for coverage)
        airplane.setBusinessSitsNumber(-5);
        airplane.setEconomySitsNumber(-10);
        airplane.setCrewSitsNumber(-2);

        // Although seats shouldn't be negative logically, there's no protection in the code, so we test the actual behavior
        assertEquals(-5, airplane.getBusinessSitsNumber());
        assertEquals(-10, airplane.getEconomySitsNumber());
        assertEquals(-2, airplane.getCrewSitsNumber());
    }

    //07 Static Lookup Method
    @Test
    public void testGetAirPlaneInfo() {
        // Test the static getAirPlaneInfo method
        // Note: Current implementation returns null, so we test this behavior
        Airplane result = Airplane.getAirPlaneInfo(AIRPLANE_ID);
        assertNull(result, "Method is expected to return null in current implementation");
    }
}
