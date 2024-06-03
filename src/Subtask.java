public class Subtask extends AbstractTask{
    Epic parents;
    Status status;
    public Subtask (String name, String description, Epic parents) {
        super(name, description);
        this.parents=parents;
    }
    public Subtask(String name, String description, Epic parents,Status status,int id) {
        super(name, description, status,id);
        this.parents=parents;
    }
    public Subtask(String name, String description, Epic parents,Status status ) {
        super(name, description, status);
        this.parents=parents;
    }
}
