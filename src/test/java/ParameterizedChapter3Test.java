import io.restassured.http.ContentType;
import org.junit.Test;
import com.tngtech.java.junit.dataprovider.*;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(DataProviderRunner.class)
public class ParameterizedChapter3Test {

    @DataProvider
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][]{
                {"us", "90210", "Beverly Hills"},
                {"us", "12345", "Schenectady"},
                {"ca", "B2R", "Waverley"}
        };
    }

    @Test
    @UseDataProvider("zipCodesAndPlaces")
    public void validateWithTestData(String countryCode, String zipCode, String expectedPlace) {
        given().
                pathParam("countryCode", countryCode).pathParam("zipCode", zipCode).
                when().
                get("http://api.zippopotam.us/{countryCode}/{zipCode}").

                then().
                assertThat().
                body("places[0].'place name'", equalTo(expectedPlace)).
                statusCode(200).
                contentType(ContentType.JSON).
                log().body();
    }
}
