import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private List<AbstractTask> viewed;

    public InMemoryHistoryManager() {
        viewed=new ArrayList<>();
    }

    @Override
    public void add(AbstractTask abstractTask){
        viewed.add(abstractTask);

    }

    @Override
    public List<AbstractTask> getHistory(){
        return viewed;
    }
}
