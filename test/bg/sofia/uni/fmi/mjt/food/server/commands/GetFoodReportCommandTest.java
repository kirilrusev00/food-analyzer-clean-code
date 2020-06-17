package bg.sofia.uni.fmi.mjt.food.server.commands;

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

import static bg.sofia.uni.fmi.mjt.food.server.constants.Constants.GET_FOOD_REPORT_USAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetFoodReportCommandTest {

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
    public void testExecuteCommandGetFoodReport() throws IOException, InterruptedException {
        final String commandLine = "get-food-report 1000000";

        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        LabelNutrients testLabelNutrients = new LabelNutrients(new LabelNutrientsInfo(10),
                new LabelNutrientsInfo(20), new LabelNutrientsInfo(30), new LabelNutrientsInfo(40),
                new LabelNutrientsInfo(50));
        FoodReport testFoodReport = new FoodReport("gtinUpc", "1000000", "description",
                "ingredients", testLabelNutrients);
        String json = new Gson().toJson(testFoodReport);
        when(httpResponseMock.body()).thenReturn(json);

        String expected = testFoodReport.toString();
        String actual = commandFactory.processLine(commandLine);
        assertEquals(expected, actual);
    }

    @Test
    public void testExecuteCommandGetFoodWhenEmptyArgumentLine() {
        final String commandLine = "get-food-report";

        String expected = GET_FOOD_REPORT_USAGE;
        String actual = commandFactory.processLine(commandLine);

        assertEquals(expected, actual);
    }
}
