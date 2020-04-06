package bg.sofia.uni.fmi.mjt.food.server.retriever.barcode;

import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetriever;
import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetrieverFactory;
import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetrieverImpl;
import bg.sofia.uni.fmi.mjt.food.server.retriever.InformationType;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BarcodeRetrieverTest {
    @Mock
    private HttpClient httpClientMock;

    @Mock
    private HttpResponse<String> httpResponseMock;

    private FoodInfoRetriever foodReportRetriever;

    @Before
    public void setUp() {
        foodReportRetriever = FoodInfoRetrieverFactory.getInstance(InformationType.REPORT, httpClientMock, null);
    }

    @Test
    public void testGetRequiredInformationWhenCodeIsGiven() throws IOException, InterruptedException {
        final String[] commandParts = {"get-food-by-barcode", "--img=barcode.gif", "--code=000000"};
        final String testFdcId = "0000000";
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        LabelNutrients testLabelNutrients = new LabelNutrients(new LabelNutrientsInfo(10),
                new LabelNutrientsInfo(20), new LabelNutrientsInfo(30), new LabelNutrientsInfo(40),
                new LabelNutrientsInfo(50));
        FoodReport testFoodReport = new FoodReport("gtinUpc", testFdcId, "description",
                "ingredients", testLabelNutrients);
        String json = new Gson().toJson(testFoodReport);
        when(httpResponseMock.body()).thenReturn(json);

        String expected = foodReportRetriever.getRequiredInformationAsString(testFdcId);
        String actual = ((FoodInfoRetrieverImpl) foodReportRetriever).checkInCache(testFdcId);

        assertEquals(expected, actual);
    }
}
