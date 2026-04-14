package com.platform.orders.service;

import com.platform.orders.config.ServiceProperties;
import com.platform.orders.domain.OrderResponse;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final ServiceProperties serviceProperties;

    public OrderService(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    public OrderResponse sampleOrder(String customerId, int quantity) {
        return new OrderResponse(
                UUID.randomUUID().toString(),
                customerId,
                quantity,
                "ACCEPTED",
                serviceProperties.getName(),
                serviceProperties.getEnvironment()
        );
    }
}
