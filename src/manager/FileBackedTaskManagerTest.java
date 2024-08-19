package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest extends TaskManagerTest{
    File tempFile= File.createTempFile("task-manager-test", ".txt");

    public FileBackedTaskManagerTest() throws IOException {
        super(new FileBackedTaskManager(File.createTempFile("task-manager-test", ".txt").toPath()));
    }


    @Test
    public void testEmptyFile(){
        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.toPath());
        manager.save();

        FileBackedTaskManager loadedManager = new FileBackedTaskManager(tempFile.toPath());;
        assertEquals(0, loadedManager.getAllTask().size());
        assertEquals(0, loadedManager.getAllSubtask().size());
        assertEquals(0, loadedManager.getAllEpic().size());
    }

    @Test
    public void setTasksInFile() throws IOException {
        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.toPath());

        Epic epic = new Epic("Test addNewSubtask", "Test addNewEpic description", 2);
        Subtask task2 = new Subtask("Test addNewSubtask1", "Test addNewSubtask description1", 2,
                Status.IN_PROGRESS, 3, Duration.ofHours(2), LocalDateTime.of(2020, 1, 1, 0, 0));
        Subtask task3 = new Subtask("Test addNewSubtask2", "Test addNewSubtask description2", 2,
                Status.IN_PROGRESS, 4, Duration.ofHours(2), LocalDateTime.of(2021, 1, 1, 0, 0));
        epic.setSubtask(task2);
        epic.setSubtask(task3);
        epic.changeStatus();

        Task task = new Task("Test addNewtask", "Test addNewSubtask description", Status.NEW, 1,
                Duration.ofHours(2), LocalDateTime.of(2022, 1, 1, 0, 0));

        manager.addTask(task);
        manager.addEpic(epic);
        manager.addSubtask(task2, task2.getEpicId());
        manager.addSubtask(task3, task3.getEpicId());

        assertEquals(1, manager.getAllTask().size());
        assertEquals(1, manager.getAllEpic().size());
        assertEquals(2, manager.getAllSubtask().size());

        FileBackedTaskManager loadFile = FileBackedTaskManager.loadFromFile(tempFile.toPath());

        assertEquals(1, loadFile.getAllTask().size());
        assertEquals(1, loadFile.getAllEpic().size());
        assertEquals(2, loadFile.getAllSubtask().size());

    }

}
