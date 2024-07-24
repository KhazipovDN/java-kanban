package test;

import manager.Managers;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ManagerTest {
    @Test
    public void testGetDefault() {
        Managers manager = new Managers();
        assertNotNull(manager.getDefault(),"Не возвращается");

    }

    @Test
    public void testGetDefaultHistory() {
        Managers manager = new Managers();
        assertNotNull(manager.getDefaultHistory(),"Не возвращается");

    }
}
