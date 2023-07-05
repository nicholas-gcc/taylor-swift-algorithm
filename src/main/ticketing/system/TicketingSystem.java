package main.ticketing.system;

import main.ticketing.system.model.User;
import main.ticketing.system.model.Ticket;
import main.ticketing.system.service.QueueService;
import main.ticketing.system.service.QueueManagerService;
import main.ticketing.system.service.WaitingRoomService;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the operations of the ticketing system.
 */
public class TicketingSystem {
    private final int acceptableRange;
    private final WaitingRoomService waitingRoomService;
    private final QueueManagerService queueManagerService;
    private final List<Ticket> tickets;
    private QueueService queueService;

    /**
     * Initializes a new TicketingSystem instance.
     *
     * @param acceptableRange the acceptable range for the queue positions
     */
    public TicketingSystem(int acceptableRange) {
        this.acceptableRange = acceptableRange;
        this.waitingRoomService = new WaitingRoomService();
        this.queueManagerService = new QueueManagerService(waitingRoomService, acceptableRange);
        this.tickets = new ArrayList<>();
    }

    /**
     * Sets up the tickets to be sold.
     *
     * @param ticketCount the number of tickets to be set up
     */
    public void setupTickets(int ticketCount) {
        for (int i = 0; i < ticketCount; i++) {
            tickets.add(new Ticket(i));
        }
    }

    /**
     * Adds a user to the waiting room.
     *
     * @param userId the id of the user to be added
     * @param arrivalTime the arrival time of the user
     */

    public void addUserToWaitingRoom(int userId, int arrivalTime) {
        waitingRoomService.addUser(new User(userId, arrivalTime));
        queueManagerService.manageQueue();
    }

    /**
     * Starts the ticket sale by processing the users in the waiting room.
     */
    public void startSale() {
        this.queueService = new QueueService(waitingRoomService, tickets);
        queueService.processQueue();
    }
}
