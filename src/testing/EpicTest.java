package testing;

import model.Epic;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EpicTest {
    @Test
    public void testEqual() {
        Epic task1 = new Epic("Test addNewEpic", "Test addNewEpic description",1);
        Epic task2 = new Epic("Test addNewEpic", "Test addNewEpic description",1);

        assertEquals(task1, task2);
    }

    @Test
    public void testNotEqual() {
        Epic task1 = new Epic("Test addNewSubtask", "Test addNewEpic description",1);
        Epic task2 = new Epic("Test addNewSubtask", "Test addNewEpic description",2);

        assertNotEquals(task1, task2);
    }


}