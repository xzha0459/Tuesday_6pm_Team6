package fit5171.monash.edu;

import java.util.HashMap;
import java.util.Map;

/**
 * Airplane class containing airplane information and seat configuration
 */
public class Airplane {
    private int airplaneID;
    private String airplaneModel;
    private int businessSeatNumber;
    private int economySeatNumber;
    private int crewSeatNumber;
    private Map<Character, Integer> seatRows;

    public Airplane(int airplaneID, String airplaneModel,
                    int businessSeatNumber, int economySeatNumber, int crewSeatNumber) {
        // validate airplane ID
        if (airplaneID <= 0) {
            throw new IllegalArgumentException("Airplane ID must be positive");
        }

        // validate airplane model
        if (airplaneModel == null || airplaneModel.trim().isEmpty()) {
            throw new IllegalArgumentException("Airplane model cannot be empty");
        }

        // validate seat counts
        if (businessSeatNumber < 0) {
            throw new IllegalArgumentException("Business seat number cannot be negative");
        }
        if (economySeatNumber < 0) {
            throw new IllegalArgumentException("Economy seat number cannot be negative");
        }
        if (crewSeatNumber < 0) {
            throw new IllegalArgumentException("Crew seat number cannot be negative");
        }

        this.airplaneID = airplaneID;
        this.airplaneModel = airplaneModel;
        this.businessSeatNumber = businessSeatNumber;
        this.economySeatNumber = economySeatNumber;
        this.crewSeatNumber = crewSeatNumber;

        // initialize seat rows A-J, each row has 7 seats
        this.seatRows = new HashMap<>();
        initializeSeatRows();
    }

    /**
     * Initialize seat rows (A-J) with 7 seats each
     */
    private void initializeSeatRows() {
        for (char row = 'A'; row <= 'J'; row++) {
            seatRows.put(row, 7);
        }
    }

    /**
     * Get number of seats in a specific row (A-J)
     */
    public int getSeatsInRow(char row) {
        if (row < 'A' || row > 'J') {
            throw new IllegalArgumentException("Row must be between A and J");
        }
        return seatRows.getOrDefault(row, 0);
    }

    /**
     * Validate if a seat identifier (e.g., "A1", "B3") is valid
     */
    public boolean isValidSeat(String seatId) {
        if (seatId == null || seatId.length() < 2) {
            return false;
        }
        char row = seatId.charAt(0);
        if (row < 'A' || row > 'J') {
            return false;
        }
        try {
            int seatNum = Integer.parseInt(seatId.substring(1));
            return seatNum > 0 && seatNum <= 7;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getAirplaneID() {
        return airplaneID;
    }

    public void setAirplaneID(int airplaneID) {
        if (airplaneID <= 0) {
            throw new IllegalArgumentException("Airplane ID must be positive");
        }
        this.airplaneID = airplaneID;
    }

    public String getAirplaneModel() {
        return airplaneModel;
    }

    public void setAirplaneModel(String airplaneModel) {
        if (airplaneModel == null || airplaneModel.trim().isEmpty()) {
            throw new IllegalArgumentException("Airplane model cannot be empty");
        }
        this.airplaneModel = airplaneModel;
    }

    public int getBusinessSeatNumber() {
        return businessSeatNumber;
    }

    public void setBusinessSeatNumber(int businessSeatNumber) {
        if (businessSeatNumber < 0) {
            throw new IllegalArgumentException("Business seat number cannot be negative");
        }
        this.businessSeatNumber = businessSeatNumber;
    }

    public int getEconomySeatNumber() {
        return economySeatNumber;
    }

    public void setEconomySeatNumber(int economySeatNumber) {
        if (economySeatNumber < 0) {
            throw new IllegalArgumentException("Economy seat number cannot be negative");
        }
        this.economySeatNumber = economySeatNumber;
    }

    public int getCrewSeatNumber() {
        return crewSeatNumber;
    }

    public void setCrewSeatNumber(int crewSeatNumber) {
        if (crewSeatNumber < 0) {
            throw new IllegalArgumentException("Crew seat number cannot be negative");
        }
        this.crewSeatNumber = crewSeatNumber;
    }

    /**
     * Get total passenger seats (business + economy)
     */
    public int getTotalSeats() {
        return businessSeatNumber + economySeatNumber;
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "model='" + getAirplaneModel() + '\'' +
                ", businessSeats=" + getBusinessSeatNumber() +
                ", economySeats=" + getEconomySeatNumber() +
                ", crewSeats=" + getCrewSeatNumber() +
                '}';
    }

    /**
     * Placeholder method for fetching airplane info by ID
     */
    public static Airplane getAirPlaneInfo(int airplane_id) {
        // TODO: implement data retrieval logic
        return null;
    }
}