package mod;

public class Subtask extends AbstractTask {
    int epicId;


    public Subtask(String name, String description, int epicId, Status status, int id) {
        super(name, description, status,id);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int epicId, Status status) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}

