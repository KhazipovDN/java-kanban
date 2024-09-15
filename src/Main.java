import manager.InMemoryTaskManager;
import manager.TaskManagerInterface;
import serverpackage.HttpTaskServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Поехали!");
        TaskManagerInterface manager = new InMemoryTaskManager();
        // передаём его в качестве аргумента в конструктор HttpTaskServer
        HttpTaskServer taskServer = new HttpTaskServer(manager);
        taskServer.start();



    }
}
