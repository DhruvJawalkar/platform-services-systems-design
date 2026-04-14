package com.platform.orders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OrdersServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthEndpointReturnsAcceptedPayload() throws Exception {
        mockMvc.perform(get("/api/v1/orders/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACCEPTED"));
    }

    @Test
    void createOrderReturnsCreated() throws Exception {
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":\"customer-123\",\"quantity\":2}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value("customer-123"))
                .andExpect(jsonPath("$.quantity").value(2));
    }
}
