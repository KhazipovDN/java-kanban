package manager;

import model.AbstractTask;

import java.util.List;

public interface HistoryManager {
    void remove(int id);
    void add(AbstractTask abstractTask, int id);
    List<AbstractTask> getHistory();
}
