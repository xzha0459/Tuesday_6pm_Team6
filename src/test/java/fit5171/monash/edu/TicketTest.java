package fit5171.monash.edu;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TicketTest {

    private Ticket ticket;

    @Mock
    private Flight mockFlight;

    @Mock
    private Passenger mockAdultPassenger;

    @Mock
    private Passenger mockChildPassenger;

    @Mock
    private Passenger mockSeniorPassenger;

    private final int TICKET_ID = 12345;
    private final int PRICE = 3000;
    private final boolean CLASS_VIP = true;

    @BeforeEach
    void setUp() {
        // Initialize the Mockito annotation
        MockitoAnnotations.openMocks(this);

        // Set the behavior of the simulated object
        when(mockAdultPassenger.getAge()).thenReturn(30);
        when(mockChildPassenger.getAge()).thenReturn(10);
        when(mockSeniorPassenger.getAge()).thenReturn(65);

        // Initialize the ticket object
        ticket = new Ticket(TICKET_ID, PRICE, mockFlight, CLASS_VIP, mockAdultPassenger);
    }

    @Test
    void testDefaultConstructor() {
        Ticket defaultTicket = new Ticket();
        assertNotNull(defaultTicket);
        assertEquals(0, defaultTicket.getTicket_id());
        assertEquals(0, defaultTicket.getPrice());
        assertNull(defaultTicket.getFlight());
        assertFalse(defaultTicket.getClassVip());
        assertFalse(defaultTicket.ticketStatus());
        assertNull(defaultTicket.getPassenger());
    }

    @Test
    void testParameterizedConstructor() {
        assertNotNull(ticket);
        assertEquals(TICKET_ID, ticket.getTicket_id());
        assertEquals(PRICE, ticket.getPrice());
        assertSame(mockFlight, ticket.getFlight());
        assertEquals(CLASS_VIP, ticket.getClassVip());
        assertFalse(ticket.ticketStatus());
        assertSame(mockAdultPassenger, ticket.getPassenger());
    }

    @Test
    void testConstructorWithNullFlight() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(TICKET_ID, PRICE, null, CLASS_VIP, mockAdultPassenger);
        });
        assertEquals("Flight cannot be null", exception.getMessage());
    }

    @Test
    void testConstructorWithNullPassenger() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(TICKET_ID, PRICE, mockFlight, CLASS_VIP, null);
        });
        assertEquals("Passenger cannot be null", exception.getMessage());
    }

    @Test
    void testConstructorWithNegativePrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(TICKET_ID, -100, mockFlight, CLASS_VIP, mockAdultPassenger);
        });
        assertEquals("Price cannot be negative", exception.getMessage());
    }

    @Test
    void testPriceSetterWithNegativeValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setPrice(-100);
        });
        assertEquals("Price cannot be negative", exception.getMessage());
    }

    @Test
    void testTicketIdGetterAndSetter() {
        int newTicketId = 54321;
        ticket.setTicket_id(newTicketId);
        assertEquals(newTicketId, ticket.getTicket_id());
    }

    @Test
    void testPriceSetterWithAdultPassenger() {
        int newPrice = 6000;
        ticket.setPrice(newPrice);
        // Verify that the getAge method was called
        verify(mockAdultPassenger).getAge();
        // Adult prices are only subject to service tax
        assertEquals((int)(newPrice * 1.12), ticket.getPrice());
    }

    @Test
    void testPriceSetterWithChildPassenger() {
        ticket = new Ticket(TICKET_ID, PRICE, mockFlight, CLASS_VIP, mockChildPassenger);
        int newPrice = 6000;
        ticket.setPrice(newPrice);
        // Verify that the getAge method was called
        verify(mockChildPassenger).getAge();
        // Children's prices apply a 50% discount followed by a 12% service tax
        int discountedPrice = (int)(newPrice * 0.5);
        assertEquals((int)(discountedPrice * 1.12), ticket.getPrice());
    }

    @Test
    void testPriceSetterWithSeniorPassenger() {
        ticket = new Ticket(TICKET_ID, PRICE, mockFlight, CLASS_VIP, mockSeniorPassenger);
        int newPrice = 6000;
        ticket.setPrice(newPrice);
        // Verify that the getAge method was called
        verify(mockSeniorPassenger).getAge();
        // The price for the elderly should be 0
        assertEquals(0, ticket.getPrice());
    }

    @Test
    void testSaleByAgeForChild() {
        ticket.setPrice(PRICE);
        ticket.saleByAge(10);
        assertEquals((int)((PRICE * 0.5) * 1.12), ticket.getPrice());
    }

    @Test
    void testSaleByAgeForAdult() {
        ticket.setPrice(PRICE);
        ticket.saleByAge(30);
        assertEquals((int)(PRICE * 1.12), ticket.getPrice());
    }

    @Test
    void testSaleByAgeForSenior() {
        ticket.setPrice(PRICE);
        ticket.saleByAge(65);
        assertEquals(0, ticket.getPrice());
    }

    @Test
    void testServiceTax() {
        ticket = new Ticket(TICKET_ID, PRICE, mockFlight, CLASS_VIP, mockAdultPassenger);
        int originalPrice = ticket.getPrice();
        ticket.serviceTax();
        assertEquals((int)(originalPrice * 1.12), ticket.getPrice());
    }

    @Test
    void testFlightGetterAndSetter() {
        Flight newMockFlight = mock(Flight.class);
        ticket.setFlight(newMockFlight);
        assertSame(newMockFlight, ticket.getFlight());
    }

    @Test
    void testSetNullFlight() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setFlight(null);
        });
        assertEquals("Flight cannot be null", exception.getMessage());
    }

    @Test
    void testClassVipGetterAndSetter() {
        boolean newClassVip = false;
        ticket.setClassVip(newClassVip);
        assertEquals(newClassVip, ticket.getClassVip());
    }

    @Test
    void testTicketStatusGetterAndSetter() {
        boolean newStatus = true;
        ticket.setTicketStatus(newStatus);
        assertEquals(newStatus, ticket.ticketStatus());
    }

    @Test
    void testPassengerGetterAndSetter() {
        Passenger newMockPassenger = mock(Passenger.class);
        ticket.setPassenger(newMockPassenger);
        assertSame(newMockPassenger, ticket.getPassenger());
    }

    @Test
    void testSetNullPassenger() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setPassenger(null);
        });
        assertEquals("Passenger cannot be null", exception.getMessage());
    }

    @Test
    void testValidateTicket() {
        assertTrue(ticket.validateTicket());

        // Test invalid ticket
        Ticket invalidTicket = new Ticket();
        assertFalse(invalidTicket.validateTicket());
    }

    @Test
    void testToString() {
        String expected = "Ticket{" + '\n' +
                "Price=" + ticket.getPrice() + "KZT, " + '\n' +
                ticket.getFlight() + '\n' + "Vip status=" + ticket.getClassVip() + '\n' +
                ticket.getPassenger() + '\n' + "Ticket was purchased=" + ticket.ticketStatus() + "\n}";

        assertEquals(expected, ticket.toString());
    }
}