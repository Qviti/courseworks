package com.example.iad_workshop_1;

import com.example.iad_workshop_1.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IadWorkshop1ApplicationTests {

	@LocalServerPort
	private int port;

	private final RestTemplate restTemplate = new RestTemplate();

	private String getBaseUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void testTeaStoreIntegration() throws IOException {
		// Step B: Get the list of products, should be empty
		ResponseEntity<Product[]> productList = restTemplate.getForEntity(getBaseUrl() + "/products", Product[].class);
		assertEquals(0, productList.getBody().length);

		// Step C: Add the first tea
		Product firstTea = new Product(1L, "Green Tea", 12.50, 100, null, null);
		HttpEntity<Product> request = new HttpEntity<>(firstTea);
		restTemplate.postForEntity(getBaseUrl() + "/products", request, Product.class);

		// Check that the tea is added
		productList = restTemplate.getForEntity(getBaseUrl() + "/products", Product[].class);
		assertEquals(1, productList.getBody().length);
		assertEquals("Green Tea", productList.getBody()[0].getName());

		// Step D: Add another tea
		Product secondTea = new Product(2L, "Black Tea", 10.00, 50, null, null);
		HttpEntity<Product> request2 = new HttpEntity<>(secondTea);
		restTemplate.postForEntity(getBaseUrl() + "/products", request2, Product.class);

		// Check that both teas are in the list
		productList = restTemplate.getForEntity(getBaseUrl() + "/products", Product[].class);
		assertEquals(2, productList.getBody().length);

		// Step E: Set and download images for both teas
		File greenTeaImage = new File("src/test/resources/green-tea.jpg");
		File blackTeaImage = new File("src/test/resources/black-tea.jpg");

		uploadProductImage(1L, greenTeaImage);
		uploadProductImage(2L, blackTeaImage);

		// Download and check the images
		ResponseEntity<byte[]> downloadedImage1 = restTemplate.getForEntity(getBaseUrl() + "/products/1/image", byte[].class);
		assertTrue(downloadedImage1.getBody().length > 0);

		ResponseEntity<byte[]> downloadedImage2 = restTemplate.getForEntity(getBaseUrl() + "/products/2/image", byte[].class);
		assertTrue(downloadedImage2.getBody().length > 0);

		// Step F: Create first shopping cart
		ResponseEntity<Long> cartId1 = restTemplate.postForEntity(getBaseUrl() + "/cart", null, Long.class);

		// Step G: Create second shopping cart
		ResponseEntity<Long> cartId2 = restTemplate.postForEntity(getBaseUrl() + "/cart", null, Long.class);

		// Step H: Add teas to the first cart and check the sum
		String addTea1ToCart = String.format("/cart/%d/products", cartId1.getBody());
		addProductToCart(cartId1.getBody(), 1L, 2);  // Add 2 units of Green Tea
		// Repeat for other teas and check the sum price

		// Step I & J: Same process with id2 and adding more products
	}

	private void uploadProductImage(Long productId, File imageFile) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", Files.readAllBytes(imageFile.toPath()));

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		restTemplate.exchange(getBaseUrl() + "/products/" + productId + "/image", HttpMethod.POST, requestEntity, String.class);
	}

	private void addProductToCart(Long cartId, Long productId, int quantity) {
		String url = getBaseUrl() + String.format("/cart/%d/products", cartId);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("productId", productId);
		body.add("quantity", quantity);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		restTemplate.postForEntity(url, requestEntity, String.class);
	}
}
