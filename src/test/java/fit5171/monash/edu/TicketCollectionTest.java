package fit5171.monash.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketCollectionTest {

    @Mock
    private Ticket mockTicket1;

    @Mock
    private Ticket mockTicket2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TicketCollection.clearTickets();

        // Set the behavior of the simulated object
        when(mockTicket1.getTicket_id()).thenReturn(101);
        when(mockTicket1.validateTicket()).thenReturn(true);

        when(mockTicket2.getTicket_id()).thenReturn(102);
        when(mockTicket2.validateTicket()).thenReturn(true);
    }

    @Test
    void testAddTicket() {
        assertTrue(TicketCollection.addTicket(mockTicket1));
        assertEquals(1, TicketCollection.getTickets().size());

        // Add the second ticket
        assertTrue(TicketCollection.addTicket(mockTicket2));
        assertEquals(2, TicketCollection.getTickets().size());
    }

    @Test
    void testAddInvalidTicket() {
        // Set invalid data
        when(mockTicket1.validateTicket()).thenReturn(false);

        assertFalse(TicketCollection.addTicket(mockTicket1));
        assertEquals(0, TicketCollection.getTickets().size());
    }

    @Test
    void testAddDuplicateTicket() {
        // Add the first ticket
        assertTrue(TicketCollection.addTicket(mockTicket1));

        // Set the second ticket to have the same ID as the first one
        when(mockTicket2.getTicket_id()).thenReturn(101);

        // Try to add duplicate tickets
        assertFalse(TicketCollection.addTicket(mockTicket2));
        assertEquals(1, TicketCollection.getTickets().size());
    }

    @Test
    void testGetTicketInfo() {
        TicketCollection.addTicket(mockTicket1);

        // Test valid ID
        Ticket result = TicketCollection.getTicketInfo(101);
        assertSame(mockTicket1, result);

        // Test invalid ID
        assertNull(TicketCollection.getTicketInfo(999));
    }

    @Test
    void testAddTicketsCollection() {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        ticketList.add(mockTicket1);
        ticketList.add(mockTicket2);

        TicketCollection.addTickets(ticketList);
        assertEquals(2, TicketCollection.getTickets().size());
    }

    @Test
    void testGetAllTickets() {
        TicketCollection.addTicket(mockTicket1);
        TicketCollection.addTicket(mockTicket2);

        ArrayList<Ticket> result = TicketCollection.getAllTickets();
        assertEquals(2, result.size());
        assertTrue(result.contains(mockTicket1));
        assertTrue(result.contains(mockTicket2));
    }

    @Test
    void testAddTicketsWithNull() {
        TicketCollection.addTickets(null);
        assertEquals(0, TicketCollection.getTickets().size());
    }

    // Integration testing
    // - Testing the integration with actual Flight and Passenger objects
    @Test
    void integrationTestTicketOperations() {
        // Create actual objects for integration testing
        Airplane airplane = new Airplane(200, "Boeing 737", 10, 100, 5);

        Flight flight = mock(Flight.class);
        Passenger passenger = mock(Passenger.class);
        when(passenger.getAge()).thenReturn(30);

        Ticket realTicket = new Ticket(100, 5000, flight, true, passenger);

        // add ticket
        assertTrue(TicketCollection.addTicket(realTicket));

        // retrieve ticket
        Ticket retrieved = TicketCollection.getTicketInfo(100);
        assertNotNull(retrieved);
        assertEquals(100, retrieved.getTicket_id());

        // Verify the status of the ticket
        assertFalse(retrieved.ticketStatus());
        retrieved.setTicketStatus(true);
        assertTrue(retrieved.ticketStatus());
    }
}