import java.util.HashMap;

public class Epic extends AbstractTask {
    private HashMap<Integer, Subtask> sons;

    public Epic(String name, String description) {
        super(name, description);
        sons = new HashMap<>();
        this.status = Status.NEW;
    }

    public void setSubtask(int id, Subtask subtask) {
        sons.put(id, subtask);
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

    public void setSons(HashMap<Integer, Subtask> sons) {
        this.sons = sons;
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

}
