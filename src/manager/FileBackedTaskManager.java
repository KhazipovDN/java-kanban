package manager;

import model.*;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private Path file;
    public static String position = "ForSaveInFile";

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

    public AbstractTask fromString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        TaskType taskType = TaskType.valueOf(parts[1]);
        String name = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];
        int epicId=0;
        if (parts.length > 5) {
            epicId = Integer.parseInt(parts[5].trim());

        }
        return switch (taskType) {
            case TASK -> new Task(name, description, status, id);
            case SUBTASK -> new Subtask(name, description, epicId, status, id);
            case EPIC -> new Epic(name, description, id);
        };
    }

    public void save() throws ManagerSaveException {
        if (position.equals("ForSaveInFile")) {
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
    }

    public static FileBackedTaskManager loadFromFile(Path file) throws IOException {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        position = "NotForSaveInFile";
        String text = Files.readString(file);
        String[] part = text.split("\n");
        if (part.length < 2) {
            System.out.println("Файл пуст, заполните задачами");
        } else {
            for (int i = 1; i < part.length; i++) {
                String value = part[i];
                AbstractTask abstractTask = manager.fromString(value);
                if (abstractTask instanceof Task) {
                    manager.addTask((Task) abstractTask);
                } else if (abstractTask instanceof Epic) {
                    manager.addEpic((Epic) abstractTask);
                } else if (abstractTask instanceof Subtask) {
                    manager.addSubtask((Subtask) abstractTask, ((Subtask) abstractTask).getEpicId());
                }
            }
        }
        position = "ForSaveInFile";
        return manager;
    }

    public class ManagerSaveException extends RuntimeException {

        public ManagerSaveException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
