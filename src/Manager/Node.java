package Manager;

import Model.AbstractTask;

    public class Node {
        public AbstractTask abstractTask;
        public Node prev;
        public Node next;

        public Node(AbstractTask abstractTask){
            this.abstractTask=abstractTask;
            this.next = null;
            this.prev = null;

        }

    }

