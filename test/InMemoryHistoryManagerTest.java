package manager;

import model.Status;
import model.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import java.time.Duration;
import java.time.LocalDateTime;

public class InMemoryHistoryManagerTest {

    @Test
    public void NewTestAddTask() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Assert.assertEquals(0, inMemoryTaskManager.getHistory().size());

        Task task1 = new Task("myModel.Task 1", "Description 1", Status.NEW,1,
                Duration.ofHours(2), LocalDateTime.of(2021, 1, 1, 0, 0));
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.getTask(1);
        Task task2 = new Task("myModel.Task 2", "Description 2",Status.NEW,2,
                Duration.ofHours(2), LocalDateTime.of(2022, 1, 1, 0, 0));
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.getTask(2);
        Task task3 = new Task("myModel.Task 3", "Description 3", Status.NEW,3,
                Duration.ofHours(2), LocalDateTime.of(2023, 1, 1, 0, 0));
        inMemoryTaskManager.addTask(task3);
        inMemoryTaskManager.getTask(3);

        Task task4 = new Task("myModel.Task 4", "Description 4", Status.NEW,4,
                Duration.ofHours(2), LocalDateTime.of(2024, 1, 1, 0, 0));
        inMemoryTaskManager.addTask(task4);
        inMemoryTaskManager.getTask(4);

        Assert.assertEquals(4, inMemoryTaskManager.getAllTask().size());
        Assert.assertEquals(4, inMemoryTaskManager.getHistory().size());

        Assert.assertEquals(task1, inMemoryTaskManager.getHistory().get(0));
        Assert.assertEquals(task2, inMemoryTaskManager.getHistory().get(1));
        Assert.assertEquals(task3, inMemoryTaskManager.getHistory().get(2));
        Assert.assertEquals(task4, inMemoryTaskManager.getHistory().get(3));

        inMemoryTaskManager.getTask(1);

        Assert.assertEquals(task2, inMemoryTaskManager.getHistory().get(0));
        Assert.assertEquals(task3, inMemoryTaskManager.getHistory().get(1));
        Assert.assertEquals(task4, inMemoryTaskManager.getHistory().get(2));
        Assert.assertEquals(task1, inMemoryTaskManager.getHistory().get(3));

        inMemoryTaskManager.getTask(1);
        Assert.assertEquals(4, inMemoryTaskManager.getHistory().size());

        inMemoryTaskManager.deleteTask(4);

        Assert.assertEquals(3, inMemoryTaskManager.getHistory().size());
        Assert.assertEquals(task2, inMemoryTaskManager.getHistory().get(0));
        Assert.assertEquals(task3, inMemoryTaskManager.getHistory().get(1));
        Assert.assertEquals(task1, inMemoryTaskManager.getHistory().get(2));

        inMemoryTaskManager.deleteTask(1);

        Assert.assertEquals(2, inMemoryTaskManager.getHistory().size());
        Assert.assertEquals(task2, inMemoryTaskManager.getHistory().get(0));
        Assert.assertEquals(task3, inMemoryTaskManager.getHistory().get(1));

        inMemoryTaskManager.deleteTask(2);

        Assert.assertEquals(1, inMemoryTaskManager.getHistory().size());
        Assert.assertEquals(task3, inMemoryTaskManager.getHistory().get(0));
    }
}
