package com.shield.server;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ApplicationContext context;

    @Test
    public void contextLoads() {
        // Test to ensure the Spring context loads successfully
        assertNotNull(context);
    }

    @Test
    public void portInjected() {
        // Test to ensure the port is injected correctly
        assertTrue(port > 0);
    }
}
