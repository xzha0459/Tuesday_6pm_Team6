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

    // ===== Test airplane property validation =====

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

    // ===== Airplane ID tests =====

    @Test
    @DisplayName("Airplane ID zero should throw exception")
    void testAirplaneIdZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(0, "Boeing 737", 30, 150, 10)
        );
        assertTrue(exception.getMessage().contains("Airplane ID must be positive"));
    }

    @Test
    @DisplayName("Negative Airplane ID should throw exception")
    void testAirplaneIdNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(-1, "Boeing 737", 30, 150, 10)
        );
        assertTrue(exception.getMessage().contains("Airplane ID must be positive"));
    }

    @Test
    @DisplayName("Setting invalid Airplane ID should throw exception")
    void testSetInvalidAirplaneId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                validAirplane.setAirplaneID(-5)
        );
        assertTrue(exception.getMessage().contains("Airplane ID must be positive"));
    }

    // ===== Airplane model tests =====

    @Test
    @DisplayName("Null model should throw exception")
    void testAirplaneModelNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(1, null, 30, 150, 10)
        );
        assertTrue(exception.getMessage().contains("Airplane model cannot be empty"));
    }

    @Test
    @DisplayName("Empty model should throw exception")
    void testAirplaneModelEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(1, "", 30, 150, 10)
        );
        assertTrue(exception.getMessage().contains("Airplane model cannot be empty"));
    }

    @Test
    @DisplayName("Blank model should throw exception")
    void testAirplaneModelBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(1, "   ", 30, 150, 10)
        );
        assertTrue(exception.getMessage().contains("Airplane model cannot be empty"));
    }

    @Test
    @DisplayName("Setting invalid model should throw exception")
    void testSetInvalidAirplaneModel() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                validAirplane.setAirplaneModel(null)
        );
        assertTrue(exception.getMessage().contains("Airplane model cannot be empty"));
    }

    // ===== Seat count tests =====

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -100})
    @DisplayName("Negative business seats should throw exception")
    void testNegativeBusinessSeats(int seats) {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(1, "Boeing 737", seats, 150, 10)
        );
        assertTrue(exception.getMessage().contains("Business seat number cannot be negative"));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -100})
    @DisplayName("Negative economy seats should throw exception")
    void testNegativeEconomySeats(int seats) {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(1, "Boeing 737", 30, seats, 10)
        );
        assertTrue(exception.getMessage().contains("Economy seat number cannot be negative"));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -100})
    @DisplayName("Negative crew seats should throw exception")
    void testNegativeCrewSeats(int seats) {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Airplane(1, "Boeing 737", 30, 150, seats)
        );
        assertTrue(exception.getMessage().contains("Crew seat number cannot be negative"));
    }

    @Test
    @DisplayName("Setting invalid business seats should throw exception")
    void testSetInvalidBusinessSeats() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                validAirplane.setBusinessSeatNumber(-20)
        );
        assertTrue(exception.getMessage().contains("Business seat number cannot be negative"));
    }

    @Test
    @DisplayName("Setting invalid economy seats should throw exception")
    void testSetInvalidEconomySeats() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                validAirplane.setEconomySeatNumber(-50)
        );
        assertTrue(exception.getMessage().contains("Economy seat number cannot be negative"));
    }

    @Test
    @DisplayName("Setting invalid crew seats should throw exception")
    void testSetInvalidCrewSeats() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                validAirplane.setCrewSeatNumber(-5)
        );
        assertTrue(exception.getMessage().contains("Crew seat number cannot be negative"));
    }

    // ===== Seat row configuration tests =====

    @Test
    @DisplayName("Seat rows A-J should all have 7 seats")
    void testSeatRowsConfiguration() {
        for (char row = 'A'; row <= 'J'; row++) {
            assertEquals(7, validAirplane.getSeatsInRow(row));
        }
    }

    @Test
    @DisplayName("Invalid row character should throw exception")
    void testInvalidSeatRow() {
        Exception exceptionLow = assertThrows(IllegalArgumentException.class, () ->
                validAirplane.getSeatsInRow('@')
        );
        assertTrue(exceptionLow.getMessage().contains("Row must be between A and J"));

        Exception exceptionHigh = assertThrows(IllegalArgumentException.class, () ->
                validAirplane.getSeatsInRow('K')
        );
        assertTrue(exceptionHigh.getMessage().contains("Row must be between A and J"));
    }

    // ===== Seat identifier validation tests =====

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
    @DisplayName("getTotalSeats returns sum of business and economy seats")
    void testGetTotalSeats() {
        Airplane airplane = new Airplane(1, "Boeing 737", 30, 150, 10);
        assertEquals(180, airplane.getTotalSeats());
    }

    @Test
    @DisplayName("toString method should include updated field names")
    void testToString() {
        String expected = "Airplane{model='Boeing 737', businessSeats=30, economySeats=150, crewSeats=10}";
        assertEquals(expected, validAirplane.toString());
    }

    @Test
    @DisplayName("getAirPlaneInfo static method returns sample airplane")
    void testGetAirPlaneInfo() {
        Airplane result = Airplane.getAirPlaneInfo(1);
        assertNotNull(result);
        assertEquals(1, result.getAirplaneID());
        assertEquals("Boeing 737", result.getAirplaneModel());
        assertEquals(20, result.getBusinessSeatNumber());
        assertEquals(100, result.getEconomySeatNumber());
        assertEquals(5, result.getCrewSeatNumber());
    }

    @Test
    @DisplayName("getAirPlaneInfo with invalid ID should throw exception")
    void testGetAirPlaneInfoInvalidId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Airplane.getAirPlaneInfo(0)
        );
        assertTrue(exception.getMessage().contains("Airplane ID must be positive"));
    }
}
