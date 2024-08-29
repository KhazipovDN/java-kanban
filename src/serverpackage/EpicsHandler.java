package serverpackage;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Epic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String requestMethod = exchange.getRequestMethod();
        String requestPath = exchange.getRequestURI().getPath();

        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 1) {
            if (requestMethod.equalsIgnoreCase("GET")) {
                setTask(Endpoint.GET_TASKS, 0, exchange);
            } else if (requestMethod.equalsIgnoreCase("POST")) {
                setTask(Endpoint.POST_TASK, 0, exchange);
            }
        } else if (pathParts.length == 2) {
            if (requestMethod.equalsIgnoreCase("GET")) {
                setTask(Endpoint.GET_TASK, Integer.parseInt(pathParts[1]), exchange);
            } else if (requestMethod.equalsIgnoreCase("DELETE")) {
                setTask(Endpoint.DELETE, Integer.parseInt(pathParts[1]), exchange);
            }
        } else if (pathParts.length == 3) {
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
                manager.addEpic(epic);
                sendAddTask(exchange, "Эпик успешно добавлен");
                break;
            }
            case GET_TASK: {
                if (manager.getEpic(id) == null) {
                    sendHasInteractions(exchange, "Такого эпика нет");
                } else {
                    String message = gson.toJson(manager.getEpic(id));
                    sendText(exchange, message);
                    break;
                }
            }
            case Endpoint.DELETE: {
                manager.deleteEpic(id);
                sendAddTask(exchange, "Эпик успешно удален");
                break;
            }
            case Endpoint.GET_SUBTASK: {
                if (manager.subtaskFromEpic(id) == null) {
                    sendHasInteractions(exchange, "Такого эпика нет");
                } else {
                    String message = gson.toJson(manager.subtaskFromEpic(id));
                    sendText(exchange, message);
                    break;
                }
            }
        }

    }
}