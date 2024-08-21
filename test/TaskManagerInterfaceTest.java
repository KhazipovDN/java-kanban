package test;

import java.io.IOException;
import manager.*;

class TaskManagerInterfaceTest extends TaskManagerTest{
    InMemoryTaskManager taskManager;

    public TaskManagerInterfaceTest() throws IOException {
        super(new InMemoryTaskManager());
    }

}