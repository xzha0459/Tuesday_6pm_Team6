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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

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
    void testBuyTicket_AlreadyBookedTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setTicket_id(2);
        ticket.setFlight(new Flight());
        ticket.setTicketStatus(true);

        try (MockedStatic<TicketCollection> mockTicketColl = Mockito.mockStatic(TicketCollection.class)) {
            mockTicketColl.when(() -> TicketCollection.getTicketInfo(2)).thenReturn(ticket);
            ticketSystem.buyTicket(2);
            String output = outputStream.toString();
            assertTrue(output.contains("already booked"));
        }
    }

    @Test
    void testChooseTicket_ConnectingFlights() throws Exception {
        String departureCity = "Sydney";
        String arrivalCity = "Paris";
        String connectingCity = "Dubai";

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

        try (
                MockedStatic<FlightCollection> mockFlightColl = Mockito.mockStatic(FlightCollection.class);
                MockedStatic<TicketCollection> mockTicketColl = Mockito.mockStatic(TicketCollection.class)
        ) {
            mockFlightColl.when(() -> FlightCollection.getFlightInfo(departureCity, arrivalCity)).thenReturn(null);
            mockFlightColl.when(() -> FlightCollection.getFlightInfo(arrivalCity)).thenReturn(flight1);
            mockFlightColl.when(() -> FlightCollection.getFlightInfo(departureCity, connectingCity)).thenReturn(flight2);

            mockTicketColl.when(() -> TicketCollection.getTicketInfo(100)).thenReturn(ticket1);
            mockTicketColl.when(() -> TicketCollection.getTicketInfo(101)).thenReturn(ticket2);

            String input = String.join("\n",
                    "John",
                    "Smith",
                    "30",
                    "Male",
                    "john@example.com",
                    "+012345678",
                    "AB123456",
                    "0"
            ) + "\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));

            ticketSystem.chooseTicket(departureCity, arrivalCity);

            String output = outputStream.toString();
            assertTrue(output.contains("Transfer way available"));
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

        try (
                MockedStatic<TicketCollection> mockTicketColl = Mockito.mockStatic(TicketCollection.class);
                MockedStatic<FlightCollection> mockFlightColl = Mockito.mockStatic(FlightCollection.class);
                MockedStatic<Airplane> mockAirplane = Mockito.mockStatic(Airplane.class)
        ) {
            mockTicketColl.when(() -> TicketCollection.getTicketInfo(validTicketId)).thenReturn(ticket);
            mockFlightColl.when(() -> FlightCollection.getFlightInfo(100)).thenReturn(flight);
            mockAirplane.when(() -> Airplane.getAirPlaneInfo(200)).thenReturn(airplane);

            String simulatedInput =
                    "Jerry\nSmith\n40\nMale\njohn@example.com\n+123456789\nAB123456\n1\n1234567890123456\n123\n";
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

            ticketSystem.buyTicket(validTicketId);

            String output = outputStream.toString();
            assertTrue(output.contains("Your bill:"));
            assertEquals(9, airplane.getEconomySitsNumber());
            assertEquals(5, airplane.getBusinessSitsNumber());
        }
    }

    @Test
    void integrationTestBuyTicket_WithRealObjects() throws Exception {
        // 手动构造 airplane
        Airplane mockAirplane = new Airplane(111, "MockPlane", 4, 8, 2);

        // 手动构造 flight
        Flight flight = new Flight();
        flight.setFlightID(10);
        flight.setDepartFrom("Sydney");
        flight.setDepartTo("Melbourne");
        flight.setAirplane(mockAirplane);

        // 构造 ticket
        Ticket ticket = new Ticket();
        ticket.setTicket_id(99);
        ticket.setFlight(flight);
        ticket.setPassenger(new Passenger());
        ticket.setClassVip(false);
        ticket.setPrice(150);

        TicketCollection.tickets = new ArrayList<>();
        TicketCollection.tickets.add(ticket);
        FlightCollection.flights = new ArrayList<>();
        FlightCollection.flights.add(flight);

        try (MockedStatic<Airplane> airplaneStatic = Mockito.mockStatic(Airplane.class)) {
            airplaneStatic.when(() -> Airplane.getAirPlaneInfo(111)).thenReturn(mockAirplane);

            // 输入流必须完全匹配 readPassengerInfo() + readPaymentInfo()
            String simulatedInput = String.join("\n",
                    "John", "Smith", "30", "Male", "john@example.com", "+123456789", "AB123456", // 乘客
                    "1", // confirm purchase
                    "4444333322221111", "123" // payment
            ) + "\n";

            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            ticketSystem.in = new Scanner(System.in);

            ticketSystem.buyTicket(99);

            String output = outputStream.toString();

            assertTrue(output.contains("Your bill:"));
            assertEquals(7, mockAirplane.getEconomySitsNumber());
        }
    }










}
