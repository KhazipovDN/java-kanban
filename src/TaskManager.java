import java.util.HashMap;

public class TaskManager {
    HashMap<Integer,Task> tasks;
    HashMap<Integer,Subtask> subtasks;
    HashMap<Integer,Subtask> buffer;
    HashMap<Integer,Epic> epics;
    int count;

    public TaskManager() {
        tasks=new HashMap<>();
        subtasks=new HashMap<>();
        epics=new HashMap<>();
        buffer=new HashMap<>();
        count=0;
    }

    public HashMap<Integer, Task> getAllTask() {
        return tasks;
    }

    public HashMap<Integer, Subtask> getAllSubtask() {
        return subtasks;
    }

    public HashMap<Integer, Epic> getAllEpic() {
        return epics;
    }
    public void deleteAllTask() {
        tasks.clear();
    }

    public void deleteAllSubtask() {
        subtasks.clear();
    }

    public void deleteAllEpic() {
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

    public void addSubtask(Subtask newSubtask){
        count++;
        newSubtask.setId(count);
        newSubtask.parents.setSubtask(count,newSubtask);
        subtasks.put(count, newSubtask);
    }

    public void addEpic(Epic newEpic){
        count++;
        newEpic.setId(count);
        epics.put(count, newEpic);
    }

    public void updateTask(Task taskObject) {
        tasks.put(taskObject.getId(), taskObject);
    }

    public void updateSubtask(Subtask newSubtask, int id) {
        subtasks.put(id, newSubtask);
        newSubtask.parents.updateSubtaskInEpic(id,newSubtask);
        newSubtask.parents.changeStatus();
    }

    public void deleteSubtask(int id) {
        subtasks.remove(id);
    }

    public void deleteEpic(int id) {
        epics.remove(id);
    }
    public void deleteTask(int id) {
        tasks.remove(id);
    }

}


