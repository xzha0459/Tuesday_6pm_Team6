package fit5171.monash.edu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketSystemTest {

    private TicketSystem ticketSystem;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        ticketSystem = new TicketSystem();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testShowTicket_ValidTicket() {
        Flight flight = new Flight();
        flight.setDepartFrom("Melbourne");
        flight.setDepartTo("Sydney");

        Ticket ticket = new Ticket();
        ticket.setFlight(flight);

        ticketSystem.ticket = ticket;
        ticketSystem.showTicket();

        String output = outputStream.toString();
        assertTrue(output.contains("You have bought a ticket for flight Melbourne - Sydney"));
    }

    @Test
    void testShowTicket_NullFlight() {
        ticketSystem.ticket = new Ticket();
        ticketSystem.showTicket();
        assertTrue(true);
    }

    @Test
    void testBuyTicket_InvalidTicketId() throws Exception {
        try (MockedStatic<TicketCollection> mockTicketColl = Mockito.mockStatic(TicketCollection.class)) {
            mockTicketColl.when(() -> TicketCollection.getTicketInfo(100)).thenReturn(null);

            ticketSystem.buyTicket(100);

            String output = outputStream.toString();
            assertTrue(output.contains("This ticket does not exist"));
        }
    }

    @Test
    void testChooseTicket_ConnectingFlights() throws Exception {
        String departureCity = "Sydney";
        String arrivalCity = "Paris";
        String connectingCity = "Dubai";

        Flight directFlight = null;

        Flight flight1 = new Flight();
        flight1.setFlightID(100);
        flight1.setDepartFrom(connectingCity);
        flight1.setDepartTo(arrivalCity);

        Flight flight2 = new Flight();
        flight2.setFlightID(101);
        flight2.setDepartFrom(departureCity);
        flight2.setDepartTo(connectingCity);

        Ticket ticket1 = new Ticket();
        ticket1.setFlight(flight1);

        Ticket ticket2 = new Ticket();
        ticket2.setFlight(flight2);

        try (MockedStatic<FlightCollection> mockFlightColl = Mockito.mockStatic(FlightCollection.class);
             MockedStatic<TicketCollection> mockTicketColl = Mockito.mockStatic(TicketCollection.class)) {

            mockFlightColl.when(() -> FlightCollection.getFlightInfo(departureCity, arrivalCity))
                    .thenReturn(directFlight);

            mockFlightColl.when(() -> FlightCollection.getFlightInfo("Paris"))
                    .thenReturn(flight1);

            mockFlightColl.when(() -> FlightCollection.getFlightInfo(departureCity, connectingCity))
                    .thenReturn(flight2);

            mockTicketColl.when(() -> TicketCollection.getTicketInfo(100))
                    .thenReturn(ticket1);
            mockTicketColl.when(() -> TicketCollection.getTicketInfo(101))
                    .thenReturn(ticket2);

            String input =
                    "John\n" +
                            "Smith\n" +
                            "30\n" +
                            "Male\n" +
                            "John@example.com\n" +
                            "+012345678\n" +
                            "AB123456\n" +
                            "0\n";

            System.setIn(new ByteArrayInputStream(input.getBytes()));

            ticketSystem.chooseTicket(departureCity, arrivalCity);

            String output = outputStream.toString();
            assertTrue(output.contains("special way to go"));
            assertTrue(output.contains("transfer way"));
        }
    }

    @Test
    void testBuyTicket_ValidTicketId() throws Exception {
        int validTicketId = 1;

        Flight flight = new Flight();
        flight.setFlightID(100);
        flight.setDepartFrom("Sydney");
        flight.setDepartTo("Melbourne");

        Airplane airplane = new Airplane(200, "Boeing 737", 5, 10, 3);
        flight.setAirplane(airplane);

        Passenger testPassenger = new Passenger();
        testPassenger.setAge(40);

        Ticket ticket = new Ticket();
        ticket.setTicket_id(validTicketId);
        ticket.setFlight(flight);
        ticket.setClassVip(false);
        ticket.setPassenger(testPassenger);
        ticket.setPrice(199);

        try (MockedStatic<TicketCollection> mockTicketColl = Mockito.mockStatic(TicketCollection.class);
             MockedStatic<FlightCollection> mockFlightColl = Mockito.mockStatic(FlightCollection.class);
             MockedStatic<Airplane> mockAirplane = Mockito.mockStatic(Airplane.class)) {

            mockTicketColl.when(() -> TicketCollection.getTicketInfo(validTicketId))
                    .thenReturn(ticket);

            mockFlightColl.when(() -> FlightCollection.getFlightInfo(100))
                    .thenReturn(flight);

            mockAirplane.when(() -> Airplane.getAirPlaneInfo(200))
                    .thenReturn(airplane);

            String simulatedInput =
                    "Jerry\n" +
                            "Smith\n" +
                            "40\n" +
                            "Male\n" +
                            "john@example.com\n" +
                            "+123456789\n" +
                            "AB123456\n" +
                            "1\n" +
                            "1234567890123456\n" +
                            "123\n";



            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

            ticketSystem.buyTicket(validTicketId);

            String output = outputStream.toString();
            assertTrue(output.contains("Your bill:"));
            assertEquals(9, airplane.getEconomySitsNumber());
            assertEquals(5, airplane.getBusinessSitsNumber());
        }
    }

}
