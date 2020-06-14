package bg.sofia.uni.fmi.mjt.food.server.retriever.report;

import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetriever;
import bg.sofia.uni.fmi.mjt.food.server.retriever.FoodInfoRetrieverFactory;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FoodReportRetrieverTest {

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
    public void testGetRequiredInformation_EmptyLabelNutrientsList() throws IOException, InterruptedException {
        final String testFdcId = "0000000";
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        when(httpResponseMock.body()).thenReturn("{}");
        FoodReport actual = ((FoodReportRetriever)foodReportRetriever).getRequiredInformation(testFdcId);
        assertNull(actual.getLabelNutrients());
    }

    @Test
    public void testGetRequiredInformation_TwoFoodsInList() throws IOException, InterruptedException {
        final String testFdcId = "0000000";
        final double delta = 0.0;
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        LabelNutrients testLabelNutrients = new LabelNutrients(new LabelNutrientsInfo(10),
                new LabelNutrientsInfo(20), new LabelNutrientsInfo(30), new LabelNutrientsInfo(40),
                new LabelNutrientsInfo(50));
        FoodReport testFoodReport = new FoodReport("gtinUpc","fdcId","description",
                "ingredients", testLabelNutrients);
        String json = new Gson().toJson(testFoodReport);
        when(httpResponseMock.body()).thenReturn(json);

        FoodReport actual = ((FoodReportRetriever) foodReportRetriever).getRequiredInformation(testFdcId);

        assertEquals("gtinUpc", actual.getGtinUpc());
        assertEquals("ingredients", actual.getIngredients());
        assertEquals(10.0, actual.getLabelNutrients().getCalories().getValue(), delta);
        assertEquals(50.0, actual.getLabelNutrients().getFiber().getValue(), delta);
    }
}

