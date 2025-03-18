package fit5171.monash.edu;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TicketSystem<T> {
    // Combined fields from original classes
    private Passenger passenger;
    private Ticket ticket;
    private Flight flight;
    private Scanner in;

    // Constructor
    public TicketSystem() {
        passenger = new Passenger();
        ticket = new Ticket();
        flight = new Flight();
        in = new Scanner(System.in);
    }

    // Method copied from BuyTicket class
    public void showTicket() {
        try {
            System.out.println("You have bought a ticket for flight " + ticket.flight.getDepartFrom() + " - " + ticket.flight.getDepartTo() + "\n\nDetails:");
            System.out.println(this.ticket.toString());
        } catch (NullPointerException e) {
            return;
        }
    }

    public void buyTicket(int ticket_id) throws Exception {
        // Method for buying a single ticket with direct flight
        int flight_id = 0;

        // Get ticket information based on ticket ID
        Ticket validTicket = TicketCollection.getTicketInfo(ticket_id);

        // If no valid ticket found, display error message and return
        if(validTicket == null) {
            System.out.println("This ticket does not exist.");
            return;
        }
        else {
            // Get flight ID from the ticket
            flight_id = validTicket.getFlight().getFlightID();

            try {
                // Collect passenger information with validation
                System.out.println("Enter your First Name: ");
                String firstName = in.nextLine();
                if(isValidName(firstName)) {
                    passenger.setFirstName(firstName);
                } else {
                    System.out.println("Invalid name format. Please try again.");
                    firstName = in.nextLine();
                    passenger.setFirstName(firstName);
                }

                System.out.println("Enter your Second name:");
                String secondName = in.nextLine();
                if(isValidName(secondName)) {
                    passenger.setSecondName(secondName);
                } else {
                    System.out.println("Invalid name format. Please try again.");
                    secondName = in.nextLine();
                    passenger.setSecondName(secondName);
                }

                System.out.println("Enter your age:");
                Integer age = in.nextInt();
                in.nextLine(); // Clear input buffer
                passenger.setAge(age);

                System.out.println("Enter your gender: ");
                String gender = in.nextLine();
                passenger.setGender(gender);

                System.out.println("Enter your e-mail address");
                String email = in.nextLine();
                if(isValidEmail(email)) {
                    passenger.setEmail(email);
                } else {
                    System.out.println("Invalid email format. Please try again.");
                    email = in.nextLine();
                    passenger.setEmail(email);
                }

                System.out.println("Enter your phone number (+7):");
                String phoneNumber = in.nextLine();
                if(isValidPhoneNumber(phoneNumber)) {
                    passenger.setPhoneNumber(phoneNumber);
                } else {
                    System.out.println("Invalid phone number format. Please try again.");
                    phoneNumber = in.nextLine();
                    passenger.setPhoneNumber(phoneNumber);
                }

                System.out.println("Enter your passport number:");
                String passportNumber = in.nextLine();
                passenger.setPassport(passportNumber);

                System.out.println("Do you want to purchase?\n 1-YES 0-NO");
                int purch = in.nextInt();
                in.nextLine(); // Clear input buffer

                if (purch == 0) {
                    return;
                } else {
                    // Get flight and airplane information
                    flight = FlightCollection.getFlightInfo(flight_id);
                    int airplane_id = flight.getAirplane().getAirplaneID();
                    Airplane airplane = Airplane.getAirPlaneInfo(airplane_id);
                    ticket = TicketCollection.getTicketInfo(ticket_id);

                    // Set ticket details
                    ticket.setPassenger(passenger);
                    ticket.setTicket_id(ticket_id);
                    ticket.setFlight(flight);
                    ticket.setPrice(ticket.getPrice());
                    ticket.setClassVip(ticket.getClassVip());
                    ticket.setTicketStatus(true);

                    // Update seat count based on ticket class
                    if (ticket.getClassVip() == true) {
                        airplane.setBusinessSitsNumber(airplane.getBusinessSitsNumber() - 1);
                    } else {
                        airplane.setEconomySitsNumber(airplane.getEconomySitsNumber() - 1);
                    }
                }

                System.out.println("Your bill: " + ticket.getPrice() + "\n");

                // Collect payment information
                System.out.println("Enter your card number:");
                String cardNumber = in.nextLine();
                if(isValidCardNumber(cardNumber)) {
                    passenger.setCardNumber(cardNumber);
                } else {
                    System.out.println("Invalid card number format. Please try again.");
                    cardNumber = in.nextLine();
                    passenger.setCardNumber(cardNumber);
                }

                System.out.println("Enter your security code:");
                Integer securityCode = in.nextInt();
                in.nextLine(); // Clear input buffer
                passenger.setSecurityCode(securityCode);

            } catch (PatternSyntaxException patternException) {
                patternException.printStackTrace();
            }
        }
    }

    @SuppressWarnings("null")
    public void buyTicket(int ticket_id_first, int ticket_id_second) throws Exception {
        // Method for buying two tickets with connecting flights
        int flight_id_first = 0;
        int flight_id_second = 0;

        System.out.println(ticket_id_first + " " + ticket_id_second);

        Ticket validTicketFirst = TicketCollection.getTicketInfo(ticket_id_first);
        Ticket validTicketSecond = TicketCollection.getTicketInfo(ticket_id_second);

        System.out.println("Processing...");

        // Validate if tickets exist
        if(validTicketFirst == null || validTicketSecond == null) {
            System.out.println("One or both tickets do not exist.");
            return;
        }
        else {
            // Get flight IDs from tickets
            flight_id_first = validTicketFirst.getFlight().getFlightID();
            flight_id_second = validTicketSecond.getFlight().getFlightID();

            try {
                // Collect passenger information with validation (similar to single ticket method)
                System.out.println("Enter your First Name: ");
                String firstName = in.nextLine();
                if(isValidName(firstName)) {
                    passenger.setFirstName(firstName);
                } else {
                    System.out.println("Invalid name format. Please try again.");
                    firstName = in.nextLine();
                    passenger.setFirstName(firstName);
                }

                System.out.println("Enter your Second name:");
                String secondName = in.nextLine();
                if(isValidName(secondName)) {
                    passenger.setSecondName(secondName);
                } else {
                    System.out.println("Invalid name format. Please try again.");
                    secondName = in.nextLine();
                    passenger.setSecondName(secondName);
                }

                System.out.println("Enter your age:");
                Integer age = in.nextInt();
                in.nextLine(); // Clear input buffer
                passenger.setAge(age);

                System.out.println("Enter your gender: ");
                String gender = in.nextLine();
                passenger.setGender(gender);

                System.out.println("Enter your e-mail address");
                String email = in.nextLine();
                if(isValidEmail(email)) {
                    passenger.setEmail(email);
                } else {
                    System.out.println("Invalid email format. Please try again.");
                    email = in.nextLine();
                    passenger.setEmail(email);
                }

                System.out.println("Enter your phone number (+7):");
                String phoneNumber = in.nextLine();
                if(isValidPhoneNumber(phoneNumber)) {
                    passenger.setPhoneNumber(phoneNumber);
                } else {
                    System.out.println("Invalid phone number format. Please try again.");
                    phoneNumber = in.nextLine();
                    passenger.setPhoneNumber(phoneNumber);
                }

                System.out.println("Enter your passport number:");
                String passportNumber = in.nextLine();
                passenger.setPassport(passportNumber);

                System.out.println("Do you want to purchase?\n 1-YES 0-NO");
                int purch = in.nextInt();
                in.nextLine(); // Clear input buffer

                if (purch == 0) {
                    return;
                }
                else {
                    // Process first flight ticket
                    Flight flight_first = FlightCollection.getFlightInfo(flight_id_first);
                    int airplane_id_first = flight_first.getAirplane().getAirplaneID();
                    Airplane airplane_first = Airplane.getAirPlaneInfo(airplane_id_first);

                    // Process second flight ticket
                    Flight flight_second = FlightCollection.getFlightInfo(flight_id_second);
                    int airplane_id_second = flight_second.getAirplane().getAirplaneID();
                    Airplane airplane_second = Airplane.getAirPlaneInfo(airplane_id_second);

                    Ticket ticket_first = TicketCollection.getTicketInfo(ticket_id_first);
                    Ticket ticket_second = TicketCollection.getTicketInfo(ticket_id_second);

                    // Set first ticket details
                    ticket_first.setPassenger(passenger);
                    ticket_first.setTicket_id(ticket_id_first);
                    ticket_first.setFlight(flight_first);
                    ticket_first.setPrice(ticket_first.getPrice());
                    ticket_first.setClassVip(ticket_first.getClassVip());
                    ticket_first.setTicketStatus(true);

                    // Update first airplane seat count
                    if (ticket_first.getClassVip() == true) {
                        airplane_first.setBusinessSitsNumber(airplane_first.getBusinessSitsNumber() - 1);
                    } else {
                        airplane_first.setEconomySitsNumber(airplane_first.getEconomySitsNumber() - 1);
                    }

                    System.out.println("--*-*-");

                    // Set second ticket details
                    ticket_second.setPassenger(passenger);
                    ticket_second.setTicket_id(ticket_id_second);
                    ticket_second.setFlight(flight_second); // Fixed to use flight_second
                    ticket_second.setPrice(ticket_second.getPrice());
                    ticket_second.setClassVip(ticket_second.getClassVip());
                    ticket_second.setTicketStatus(true);

                    // Update second airplane seat count
                    if (ticket_second.getClassVip() == true) {
                        airplane_second.setBusinessSitsNumber(airplane_second.getBusinessSitsNumber() - 1);
                    } else {
                        airplane_second.setEconomySitsNumber(airplane_second.getEconomySitsNumber() - 1);
                    }

                    System.out.println("--*-*-");

                    // Calculate and set total price
                    ticket.setPrice(ticket_first.getPrice() + ticket_second.getPrice());

                    System.out.println("Your bill: " + ticket.getPrice() + "\n");

                    // Collect payment information
                    System.out.println("Enter your card number:");
                    String cardNumber = in.nextLine();
                    if(isValidCardNumber(cardNumber)) {
                        passenger.setCardNumber(cardNumber);
                    } else {
                        System.out.println("Invalid card number format. Please try again.");
                        cardNumber = in.nextLine();
                        passenger.setCardNumber(cardNumber);
                    }

                    System.out.println("Enter your security code:");
                    Integer securityCode = in.nextInt();
                    in.nextLine(); // Clear input buffer
                    passenger.setSecurityCode(securityCode);
                }
            } catch (PatternSyntaxException patternException) {
                patternException.printStackTrace();
            }
        }
    }

    // Method copied from ChooseTicket class
    public void chooseTicket(String city1, String city2) throws Exception {
        int counter = 1;
        int idFirst = 0;
        int idSecond = 0;

        Flight flight = new Flight();

        // Search for direct flight from city1 to city2
        flight = FlightCollection.getFlightInfo(city1, city2);

        if(flight != null) {
            // Display all available tickets
            TicketCollection.getAllTickets();

            System.out.println("\nEnter ID of ticket you want to choose:");
            int ticket_id = in.nextInt();
            in.nextLine(); // Clear input buffer

            // Buy the selected ticket
            this.buyTicket(ticket_id);
        }
        else {
            // Handle case when there's no direct flight, look for connecting flights
            Flight departTo = FlightCollection.getFlightInfo(city2);

            if(departTo == null) {
                System.out.println("No flights available to " + city2);
                return;
            }

            // Get connecting city
            String connectCity = departTo.getDepartFrom();

            // Find flight from origin city to connecting city
            Flight flightConnectingTwoCities = FlightCollection.getFlightInfo(city1, connectCity);

            if(flightConnectingTwoCities != null){
                System.out.println("There is special way to go there. And it is transfer way, like above. Way №" + counter);

                idFirst = departTo.getFlightID();
                idSecond = flightConnectingTwoCities.getFlightID();
                counter++;
            }

            // If connecting flight found, purchase tickets
            if(counter > 1) {
                this.buyTicket(idFirst, idSecond);
            } else {
                System.out.println("There are no possible variants.");
            }
            return;
        }
    }

    // Validation methods
    private boolean isValidEmail(String email) {
        if(email == null || email.isEmpty()) {
            return false;
        }
        // Basic email format validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        if(phone == null || phone.isEmpty()) {
            return false;
        }
        // Phone number format validation (example: +7xxxxxxxxxx)
        String phoneRegex = "^\\+7\\d{10}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phone).matches();
    }

    private boolean isValidName(String name) {
        if(name == null || name.isEmpty()) {
            return false;
        }
        // Name should contain only letters and spaces
        String nameRegex = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(nameRegex);
        return pattern.matcher(name).matches();
    }

    private boolean isValidCardNumber(String cardNumber) {
        if(cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }
        // Credit card number must be 16 digits
        String cardRegex = "^\\d{16}$";
        Pattern pattern = Pattern.compile(cardRegex);
        return pattern.matcher(cardNumber).matches();
    }
}