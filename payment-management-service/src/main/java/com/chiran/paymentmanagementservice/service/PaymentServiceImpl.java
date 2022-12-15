package com.chiran.paymentmanagementservice.service;

import com.chiran.paymentmanagementservice.entity.TransactionDetail;
import com.chiran.paymentmanagementservice.exception.CustomException;
import com.chiran.paymentmanagementservice.model.PaymentMode;
import com.chiran.paymentmanagementservice.model.PaymentRequest;
import com.chiran.paymentmanagementservice.model.PaymentResponse;
import com.chiran.paymentmanagementservice.repository.TransactionDetailRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;
    @Override
    public Long doPayment(PaymentRequest paymentRequest) {

        log.info("Recording payment details {}",paymentRequest);

        TransactionDetail transactionDetail = TransactionDetail.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();
        transactionDetailRepository.save(transactionDetail);

        log.info("Transaction id {} completed",transactionDetail.getId());

        return transactionDetail.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        log.info("Getting payment details by order Id:",orderId);

        TransactionDetail transactionDetail = transactionDetailRepository.findByOrderId(Long.valueOf(orderId)).orElseThrow(
                ()->new CustomException("No such order found.","NOT_FOUND",404)
        );

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(transactionDetail.getId())
                .paymentMode(PaymentMode.valueOf(transactionDetail.getPaymentMode()))
                .paymentDate(transactionDetail.getPaymentDate())
                .orderId(transactionDetail.getOrderId())
                .status(transactionDetail.getPaymentStatus())
                .amount(transactionDetail.getAmount())
                .build();

        log.info("Payment response {}",paymentResponse);
        return paymentResponse;
    }
}
