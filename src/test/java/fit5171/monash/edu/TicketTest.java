package fit5171.monash.edu;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.time.LocalDateTime;

class TicketTest {

    private Ticket ticket;
    private Flight flight;
    private Passenger adultPassenger;
    private Passenger childPassenger;
    private Passenger seniorPassenger;
    private Airplane airplane;

    private final int TICKET_ID = 12345;
    private final int PRICE = 5000;
    private final boolean CLASS_VIP = true;

    @BeforeEach
    void setUp() {
        // Create an airplane with required parameters
        airplane = new Airplane(
                1,              // airplaneID
                "C919",   // airplaneModel
                24,             // businessSitsNumber
                200,            // economySitsNumber
                6               // crewSitsNumber
        );

        // Create a flight for testing with required parameters
        flight = new Flight(
                100,                                         // flight_id
                "Melbourne",                                 // departTo
                "Sydney",                                    // departFrom
                "MEL-SYD101",                                // code
                "Qantas",                                    // company
                Timestamp.valueOf(LocalDateTime.now()),      // dateFrom
                Timestamp.valueOf(LocalDateTime.now().plusHours(2)), // dateTo
                airplane                                     // airplane
        );

        // Create passengers of different age categories
        adultPassenger = new Passenger("J", "J", 22, "Male",
                "jj@gmail.com", "0123456789",
                "AB123456", "1234-5678-9012-3456", 123);

        childPassenger = new Passenger("XT", "Z", 10, "Female",
                "xtz@gmail.com", "+1987654321",
                "CD654321", "9876-5432-1098-7654", 456);

        seniorPassenger = new Passenger("TY", "L", 65, "Male",
                "tyl@gmail.com", "+1122334455",
                "EF987654", "5678-1234-9012-3456", 789);

        // Initialize with adult passenger by default
        ticket = new Ticket(TICKET_ID, PRICE, flight, CLASS_VIP, adultPassenger);
    }

    @Test
    void testDefaultConstructor() {
        Ticket defaultTicket = new Ticket();
        assertNotNull(defaultTicket, "Default constructor should create non-null object");
        assertEquals(0, defaultTicket.getTicket_id(), "Default ticket ID should be 0");
        assertEquals(0, defaultTicket.getPrice(), "Default price should be 0");
        assertNull(defaultTicket.getFlight(), "Default flight should be null");
        assertFalse(defaultTicket.getClassVip(), "Default VIP status should be false");
        assertFalse(defaultTicket.ticketStatus(), "Default ticket status should be false");
        assertNull(defaultTicket.getPassenger(), "Default passenger should be null");
    }

    @Test
    void testParameterizedConstructor() {
        assertNotNull(ticket, "Parameterized constructor should create non-null object");
        assertEquals(TICKET_ID, ticket.getTicket_id(), "Ticket ID should match");
        assertEquals(PRICE, ticket.getPrice(), "Price should match");
        assertSame(flight, ticket.getFlight(), "Flight should match");
        assertEquals(CLASS_VIP, ticket.getClassVip(), "Class VIP status should match");
        assertFalse(ticket.ticketStatus(), "Ticket status should be false by default");
        assertSame(adultPassenger, ticket.getPassenger(), "Passenger should match");
    }

    @Test
    void testTicketIdGetterAndSetter() {
        int newTicketId = 54321;
        ticket.setTicket_id(newTicketId);
        assertEquals(newTicketId, ticket.getTicket_id(), "Ticket ID should be updated");
    }

    @Test
    void testPriceSetterWithAdultPassenger() {
        int newPrice = 6000;
        ticket.setPrice(newPrice);
        // Adult price with 12% service tax
        int expectedPrice = (int)(newPrice * 1.12);
        assertEquals(expectedPrice, ticket.getPrice(), "Price should be updated with service tax for adult");
    }

    @Test
    void testPriceSetterWithChildPassenger() {
        ticket.setPassenger(childPassenger);
        int newPrice = 6000;
        ticket.setPrice(newPrice);
        // Child price with 50% discount and then 12% service tax
        int expectedPrice = (int)((newPrice * 0.5) * 1.12);
        assertEquals(expectedPrice, ticket.getPrice(), "Price should be 50% discounted and then taxed for child");
    }

    @Test
    void testPriceSetterWithSeniorPassenger() {
        ticket.setPassenger(seniorPassenger);
        int newPrice = 6000;
        ticket.setPrice(newPrice);
        // Senior price is zero regardless of service tax
        assertEquals(0, ticket.getPrice(), "Price should be zero for senior passenger");
    }

