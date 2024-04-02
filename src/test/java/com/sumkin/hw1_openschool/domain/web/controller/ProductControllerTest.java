package com.sumkin.hw1_openschool.domain.web.controller;

import com.sumkin.hw1_openschool.Hw1OpenSchoolApplication;
import com.sumkin.hw1_openschool.domain.entities.category.Category;
import com.sumkin.hw1_openschool.domain.entities.product.Product;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Hw1OpenSchoolApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private final HttpHeaders headers = new HttpHeaders();
    private final HttpEntity<String> entity = new HttpEntity<>(null, headers);

    private ResponseEntity<String> response;

    private String expected;


    @Test
    public void testFindAllProducts() throws JSONException {

        response = restTemplate.exchange(
                createURLWithPort("/products"),
                HttpMethod.GET, entity, String.class);


        expected = "[{\"id\":1,\"name\":\"Apple\",\"description\":\"Red apple\",\"price\":10," +
                "\"category\":\"Food\",\"rating\":5,\"review\":\"Good\"}," +
                "{\"id\":2,\"name\":\"T-Shirt\",\"description\":\"Green t-shirt\",\"price\":5.15," +
                "\"category\":\"Clothes\",\"rating\":4,\"review\":\"Comfy\"}," +
                "{\"id\":3,\"name\":\"Television\",\"description\":\"LG 42 inch\",\"price\":500," +
                "\"category\":\"Appliances\",\"rating\":3,\"review\":\"Not bad\"}," +
                "{\"id\":4,\"name\":\"Pear\",\"description\":\"White pear\",\"price\":12.25," +
                "\"category\":\"Food\",\"rating\":4,\"review\":\"Not bad\"}," +
                "{\"id\":5,\"name\":\"Sweater\",\"description\":\"Blue t-shirt\",\"price\":20," +
                "\"category\":\"Clothes\",\"rating\":5,\"review\":\"Excellent\"}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testFindProductById() throws JSONException {
        response = restTemplate.exchange(
                createURLWithPort("/products/1"),
                HttpMethod.GET, entity, String.class);

        expected = "{\"id\":1,\"name\":\"Apple\",\"description\":\"Red apple\",\"price\":10," +
                "\"category\":\"Food\",\"rating\":5,\"review\":\"Good\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testCreateUpdateAndDeleteProduct() throws JSONException {

        Category category = new Category();
        category.setId(1L);
        category.setName("Food");

        Product product = new Product();
        product.setName("Orange");
        product.setDescription("Red apple");
        product.setPrice(BigDecimal.valueOf(3.12));
        product.setCategory(category);
        product.setRating((short) 5);
        product.setReview("Tasty");

        HttpEntity<Product> entity = new HttpEntity<>(product, headers);

        response = restTemplate.exchange(
                createURLWithPort("/products"),
                HttpMethod.POST, entity, String.class);

        expected = "{\"id\":10,\"name\":\"Orange\",\"description\":\"Red apple\",\"price\":3.12," +
                "\"category\":\"null \",\"rating\":5,\"review\":\"Tasty\"}";


        product.setName("Samsung S23");
        entity = new HttpEntity<>(product, headers);
        response = restTemplate.exchange(
                createURLWithPort("/products/6"),
                HttpMethod.PUT, entity, String.class);

        expected = "{\"id\":10,\"name\":\"Samsung S23\",\"description\":\"Red apple\",\"price\":3.12," +
                "\"category\":\" null " +
                "\",\"rating\":5,\"review\":\"Tasty\"}";


        response = restTemplate.exchange(
                createURLWithPort("/products/10"),
                HttpMethod.DELETE, entity, String.class);
    }


    @Test
    public void testUpdateReview() throws JSONException {


        HttpEntity<String> entity = new HttpEntity<>("Not bad", headers);
        response = restTemplate.exchange(
                createURLWithPort("/products/3/review"),
                HttpMethod.PUT, entity, String.class);

        expected = "{\"id\":3,\"name\":\"Television\",\"description\":\"LG 42 inch\",\"price\":500," +
                "\"category\":\"Appliances\",\"rating\":3,\"review\":\"Not bad\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }


    @Test
    public void testNoProductFoundById() throws JSONException {
        response = restTemplate.exchange(
                createURLWithPort("/products/45"),
                HttpMethod.GET, entity, String.class);

        expected = "{\"message\": \"Product not found\", \"errors\": null}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testUpdateRating() throws JSONException {
        HttpEntity<Short> entity = new HttpEntity<>((short) 3, headers);
        response = restTemplate.exchange(
                createURLWithPort("/products/3/rating"),
                HttpMethod.PUT, entity, String.class);

        expected = "{\"id\":3,\"name\":\"Television\",\"description\":\"LG 42 inch\",\"price\":500," +
                "\"category\":\"Appliances\",\"rating\":3,\"review\":\"Good\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}


