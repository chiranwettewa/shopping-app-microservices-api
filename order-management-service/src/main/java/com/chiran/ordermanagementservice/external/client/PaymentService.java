package com.chiran.ordermanagementservice.external.client;

import com.chiran.ordermanagementservice.exception.CustomException;
import com.chiran.ordermanagementservice.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "payment-management-service/payment")
public interface PaymentService {
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

    default ResponseEntity<Long> fallback(Exception e){
        throw new CustomException("Payment service is not available","UNAVAILABLE",500);
        //return "Payment service is not available";
    }
}