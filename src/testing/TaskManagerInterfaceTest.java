package testing;

import manager.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerInterfaceTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @Test
    void addNewEpic() {
        Epic epic = new Epic("Test addNewSubtask", "Test addNewSubtask description");
        taskManager.addEpic(epic);
        final int epicId = epic.getId();

        final Epic savedEpic = taskManager.getEpic(epicId);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getAllEpic();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }


    @Test
    void addNewSubtask() {
        Epic epic = new Epic("Test addNewSubtask", "Test addNewSubtask description");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Test addNewSubtask", "Test addNewSubtask description",1, Status.NEW);
        taskManager.addSubtask(subtask,1);
        final int subtaskId = subtask.getId();

        final Subtask savedSubtask = taskManager.getSubtask(subtaskId);

        assertNotNull(subtask, "Задача не найдена.");
        assertEquals(subtask, savedSubtask, "Задачи не совпадают.");

        final List<Subtask> subtasks = taskManager.getAllSubtask();

        assertNotNull(subtasks, "Задачи не возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, subtasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskManager.addTask(task);
        final int taskId = task.getId();

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTask();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    public void checkIdConflict() {
        Task task1 = new Task("Test addFirstTask", "Test addFirstTask description", Status.NEW);
        taskManager.addTask(task1);

        Task task2 = new Task("Test addSecondTask", "Test addSecondTask description", Status.NEW,2);
        assertNotEquals(task1, task2);
    }

    @Test
    public void immutabilityTask() {
        Task task1 = new Task("Test addFirstTask", "Test addFirstTask description", Status.NEW,1);
        taskManager.addTask(task1);

        Task task2 = taskManager.getTask(1);
        assertEquals(task1, task2);
    }


}