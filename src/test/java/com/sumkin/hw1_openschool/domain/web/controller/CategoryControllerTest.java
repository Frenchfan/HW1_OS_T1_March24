package com.sumkin.hw1_openschool.domain.web.controller;

import com.sumkin.hw1_openschool.Hw1OpenSchoolApplication;
import com.sumkin.hw1_openschool.domain.web.dto.CategoryDto;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Hw1OpenSchoolApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private final HttpHeaders headers = new HttpHeaders();
    private final HttpEntity<String> entity = new HttpEntity<>(null, headers);

    private ResponseEntity<String> response;

    private String expected;


    @Test
    public void testFindAllCategories() throws JSONException {


        response = restTemplate.exchange(
                createURLWithPort("/categories"),
                HttpMethod.GET, entity, String.class);


        expected = "[{\"id\":1,\"name\":\"Food\"},{\"id\":2,\"name\":\"Clothes\"}, " +
                "{\"id\":3,\"name\":\"Appliances\"}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);


    }

    @Test
    public void testFindAllCategoriesWithProducts() throws JSONException {

        response = restTemplate.exchange(
                createURLWithPort("/categories/with-products"),
                HttpMethod.GET, entity, String.class);

        expected = "[{\"id\":1,\"name\":\"Food\",\"products\":[{\"id\":1," +
                "\"name\":\"Apple\",\"description\":\"Red apple\",\"price\":10," +
                "\"category\":\"Food\",\"rating\":5,\"review\":\"Good\"},{\"id\":4," +
                "\"name\":\"Pear\",\"description\":\"White pear\",\"price\":12.25," +
                "\"category\":\"Food\",\"rating\":4,\"review\":\"Not bad\"},{\"id\":7," +
                "\"name\":\"Orange\",\"description\":\"Red apple\",\"price\":3.12," +
                "\"category\":\"Food\",\"rating\":5,\"review\":\"Tasty\"}]},{\"id\":2," +
                "\"name\":\"Clothes\",\"products\":[{\"id\":2,\"name\":\"T-Shirt\"," +
                "\"description\":\"Green t-shirt\",\"price\":5.15,\"category\":\"Clothes\"," +
                "\"rating\":4,\"review\":\"Comfy\"},{\"id\":5,\"name\":\"Sweater\"," +
                "\"description\":\"Blue t-shirt\",\"price\":20,\"category\":\"Clothes\"," +
                "\"rating\":5,\"review\":\"Excellent\"},{\"id\":8,\"name\":\"Pants\"," +
                "\"description\":\"deep blue jeans\",\"price\":23,\"category\":\"Clothes\"," +
                "\"rating\":4,\"review\":\"Good quality\"}]},{\"id\":3,\"name\":" +
                "\"Appliances\",\"products\":[{\"id\":3,\"name\":\"Television\"," +
                "\"description\":\"LG 42 inch\",\"price\":500,\"category\":\"Appliances\"," +
                "\"rating\":5,\"review\":\"Good\"},{\"id\":6,\"name\":\"Mobile\"," +
                "\"description\":\"Samsung S23\",\"price\":800,\"category\":\"Appliances\"," +
                "\"rating\":5,\"review\":\"Not bad\"},{\"id\":9,\"name\":\"Router\"," +
                "\"description\":\"Huawei router\",\"price\":120,\"category\":\"Appliances\"" +
                ",\"rating\":5,\"review\":\"Excellent connection\"}]}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    public void testFindProductsForCategory() throws JSONException {

        response = restTemplate.exchange(
                createURLWithPort("/categories/1/products"),
                HttpMethod.GET, entity, String.class);

        expected = "[{\"id\":1,\"name\":\"Apple\",\"description\":\"Red apple\"," +
                "\"price\":10,\"category\":\"Food\",\"rating\":5,\"review\":" +
                "\"Good\"},{\"id\":4,\"name\":\"Pear\",\"description\":\"White pear\"," +
                "\"price\":12.25,\"category\":\"Food\",\"rating\":4,\"review\":\"Not bad\"}," +
                "{\"id\":7,\"name\":\"Orange\",\"description\":\"Red apple\",\"price\":3.12," +
                "\"category\":\"Food\",\"rating\":5,\"review\":\"Tasty\"}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testFindCategoryById() throws JSONException {

        response = restTemplate.exchange(
                createURLWithPort("/categories/2"),
                HttpMethod.GET, entity, String.class);

        expected = "{\"id\":2,\"name\":\"Clothes\"}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testNoCategoryFoundById() throws JSONException {

        response = restTemplate.exchange(
                createURLWithPort("/categories/5"),
                HttpMethod.GET, entity, String.class);

        expected = "{\"message\": \"Category not found\", \"errors\": null}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testPutCategory() throws JSONException {

    	CategoryDto category = CategoryDto
                .builder()
                .name("Nutricious Food")
                .build();

    	HttpEntity<CategoryDto> entity = new HttpEntity<>(category, headers);

    	response = restTemplate.exchange(
    			createURLWithPort("/categories/1"),
    			HttpMethod.PUT, entity, String.class);

    	expected = "{\"id\":1,\"name\":\"Nutricious Food\"}";

    	JSONAssert.assertEquals(expected, response.getBody(), false);

        category.setName("Food");

        entity = new HttpEntity<>(category, headers);

        response = restTemplate.exchange(
                createURLWithPort("/categories/1"),
                HttpMethod.PUT, entity, String.class);

        expected = "{\"id\":1,\"name\":\"Food\"}";

        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    public void testCreateAndDeleteCategory() throws JSONException {
        CategoryDto category = CategoryDto
                .builder()
                .name("Special category")
                .build();

        HttpEntity<CategoryDto> entity = new HttpEntity<>(category, headers);

        response = restTemplate.exchange(
                createURLWithPort("/categories"),
                HttpMethod.POST, entity, String.class);

        expected = "{\"id\":4,\"name\":\"Special category\"}";

        JSONAssert.assertEquals(expected, response.getBody(), false);

        response = restTemplate.exchange(
                createURLWithPort("/categories/4"),
                HttpMethod.DELETE, entity, String.class);

        response = restTemplate.exchange(
                createURLWithPort("/categories"),
                HttpMethod.GET, entity, String.class);


        expected = "[{\"id\":1,\"name\":\"Food\"},{\"id\":2,\"name\":\"Clothes\"}, " +
                "{\"id\":3,\"name\":\"Appliances\"}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}




