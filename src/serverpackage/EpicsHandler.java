package serverpackage;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManagerInterface;
import model.Epic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {
    TaskManagerInterface manager;
    Gson gson;

    public EpicsHandler(TaskManagerInterface manager) {
        this.manager = manager;
        gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String requestMethod = exchange.getRequestMethod();
        String requestPath = exchange.getRequestURI().getPath();

        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 2) {
            if (requestMethod.equalsIgnoreCase("GET")) {
                setTask(Endpoint.GET_TASKS, 0, exchange);
            } else if (requestMethod.equalsIgnoreCase("POST")) {
                setTask(Endpoint.POST_TASK, 0, exchange);
            }
        } else if (pathParts.length == 3) {
            if (requestMethod.equalsIgnoreCase("GET")) {
                setTask(Endpoint.GET_TASK, Integer.parseInt(pathParts[1]), exchange);
            } else if (requestMethod.equalsIgnoreCase("DELETE")) {
                setTask(Endpoint.DELETE, Integer.parseInt(pathParts[1]), exchange);
            }
        } else if (pathParts.length == 4) {
            setTask(Endpoint.GET_SUBTASK, Integer.parseInt(pathParts[1]), exchange);
        }
    }

    private void setTask(Endpoint endpoint, int id, HttpExchange exchange) throws IOException {

        switch (endpoint) {
            case GET_TASKS: {
                String message = gson.toJson(manager.getAllEpic());
                sendText(exchange, message);
                break;
            }
            case Endpoint.POST_TASK: {
                String message = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Epic epic = gson.fromJson(message, Epic.class);
                if (epic.getId() == 0) {
                    manager.addEpic(epic);
                } else manager.updateEpic(epic);
                manager.addEpic(epic);
                sendAddTask(exchange, "Эпик c id=" + epic.getId() + " успешно добавлен");
                break;
            }
            case GET_TASK: {
                if (manager.getEpic(id) == null) {
                    sendHasInteractions(exchange, "Такого эпика нет c id=" + id);
                } else {
                    String message = gson.toJson(manager.getEpic(id));
                    sendText(exchange, message);
                    break;
                }
            }
            case Endpoint.DELETE: {
                manager.deleteEpic(id);
                sendAddTask(exchange, "Эпик c id=" + id + " успешно удален");
                break;
            }
            case Endpoint.GET_SUBTASK: {
                if (manager.subtaskFromEpic(id) == null) {
                    sendHasInteractions(exchange, "Такого эпика нет id=" + id);
                } else {
                    String message = gson.toJson(manager.subtaskFromEpic(id));
                    sendText(exchange, message);
                    break;
                }
            }
        }

    }
}