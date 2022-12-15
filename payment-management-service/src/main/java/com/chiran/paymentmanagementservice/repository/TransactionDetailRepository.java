package com.chiran.paymentmanagementservice.repository;

import com.chiran.paymentmanagementservice.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail,Long> {
    Optional<TransactionDetail>  findByOrderId(Long orderId);
}
