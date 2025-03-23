package fit5171.monash.edu;

import java.util.*;
import java.util.regex.PatternSyntaxException;

public class TicketSystem<T> {
    Passenger passenger = new Passenger();
    Ticket ticket = new Ticket();
    Flight flight = new Flight();
    Scanner in = new Scanner(System.in);

    public TicketSystem() {
        passenger = new Passenger();
        ticket = new Ticket();
        flight = new Flight();
    }

    public void showTicket() {
        try {
            System.out.println("You have bought a ticket for flight " + ticket.flight.getDepartFrom() + " - " + ticket.flight.getDepartTo() + "\n\nDetails:");
            System.out.println(this.ticket.toString());
        } catch (NullPointerException e) {
            return;
        }
    }

    public void buyTicket(int ticket_id) throws Exception {
        int flight_id = 0;

        // Select ticket where ticket_id = ticket_id
        Ticket validTicket = TicketCollection.getTicketInfo(ticket_id);

        // If there is no valid ticket id was input then show message, otherwise we buy it
        if (validTicket == null) {
            System.out.println("This ticket does not exist.");
            return;
        } else {
            // Select flight_id from ticket where ticket_id = ticket_id
            flight_id = validTicket.getFlight().getFlightID();

            try {
                System.out.println("Enter your First Name: ");
                String firstName = in.nextLine();
                passenger.setFirstName(firstName);

                System.out.println("Enter your Second name:");
                String secondName = in.nextLine();
                passenger.setSecondName(secondName); // setting passengers info

                System.out.println("Enter your age:");
                Integer age = in.nextInt();
                in.nextLine(); // Consume newline
                passenger.setAge(age);

                System.out.println("Enter your gender: ");
                String gender = in.nextLine();
                passenger.setGender(gender);

                System.out.println("Enter your e-mail address");
                String email = in.nextLine();
                passenger.setEmail(email);

                System.out.println("Enter your phone number (+7):");
                String phoneNumber = in.nextLine();
                passenger.setPhoneNumber(phoneNumber);

                System.out.println("Enter your passport number:");
                String passportNumber = in.nextLine();
                passenger.setPassport(passportNumber);

                System.out.println("Do you want to purchase?\n 1-YES 0-NO");
                int purch = in.nextInt();
                in.nextLine();

                if (purch == 0) {
                    return;
                } else {
                    flight = FlightCollection.getFlightInfo(flight_id);
                    int airplane_id = flight.getAirplane().getAirplaneID();
                    Airplane airplane = Airplane.getAirPlaneInfo(airplane_id);

                    ticket = validTicket;
                    ticket.setPassenger(passenger);
                    ticket.setTicket_id(ticket_id);
                    ticket.setFlight(flight);
                    ticket.setPrice(ticket.getPrice());
                    ticket.setClassVip(ticket.getClassVip());
                    ticket.setTicketStatus(true);

                    // Check seat availability and update seat count
                    if (ticket.getClassVip()) {
                        if (airplane.getBusinessSitsNumber() <= 0) {
                            throw new IllegalStateException("No business class seats available");
                        }
                        airplane.setBusinessSitsNumber(airplane.getBusinessSitsNumber() - 1);
                    } else {
                        if (airplane.getEconomySitsNumber() <= 0) {
                            throw new IllegalStateException("No economy seats available");
                        }
                        airplane.setEconomySitsNumber(airplane.getEconomySitsNumber() - 1);
                    }
                }

                System.out.println("Your bill: " + ticket.getPrice() + "\n");

                System.out.println("Enter your card number:");
                String cardNumber = in.nextLine();
                passenger.setCardNumber(cardNumber);

                System.out.println("Enter your security code:");
                Integer securityCode = in.nextInt();
                in.nextLine(); // Consume newline
                passenger.setSecurityCode(securityCode);

            } catch (PatternSyntaxException patternException) {
                patternException.printStackTrace();
            }
        }
    }

