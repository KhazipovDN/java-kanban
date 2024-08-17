package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class AbstractTask implements Comparable<AbstractTask> {
    protected String name;
    protected String description;
    protected int id;
    protected Status status;
    protected Duration duration;
    protected LocalDateTime startTime;


    public AbstractTask(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public AbstractTask(String name, String description, int id, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public AbstractTask(String name, String description, Status status,  Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public AbstractTask(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public AbstractTask(String name, String description, Status status, int id,  Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTask that = (AbstractTask) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
    }

    @Override
    public int compareTo(AbstractTask abstractTask) {
        return startTime.compareTo(abstractTask.getStartTime());
    }
}
