package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Epic extends AbstractTask {
    private HashMap<Integer, Subtask> sons;
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
        sons = new HashMap<>();
        this.status = Status.NEW;
    }

    public Epic(String name, String description, int id, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, id, duration, startTime);
        sons = new HashMap<>();
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
        sons = new HashMap<>();
    }

    public void setSubtask(Subtask subtask) {
        sons.put(subtask.getId(), subtask);
        setTime();
    }

    public void updateSubtaskInEpic(int id, Subtask subtask) {
        sons.put(id, subtask);
    }

    public void changeStatus() {
        int count = 0;
        if (sons.isEmpty()) status = Status.NEW;
        else {
            for (Subtask son : sons.values()) {
                if (son.getStatus() == Status.IN_PROGRESS) {
                    count++;
                } else if (son.getStatus() == Status.DONE) {
                    count = count + 2;
                }
            }
            if (count == 0) setStatus(Status.NEW);
            else if (count == 2 * sons.size()) setStatus(Status.DONE);
            else setStatus(Status.IN_PROGRESS);

        }
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setSons(HashMap<Integer, Subtask> sons) {
        this.sons = sons;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void deleteUnitSubstack(int id) {
        sons.remove(id);
    }

    public void deleteAllSubstack() {
        sons.clear();
    }

    public HashMap<Integer, Subtask> getSons() {
        return sons;
    }

    public void setTime() {
        Duration subDuration = Duration.ofSeconds(0);
        duration = Duration.ofSeconds(0);
        startTime = LocalDateTime.MAX;
        endTime = LocalDateTime.MIN;
        if (sons.isEmpty() && duration != null) {
            startTime = null;
            endTime = null;
        } else {
            for (Subtask son : sons.values()) {
                if (startTime.isAfter(son.getStartTime()))
                    startTime = son.getStartTime();
                if (endTime.isBefore(son.getEndTime())) {
                    endTime = son.getEndTime();
                }
                subDuration = Duration.between(son.getStartTime(), son.getEndTime());
                duration = duration.plus(subDuration);
            }
        }
    }

    @Override
    public String toString() {
        if (duration == null) {
            duration = Duration.ofHours(0);
        }
        return id +
                ",EPIC" +
                "," + name +
                "," + status +
                "," + description +
                "," + startTime +
                "," + duration.toMinutes();
    }

}
