import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends AbstractTask {
    HashMap<Integer,Subtask> sons;
    Status status;

    public Epic(String name, String description) {
        super(name, description);
        sons = new HashMap<>();
        this.status=Status.NEW;
    }

    public void setSubtask(int id,Subtask subtask) {
        sons.put(id,subtask);
    }

    public void updateSubtaskInEpic(int id, Subtask subtask) {
        sons.put(id,subtask);
    }

    public void changeStatus() {
        int count=0;
        if (sons.size() == 1) status = Status.NEW;
        else {
            for (Subtask son : sons.values()) {
                if (son.getStatus() == Status.IN_PROGRESS) {
                    count++;
                } else if (son.getStatus() == Status.DONE) {
                    count = count + 2;
                }
            }

            if (count==0) setStatus(Status.NEW);
            else if(count==2*sons.size()) setStatus(Status.DONE);
            else setStatus(Status.IN_PROGRESS);

        }
    }
}
