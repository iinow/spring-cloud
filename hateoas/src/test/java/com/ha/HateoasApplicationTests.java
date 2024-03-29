package com.ha;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class HateoasApplicationTests {

	@Test
	public void contextLoads() {
		assertEquals(12, 2);
	}

	WebTestClient webClient;
	
	@Before
	public void setup() {
		webClient = WebTestClient.bindToServer()
					.baseUrl("http://localhost:8080").build();
	}
	
	@Test
	public void testWebFluxEndPoint() throws Exception {
		webClient.get().uri("/")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Greet.class).returnResult()
			.getResponseBody().getMessage().equals("Hello");
	}
}
