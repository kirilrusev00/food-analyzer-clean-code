package bg.sofia.uni.fmi.mjt.food.server;

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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandExecutorTest {
    @Mock
    private HttpClient httpClientMock;

    @Mock
    private HttpResponse<String> httpResponseMock;

    private CommandExecutor commandExecutor;

    @Before
    public void setUp() {
        commandExecutor = new CommandExecutor(httpClientMock);
    }

    @Test
    public void testExecuteCommandWhenCommandIsNotFull() {
        final String commandLine = null;
        String expected = "Empty command";
        String actual = commandExecutor.executeCommand(commandLine);
        assertEquals(expected, actual);
    }

    @Test
    public void testExecuteCommandDisconnect() {
        final String commandLine = "disconnect";
        String expected = "Disconnected from server.";
        String actual = commandExecutor.executeCommand(commandLine);
        assertEquals(expected, actual);
    }

    @Test
    public void testExecuteCommandWrongCommand() {
        final String commandLine = "discnnect";
        String expected = "Unknown command";
        String actual = commandExecutor.executeCommand(commandLine);
        assertEquals(expected, actual);
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
        String actual = commandExecutor.executeCommand(commandLine);
        assertEquals(expected, actual);
    }

    @Test
    public void testExecuteCommandGetFoodReport() throws IOException, InterruptedException {
        final String commandLine = "get-food-report 0000000";

        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        LabelNutrients testLabelNutrients = new LabelNutrients(new LabelNutrientsInfo(10),
                new LabelNutrientsInfo(20), new LabelNutrientsInfo(30), new LabelNutrientsInfo(40),
                new LabelNutrientsInfo(50));
        FoodReport testFoodReport = new FoodReport("gtinUpc", "0000000", "description",
                "ingredients", testLabelNutrients);
        String json = new Gson().toJson(testFoodReport);
        when(httpResponseMock.body()).thenReturn(json);

        String expected = testFoodReport.toString();
        String actual = commandExecutor.executeCommand(commandLine);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetFoodReportByBarcodeWhenCodeIsNotGivenAndFoodIsNotInCache() {
        final String commandLine = "get-food-by-barcode --img=barcode.gif";

        String expected = "Item not found in cache";
        String actual = commandExecutor.executeCommand(commandLine);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetFoodReportByBarcodeWhenCodeIsGivenAndFoodIsNotInCache() {
        final String commandLine = "get-food-by-barcode --img=barcode.gif --code=000000";

        String expected = "Item not found in cache";
        String actual = commandExecutor.executeCommand(commandLine);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetFoodReportByBarcodeWhenCodeIsGivenAndFoodIsInCache() throws IOException, InterruptedException {
        final String commandLine = "get-food-by-barcode --img=barcode.gif --code=000000";
        final String testFdcId = "fdcId";
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        LabelNutrients testLabelNutrients = new LabelNutrients(new LabelNutrientsInfo(10),
                new LabelNutrientsInfo(20), new LabelNutrientsInfo(30), new LabelNutrientsInfo(40),
                new LabelNutrientsInfo(50));
        FoodReport testFoodReport = new FoodReport("000000", testFdcId, "description",
                "ingredients", testLabelNutrients);
        String json = new Gson().toJson(testFoodReport);
        when(httpResponseMock.body()).thenReturn(json);

        commandExecutor.executeCommand("get-food-report " + testFdcId);

        String expected = testFoodReport.toString();
        String actual = commandExecutor.executeCommand(commandLine);

        assertEquals(expected, actual);
    }
}
