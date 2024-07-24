package manager;

public class Managers {
    static TaskManagerInterface TaskManager = new InMemoryTaskManager();
    static HistoryManager viewed = new InMemoryHistoryManager();


    public static TaskManagerInterface getDefault() {
        return TaskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return viewed;
    }
}
