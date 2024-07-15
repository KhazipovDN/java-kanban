package testing;

import Manager.InMemoryTaskManager;
import Model.Status;
import Model.Task;
import org.junit.Test;
import static org.junit.Assert.*;

public class InMemoryHistoryManagerTest {

    @Test
    public void testAddTask() {
        InMemoryTaskManager inMemoryTaskManager=new InMemoryTaskManager();
        Task task1 = new Task("Model.Task 1", "Description 1", Status.NEW,1);
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.getTask(1);
        Task task2 = new Task("Model.Task 2", "Description 2",Status.NEW,1);
        inMemoryTaskManager.updateTask(task2);
        inMemoryTaskManager.getTask(2);

        assertEquals(1, inMemoryTaskManager.getAllTask().size());
        assertEquals(2, inMemoryTaskManager.getHistory().size());
        assertNotEquals(inMemoryTaskManager.getHistory().get(0),inMemoryTaskManager.getHistory().get(1));

    }

}
