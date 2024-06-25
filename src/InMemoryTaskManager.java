import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManagerInterface{
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Subtask> subtasks;
    private HashMap<Integer, Epic> epics;
    int count;
    HistoryManager historyManager;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        count = 0;
        historyManager=new InMemoryHistoryManager();
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
        tasks.clear();
    }
    @Override
    public void deleteAllSubtask() {
        for (Epic e : epics.values()) {
            e.deleteAllSubstack();
            e.changeStatus();
        }
        subtasks.clear();
    }
    @Override
    public void deleteAllEpic() {
        subtasks.clear();
        epics.clear();
    }
    @Override
    public Task getTask(int id) {
        if (historyManager.getHistory().size()<10) historyManager.add(tasks.get(id));
        else {historyManager.getHistory().remove(0);
            historyManager.add(tasks.get(id));}
        return tasks.get(id);
    }
    @Override
    public Subtask getSubtask(int id) {
        if (historyManager.getHistory().size()<10) historyManager.add(subtasks.get(id));
        else {historyManager.getHistory().remove(0);
            historyManager.add(subtasks.get(id));}
        return subtasks.get(id);
    }
    @Override
    public Epic getEpic(int id) {
        if (historyManager.getHistory().size()<10) historyManager.add(epics.get(id));
        else {historyManager.getHistory().remove(0);
            historyManager.add(epics.get(id));}
        return epics.get(id);
    }
    @Override
    public void addTask(Task newtask) {
        count++;
        newtask.setId(count);
        tasks.put(count, newtask);
    }
    public void addTaskWithId(Task newtask, int id) {
        newtask.setId(id);
        tasks.put(id, newtask);
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

    public void addSubtaskWithId(Subtask newSubtask, int epicId, int id) {
        newSubtask.setId(id);
        subtasks.put(id, newSubtask);
        Epic parentEpic = epics.get(epicId);
        parentEpic.setSubtask(newSubtask);
        parentEpic.changeStatus();
    }

    @Override
    public void addEpic(Epic newEpic) {
        count++;
        newEpic.setId(count);
        epics.put(count, newEpic);
    }

    public void addEpicWithId(Epic newEpic, int id) {
        newEpic.setId(id);
        epics.put(id, newEpic);
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
            for (int i : epics.get(id).getSons().keySet()) {
                subtasks.remove(i);
            }
            epics.remove(id);
        }
    }
    @Override
    public void deleteTask(int id) {
        if (!tasks.containsKey(id))
            System.out.println("Нет такой подзадачи по такому идентификатору");
        else {
            tasks.remove(id);
        }
    }

    @Override
    public List<AbstractTask> getHistory(){
        return historyManager.getHistory();
    }

}
