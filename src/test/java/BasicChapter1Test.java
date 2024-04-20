import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BasicChapter1Test {
    @Test
    public void validatePlaceName() {
        given().
                log().all().
                when().
                get("http://api.zippopotam.us/us/90210").

                then().
                assertThat().
                body("places[0].'place name'", equalTo("Beverly Hills")).
                statusCode(200).
                contentType(ContentType.JSON).
                log().body();
    }

    @Test
    public void validatePlaceState() {
        given().
                when().
                get("http://api.zippopotam.us/us/90210").

                then().
                assertThat().
                body("places[0].state", equalTo("California"));
    }

    @Test
    public void validatePlaceNameContains() {
        given().
                when().
                get("http://api.zippopotam.us/us/90210").

                then().
                assertThat().
                body("places.'place name'",hasItem("Beverly Hills"));
    }

    @Test
    public void validatePlaceNameSize() {
        given().
                when().
                get("http://api.zippopotam.us/us/90210").

                then().
                assertThat().
                body("places.'place name'", hasSize(1));
    }
}
