package fit5171.monash.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FlightCollectionTest {

    private Flight flight1;
    private Flight flight2;
    private ArrayList<Flight> flights;

    @BeforeEach
    void setUp() {
        // Create a shared airplane object
        Airplane airplane = new Airplane(200, "Boeing 737", 5, 10, 3);

        // Setup timestamps
        Timestamp ts1 = Timestamp.valueOf("2024-05-01 08:00:00");
        Timestamp ts2 = Timestamp.valueOf("2024-05-01 11:00:00");

        // Create two valid flights
        flight1 = new Flight(1, "Sydney", "Melbourne", "SYD123", "Qantas", ts1, ts2, airplane);
        flight2 = new Flight(2, "Melbourne", "Perth", "MEL456", "Jetstar", ts1, ts2, airplane);

        // Add them to a test list
        flights = new ArrayList<>();
        flights.add(flight1);
        flights.add(flight2);

        // Reset static flight collection before each test
        FlightCollection.flights = new ArrayList<>();
    }

    @Test
    void testAddFlights() {
        FlightCollection.addFlights(flights);
        assertEquals(2, FlightCollection.getFlights().size());
    }

    @Test
    void testGetFlightByCityPair() {
        FlightCollection.addFlights(flights);
        Flight result = FlightCollection.getFlightInfo("Perth", "Melbourne"); // 修复此行
        assertNotNull(result);
        assertEquals("Jetstar", result.getCompany());
    }

    @Test
    void testGetFlightByArrivalCity() {
        FlightCollection.addFlights(flights);
        // Sydney -> Melbourne
        Flight result = FlightCollection.getFlightInfo("Perth", "Melbourne");
        assertNotNull(result);
        assertEquals("Jetstar", result.getCompany());
    }

    @Test
    void testGetFlightById() {
        FlightCollection.addFlights(flights);
        Flight result = FlightCollection.getFlightInfo(1);
        assertNotNull(result);
        assertEquals("Qantas", result.getCompany());
    }

    @Test
    void testGetFlight_NotFound() {
        FlightCollection.addFlights(flights);
        Flight result = FlightCollection.getFlightInfo(999);
        assertNull(result);
    }

    @Test
    void testAddDuplicateFlight_ShouldThrowException() {
        FlightCollection.addFlight(flight1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FlightCollection.addFlight(flight1);
        });
        assertEquals("This flight already exists in the system.", exception.getMessage());
    }

    @Test
    void testAddFlightWithInvalidCityName_ShouldThrowException() {
        Airplane airplane = new Airplane(300, "Test", 1, 1, 1);
        Timestamp ts = Timestamp.valueOf("2024-05-01 08:00:00");
        Flight invalidFlight = new Flight(3, "123InvalidCity", "ValidCity", "X", "A", ts, ts, airplane);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FlightCollection.addFlight(invalidFlight);
        });
        assertEquals("City names must be valid (alphabetic).", exception.getMessage());
    }
}
