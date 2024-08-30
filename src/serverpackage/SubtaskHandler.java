package serverpackage;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManagerInterface;
import model.Subtask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {
    TaskManagerInterface manager;
    Gson gson;

    public SubtaskHandler(TaskManagerInterface manager) {
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
        }
    }

    private void setTask(Endpoint endpoint, int id, HttpExchange exchange) throws IOException {

        switch (endpoint) {
            case GET_TASKS: {
                String message = gson.toJson(manager.getAllSubtask());
                sendText(exchange, message);
                break;
            }
            case Endpoint.POST_TASK: {
                String message = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                System.out.println(message);
                Subtask subtask = gson.fromJson(message, Subtask.class);
                System.out.println(subtask);
                if (!manager.isChecked(subtask)) {
                    overlapText(exchange, "Поздадачи пересекаются.");
                } else {
                    if (subtask.getId() == 0) {
                        manager.addSubtask(subtask, subtask.getEpicId());
                    } else manager.updateSubtask(subtask);
                    sendAddTask(exchange, "Подзадача id=" + subtask.getId() + "  успешно добавлена/обновлена");
                }
                break;
            }

            case GET_TASK: {
                if (manager.getSubtask(id) == null) {
                    sendHasInteractions(exchange, "Такой подзадачи нет c id=" + id);
                } else {
                    String message = gson.toJson(manager.getSubtask(id));
                    sendText(exchange, message);
                    break;
                }
            }
            case Endpoint.DELETE: {
                manager.deleteSubtask(id);
                sendAddTask(exchange, "Подзадача успешно удалена c id=" + id);
                break;
            }
        }

    }
}