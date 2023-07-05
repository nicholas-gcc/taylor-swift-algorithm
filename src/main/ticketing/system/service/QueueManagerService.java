package main.ticketing.system.service;

import main.ticketing.system.model.User;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QueueManagerService {
    private final WaitingRoomService waitingRoomService;
    private final int acceptableRange;

    /**
     * Initializes a new QueueManagerService instance.
     *
     * @param waitingRoomService the service to manage the waiting room
     * @param acceptableRange the acceptable range for the queue positions
     */
    public QueueManagerService(WaitingRoomService waitingRoomService, int acceptableRange) {
        this.waitingRoomService = waitingRoomService;
        this.acceptableRange = acceptableRange;
    }

    /**
     * Manages the queue by setting the queue position for each user in the waiting room.
     */
    public void manageQueue() {
        List<User> users = waitingRoomService.getWaitingRoom();

        for (User user : users) {
            int queuePosition = calculateQueuePosition(user, users);
            user.setQueuePosition(queuePosition);
        }
    }

    /**
     * Calculates the queue position for the given user.
     *
     * @param user the user whose queue position will be calculated
     * @param users the list of users in the waiting room
     * @return the queue position for the user
     */
    private int calculateQueuePosition(User user, List<User> users) {
        if (users.isEmpty()) {
            return 0;
        } else {
            int lowerBound = Math.max(0, users.size() - acceptableRange);
            return ThreadLocalRandom.current().nextInt(lowerBound, users.size() + 1);
        }
    }
}


