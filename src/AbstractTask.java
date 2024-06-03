import java.util.Objects;

public class AbstractTask {
    public String name;
    public String description;
    public int id;
    Status status;

    public AbstractTask(String name, String description) {
        this.name = name;
        this.description = description;
        status= Status.NEW;
    }
    public AbstractTask(String name, String description, Status status,int id) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public AbstractTask(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
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
