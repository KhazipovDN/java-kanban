public class Managers {
    TaskManagerInterface TaskManager=new InMemoryTaskManager();
    HistoryManager viewed = new InMemoryHistoryManager();

    public TaskManagerInterface getDefault(){
        return TaskManager;
    }

    public HistoryManager getDefaultHistory(){
        return viewed;
    }
}
