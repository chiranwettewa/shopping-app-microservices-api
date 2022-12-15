package com.chiran.paymentmanagementservice.service;

import com.chiran.paymentmanagementservice.model.PaymentRequest;
import com.chiran.paymentmanagementservice.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
