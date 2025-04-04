package fit5171.monash.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class FlightTest {

    private Flight flight;
    private Airplane airplane;

    // Sample constants for testing
    private static final int FLIGHT_ID = 100;
    private static final String DEPART_FROM = "Melbourne";
    private static final String DEPART_TO = "Sydney";

    @BeforeEach
    void setUp() {
        // Create a fresh Flight object before each test
        flight = new Flight();
        // Optionally, create an Airplane to attach
        airplane = new Airplane(200, "Boeing 737", 10, 100, 5);
    }


    @Test
    void testDefaultConstructor() {
        // New Flight should have default/zero/null values
        assertEquals(0, flight.getFlightID());
        assertNull(flight.getAirplane());
        assertNull(flight.getDepartFrom());
        assertNull(flight.getDepartTo());
    }


    @Test
    void testSetAndGetFlightID() {
        flight.setFlightID(FLIGHT_ID);
        assertEquals(FLIGHT_ID, flight.getFlightID());
    }


    @Test
    void testSetAndGetDepartFrom() {
        flight.setDepartFrom(DEPART_FROM);
        assertEquals(DEPART_FROM, flight.getDepartFrom());
    }


    @Test
    void testSetAndGetDepartTo() {
        flight.setDepartTo(DEPART_TO);
        assertEquals(DEPART_TO, flight.getDepartTo());
    }


    @Test
    void testSetAndGetAirplane() {
        flight.setAirplane(airplane);
        assertNotNull(flight.getAirplane());
        assertEquals(200, flight.getAirplane().getAirplaneID());
        assertEquals("Boeing 737", flight.getAirplane().getAirplaneModel());
    }


    @Test
    void testModifyFlightData() {
        flight.setFlightID(300);
        flight.setDepartFrom("Brisbane");
        flight.setDepartTo("Perth");

        assertEquals(300, flight.getFlightID());
        assertEquals("Brisbane", flight.getDepartFrom());
        assertEquals("Perth", flight.getDepartTo());
    }


    @Test
    void testReassignAirplane() {
        // Assign first airplane
        flight.setAirplane(airplane);
        assertEquals(200, flight.getAirplane().getAirplaneID());

        // Create a second Airplane
        Airplane newAirplane = new Airplane(401, "Airbus A320", 10, 100, 6);
        flight.setAirplane(newAirplane);

        // Should now reference the new airplane
        assertEquals(401, flight.getAirplane().getAirplaneID());
        assertEquals("Airbus A320", flight.getAirplane().getAirplaneModel());
    }


    @Test
    void testSetAirplaneToNull() {
        flight.setAirplane(null);
        assertNull(flight.getAirplane());
    }


    @Test
    void testToString() {
        flight.setFlightID(FLIGHT_ID);
        flight.setDepartFrom(DEPART_FROM);
        flight.setDepartTo(DEPART_TO);
        flight.setAirplane(airplane);

        String flightStr = flight.toString();
        assertTrue(flightStr.contains(String.valueOf(FLIGHT_ID)));
        assertTrue(flightStr.contains(DEPART_FROM));
        assertTrue(flightStr.contains(DEPART_TO));
        assertTrue(flightStr.contains(String.valueOf(airplane.getAirplaneID())));
    }
}