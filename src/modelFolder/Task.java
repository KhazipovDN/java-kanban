package modelFolder;

import java.util.Objects;

public class Task extends AbstractTask {

    public Task(String name, String description, Status status, int id) {
        super(name, description, status, id);
    }

    public Task(String name, String description, Status status) {
        super(name, description, status);
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

}
