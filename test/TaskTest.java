package test;

import model.Status;
import model.Task;
import org.junit.Test;
import manager.*;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TaskTest {
    @Test
    public void testEqual() {
        Task task1 = new Task("Test addNewSubtask", "Test addNewSubtask description",Status.NEW,1,
                Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 0, 0));
        Task task2 = new Task("Test addNewSubtask", "Test addNewSubtask description",Status.NEW,1,
                Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 0, 0));

        assertEquals(task1, task2);
    }

    @Test
    public void testNotEqual() {
        Task task1 = new Task("Test addNewSubtask", "Test addNewSubtask description",Status.NEW,1,
                Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 0, 0));
        Task task2 = new Task("Test addNewSubtask", "Test addNewSubtask description", Status.NEW,2,
                Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 0, 0));

        assertNotEquals(task1, task2);
    }


}