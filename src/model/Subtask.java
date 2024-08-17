package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends AbstractTask {
    int epicId;

    public Subtask(String name, String description, int epicId, Status status, int id, Duration duration, LocalDateTime startTime) {
        super(name, description, status,id, duration, startTime);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int epicId, Status status,  Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return id +
                ",SUBTASK" +
                "," + name +
                "," + status +
                "," + description +
                "," + startTime +
                "," + duration.toMinutes() +
                "," + epicId;
    }

}

