package serverpackage;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {

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
        }
    }

    private void setTask(Endpoint endpoint, int id, HttpExchange exchange) throws IOException {

        switch (endpoint) {
            case GET_TASKS: {
                String message;
                if (manager.getAllTask() == null) {
                    message = "Задач нет";
                } else message = gson.toJson(manager.getAllTask());
                sendText(exchange, message);
                break;
            }
            case Endpoint.POST_TASK: {
                String message = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Task task = gson.fromJson(message, Task.class);
                if (!manager.isChecked(task)) {
                    overlapText(exchange, "Поздадачи пересекаются.");
                } else {
                    if (task.getId() == 0) {
                        manager.addTask(task);
                    } else manager.updateTask(task);
                    sendAddTask(exchange, "Подзадача успешно добавлена/обновлена");
                }
                break;
            }

            case GET_TASK: {
                if (manager.getTask(id) == null) {
                    sendHasInteractions(exchange, "Такой подзадачи нет");
                } else {
                    String message = gson.toJson(manager.getTask(id));
                    sendText(exchange, message);
                    break;
                }
            }
            case Endpoint.DELETE: {
                manager.deleteTask(id);
                sendAddTask(exchange, "Подзадача успешно удалена");
                break;
            }
        }
    }
}