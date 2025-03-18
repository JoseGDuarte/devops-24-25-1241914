package basic_demo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void test() {
        App classUnderTest = new App();
        assertNotNull("App should have a greeting", classUnderTest.getGreeting());
    }
}
