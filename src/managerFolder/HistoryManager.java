package managerFolder;

import modelFolder.AbstractTask;

import java.util.List;

public interface HistoryManager {

    void add(AbstractTask abstractTask);

    List<AbstractTask> getHistory();
}
