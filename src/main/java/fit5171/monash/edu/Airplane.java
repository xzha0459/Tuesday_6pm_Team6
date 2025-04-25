package fit5171.monash.edu;

import java.util.*;

/**
 * - 7 seats per row, rows labeled A–J
 * - Business, economy, and crew seats
 */
public class Airplane {
    private int airplaneID;
    private String airplaneModel;
    private int businessSeatsNumber;
    private int economySeatsNumber;
    private int crewSeatsNumber;

    private List<Seat> seats;

    private static final char[] ROWS = "ABCDEFGHIJ".toCharArray();
    private static final int SEATS_PER_ROW = 7;
    private static final int MAX_SEATS = ROWS.length * SEATS_PER_ROW; // total of 70 seats

    public Airplane(int airplaneID, String airplaneModel,
                    int businessSeatsNumber, int economySeatsNumber, int crewSeatsNumber) {
        if (airplaneID <= 0) throw new IllegalArgumentException("Airplane ID must be positive");
        if (airplaneModel == null || airplaneModel.trim().isEmpty())
            throw new IllegalArgumentException("Airplane model cannot be empty");
        if (businessSeatsNumber < 0 || economySeatsNumber < 0 || crewSeatsNumber < 0)
            throw new IllegalArgumentException("Seat counts cannot be negative");

        // ensure total seats do not exceed capacity
        int totalSeats = businessSeatsNumber + economySeatsNumber + crewSeatsNumber;
        if (totalSeats > MAX_SEATS) {
            throw new IllegalArgumentException("Total seats cannot exceed max capacity(" + MAX_SEATS + ")");
        }

        this.airplaneID = airplaneID;
        this.airplaneModel = airplaneModel.trim();
        this.businessSeatsNumber = businessSeatsNumber;
        this.economySeatsNumber = economySeatsNumber;
        this.crewSeatsNumber = crewSeatsNumber;

        // assign seats by class
        seats = new ArrayList<>();
        assignSeats(SeatClass.BUSINESS, businessSeatsNumber);
        assignSeats(SeatClass.ECONOMY, economySeatsNumber);
        assignSeats(SeatClass.CREW, crewSeatsNumber);
    }

    /**
     * Assign seats in row/number order
     */
    private void assignSeats(SeatClass type, int count) {
        int assigned = 0;
        for (char row : ROWS) {
            for (int num = 1; num <= SEATS_PER_ROW; num++) {
                if (assigned >= count) return;

                if (!isSeatTaken(row, num)) {
                    seats.add(new Seat(row, num, type));
                    assigned++;
                }
            }
        }
    }

    /**
     * Check if a seat is already taken
     */
    private boolean isSeatTaken(char row, int number) {
        for (Seat seat : seats) {
            if (seat.row == row && seat.number == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find a specific seat by row and number
     * @return the Seat if found, otherwise null
     */
    public Seat findSeat(char row, int number) {
        for (Seat seat : seats) {
            if (seat.row == row && seat.number == number) {
                return seat;
            }
        }
        return null;
    }

    public int getAirplaneID() {
        return airplaneID;
    }

    public String getAirplaneModel() {
        return airplaneModel;
    }

    public int getBusinessSeatsNumber() {
        return businessSeatsNumber;
    }

    public int getEconomySeatsNumber() {
        return economySeatsNumber;
    }

    public int getCrewSeatsNumber() {
        return crewSeatsNumber;
    }

    public int getTotalSeatsNumber() {
        return businessSeatsNumber + economySeatsNumber + crewSeatsNumber;
    }

    public List<Seat> getSeats() {
        return new ArrayList<>(seats);
    }

    /**
     * Get seats of a specific class
     */
    public List<Seat> getSeatsByType(SeatClass type) {
        List<Seat> result = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.type == type) {
                result.add(seat);
            }
        }
        return result;
    }

    /**
     * Get the number of seats in a specific row
     */
    public int getSeatsInRow(char row) {
        if (row < 'A' || row > 'J') {
            throw new IllegalArgumentException("Row must be between A and J");
        }

        int count = 0;
        for (Seat seat : seats) {
            if (seat.row == row) {
                count++;
            }
        }
        return count;
    }

    // Simple output of airplane information
    @Override
    public String toString() {
        return "Airplane[ID=" + airplaneID + ", model=" + airplaneModel +
                ", business=" + businessSeatsNumber +
                ", economy=" + economySeatsNumber +
                ", crew=" + crewSeatsNumber + "]";
    }

    /**
     * Get airplane information by ID (static method)
     * @param airplaneID Airplane ID
     * @return Airplane object if found, otherwise null
     */
    public static Airplane getAirplaneInfo(int airplaneID) {
        // This method should implement querying an airplane database or collection and return matching airplane
        return null;
    }

    /**
     * Seat class types
     */
    public enum SeatClass { BUSINESS, ECONOMY, CREW }

    /**
     * Represents a seat: row (A-J), number (1-7), and class
     */
    public static class Seat {
        public final char row;      // Row label
        public final int number;    // Seat number
        public final SeatClass type; // Seat class

        public Seat(char row, int number, SeatClass type) {
            // Validate seat row and number
            if (row < 'A' || row > 'J') {
                throw new IllegalArgumentException("Row must be between A and J");
            }
            if (number < 1 || number > SEATS_PER_ROW) {
                throw new IllegalArgumentException("Seat number must be between 1 and " + SEATS_PER_ROW);
            }

            this.row = row;
            this.number = number;
            this.type = type;
        }

        @Override
        public String toString() {
            return row + "" + number + "(" + type + ")";
        }
    }
}