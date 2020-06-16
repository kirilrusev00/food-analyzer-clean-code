package bg.sofia.uni.fmi.mjt.food.server.retriever;

import bg.sofia.uni.fmi.mjt.food.server.cache.FoodInfoCache;
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

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FoodInfoRetrieverTest {
    @Mock
    private HttpClient httpClientMock;

    @Mock
    private HttpResponse<String> httpResponseMock;

    private FoodInfoRetriever foodDataRetriever;
    private FoodInfoRetriever foodReportRetriever;

    @Before
    public void setUp() {
        foodDataRetriever = FoodInfoRetrieverFactory.getInstance(InformationType.DATA, httpClientMock, null);
        foodReportRetriever = FoodInfoRetrieverFactory.getInstance(InformationType.REPORT, httpClientMock, null);
    }

    @Test
    public void testFoodReportGetRequiredInformationAsString_CheckInCache_WhenFoodInCache() throws Exception {
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
        String actual = FoodInfoCache.checkInCache(testFdcId);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetRequiredInformationAsString_CheckInCache_WhenFoodNotInCache() throws Exception {
        final String testFdcId = "0000000";
        String actual = FoodInfoCache.checkInCache(testFdcId);

        assertNull(actual);
    }

    @Test
    public void testFoodDataGetRequiredInformationAsString_CheckInCache_WhenFoodInCache() throws Exception {
        final String testSearchInput = "test";
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        List<Food> testFoods = List.of(new Food("soup", "id1", "gtin1"),
                new Food("salad", "id2", "gtin2"));
        FoodSearch testFoodSearch = new FoodSearch(testSearchInput, testFoods);
        String json = new Gson().toJson(testFoodSearch);
        when(httpResponseMock.body()).thenReturn(json);

        String expected = foodDataRetriever.getRequiredInformationAsString(testSearchInput);
        String actual = FoodInfoCache.checkInCache(testSearchInput);

        assertEquals(expected, actual);
    }
}