package bg.sofia.uni.fmi.mjt.food.server.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.http.HttpClient;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DisconnectTest {

    @Mock
    private HttpClient httpClientMock;

    private CommandFactory commandFactory;

    @Before
    public void setUp() {
        commandFactory = new CommandFactory(httpClientMock);
    }

    @Test
    public void testExecuteCommandDisconnect() {
        final String commandLine = "disconnect";
        String expected = "Disconnected from server.";
        String actual = commandFactory.processLine(commandLine);
        assertEquals(expected, actual);
    }
}
