package test;

import myexception.ManagerSaveException;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionTest {
    @Test
    public void testManagerSaveException() {// не знаю, правильно ли сделано, но я не понял что делать
        assertThrows(ManagerSaveException.class, () -> {
            File tempFile = File.createTempFile("task-manager-test", ".txt");
            tempFile.setReadOnly();

            FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.toPath());
            manager.save();
        }, "Вывод ManagerSaveException");
    }

}
