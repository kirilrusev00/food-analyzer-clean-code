package bg.sofia.uni.fmi.mjt.food.server.commands;

import bg.sofia.uni.fmi.mjt.food.server.commands.CommandFactory;
import bg.sofia.uni.fmi.mjt.food.server.retriever.data.search.Food;
import bg.sofia.uni.fmi.mjt.food.server.retriever.data.search.FoodSearch;
import bg.sofia.uni.fmi.mjt.food.server.retriever.report.search.FoodReport;
import bg.sofia.uni.fmi.mjt.food.server.retriever.report.search.LabelNutrients;
import bg.sofia.uni.fmi.mjt.food.server.retriever.report.search.LabelNutrientsInfo;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.NOT_FOUND_IN_CACHE_MESSAGE;
import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.UNKNOWN_COMMAND_MESSAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandFactoryTest {
    @Mock
    private HttpClient httpClientMock;

    private CommandFactory commandFactory;

    @Before
    public void setUp() {
        commandFactory = new CommandFactory(httpClientMock);
    }

    @Test
    public void testExecuteCommandWrongCommand() {
        final String commandLine = "discnnect";

        String expected = UNKNOWN_COMMAND_MESSAGE;
        String actual = commandFactory.processLine(commandLine);

        assertEquals(expected, actual);
    }

}