    @Test
    void testSaleByAgeForChild() {
        // Create a fresh ticket to avoid any previous price modifications
        ticket = new Ticket(TICKET_ID, PRICE, flight, CLASS_VIP, childPassenger);
        ticket.saleByAge(childPassenger.getAge());
        assertEquals((int)(PRICE * 0.5), ticket.getPrice(), "Price should be 50% of original for child");
    }

    @Test
    void testSaleByAgeForAdult() {
        // Create a fresh ticket to avoid any previous price modifications
        ticket = new Ticket(TICKET_ID, PRICE, flight, CLASS_VIP, adultPassenger);
        ticket.saleByAge(adultPassenger.getAge());
        assertEquals(PRICE, ticket.getPrice(), "Price should remain unchanged for adult");
    }

    @Test
    void testSaleByAgeForSenior() {
        // Create a fresh ticket to avoid any previous price modifications
        ticket = new Ticket(TICKET_ID, PRICE, flight, CLASS_VIP, seniorPassenger);
        ticket.saleByAge(seniorPassenger.getAge());
        assertEquals(0, ticket.getPrice(), "Price should be zero for senior");
    }

    @Test
    void testServiceTax() {
        int originalPrice = ticket.getPrice();
        ticket.serviceTax();
        assertEquals((int)(originalPrice * 1.12), ticket.getPrice(), "Price should increase by 12% after service tax");
    }

    @Test
    void testFlightGetterAndSetter() {
        // Create a new airplane for the new flight
        Airplane newAirplane = new Airplane(
                2,              // airplaneID
                "Airbus A320",  // airplaneModel
                16,             // businessSitsNumber
                150,            // economySitsNumber
                5               // crewSitsNumber
        );

        // Create a new flight object with required parameters
        Flight newFlight = new Flight(
                202,                                         // flight_id
                "Brisbane",                                  // departTo
                "Melbourne",                                 // departFrom
                "BNE-MEL202",                                // code
                "Virgin Australia",                          // company
                Timestamp.valueOf(LocalDateTime.now()),      // dateFrom
                Timestamp.valueOf(LocalDateTime.now().plusHours(3)), // dateTo
                newAirplane                                  // airplane
        );
        ticket.setFlight(newFlight);
        assertSame(newFlight, ticket.getFlight(), "Flight should be updated");
    }

    @Test
    void testClassVipGetterAndSetter() {
        boolean newClassVip = false;
        ticket.setClassVip(newClassVip);
        assertEquals(newClassVip, ticket.getClassVip(), "Class VIP status should be updated");
    }

    @Test
    void testTicketStatusGetterAndSetter() {
        boolean newStatus = true;
        ticket.setTicketStatus(newStatus);
        assertEquals(newStatus, ticket.ticketStatus(), "Ticket status should be updated");
    }

    @Test
    void testPassengerGetterAndSetter() {
        Passenger newPassenger = new Passenger("Bronny", "Lebron", 22, "Male",
                "B.Lebron@gmail.com", "+1357924680",
                "GH246810", "2468-1357-8024-6913", 135);
        ticket.setPassenger(newPassenger);
        assertSame(newPassenger, ticket.getPassenger(), "Passenger should be updated");
    }

    @Test
    void testPriceCalculationFlow() {
        // Test for adult - only service tax applied
        ticket = new Ticket(TICKET_ID, PRICE, flight, CLASS_VIP, adultPassenger);
        ticket.setPrice(PRICE); // This triggers both saleByAge and serviceTax
        assertEquals((int)(PRICE * 1.12), ticket.getPrice(), "Adult price should only have service tax applied");

        // Test for child - 50% discount then service tax
        ticket = new Ticket(TICKET_ID, PRICE, flight, CLASS_VIP, childPassenger);
        ticket.setPrice(PRICE); // This triggers both saleByAge and serviceTax
        assertEquals((int)((PRICE * 0.5) * 1.12), ticket.getPrice(), "Child price should have 50% discount then service tax");

        // Test for senior - price should be 0 regardless of service tax
        ticket = new Ticket(TICKET_ID, PRICE, flight, CLASS_VIP, seniorPassenger);
        ticket.setPrice(PRICE); // This triggers both saleByAge and serviceTax
        assertEquals(0, ticket.getPrice(), "Senior price should be 0 regardless of service tax");
    }

    @Test
    void testToString() {
        String expected = "Ticket{" + '\n' +
                "Price=" + ticket.getPrice() + "KZT, " + '\n' +
                ticket.getFlight() + '\n' + "Vip status=" + ticket.getClassVip() + '\n' +
                ticket.getPassenger() + '\n' + "Ticket was purchased=" + ticket.ticketStatus() + "\n}";

        assertEquals(expected, ticket.toString(), "toString should return the expected format");
    }
}