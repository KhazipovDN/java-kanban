package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EpicTest {
    @Test
    public void testEqual() {
        Epic task1 = new Epic("Test addNewSubtask", "Test addNewEpic description",1);
        Epic task2 = new Epic("Test addNewSubtask", "Test addNewEpic description",1);

        assertEquals(task1, task2);
    }

    @Test
    public void testNotEqual() {
        Epic task1 = new Epic("Test addNewSubtask", "Test addNewEpic description",1);
        Epic task2 = new Epic("Test addNewSubtask", "Test addNewEpic description",2);

        assertNotEquals(task1, task2);
    }

    @Test
    public void testEqualNew() {
        Epic task1 = new Epic("Test addNewSubtask", "Test addNewEpic description",1);
        Subtask task2 = new Subtask("Test addNewSubtask1", "Test addNewSubtask description1",1,
                Status.NEW,1, Duration.ofHours(2), LocalDateTime.of(2024, 1, 1, 0, 0));
        Subtask task3 = new Subtask("Test addNewSubtask2", "Test addNewSubtask description2",2,
                Status.NEW, 2,Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 0, 0));
        task1.setSubtask(task2);
        task1.setSubtask(task3);
        task1.changeStatus();
        assertEquals(Status.NEW, task1.getStatus());
    }

    @Test
    public void testEqualDone() {
        Epic task1 = new Epic("Test addNewSubtask", "Test addNewEpic description",1);
        Subtask task2 = new Subtask("Test addNewSubtask1", "Test addNewSubtask description1",1,
                Status.DONE,1, Duration.ofHours(2), LocalDateTime.of(2024, 1, 1, 0, 0));
        Subtask task3 = new Subtask("Test addNewSubtask2", "Test addNewSubtask description2",2,
                Status.DONE, 2,Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 0, 0));
        task1.setSubtask(task2);
        task1.setSubtask(task3);
        task1.changeStatus();
        assertEquals(Status.DONE, task1.getStatus());
    }

    @Test
    public void testEqualNewDone() {
        Epic task1 = new Epic("Test addNewSubtask", "Test addNewEpic description",1);
        Subtask task2 = new Subtask("Test addNewSubtask1", "Test addNewSubtask description1",1,
                Status.NEW,1, Duration.ofHours(2), LocalDateTime.of(2024, 1, 1, 0, 0));
        Subtask task3 = new Subtask("Test addNewSubtask2", "Test addNewSubtask description2",2,
                Status.DONE, 2,Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 0, 0));
        task1.setSubtask(task2);
        task1.setSubtask(task3);
        task1.changeStatus();
        assertEquals(Status.IN_PROGRESS, task1.getStatus());
    }

    @Test
    public void testEqualInProgres() {
        Epic task1 = new Epic("Test addNewSubtask", "Test addNewEpic description",1);
        Subtask task2 = new Subtask("Test addNewSubtask1", "Test addNewSubtask description1",1,
                Status.IN_PROGRESS,1, Duration.ofHours(2), LocalDateTime.of(2024, 1, 1, 0, 0));
        Subtask task3 = new Subtask("Test addNewSubtask2", "Test addNewSubtask description2",2,
                Status.IN_PROGRESS, 2,Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 0, 0));
        task1.setSubtask(task2);
        task1.setSubtask(task3);
        task1.changeStatus();
        assertEquals(Status.IN_PROGRESS, task1.getStatus());
    }
}