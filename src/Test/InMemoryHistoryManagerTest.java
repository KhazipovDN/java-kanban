package Test;

import Manager.InMemoryHistoryManager;
import Manager.InMemoryTaskManager;
import Model.Status;
import Model.Task;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class InMemoryHistoryManagerTest {

    private InMemoryTaskManager inMemoryTaskManager= new InMemoryTaskManager();;
/*
    @BeforeEach
    public void setUp() {
        inMemoryTaskManager = new InMemoryTaskManager();
    }*/

    @Test
    public void testAddTask() {
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

    @Test
    public void NewTestAddTask() {
        Task task1 = new Task("Model.Task 1", "Description 1", Status.NEW,1);
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.getTask(1);
        Task task2 = new Task("Model.Task 2", "Description 2",Status.NEW,1);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.getTask(2);

        assertEquals(2, inMemoryTaskManager.getAllTask().size());
        assertEquals(2, inMemoryTaskManager.getHistory().size());

        assertEquals(task1, inMemoryTaskManager.getHistory().get(0));
        assertEquals(task2, inMemoryTaskManager.getHistory().get(1));

        inMemoryTaskManager.getTask(1);

        assertEquals(task2, inMemoryTaskManager.getHistory().get(0));
        assertEquals(task1, inMemoryTaskManager.getHistory().get(1));


    }

}
