package main.ticketing.system.model;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a ticket in the ticketing system.
 */
public class Ticket {
    private final int id;
    private AtomicBoolean locked = new AtomicBoolean(false);

    /**
     * Initializes a new Ticket instance.
     *
     * @param id the id of the ticket
     */
    public Ticket(int id) {
        this.id = id;
    }

    /**
     * Returns the id of the ticket.
     *
     * @return the id of the ticket
     */
    public int getId() {
        return id;
    }

    /**
     * Checks if the ticket is locked.
     *
     * @return true if the ticket is locked, false otherwise
     */
    public boolean isLocked() {
        return locked.get();
    }

    /**
     * Tries to lock the ticket for purchase. If the ticket is already locked,
     * it cannot be locked again.
     *
     * @return true if the ticket was successfully locked, false otherwise
     */
    public boolean tryLock() {
        return locked.compareAndSet(false, true);
    }
}
