package Manager;

import Model.AbstractTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<AbstractTask> viewed;
    Node head;
    Node tail;
    private HashMap<Integer, Node> NodeMap ;

    public InMemoryHistoryManager() {
        viewed = new ArrayList<>();
        NodeMap = new HashMap<>();
    }

    @Override
    public void add(AbstractTask abstractTask, int taskId) {// Первоначально функция выглядеа так: public void add(AbstractTask abstractTask, int taskId)
        int id=taskId;                                      //int id=abstractTask.getId() - но у меня вылетала ошибка, то значение null
        Node node = new Node(abstractTask);                 // почему я е могу получить id по вызову getId()?
        if (NodeMap.containsKey(id)) {
            Node forDeleteNode = NodeMap.get(id);
            removeNode(forDeleteNode);
        }
        linkLast(abstractTask);
        NodeMap.put(id, node);

    }

    @Override
    public List<AbstractTask> getHistory() {
        for (Node node = head; node != null; node = node.next) {
            viewed.add(node.abstractTask);
        }
        return viewed;
    }


    @Override
    public void remove(int id) {
    }

    public void linkLast(AbstractTask abstractTask) {
        Node newNode = new Node(abstractTask);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public ArrayList<AbstractTask> getTasks() {
        ArrayList<AbstractTask> ArrTasks = new ArrayList<>();
        for (Node node = head; node != null; node = node.next) {
            ArrTasks.add(node.abstractTask);
        }
        return ArrTasks;
    }

    public void removeNode(Node node) {
        if (node == null) {
            return;
        }
        if (node == head && node == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = node.next;
        } else if (node == tail) {
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }
}