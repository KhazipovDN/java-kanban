package manager;

import model.AbstractTask;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public interface TaskManagerInterface {
    List<Subtask> subtaskFromEpic(int id);

    List<Task> getAllTask();

    List<Subtask> getAllSubtask();

    List<Epic> getAllEpic();

    void deleteAllTask();

    void deleteAllSubtask();

    void deleteAllEpic();

    Task getTask (int id);

    Subtask getSubtask (int id);

    Epic getEpic (int id);

    void addTask (Task newtask);

    void addSubtask (Subtask newSubtask, int epicId);

    void addEpic (Epic newEpic);

    void updateTask (Task taskObject);

    void updateEpic (Epic newEpic);

    void updateSubtask (Subtask newSubtask);

    void deleteSubtask (int id);

    void deleteEpic (int id);

    void deleteTask (int id);

    List<AbstractTask> getHistory();

}
