package serverpackage;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import manager.TaskManagerInterface;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    HttpServer server;
    TaskManagerInterface manager;
    Gson gson;

    public HttpTaskServer(TaskManagerInterface manager) throws IOException {
        this.manager = manager;
        gson = new Gson();
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/tasks", new TaskHandler(manager));
        server.createContext("/subtasks", new SubtaskHandler(manager));
        server.createContext("/epics", new EpicsHandler(manager));
        server.createContext("/history", new HistoryHandler(manager));
        server.createContext("/prioritized", new PrioritizedHandler(manager));

    }
    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

}
