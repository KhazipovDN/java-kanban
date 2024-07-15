package TestModule;

import Model.Status;
import Model.Subtask;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SubtaskTest {
    @Test
    public void testEqual() {
        Subtask task1 = new Subtask("Test addNewSubtask", "Test addNewSubtask description",1, Status.NEW,1);
        Subtask task2 = new Subtask("Test addNewSubtask", "Test addNewSubtask description",1, Status.NEW,1);

        assertEquals(task1, task2);
    }

    @Test
    public void testNotEqual() {
        Subtask task1 = new Subtask("Test addNewSubtask", "Test addNewSubtask description",1, Status.NEW,1);
        Subtask task2 = new Subtask("Test addNewSubtask", "Test addNewSubtask description",1, Status.NEW,2);

        assertNotEquals(task1, task2);
    }
}