    public void buyTicket(int ticket_id_first, int ticket_id_second) throws Exception {
        int flight_id_first = 0;
        int flight_id_second = 0;

        System.out.println(ticket_id_first + " " + ticket_id_second);

        Ticket validTicketFirst = TicketCollection.getTicketInfo(ticket_id_first);
        Ticket validTicketSecond = TicketCollection.getTicketInfo(ticket_id_second);

        System.out.println("Processing...");

        // If there are no valid ticket ids input then show message, otherwise we buy them
        if (validTicketFirst == null || validTicketSecond == null) {
            System.out.println("One or both tickets do not exist.");
            return;
        } else {
            flight_id_first = validTicketFirst.getFlight().getFlightID();
            flight_id_second = validTicketSecond.getFlight().getFlightID();

            try {
                System.out.println("Enter your First Name: ");
                String firstName = in.nextLine();
                passenger.setFirstName(firstName);

                System.out.println("Enter your Second name:");
                String secondName = in.nextLine();
                passenger.setSecondName(secondName);

                System.out.println("Enter your age:");
                Integer age = in.nextInt();
                in.nextLine();
                passenger.setAge(age);

                System.out.println("Enter your gender: ");
                String gender = in.nextLine();
                passenger.setGender(gender);

                System.out.println("Enter your e-mail address");
                String email = in.nextLine();
                passenger.setEmail(email);

                System.out.println("Enter your phone number (+7):");
                String phoneNumber = in.nextLine();
                passenger.setPhoneNumber(phoneNumber);

                System.out.println("Enter your passport number:");
                String passportNumber = in.nextLine();
                passenger.setPassport(passportNumber);

                System.out.println("Do you want to purchase?\n 1-YES 0-NO");
                int purch = in.nextInt();
                in.nextLine();

                if (purch == 0) {
                    return;
                } else {
                    Flight flight_first = FlightCollection.getFlightInfo(flight_id_first);
                    int airplane_id_first = flight_first.getAirplane().getAirplaneID();
                    Airplane airplane_first = Airplane.getAirPlaneInfo(airplane_id_first);

                    Flight flight_second = FlightCollection.getFlightInfo(flight_id_second);
                    int airplane_id_second = flight_second.getAirplane().getAirplaneID();
                    Airplane airplane_second = Airplane.getAirPlaneInfo(airplane_id_second);

                    Ticket ticket_first = validTicketFirst;
                    Ticket ticket_second = validTicketSecond;

                    ticket_first.setPassenger(passenger);
                    ticket_first.setTicket_id(ticket_id_first);
                    ticket_first.setFlight(flight_first);
                    ticket_first.setPrice(ticket_first.getPrice());
                    ticket_first.setClassVip(ticket_first.getClassVip());
                    ticket_first.setTicketStatus(true);

                    if (ticket_first.getClassVip()) {
                        if (airplane_first.getBusinessSitsNumber() <= 0) {
                            throw new IllegalStateException("No business class seats available on first flight");
                        }
                        airplane_first.setBusinessSitsNumber(airplane_first.getBusinessSitsNumber() - 1);
                    } else {
                        if (airplane_first.getEconomySitsNumber() <= 0) {
                            throw new IllegalStateException("No economy seats available on first flight");
                        }
                        airplane_first.setEconomySitsNumber(airplane_first.getEconomySitsNumber() - 1);
                    }

                    System.out.println("--*-*-");

                    ticket_second.setPassenger(passenger);
                    ticket_second.setTicket_id(ticket_id_second);
                    ticket_second.setFlight(flight_second);
                    ticket_second.setPrice(ticket_second.getPrice());
                    ticket_second.setClassVip(ticket_second.getClassVip());
                    ticket_second.setTicketStatus(true);

                    // Check seat availability for second flight
                    if (ticket_second.getClassVip()) {
                        if (airplane_second.getBusinessSitsNumber() <= 0) {
                            throw new IllegalStateException("No business class seats available on second flight");
                        }
                        airplane_second.setBusinessSitsNumber(airplane_second.getBusinessSitsNumber() - 1);
                    } else {
                        if (airplane_second.getEconomySitsNumber() <= 0) {
                            throw new IllegalStateException("No economy seats available on second flight");
                        }
                        airplane_second.setEconomySitsNumber(airplane_second.getEconomySitsNumber() - 1);
                    }

                    System.out.println("--*-*-");

                    double totalPrice = ticket_first.getPrice() + ticket_second.getPrice();
                    ticket.setPrice((int) totalPrice);

                    System.out.println("Your bill: " + ticket.getPrice() + "\n");

                    System.out.println("Enter your card number:");
                    String cardNumber = in.nextLine();
                    passenger.setCardNumber(cardNumber);

                    System.out.println("Enter your security code:");
                    Integer securityCode = in.nextInt();
                    in.nextLine();
                    passenger.setSecurityCode(securityCode);
                }
            } catch (PatternSyntaxException patternException) {
                patternException.printStackTrace();
            }
        }
    }

    public void chooseTicket(String city1, String city2) throws Exception {
        int counter = 1;
        int idFirst = 0;
        int idSecond = 0;

        Flight directFlight = FlightCollection.getFlightInfo(city1, city2);

        if (directFlight != null) {
            TicketCollection.getAllTickets();

            System.out.println("\nEnter ID of ticket you want to choose:");
            int ticket_id = in.nextInt();
            in.nextLine(); // Consume newline

            // Buy the ticket
            buyTicket(ticket_id);
        } else {

            Flight departToCity2 = FlightCollection.getFlightInfo(city2);

            if (departToCity2 != null) {
                String connectCity = departToCity2.getDepartFrom();

                Flight flightConnectingTwoCities = FlightCollection.getFlightInfo(city1, connectCity);

                if (flightConnectingTwoCities != null) {
                    System.out.println("There is special way to go there. And it is transfer way, like above. Way №" + counter);

                    idFirst = departToCity2.getFlightID();
                    idSecond = flightConnectingTwoCities.getFlightID();

                    counter++;
                    buyTicket(idFirst, idSecond); // Pass two tickets and buy them
                } else {
                    System.out.println("There is no possible variants.");
                }
            } else {
                System.out.println("There is no possible variants.");
            }

            if (counter == 1) {
                System.out.println("There is no possible variants.");
            }
        }
    }
}