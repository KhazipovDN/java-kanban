import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer,Task> tasks;
    private HashMap<Integer,Subtask> subtasks;
    private HashMap<Integer,Epic> epics;
    int count;

    public TaskManager() {
        tasks=new HashMap<>();
        subtasks=new HashMap<>();
        epics=new HashMap<>();
        count=0;
    }

    public ArrayList<Subtask> subtaskFromEpic(int id){
        ArrayList<Subtask> arrSubtasks=new ArrayList<>();
        if(epics.get(id).getSons()==null) return arrSubtasks;
        else {
            arrSubtasks = new ArrayList<>(epics.get(id).getSons().values());
            return arrSubtasks;
        }
    }

    public ArrayList<Task> getAllTask() {
        ArrayList<Task> arrTask =new ArrayList<>(tasks.values());
        return arrTask;
    }

    public ArrayList<Subtask> getAllSubtask() {
        ArrayList<Subtask> arrSubtasks =new ArrayList<>(subtasks.values());
        return arrSubtasks;
    }

    public ArrayList<Epic> getAllEpic() {
        ArrayList<Epic> arrEpics =new ArrayList<>(epics.values());
        return arrEpics;
    }

    public void deleteAllTask() {
        tasks.clear();
    }

    public void deleteAllSubtask() {
        for (Epic e:epics.values()){
        e.deleteAllSubstack();
        e.changeStatus();
        }
        subtasks.clear();
    }

    public void deleteAllEpic() {
        subtasks.clear();
        epics.clear();
    }
    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public void addTask(Task newtask){
        count++;
        newtask.setId(count);
        tasks.put(count, newtask);
    }

    public void addSubtask(Subtask newSubtask,int epicId) {
        if (epics.containsKey(epicId)) {
            count++;
            newSubtask.setId(count);
            subtasks.put(count, newSubtask);
            Epic parentEpic=epics.get(epicId);
            parentEpic.setSubtask(newSubtask);
            parentEpic.changeStatus();
        }
        else
            System.out.println("Нет Эпика!");

    }
    public void addEpic(Epic newEpic){
        count++;
        newEpic.setId(count);
        epics.put(count, newEpic);
    }

    public void updateTask(Task taskObject) {
        if (tasks.containsKey(taskObject.getId()))
            tasks.put(taskObject.getId(), taskObject);
        else
            System.out.println("Нет задачи для обновления");
    }

    public void updateEpic(Epic newEpic) {
        if (epics.containsKey(newEpic.getId())){
            newEpic.setStatus(epics.get(newEpic.getId()).getStatus());
            newEpic.setSons(epics.get(newEpic.getId()).getSons());
            epics.put(newEpic.getId(), newEpic);
        }
        else
            System.out.println("Нет эпиков для обновления");
    }

    public void updateSubtask(Subtask newSubtask) {
        int id=newSubtask.getId();
        if (subtasks.containsKey(id)){
            if (newSubtask.getEpicId()==subtasks.get(id).getEpicId()) {
                subtasks.put(id, newSubtask);
                epics.get(newSubtask.getEpicId()).updateSubtaskInEpic(id, newSubtask);
                epics.get(newSubtask.getEpicId()).changeStatus();
            } else {
                System.out.println("Не совпадают номера эпиков");
            }
        } else
            System.out.println("Нет подзадачи для обновления");
    }

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
    public void deleteTask(int id) {
        if (!tasks.containsKey(id))
            System.out.println("Нет такой подзадачи по такому идентификатору");
        else {
            tasks.remove(id);
        }
    }
}


