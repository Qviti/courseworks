package com.example.iad_workshop_1;

import com.example.iad_workshop_1.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		ResponseEntity<Product[]> productList = restTemplate.getForEntity(getBaseUrl() + "/products", Product[].class);
		assertEquals(0, productList.getBody().length);
		//"{product}"
		//Product firstTea = new Product(1L, "Tea", 12.50, 100, null, null);
		new ObjectMapper().writeValueAsString(firstTea);
		HttpEntity<Product> request = new HttpEntity<>(firstTea);
		restTemplate.postForEntity(getBaseUrl() + "/products", request, Product.class);

	}
}
