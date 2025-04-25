package fit5171.monash.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {

    private Flight flight;
    private Airplane airplane;

    @BeforeEach
    void setUp() {
        flight = new Flight();
        airplane = new Airplane(200, "Boeing 737", 10, 100, 5);
    }

    @Test
    void testDefaultConstructor() {
        assertEquals(0, flight.getFlightID());
        assertNull(flight.getDepartFrom());
        assertNull(flight.getDepartTo());
        assertNull(flight.getCompany());
        assertNull(flight.getCode());
        assertNull(flight.getDateFrom());
        assertNull(flight.getDateTo());
        assertNull(flight.getAirplane());
    }

    @Test
    void testValidConstructor() {
        Timestamp from = Timestamp.valueOf("2024-06-01 08:00:00");
        Timestamp to = Timestamp.valueOf("2024-06-01 10:00:00");

        Flight newFlight = new Flight(10, "Sydney", "Melbourne", "SYD123", "Qantas", from, to, airplane);

        assertEquals(10, newFlight.getFlightID());
        assertEquals("Sydney", newFlight.getDepartTo());
        assertEquals("Melbourne", newFlight.getDepartFrom());
        assertEquals("SYD123", newFlight.getCode());
        assertEquals("Qantas", newFlight.getCompany());
        assertEquals(from, newFlight.getDateFrom());
        assertEquals(to, newFlight.getDateTo());
        assertEquals(airplane, newFlight.getAirplane());
    }

    @Test
    void testSetAndGetFlightID() {
        flight.setFlightID(123);
        assertEquals(123, flight.getFlightID());
    }

    @Test
    void testSetInvalidFlightID() {
        assertThrows(IllegalArgumentException.class, () -> flight.setFlightID(0));
    }

    @Test
    void testSetAndGetDepartFrom() {
        flight.setDepartFrom("Melbourne");
        assertEquals("Melbourne", flight.getDepartFrom());
    }

    @Test
    void testSetInvalidDepartFrom() {
        assertThrows(IllegalArgumentException.class, () -> flight.setDepartFrom(null));
        assertThrows(IllegalArgumentException.class, () -> flight.setDepartFrom(""));
    }

    @Test
    void testSetAndGetDepartTo() {
        flight.setDepartTo("Sydney");
        assertEquals("Sydney", flight.getDepartTo());
    }

    @Test
    void testSetInvalidDepartTo() {
        assertThrows(IllegalArgumentException.class, () -> flight.setDepartTo(null));
        assertThrows(IllegalArgumentException.class, () -> flight.setDepartTo(""));
    }

    @Test
    void testSetAndGetCode() {
        flight.setCode("A123");
        assertEquals("A123", flight.getCode());
    }

    @Test
    void testSetInvalidCode() {
        assertThrows(IllegalArgumentException.class, () -> flight.setCode(null));
        assertThrows(IllegalArgumentException.class, () -> flight.setCode(" "));
    }

    @Test
    void testSetAndGetCompany() {
        flight.setCompany("Jetstar");
        assertEquals("Jetstar", flight.getCompany());
    }

    @Test
    void testSetInvalidCompany() {
        assertThrows(IllegalArgumentException.class, () -> flight.setCompany(null));
        assertThrows(IllegalArgumentException.class, () -> flight.setCompany(""));
    }

    @Test
    void testSetAndGetDateFrom() {
        Timestamp ts = Timestamp.valueOf("2024-06-01 08:00:00");
        flight.setDateFrom(ts);
        assertEquals(ts, flight.getDateFrom());
    }

    @Test
    void testSetInvalidDateFrom() {
        assertThrows(IllegalArgumentException.class, () -> flight.setDateFrom(null));
    }

    @Test
    void testSetAndGetDateTo() {
        Timestamp ts = Timestamp.valueOf("2024-06-01 10:00:00");
        flight.setDateTo(ts);
        assertEquals(ts, flight.getDateTo());
    }

    @Test
    void testSetInvalidDateTo() {
        assertThrows(IllegalArgumentException.class, () -> flight.setDateTo(null));
    }

    @Test
    void testSetAndGetAirplane() {
        flight.setAirplane(airplane);
        assertEquals(airplane, flight.getAirplane());
    }

    @Test
    void testSetInvalidAirplane() {
        assertThrows(IllegalArgumentException.class, () -> flight.setAirplane(null));
    }

    @Test
    void testToStringIncludesFields() {
        flight.setFlightID(77);
        flight.setDepartFrom("Adelaide");
        flight.setDepartTo("Brisbane");
        flight.setCode("F777");
        flight.setCompany("TigerAir");
        Timestamp from = Timestamp.valueOf("2024-06-01 08:00:00");
        Timestamp to = Timestamp.valueOf("2024-06-01 10:00:00");
        flight.setDateFrom(from);
        flight.setDateTo(to);
        flight.setAirplane(airplane);

        String result = flight.toString();
        assertTrue(result.contains("77"));
        assertTrue(result.contains("Adelaide"));
        assertTrue(result.contains("Brisbane"));
        assertTrue(result.contains("F777"));
        assertTrue(result.contains("TigerAir"));
        assertTrue(result.contains(String.valueOf(airplane.getAirplaneID())));
    }
}
