package manager;

import model.*;
import myexception.ManagerSaveException;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private Path file;

    public FileBackedTaskManager(Path file) {
        this.file = file;

        if (!Files.exists(file)) {
            try {
                this.file = Files.createFile(file);
            } catch (Exception e) {
                System.out.println("Ошибка при создании файла: " + e.getMessage());
            }
        }
    }

    @Override
    public void addSubtask(Subtask subtask, int id) {
        super.addSubtask(subtask, id);
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public void deleteAllSubtask() {
        super.deleteAllSubtask();
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void updateTask(Task taskObject) {
        super.updateTask(taskObject);
        save();
    }

    @Override
    public void updateEpic(Epic newEpic) {
        super.updateEpic(newEpic);
        save();
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        super.updateSubtask(newSubtask);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    public AbstractTask fromString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        if (id > count) {
            count = id;
        }
        TaskType taskType = TaskType.valueOf(parts[1]);
        String name = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];
        int epicId = 0;
        if (parts.length > 5) {
            epicId = Integer.parseInt(parts[5].trim());

        }
        return switch (taskType) {
            case TASK -> new Task(name, description, status, id);
            case SUBTASK -> new Subtask(name, description, epicId, status, id);
            case EPIC -> {
                Epic epic = new Epic(name, description, id);
                epic.setStatus(status);
                yield epic;
            }
        };
    }

    public void save() throws ManagerSaveException {
        ArrayList<Task> arrTask = getAllTask();
        ArrayList<Subtask> arrSubtasks = getAllSubtask();
        ArrayList<Epic> arrEpics = getAllEpic();

        try (Writer writer = Files.newBufferedWriter(file)) {
            writer.write("id,type,name,status,description,epic");
            writer.append(System.lineSeparator());

            for (Task task : arrTask) {
                writer.write(task.toString());
                writer.append(System.lineSeparator());
            }

            for (Epic epic : arrEpics) {
                writer.write(epic.toString());
                writer.append(System.lineSeparator());
            }

            for (Subtask subtask : arrSubtasks) {
                writer.write(subtask.toString());
                writer.append(System.lineSeparator());
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных", e);
        }
    }

    public static FileBackedTaskManager loadFromFile(Path file) throws IOException {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        String text = Files.readString(file);
        String[] part = text.split("\n");
        if (part.length < 2) {
            System.out.println("Файл пуст, заполните задачами");
        } else {
            for (int i = 1; i < part.length; i++) {
                String value = part[i];
                AbstractTask abstractTask = manager.fromString(value);
                if (abstractTask instanceof Task) {
                    manager.putTask((Task) abstractTask);
                } else if (abstractTask instanceof Epic) {
                    manager.putEpic((Epic) abstractTask);
                } else if (abstractTask instanceof Subtask) {
                    manager.putSubtask((Subtask) abstractTask);
                }
            }
        }
        manager.subtaskIntoEpic();
        return manager;
    }

}
