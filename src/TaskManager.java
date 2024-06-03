import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    HashMap<Integer,Task> tasks;
    HashMap<Integer,Subtask> subtasks;
    HashMap<Integer,Epic> epics;
    ArrayList<Task> arrTasks;
    ArrayList<Subtask> arrSubtasks;
    ArrayList<Epic> arrEpics;
    int count;

    public TaskManager() {
        tasks=new HashMap<>();
        subtasks=new HashMap<>();
        epics=new HashMap<>();
        count=0;
    }

    public ArrayList<Subtask> subtaskFromEpic(int id){
        arrSubtasks=new ArrayList<>();
        for (Subtask s:epics.get(id).getSons().values())
            arrSubtasks.add(s);
        return arrSubtasks;
    }

    public ArrayList<Task> getAllTask() {
        arrTasks=new ArrayList<>();
        for (Task t:tasks.values())
            arrTasks.add(t);
        return arrTasks;
    }

    public ArrayList<Subtask> getAllSubtask() {
        arrSubtasks=new ArrayList<>();
        for (Subtask s:subtasks.values())
            arrSubtasks.add(s);
        return arrSubtasks;
    }

    public ArrayList<Epic> getAllEpic() {
        arrEpics=new ArrayList<>();
        for (Epic e:epics.values())
            arrEpics.add(e);
        return arrEpics;
    }
    public void deleteAllTask() {
        tasks.clear();
    }

    public void deleteAllSubtask() {
        for (Epic e:epics.values()){
        e.getSons().clear();
        e.changeStatus();
        }
        subtasks.clear();
    }

    public void deleteAllEpic() {
        deleteAllSubtask();
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
            epics.get(epicId).setSubtask(count, newSubtask);
            epics.get(epicId).changeStatus();
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

    public void updateSubtask(Subtask newSubtask, int id) {
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
        int epicInt=subtasks.get(id).getEpicId();
        Epic thisEpic=epics.get(epicInt);
        thisEpic.deleteUnitSubstack(id);
        thisEpic.changeStatus();
        subtasks.remove(id);
    }

    public void deleteEpic(int id) {
        for(int i:epics.get(id).getSons().keySet()){
        subtasks.remove(i);
        }
        epics.remove(id);
    }
    public void deleteTask(int id) {
        tasks.remove(id);
    }

}


