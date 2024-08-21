package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task extends AbstractTask {

    public Task(String name, String description, Status status, int id, Duration duration, LocalDateTime startTime) {
        super(name, description, status, id, duration, startTime);
    }

    public Task(String name, String description, Status status, int id) {
        super(name, description, status, id);
    }

    public Task(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task that = (Task) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return id +
                ",TASK" +
                "," + name +
                "," + status +
                "," + description +
                "," + startTime +
                "," + duration.toMinutes();
    }

}
