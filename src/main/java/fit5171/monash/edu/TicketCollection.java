package fit5171.monash.edu;

import java.util.ArrayList;

public class TicketCollection {

	public static ArrayList<Ticket> tickets = new ArrayList<>();

	public static ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public static boolean addTicket(Ticket ticket) {
		// validate ticket
		if (ticket == null || !ticket.validateTicket()) {
			return false;
		}

		// Check whether the same air ticket ID already exists
		for (Ticket existingTicket : tickets) {
			if (existingTicket.getTicket_id() == ticket.getTicket_id()) {
				return false;
			}
		}

		tickets.add(ticket);
		return true;
	}

	public static void addTickets(ArrayList<Ticket> tickets_db) {
		if (tickets_db == null) {
			return;
		}

		for (Ticket ticket : tickets_db) {
			addTicket(ticket);
		}
	}

	public static ArrayList<Ticket> getAllTickets() {
		return new ArrayList<>(tickets);
	}

	public static Ticket getTicketInfo(int ticket_id) {
		for (Ticket ticket : tickets) {
			if (ticket.getTicket_id() == ticket_id) {
				return ticket;
			}
		}
		return null;
	}

	// Clear the air ticket collection (just for testing)
	public static void clearTickets() {
		tickets.clear();
	}
}
