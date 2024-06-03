public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");


        TaskManager taskManager = new TaskManager();
       Epic Magaz= new Epic("Сходить в магазин","Выйти на улицу и сходить в магазин");

        taskManager.addEpic(Magaz);
        taskManager.addSubtask(new Subtask("Выйти из дома","Сесть в лифт и спуститься",1,Status.IN_PROGRESS),1);
        taskManager.addSubtask(new Subtask("Дойти до булочной","Выйти из подъезда и дойти до булочной",1,Status.DONE),1);
        taskManager.addSubtask(new Subtask("Зайти в магазин","Открыть дверь и зайти",1,Status.DONE),1);
        System.out.println(taskManager.getAllEpic());
        //System.out.println(taskManager.getAllSubtask());
        taskManager.updateSubtask(new Subtask("Сходить в пивную","Купить пива",1,Status.IN_PROGRESS, 2),2);
        //System.out.println(taskManager.getAllSubtask());
        //System.out.println(taskManager.getAllEpic());
        //System.out.println(taskManager.SubtaskFromEpic(1));
        //taskManager.deleteAllSubtask();
        //System.out.println(taskManager.getAllSubtask());
        //System.out.println(taskManager.SubtaskFromEpic(1));
        System.out.println(taskManager.getAllEpic());
    }
}
