package testing;

import modelFolder.Status;
import modelFolder.Task;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TaskTest {
    @Test
    public void testEqual() {
        Task task1 = new Task("Test addNewSubtask", "Test addNewSubtask description",Status.NEW,1);
        Task task2 = new Task("Test addNewSubtask", "Test addNewSubtask description",Status.NEW,1);

        assertEquals(task1, task2);
    }

    @Test
    public void testNotEqual() {
        Task task1 = new Task("Test addNewSubtask", "Test addNewSubtask description",Status.NEW,1);
        Task task2 = new Task("Test addNewSubtask", "Test addNewSubtask description", Status.NEW,2);

        assertNotEquals(task1, task2);
    }


}