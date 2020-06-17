package bg.sofia.uni.fmi.mjt.food.server.commands;

import bg.sofia.uni.fmi.mjt.food.server.retriever.data.search.Food;
import bg.sofia.uni.fmi.mjt.food.server.retriever.data.search.FoodSearch;
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

import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.GET_FOOD_USAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetFoodCommandTest {

    @Mock
    private HttpClient httpClientMock;

    @Mock
    private HttpResponse<String> httpResponseMock;

    private CommandFactory commandFactory;

    @Before
    public void setUp() {
        commandFactory = new CommandFactory(httpClientMock);
    }

    @Test
    public void testExecuteCommandGetFood() throws IOException, InterruptedException {
        final String commandLine = "get-food beef noodle soup";
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        List<Food> testFoods = List.of(new Food("soup", "id1", "gtin1"),
                new Food("salad", "id2", "gtin2"));
        FoodSearch testFoodSearch = new FoodSearch("beef noodle soup", testFoods);
        String json = new Gson().toJson(testFoodSearch);
        when(httpResponseMock.body()).thenReturn(json);

        String expected = testFoodSearch.getFoods().toString();
        String actual = commandFactory.processLine(commandLine);
        assertEquals(expected, actual);
    }

    @Test
    public void testExecuteCommandGetFoodWhenEmptyArgumentLine() {
        final String commandLine = "get-food";

        String expected = GET_FOOD_USAGE;
        String actual = commandFactory.processLine(commandLine);

        assertEquals(expected, actual);
    }
}
