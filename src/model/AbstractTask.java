package model;

import java.util.Objects;

public class AbstractTask {
    protected String name;
    protected String description;
    protected int id;
    protected Status status;

    public AbstractTask(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public AbstractTask(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public AbstractTask (String name, String description, Status status, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getName() {
        return name;
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
}
