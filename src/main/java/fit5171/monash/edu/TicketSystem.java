package fit5171.monash.edu;

import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class TicketSystem {
    Passenger passenger;
    Ticket ticket;
    Flight flight;
    Scanner in;

    public TicketSystem() {
        passenger = new Passenger();
        ticket = new Ticket();
        flight = new Flight();
    }

    public void showTicket() {
        try {
            System.out.println("You have bought a ticket for flight "
                    + ticket.flight.getDepartFrom() + " - " + ticket.flight.getDepartTo()
                    + "\n\nDetails:");
            System.out.println(this.ticket.toString());
        } catch (NullPointerException e) {
            return;
        }
    }

    public void buyTicket(int ticket_id) throws Exception {
        this.in = new Scanner(System.in);

        Ticket validTicket = TicketCollection.getTicketInfo(ticket_id);
        if (validTicket == null) {
            System.out.println("This ticket does not exist.");
            return;
        }

        if (validTicket.ticketStatus()) {
            System.out.println("This ticket is already booked.");
            return;
        }

        int flight_id = validTicket.getFlight().getFlightID();
        readPassengerInfo();

        System.out.println("Do you want to purchase?\n 1-YES 0-NO");
        int purch = this.in.nextInt();
        this.in.nextLine();

        if (purch == 0) return;

        flight = FlightCollection.getFlightInfo(flight_id);
        if (flight == null || flight.getAirplane() == null) {
            System.out.println("Flight or Airplane info is missing.");
            return;
        }

        Airplane airplane = Airplane.getAirPlaneInfo(flight.getAirplane().getAirplaneID());

        ticket = validTicket;
        ticket.setPassenger(passenger);
        ticket.setTicket_id(ticket_id);
        ticket.setFlight(flight);
        ticket.setClassVip(ticket.getClassVip());
        ticket.setTicketStatus(true);
        ticket.setPrice(ticket.getPrice());

        if (ticket.getClassVip()) {
            if (airplane.getBusinessSeatNumber() <= 0) {
                throw new IllegalStateException("No business class seats available");
            }
            airplane.setBusinessSeatNumber(airplane.getBusinessSeatNumber() - 1);
        } else {
            if (airplane.getEconomySeatNumber() <= 0) {
                throw new IllegalStateException("No economy seats available");
            }
            airplane.setEconomySeatNumber(airplane.getEconomySeatNumber() - 1);
        }

        System.out.println("Your bill: " + ticket.getPrice() + "\n");
        readPaymentInfo();
    }

    public void buyTicket(int ticket_id_first, int ticket_id_second) throws Exception {
        this.in = new Scanner(System.in);
        Ticket t1 = TicketCollection.getTicketInfo(ticket_id_first);
        Ticket t2 = TicketCollection.getTicketInfo(ticket_id_second);

        if (t1 == null || t2 == null || t1.ticketStatus() || t2.ticketStatus()) {
            System.out.println("One or both tickets do not exist or are already booked.");
            return;
        }

        int fid1 = t1.getFlight().getFlightID();
        int fid2 = t2.getFlight().getFlightID();
        readPassengerInfo();

        System.out.println("Do you want to purchase?\n 1-YES 0-NO");
        int purch = this.in.nextInt();
        this.in.nextLine();

        if (purch == 0) return;

        Flight f1 = FlightCollection.getFlightInfo(fid1);
        Flight f2 = FlightCollection.getFlightInfo(fid2);
        Airplane a1 = Airplane.getAirPlaneInfo(f1.getAirplane().getAirplaneID());
        Airplane a2 = Airplane.getAirPlaneInfo(f2.getAirplane().getAirplaneID());

        updateTicketInfo(t1, f1);
        updateSeat(a1, t1);

        updateTicketInfo(t2, f2);
        updateSeat(a2, t2);

        ticket.setPassenger(passenger);
        ticket.setPrice(t1.getPrice() + t2.getPrice());
        System.out.println("Your bill: " + ticket.getPrice() + "\n");
        readPaymentInfo();
    }

    public void chooseTicket(String city1, String city2) throws Exception {
        this.in = new Scanner(System.in);

        if (!city1.matches("[A-Za-z\\s]+") || !city2.matches("[A-Za-z\\s]+")) {
            System.out.println("Invalid city name format.");
            return;
        }

        int counter = 1;
        Flight direct = FlightCollection.getFlightInfo(city1, city2);

        if (direct != null) {
            TicketCollection.getAllTickets();
            System.out.println("\nEnter ID of ticket you want to choose:");
            int ticket_id = this.in.nextInt();
            this.in.nextLine();
            buyTicket(ticket_id);
        } else {
            Flight mid = FlightCollection.getFlightInfo(city2);
            if (mid != null) {
                String connect = mid.getDepartFrom();
                Flight toMid = FlightCollection.getFlightInfo(city1, connect);

                if (toMid != null) {
                    System.out.println("Transfer way available. Way №" + counter);
                    buyTicket(mid.getFlightID(), toMid.getFlightID());
                    return;
                }
            }
            System.out.println("There is no possible variants.");
        }
    }

    private void readPassengerInfo() {
        try {
            System.out.println("Enter your First Name: ");
            passenger.setFirstName(this.in.nextLine());

            System.out.println("Enter your Second name:");
            passenger.setLastName(this.in.nextLine());

            System.out.println("Enter your age:");
            passenger.setAge(this.in.nextInt());
            this.in.nextLine();

            System.out.println("Enter your gender: ");
            passenger.setGender(this.in.nextLine());

            System.out.println("Enter your e-mail address:");
            passenger.setEmail(this.in.nextLine());

            System.out.println("Enter your phone number (+7):");
            passenger.setPhoneNumber(this.in.nextLine());

            System.out.println("Enter your passport number:");
            passenger.setPassport(this.in.nextLine());

        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
    }

    private void readPaymentInfo() {
        System.out.println("Enter your card number:");
        passenger.setCardNumber(this.in.nextLine());

        System.out.println("Enter your security code:");
        passenger.setSecurityCode(this.in.nextInt());
    }

    private void updateTicketInfo(Ticket t, Flight f) {
        t.setPassenger(passenger);
        t.setFlight(f);
        t.setTicketStatus(true);
    }

    private void updateSeat(Airplane a, Ticket t) {
        if (t.getClassVip()) {
            if (a.getBusinessSeatNumber() <= 0) {
                throw new IllegalStateException("No business class seats available");
            }
            a.setBusinessSeatNumber(a.getBusinessSeatNumber() - 1);
        } else {
            if (a.getEconomySeatNumber() <= 0) {
                throw new IllegalStateException("No economy seats available");
            }
            a.setEconomySeatNumber(a.getEconomySeatNumber() - 1);
        }
    }
}
