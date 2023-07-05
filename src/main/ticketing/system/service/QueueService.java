package main.ticketing.system.service;

import main.ticketing.system.model.User;
import main.ticketing.system.model.Ticket;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Handles the operations of the queue.
 */
public class QueueService {
    private final WaitingRoomService waitingRoomService;
    private final List<Ticket> tickets;

    /**
     * Initializes a new QueueService instance.
     *
     * @param waitingRoomService the service to manage the waiting room
     * @param tickets the list of tickets available for sale
     */
    public QueueService(WaitingRoomService waitingRoomService, List<Ticket> tickets) {
        this.waitingRoomService = waitingRoomService;
        this.tickets = new CopyOnWriteArrayList<>(tickets);
    }

    /**
     * Processes the users in the waiting room, allowing them to attempt to purchase tickets.
     */
    public void processQueue() {
        waitingRoomService.getWaitingRoom().stream()
                .sorted(Comparator.comparing(User::getQueuePosition))
                .forEach(this::attemptPurchase);
    }

    /**
     * Allows the given user to attempt to purchase a ticket.
     *
     * @param user the user who will attempt to purchase a ticket
     */
    private void attemptPurchase(User user) {
        for (Ticket ticket : tickets) {
            if (ticket.tryLock()) {
                System.out.println("User " + user.getId() + " arriving at time " + user.getArrivalTime() + " purchased ticket " + ticket.getId());
                waitingRoomService.getWaitingRoom().remove(user);  // Remove user from waiting room after purchase
                tickets.remove(ticket);
                break;
            }
        }
    }
    /**
     * Returns the list of tickets.
     *
     * @return the list of tickets
     */
    public List<Ticket> getTickets() {
        return tickets;
    }
}
