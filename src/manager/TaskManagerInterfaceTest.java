package manager;

import java.io.IOException;

class TaskManagerInterfaceTest extends TaskManagerTest{
    InMemoryTaskManager taskManager;

    public TaskManagerInterfaceTest() throws IOException {
        super(new InMemoryTaskManager());
    }

}