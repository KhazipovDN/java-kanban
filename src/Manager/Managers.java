package Manager;

public class Managers {
    static TaskManagerInterface TaskManager =new Manager.InMemoryTaskManager();
    static HistoryManager viewed = new InMemoryHistoryManager();


    public static Manager.TaskManagerInterface getDefault(){
        return TaskManager;
    }

    public static HistoryManager getDefaultHistory(){
        return viewed;
    }
}
