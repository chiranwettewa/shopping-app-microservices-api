package com.chiran.paymentmanagementservice.controller;

import com.chiran.paymentmanagementservice.model.PaymentRequest;
import com.chiran.paymentmanagementservice.model.PaymentResponse;
import com.chiran.paymentmanagementservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest){

        return new ResponseEntity<>(
                paymentService.doPayment(paymentRequest), HttpStatus.OK
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable String orderId){

        return new ResponseEntity<>(
                paymentService.getPaymentDetailsByOrderId(orderId), HttpStatus.OK
        );
    }
}
