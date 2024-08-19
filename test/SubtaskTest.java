package test;

import model.Status;
import model.Subtask;
import org.junit.Test;
import org.manager.*;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SubtaskTest {
    @Test
    public void testEqual() {
        Subtask task1 = new Subtask("Test addNewSubtask", "Test addNewSubtask description",1,
                Status.NEW, Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 0, 0));
        Subtask task2 = new Subtask("Test addNewSubtask", "Test addNewSubtask description",1,
                Status.NEW, Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 0, 0));

        assertEquals(task1, task2);
    }

    @Test
    public void testNotEqual() {
        Subtask task1 = new Subtask("Test addNewSubtask1", "Test addNewSubtask description1",1,
                Status.NEW,1, Duration.ofHours(2), LocalDateTime.of(2024, 1, 1, 0, 0));
        Subtask task2 = new Subtask("Test addNewSubtask2", "Test addNewSubtask description2",2,
                Status.NEW, 2,Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 0, 0));

        assertNotEquals(task1, task2);
    }
}