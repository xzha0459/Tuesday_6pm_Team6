package fit5171.monash.edu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlightCollection {
	public static List<Flight> flights = new ArrayList<>();

	public static List<Flight> getFlights() {
		return flights;
	}

	public static void addFlight(Flight flight) {
		if (flight == null) {
			throw new IllegalArgumentException("Flight cannot be null.");
		}

		if (flight.getDepartFrom() == null || flight.getDepartTo() == null ||
				flight.getCode() == null || flight.getCompany() == null ||
				flight.getDateFrom() == null || flight.getDateTo() == null ||
				flight.getAirplane() == null) {
			throw new IllegalArgumentException("All flight fields are required.");
		}

		if (!flight.getDepartFrom().matches("[A-Za-z\\s]+") || !flight.getDepartTo().matches("[A-Za-z\\s]+")) {
			throw new IllegalArgumentException("City names must be valid (alphabetic).");
		}

		for (Flight f : flights) {
			if (f.getFlightID() == flight.getFlightID()) {
				throw new IllegalArgumentException("This flight already exists in the system.");
			}
		}

		flights.add(flight);
	}

	public static void addFlights(List<Flight> flightsToAdd) {
		for (Flight flight : flightsToAdd) {
			addFlight(flight);
		}
	}

	public static Flight getFlightInfo(String from, String to) {
		if (from == null || to == null) return null;
		for (Flight f : flights) {
			if (f.getDepartFrom().equalsIgnoreCase(from) &&
					f.getDepartTo().equalsIgnoreCase(to)) {
				return f;
			}
		}
		return null;
	}

	public static Flight getFlightInfo(String to) {
		if (to == null) return null;
		for (Flight f : flights) {
			if (f.getDepartTo().equalsIgnoreCase(to)) {
				return f;
			}
		}
		return null;
	}

	public static Flight getFlightInfo(int flightId) {
		for (Flight f : flights) {
			if (f.getFlightID() == flightId) {
				return f;
			}
		}
		return null;
	}
}
