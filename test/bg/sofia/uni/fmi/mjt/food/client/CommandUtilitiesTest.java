package bg.sofia.uni.fmi.mjt.food.client;

import org.junit.Test;

import static bg.sofia.uni.fmi.mjt.food.client.CommandUtilities.checkIfCorrectCommand;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommandUtilitiesTest {

    @Test
    public void testCheckIfCorrectCommandEmptyCommand() {
        final String command = "";

        assertFalse(checkIfCorrectCommand(command));
    }

    @Test
    public void testCheckIfCorrectCommandFullCommand() {
        final String command = "disconnect";

        assertTrue(checkIfCorrectCommand(command));
    }
}
