package manag;

import mod.AbstractTask;

import java.util.List;

public interface HistoryManager {

    void add(AbstractTask abstractTask);

    List<AbstractTask> getHistory();
}
