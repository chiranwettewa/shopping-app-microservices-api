package com.chiran.ordermanagementservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetail {

    private long paymentId;
    private PaymentMode paymentMode;
    private String paymentStatus;
    private Instant paymentDate;

}
