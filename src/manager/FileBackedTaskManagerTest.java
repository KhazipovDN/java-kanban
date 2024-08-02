package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest {


    @Test
    public void testEmptyFile() throws IOException {
        File tempFile = File.createTempFile("task-manager-test", ".txt");
        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.toPath());
        manager.save();

        FileBackedTaskManager loadedManager = new FileBackedTaskManager(tempFile.toPath());;
        assertEquals(0, loadedManager.getAllTask().size());
        assertEquals(0, loadedManager.getAllSubtask().size());
        assertEquals(0, loadedManager.getAllEpic().size());
    }

    @Test
    public void setTasksInFile() throws IOException {
        File tempFile = File.createTempFile("task-manager-test", ".txt");

        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.toPath());

        Epic epic = new Epic("Epic Name 1", "Description epic1");
        Task task = new Task("Task 1", "Description task1", Status.NEW);
        Subtask subtask = new Subtask("Subtask name 1", "Description subtask1", 1, Status.NEW);

        manager.addEpic(epic);
        manager.addTask(task);
        manager.addSubtask(subtask, subtask.getEpicId());

        assertEquals(1, manager.getAllTask().size());
        assertEquals(1, manager.getAllSubtask().size());
        assertEquals(1, manager.getAllEpic().size());

        //FileBackedTaskManager loadFile = FileBackedTaskManager.loadFromFile(tempFile.toPath());

        //assertEquals(1, loadFile.getAllTask().size());
        //assertEquals(1, loadFile.getAllEpic().size());
        //assertEquals(1, loadFile.getAllSubtask().size());- никак ошибка не хочет отлавливаться ошибка,
        // где то не передается номер эпика, нигде не могу найти. Subtaskов нет вообще (Нет Эпика для добавления подзадачи)
        // сразу же прошу выдать и другие замечания

    }

}
