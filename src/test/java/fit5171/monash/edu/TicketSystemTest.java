package fit5171.monash.edu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 该测试类总共有 5 个测试用例：
 * 1) testShowTicket_ValidTicket
 * 2) testShowTicket_NullFlight
 * 3) testBuyTicket_InvalidTicketId
 * 4) testChooseTicket_ConnectingFlights
 * 5) testBuyTicket_ValidTicketId
 */
@ExtendWith(MockitoExtension.class)
public class TicketSystemTest {

    @InjectMocks
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

    /**
     * Test ID: TS01
     * Test showTicket method with a valid ticket
     */
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

    /**
     * Test ID: TS02
     * Test showTicket method with a null flight reference
     */
    @Test
    void testShowTicket_NullFlight() {
        ticketSystem.ticket = new Ticket(); // flight is null
        ticketSystem.showTicket();
        // If no exception => pass
        assertTrue(true);
    }

    /**
     * Test ID: TS03
     * buyTicket with invalid ticket ID => "This ticket does not exist"
     */
    @Test
    void testBuyTicket_InvalidTicketId() throws Exception {
        try (MockedStatic<TicketCollection> mockedTicketCollection = Mockito.mockStatic(TicketCollection.class)) {
            mockedTicketCollection.when(() -> TicketCollection.getTicketInfo(999))
                    .thenReturn(null);

            ticketSystem.buyTicket(999);

            String output = outputStream.toString();
            assertTrue(output.contains("This ticket does not exist"));
        }
    }

    /**
     * Test ID: TS04
     * chooseTicket with connecting flights scenario
     */
    @Test
    void testChooseTicket_ConnectingFlights() throws Exception {
        String departureCity = "Sydney";
        String arrivalCity = "Paris";
        String connectingCity = "Dubai";

        // No direct flight => null
        Flight directFlight = null;

        Flight connectingFlight1 = new Flight();
        connectingFlight1.setFlightID(101);
        connectingFlight1.setDepartFrom(connectingCity);
        connectingFlight1.setDepartTo(arrivalCity);

        Flight connectingFlight2 = new Flight();
        connectingFlight2.setFlightID(102);
        connectingFlight2.setDepartFrom(departureCity);
        connectingFlight2.setDepartTo(connectingCity);

        // Mock static calls
        try (MockedStatic<FlightCollection> mockedFlightCollection = Mockito.mockStatic(FlightCollection.class);
             MockedStatic<TicketCollection> mockedTicketCollection = Mockito.mockStatic(TicketCollection.class)) {

            // direct flight => null
            mockedFlightCollection.when(() -> FlightCollection.getFlightInfo(departureCity, arrivalCity))
                    .thenReturn(directFlight);

            mockedFlightCollection.when(() -> FlightCollection.getFlightInfo(arrivalCity))
                    .thenReturn(connectingFlight1);

            mockedFlightCollection.when(() -> FlightCollection.getFlightInfo(departureCity, connectingCity))
                    .thenReturn(connectingFlight2);

            Ticket ticket1 = new Ticket();
            ticket1.setFlight(connectingFlight1);

            Ticket ticket2 = new Ticket();
            ticket2.setFlight(connectingFlight2);

            mockedTicketCollection.when(() -> TicketCollection.getTicketInfo(101))
                    .thenReturn(ticket1);
            mockedTicketCollection.when(() -> TicketCollection.getTicketInfo(102))
                    .thenReturn(ticket2);

            // Provide enough input for buyTicket(int,int)
            // We have 8 lines because the code calls nextLine() ~8 times before asking purchase
            String input =
                    "Alice\n" +           // First Name
                            "Smith\n" +           // Second name
                            "25\n" +              // Age
                            "Female\n" +          // Gender
                            "alice@example.com\n" + // Email
                            "+71234567890\n" +    // phone number
                            "AB123456\n" +        // passport
                            "0\n";                // "Do you want to purchase?" => NO

            System.setIn(new ByteArrayInputStream(input.getBytes()));

            // Act
            ticketSystem.chooseTicket(departureCity, arrivalCity);

            // Assert
            String output = outputStream.toString();
            assertTrue(output.contains("special way to go"));
            assertTrue(output.contains("transfer way"));
        }
    }

    /**
     * Test ID: TS05
     * buyTicket with valid ticket ID => seat decrement
     */
    @Test
    void testBuyTicket_ValidTicketId() throws Exception {
        int validTicketId = 1;

        // Real flight & airplane
        Flight flight = new Flight();
        flight.setFlightID(101);
        flight.setDepartFrom("Sydney");
        flight.setDepartTo("Melbourne");

        Airplane airplane = new Airplane(
                201,
                "Boeing 737",
                5,   // business seats
                10,  // economy seats
                3    // crew seats
        );
        flight.setAirplane(airplane);

        Ticket ticket = new Ticket();
        ticket.setTicket_id(validTicketId);
        ticket.setFlight(flight);
        ticket.setPrice(200);
        ticket.setClassVip(false); // economy

        // Mock the static calls so code uses our flight & airplane
        try (MockedStatic<TicketCollection> mockedTicketCollection = Mockito.mockStatic(TicketCollection.class);
             MockedStatic<FlightCollection> mockedFlightCollection = Mockito.mockStatic(FlightCollection.class);
             MockedStatic<Airplane> mockedAirplane = Mockito.mockStatic(Airplane.class)) {

            // Return our 'ticket'
            mockedTicketCollection.when(() -> TicketCollection.getTicketInfo(validTicketId))
                    .thenReturn(ticket);

            // Return our 'flight'
            mockedFlightCollection.when(() -> FlightCollection.getFlightInfo(101))
                    .thenReturn(flight);

            // Return our 'airplane'
            mockedAirplane.when(() -> Airplane.getAirPlaneInfo(201))
                    .thenReturn(airplane);

            // user input => 1 => seat changes
            String simulatedInput =
                    "John\n" +
                            "Doe\n" +
                            "30\n" +
                            "Male\n" +
                            "john@example.com\n" +
                            "+71234567890\n" +
                            "AB999999\n" +
                            "1\n" +  // purchase => yes
                            "1234567890123456\n" +
                            "123\n";

            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

            // Act
            ticketSystem.buyTicket(validTicketId);

            // Assert => "Your bill: 200" & seats decreased
            String output = outputStream.toString();
            assertTrue(output.contains("Your bill: 200"));
            // economy from 10 => 9
            assertEquals(9, airplane.getEconomySitsNumber());
            // business from 5 => remains 5
            assertEquals(5, airplane.getBusinessSitsNumber());
        }
    }
}
