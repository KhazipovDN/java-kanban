package manager;

import model.*;
import myexception.ManagerSaveException;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private Path file;
    private TreeSet<AbstractTask> abstractTasks;
    ArrayList<Task> arrTasks;
    ArrayList<Subtask> arrSubtasks;
    ArrayList<Epic> arrEpics;

    public FileBackedTaskManager(Path file) {
        this.file = file;
        abstractTasks = new TreeSet<>();

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
        List<AbstractTask> tasksList = getPrioritizedTasks();
        boolean isChecked = tasksList.stream().anyMatch(anyTask -> isIntersection(((AbstractTask)subtask).getStartTime(),
                ((AbstractTask)subtask).getEndTime(), ((AbstractTask)anyTask).getStartTime(), ((AbstractTask)anyTask).getEndTime()));
        if (!isChecked) {
            super.addSubtask(subtask, id);
            abstractTasks.add(subtask);
            save();
        }
        else {
            System.out.println("Задачи пересекаются");
            System.out.println(subtask.getStartTime()+" "+subtask.getEndTime());

        }
    }

    @Override
    public void addTask(Task task) {
        List<AbstractTask> tasksList = getPrioritizedTasks();
        boolean isChecked = tasksList.stream().anyMatch(anyTask -> isIntersection(((AbstractTask)task).getStartTime(),
                ((AbstractTask)task).getEndTime(), ((AbstractTask)anyTask).getStartTime(), ((AbstractTask)anyTask).getEndTime()));
        if (!isChecked) {
        super.addTask(task);
        abstractTasks.add(task);
        save();
        }
        else {
            System.out.println("Задачи пересекаются");
            System.out.println(task.getStartTime()+" "+task.getEndTime());
        }
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);;
            save();
    }

    @Override
    public void deleteAllTask() {
        arrTasks = getAllTask();
        for (Task task : arrTasks) {
            abstractTasks.remove(task);
        }
        super.deleteAllTask();
        save();
    }

    @Override
    public void deleteAllSubtask() {
        arrSubtasks = getAllSubtask();
        for (Subtask subtask : arrSubtasks) {
            abstractTasks.remove(subtask);
        }
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
        List<AbstractTask> tasksList = getPrioritizedTasks();
        boolean isChecked = tasksList.stream().anyMatch(anyTask -> isIntersection(((AbstractTask)taskObject).getStartTime(),
                ((AbstractTask)taskObject).getEndTime(), ((AbstractTask)anyTask).getStartTime(), ((AbstractTask)anyTask).getEndTime()));
        if (!isChecked) {
            super.updateTask(taskObject);
            abstractTasks.add(taskObject);
            save();
        }
        else {
            System.out.println("Задачи пересекаются");
            System.out.println(taskObject.getStartTime()+" "+taskObject.getEndTime());
        }
    }

    @Override
    public void updateEpic(Epic newEpic) {
            super.updateEpic(newEpic);
            save();
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        List<AbstractTask> tasksList = getPrioritizedTasks();
        boolean isChecked = tasksList.stream().anyMatch(anyTask -> isIntersection(((AbstractTask)newSubtask).getStartTime(),
                ((AbstractTask)newSubtask).getEndTime(), ((AbstractTask)anyTask).getStartTime(), ((AbstractTask)anyTask).getEndTime()));
        if (!isChecked) {
            super.updateSubtask(newSubtask);
            abstractTasks.add(newSubtask);
            save();
        }
        else {
            System.out.println("Задачи пересекаются");
            System.out.println(newSubtask.getStartTime()+" "+newSubtask.getEndTime());
        }
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        abstractTasks.remove(getSubtask(id));
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
        abstractTasks.remove(getTask(id));
        save();
    }

    public boolean isIntersection(LocalDateTime startTime1, LocalDateTime endTime1,
                                  LocalDateTime startTime2, LocalDateTime endTime2) {
        return !(endTime1.isBefore(startTime2) || endTime2.isBefore(startTime1));
    }

    public List<AbstractTask> getPrioritizedTasks() {
        return new ArrayList<>(abstractTasks);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(parts[5], formatter);
        int minutes = Integer.parseInt(parts[6].trim());
        Duration duration = Duration.ofMinutes(minutes);
        int epicId = 0;
        if (parts.length > 7) {
            epicId = Integer.parseInt(parts[7].trim());
        }
        return switch (taskType) {
            case TASK -> new Task(name, description, status, id, duration, startTime);
            case SUBTASK -> new Subtask(name, description, epicId, status, id, duration, startTime);
            case EPIC -> {
                Epic epic = new Epic(name, description, id, duration, startTime);
                epic.setStatus(status);
                yield epic;
            }
        };
    }

    public void save() throws ManagerSaveException {
        arrTasks = getAllTask();
        arrSubtasks = getAllSubtask();
        arrEpics = getAllEpic();
        try (Writer writer = Files.newBufferedWriter(file)) {
            writer.write("id,type,name,status,description,duration,startTime,epic");
            writer.append(System.lineSeparator());

            for (Task task : arrTasks) {
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
