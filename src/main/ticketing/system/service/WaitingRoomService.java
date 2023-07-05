package main.ticketing.system.service;

import main.ticketing.system.model.User;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manages the operations of the waiting room.
 */
public class WaitingRoomService {
    private final List<User> waitingRoom = new CopyOnWriteArrayList<>();

    /**
     * Adds a user to the waiting room.
     *
     * @param user the user to be added to the waiting room
     */
    public void addUser(User user) {
        waitingRoom.add(user);
    }

    /**
     * Returns the list of users in the waiting room.
     *
     * @return the list of users in the waiting room
     */
    public List<User> getWaitingRoom() {
        return waitingRoom;
    }
}
