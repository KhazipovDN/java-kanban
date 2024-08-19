package test;

import myexception.ManagerSaveException;
import org.junit.jupiter.api.Test;
import org.manager.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionTest {

    @Test
    public void testManagerSaveException() {
        assertThrows(ManagerSaveException.class, () -> {
            File tempFile = File.createTempFile("task-manager-test", ".txt");
            tempFile.setReadOnly();

            FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.toPath());
            manager.save();
        }, "Вывод ManagerSaveException");
    }

}
