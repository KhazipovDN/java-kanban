package manager;

import model.Status;
import model.Task;
import org.junit.Assert;
import org.junit.Test;

public class InMemoryHistoryManagerTest {

    private InMemoryTaskManager inMemoryTaskManager= new InMemoryTaskManager();;

    @Test
    public void NewTestAddTask() {
        Task task1 = new Task("myModel.Task 1", "Description 1", Status.NEW,1);
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.getTask(1);
        Task task2 = new Task("myModel.Task 2", "Description 2",Status.NEW,2);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.getTask(2);
        Task task3 = new Task("myModel.Task 3", "Description 3", Status.NEW,3);
        inMemoryTaskManager.addTask(task3);
        inMemoryTaskManager.getTask(3);

        Assert.assertEquals(3, inMemoryTaskManager.getAllTask().size());
        Assert.assertEquals(3, inMemoryTaskManager.getHistory().size());

        Assert.assertEquals(task1, inMemoryTaskManager.getHistory().get(0));
        Assert.assertEquals(task2, inMemoryTaskManager.getHistory().get(1));
        Assert.assertEquals(task3, inMemoryTaskManager.getHistory().get(2));

        inMemoryTaskManager.getTask(1);

        Assert.assertEquals(task2, inMemoryTaskManager.getHistory().get(0));
        Assert.assertEquals(task3, inMemoryTaskManager.getHistory().get(1));
        Assert.assertEquals(task1, inMemoryTaskManager.getHistory().get(2));


    }

}
