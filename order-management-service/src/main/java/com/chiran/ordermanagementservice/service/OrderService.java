package com.chiran.ordermanagementservice.service;

import com.chiran.ordermanagementservice.model.OrderRequest;
import com.chiran.ordermanagementservice.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(Long orderId);
}
