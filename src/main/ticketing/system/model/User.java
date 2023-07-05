package main.ticketing.system.model;

/**
 * Represents a user in the ticketing system.
 */
public class User {
    private final int id;
    private final int arrivalTime;
    private Integer queuePosition;

    /**
     * Initializes a new User instance.
     *
     * @param id the id of the user
     * @param arrivalTime the arrival time of the user
     */
    public User(int id, int arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    /**
     * Returns the id of the user.
     *
     * @return the id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the arrival time of the user.
     *
     * @return the arrival time of the user
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Returns the queue position of the user.
     *
     * @return the queue position of the user
     */
    public Integer getQueuePosition() {
        return queuePosition;
    }

    /**
     * Sets the queue position of the user.
     *
     * @param queuePosition the queue position to be set
     */
    public void setQueuePosition(Integer queuePosition) {
        this.queuePosition = queuePosition;
    }
}
