package fit5171.monash.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Airplane class using BVT and ET strategies
 */
public class AirplaneTest {

    private Airplane validAirplane;

    @BeforeEach
    void setUp() {
        // Create a valid Airplane instance for tests
        validAirplane = new Airplane(1, "Boeing 737", 30, 150, 10);
    }

    @Test
    @DisplayName("Create a valid airplane instance")
    void testCreateValidAirplane() {
        Airplane airplane = new Airplane(1, "Boeing 737", 30, 150, 10);

        assertEquals(1, airplane.getAirplaneID());
        assertEquals("Boeing 737", airplane.getAirplaneModel());
        assertEquals(30, airplane.getBusinessSeatNumber());
        assertEquals(150, airplane.getEconomySeatNumber());
        assertEquals(10, airplane.getCrewSeatNumber());
    }

    // ===== Property validation tests =====

    @Test
    @DisplayName("Invalid ID values should throw exceptions")
    void testInvalidAirplaneId() {
        // Test ID = 0
        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(0, "Boeing 737", 30, 150, 10)
        );
        assertTrue(exception1.getMessage().contains("Airplane ID must be positive"));

        // Test negative ID
        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(-1, "Boeing 737", 30, 150, 10)
        );
        assertTrue(exception2.getMessage().contains("Airplane ID must be positive"));

        // Test setter with invalid ID
        Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                validAirplane.setAirplaneID(-5)
        );
        assertTrue(exception3.getMessage().contains("Airplane ID must be positive"));
    }

    @Test
    @DisplayName("Invalid model values should throw exceptions")
    void testInvalidAirplaneModel() {
        // Test null model
        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(1, null, 30, 150, 10)
        );
        assertTrue(exception1.getMessage().contains("Airplane model cannot be empty"));

        // Test empty model
        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(1, "", 30, 150, 10)
        );
        assertTrue(exception2.getMessage().contains("Airplane model cannot be empty"));

        // Test blank model
        Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(1, "   ", 30, 150, 10)
        );
        assertTrue(exception3.getMessage().contains("Airplane model cannot be empty"));
    }

    @ParameterizedTest
    @CsvSource({
            "business, -1",
            "economy, -10",
            "crew, -5"
    })
    @DisplayName("Negative seat counts should throw exceptions")
    void testNegativeSeatCounts(String seatType, int seatCount) {
        Exception exception = null;

        switch(seatType) {
            case "business":
                exception = assertThrows(IllegalArgumentException.class, () ->
                        new Airplane(1, "Boeing 737", seatCount, 150, 10)
                );
                assertTrue(exception.getMessage().contains("Business seat number cannot be negative"));
                break;
            case "economy":
                exception = assertThrows(IllegalArgumentException.class, () ->
                        new Airplane(1, "Boeing 737", 30, seatCount, 10)
                );
                assertTrue(exception.getMessage().contains("Economy seat number cannot be negative"));
                break;
            case "crew":
                exception = assertThrows(IllegalArgumentException.class, () ->
                        new Airplane(1, "Boeing 737", 30, 150, seatCount)
                );
                assertTrue(exception.getMessage().contains("Crew seat number cannot be negative"));
                break;
        }

        assertNotNull(exception);
    }

    // ===== Seat configuration tests =====

    @Test
    @DisplayName("Seat rows A-J should all have 7 seats")
    void testSeatRowsConfiguration() {
        for (char row = 'A'; row <= 'J'; row++) {
            assertEquals(7, validAirplane.getSeatsInRow(row));
        }

        // Test invalid rows
        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                validAirplane.getSeatsInRow('@')
        );
        assertTrue(exception1.getMessage().contains("Row must be between A and J"));

        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                validAirplane.getSeatsInRow('K')
        );
        assertTrue(exception2.getMessage().contains("Row must be between A and J"));
    }

    @ParameterizedTest
    @CsvSource({
            "A1, true",
            "A7, true",
            "J1, true",
            "J7, true",
            "E4, true",
            "A0, false",
            "A8, false",
            "K1, false",
            "91, false",
            "AA, false",
            "A, false",
            "1A, false"
    })
    @DisplayName("Seat identifier validity")
    void testSeatValidity(String seatId, boolean expected) {
        assertEquals(expected, validAirplane.isValidSeat(seatId));
    }



    // ===== Other functionality tests =====

    @Test
    @DisplayName("Test other airplane functionality")
    void testOtherFunctionality() {
        // Test getTotalSeats
        assertEquals(180, validAirplane.getTotalSeats());

        // Test toString
        String expected = "Airplane{model='Boeing 737', businessSeats=30, economySeats=150, crewSeats=10}";
        assertEquals(expected, validAirplane.toString());

        // Test getAirPlaneInfo
        Airplane result = Airplane.getAirPlaneInfo(1);
        assertNotNull(result);
        assertEquals(1, result.getAirplaneID());
        assertEquals("Boeing 737", result.getAirplaneModel());
        assertEquals(20, result.getBusinessSeatNumber());

        // Test getAirPlaneInfo with invalid ID
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Airplane.getAirPlaneInfo(0)
        );
        assertTrue(exception.getMessage().contains("Airplane ID must be positive"));
    }
}