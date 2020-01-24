package ricochet_robots;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void testMainHasAGreeting() {
        Main classUnderTest = new Main();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
