package com.mz.kalah.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.mz.kalah.KalahApplication;

/** 
 * Integration test for create game and move game
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KalahApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class KalahResourceTest {

	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateGame() throws Exception {
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/games"), entity, String.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	@SuppressWarnings("unused")
	@Test
	public void testMove() throws Exception {
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> createResponse = restTemplate.postForEntity(createURLWithPort("/games"), entity, String.class);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/games/1/pits/1"), HttpMethod.PUT,
				entity, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		String expected = "{\"id\":\"1\",\"uri\":\"http://localhost:8080/games/1\",\"status\":{\"1\":\"0\",\"2\":\"7\",\"3\":\"7\",\"4\":\"7\",\"5\":\"7\",\"6\":\"7\",\"7\":\"1\",\"8\":\"6\",\"9\":\"6\",\"10\":\"6\",\"11\":\"6\",\"12\":\"6\",\"13\":\"6\",\"14\":\"0\"}}";

		JSONAssert.assertEquals(expected, response.getBody(), false);

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + "8080" + uri;
	}

}
