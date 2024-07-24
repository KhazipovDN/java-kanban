package manager;

import model.AbstractTask;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManagerInterface {
    private Map<Integer, Task> tasks;
    private Map<Integer, Subtask> subtasks;
    private Map<Integer, Epic> epics;
    int count;
    HistoryManager historyManager;
    Managers managers;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        count = 0;
        managers = new Managers();
        historyManager = managers.getDefaultHistory();
    }

    @Override
    public ArrayList<Subtask> subtaskFromEpic(int id) {
        ArrayList<Subtask> arrSubtasks = new ArrayList<>();
        if (epics.get(id).getSons() == null) return arrSubtasks;
        else {
            arrSubtasks = new ArrayList<>(epics.get(id).getSons().values());
            return arrSubtasks;
        }
    }

    @Override
    public ArrayList<Task> getAllTask() {
        ArrayList<Task> arrTask = new ArrayList<>(tasks.values());
        return arrTask;
    }

    @Override
    public ArrayList<Subtask> getAllSubtask() {
        ArrayList<Subtask> arrSubtasks = new ArrayList<>(subtasks.values());
        return arrSubtasks;
    }

    @Override
    public ArrayList<Epic> getAllEpic() {
        ArrayList<Epic> arrEpics = new ArrayList<>(epics.values());
        return arrEpics;
    }

    @Override
    public void deleteAllTask() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public void deleteAllSubtask() {
        for (Epic e : epics.values()) {
            e.deleteAllSubstack();
            e.changeStatus();
        }
        for (Subtask subtask : subtasks.values()) {
            historyManager.remove(subtask.getId());
        }
        subtasks.clear();
    }

    @Override
    public void deleteAllEpic() {
        for (Subtask subtask : subtasks.values()) {
            historyManager.remove(subtask.getId());
        }
        for (Epic e : epics.values()) {
            historyManager.remove(e.getId());
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public Task getTask(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void addTask(Task newtask) {
        count++;
        newtask.setId(count);
        tasks.put(count, newtask);
    }

    @Override
    public void addSubtask(Subtask newSubtask, int epicId) {
        if (epics.containsKey(epicId)) {
            count++;
            newSubtask.setId(count);
            subtasks.put(count, newSubtask);
            Epic parentEpic = epics.get(epicId);
            parentEpic.setSubtask(newSubtask);
            parentEpic.changeStatus();
        } else
            System.out.println("Нет Эпика!");
    }

    @Override
    public void addEpic(Epic newEpic) {
        count++;
        newEpic.setId(count);
        epics.put(count, newEpic);
    }

    @Override
    public void updateTask(Task taskObject) {
        if (tasks.containsKey(taskObject.getId()))
            tasks.put(taskObject.getId(), taskObject);
        else
            System.out.println("Нет задачи для обновления");
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (epics.containsKey(newEpic.getId())) {
            newEpic.setStatus(epics.get(newEpic.getId()).getStatus());
            newEpic.setSons(epics.get(newEpic.getId()).getSons());
            epics.put(newEpic.getId(), newEpic);
        } else
            System.out.println("Нет эпиков для обновления");
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        int id = newSubtask.getId();
        if (subtasks.containsKey(id)) {
            if (newSubtask.getEpicId() == subtasks.get(id).getEpicId()) {
                subtasks.put(id, newSubtask);
                epics.get(newSubtask.getEpicId()).updateSubtaskInEpic(id, newSubtask);
                epics.get(newSubtask.getEpicId()).changeStatus();
            } else {
                System.out.println("Не совпадают номера эпиков");
            }
        } else
            System.out.println("Нет подзадачи для обновления");
    }

    @Override
    public void deleteSubtask(int id) {
        if (!subtasks.containsKey(id))
            System.out.println("Нет такой подзадачи по такому идентификатору");
        else {
            historyManager.remove(id);
            int epicInt = subtasks.get(id).getEpicId();
            Epic thisEpic = epics.get(epicInt);
            thisEpic.deleteUnitSubstack(id);
            thisEpic.changeStatus();
            subtasks.remove(id);
        }
    }

    @Override
    public void deleteEpic(int id) {
        if (!epics.containsKey(id))
            System.out.println("Нет такого эпика по такому идентификатору");
        else {
            historyManager.remove(id);
            for (int i : epics.get(id).getSons().keySet()) {
                subtasks.remove(i);
                historyManager.remove(i);
            }
            epics.remove(id);
        }
    }

    @Override
    public void deleteTask(int id) {
        if (!tasks.containsKey(id))
            System.out.println("Нет такой подзадачи по такому идентификатору");
        else {
            historyManager.remove(id);
            tasks.remove(id);
        }
    }

    @Override
    public List<AbstractTask> getHistory() {
        return historyManager.getHistory();
    }
}
