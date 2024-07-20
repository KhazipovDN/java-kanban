package manager;

import model.AbstractTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    Node head;
    Node tail;
    private HashMap<Integer, Node> nodeMap;

    public InMemoryHistoryManager() {
        nodeMap = new HashMap<>();
    }

    @Override
    public void add(AbstractTask abstractTask) {
        if (abstractTask != null) {
            int id = abstractTask.getId();
            if (nodeMap.containsKey(id)) {
                Node forDeleteNode = nodeMap.get(id);
                removeNode(forDeleteNode);
            }
            linkLast(abstractTask, id);
        }
    }

    @Override
    public List<AbstractTask> getHistory() {
        return getTasks();
    }


    @Override
    public void remove(int id) {
        if (nodeMap.containsKey(id)) {
            Node forDeleteNode = nodeMap.get(id);
            removeNode(forDeleteNode);
        }
    }

    private void linkLast(AbstractTask abstractTask, int id) {
        Node newNode = new Node(abstractTask);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        nodeMap.put(id, newNode);
    }

    public ArrayList<AbstractTask> getTasks() {
        ArrayList<AbstractTask> arrTasks = new ArrayList<>();
        arrTasks.clear();
        for (Node node = head; node != null; node = node.next) {
            arrTasks.add(node.abstractTask);
        }
        return arrTasks;
    }

    private void removeNode(Node node) {
        if (node == null) {
            return;
        }
        if (node == head && node == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = node.next;
            head.prev = null;
        } else if (node == tail) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }
}