package com.parking.service.integration;

import com.parking.service.dto.CreateTicketRequest;
import com.parking.service.dto.TicketResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ParkingIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Test
    void fullFlowCarEntryExit() throws InterruptedException {
        String base = "http://localhost:" + port + "/api/v1/parking";

        CreateTicketRequest req = new CreateTicketRequest("AAA111", "CAR");
        TicketResponse entry = rest.postForObject(base + "/entry", req, TicketResponse.class);
        assertNotNull(entry.getId());
        assertEquals("AAA111", entry.getPlate());
        assertFalse(entry.getPaid());

        Thread.sleep(2000);

        TicketResponse exit = rest.postForObject(base + "/exit/AAA111", null, TicketResponse.class);
        assertTrue(exit.getPaid());
        assertNotNull(exit.getTotalPayment());
        assertTrue(exit.getTotalPayment() > 0);
    }
}
