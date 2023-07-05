package test.ticketing.system;

import main.ticketing.system.model.User;
import main.ticketing.system.model.Ticket;
import main.ticketing.system.service.QueueService;
import main.ticketing.system.service.QueueManagerService;
import main.ticketing.system.service.WaitingRoomService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class AppTest {
    @Test
    public void testProgress() {
        // Arrange
        int acceptableRange = 100;
        WaitingRoomService waitingRoomService = new WaitingRoomService();
        QueueManagerService queueManagerService = new QueueManagerService(waitingRoomService, acceptableRange);
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tickets.add(new Ticket(i));
        }
        QueueService queueService = new QueueService(waitingRoomService, tickets);

        // Act
        for (int i = 0; i < 15; i++) {
            waitingRoomService.addUser(new User(i, 0));
        }
        queueManagerService.manageQueue();
        queueService.processQueue();

        // Assert
        assertEquals(0, queueService.getTickets().size());
    }

    @Test
    public void testMutualExclusion() {
        // Arrange
        int acceptableRange = 100;
        WaitingRoomService waitingRoomService = new WaitingRoomService();
        QueueManagerService queueManagerService = new QueueManagerService(waitingRoomService, acceptableRange);
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tickets.add(new Ticket(i));
        }
        QueueService queueService = new QueueService(waitingRoomService, tickets);

        // Act & Assert
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 15; i++) {
                waitingRoomService.addUser(new User(i, 0));
            }
            queueManagerService.manageQueue();
            queueService.processQueue();
        });
    }

    @Test
    public void testNoStarvation() {
        // Arrange
        int acceptableRange = 100;
        WaitingRoomService waitingRoomService = new WaitingRoomService();
        QueueManagerService queueManagerService = new QueueManagerService(waitingRoomService, acceptableRange);
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tickets.add(new Ticket(i));
        }
        QueueService queueService = new QueueService(waitingRoomService, tickets);

        // Act
        for (int i = 0; i < 15; i++) {
            waitingRoomService.addUser(new User(i, 0));
        }
        queueManagerService.manageQueue();
        queueService.processQueue();

        // Assert
        assertEquals(5, waitingRoomService.getWaitingRoom().size());
    }

    @Test
    public void testFairness() {
        // Arrange
        int acceptableRange = 100;
        WaitingRoomService waitingRoomService = new WaitingRoomService();
        QueueManagerService queueManagerService = new QueueManagerService(waitingRoomService, acceptableRange);
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tickets.add(new Ticket(i));
        }
        QueueService queueService = new QueueService(waitingRoomService, tickets);

        // Act
        for (int i = 0; i < 10; i++) {
            waitingRoomService.addUser(new User(i, 0));
        }
        queueManagerService.manageQueue();

        // Assert
        for (User user : waitingRoomService.getWaitingRoom()) {
            assertTrue(Math.abs(user.getQueuePosition() - waitingRoomService.getWaitingRoom().size()) <= acceptableRange);
        }
    }


}
