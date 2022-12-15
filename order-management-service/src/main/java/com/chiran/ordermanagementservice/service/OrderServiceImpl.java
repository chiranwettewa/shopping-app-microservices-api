package com.chiran.ordermanagementservice.service;

import brave.messaging.ProducerResponse;
import com.chiran.ordermanagementservice.entity.Order;
import com.chiran.ordermanagementservice.exception.CustomException;
import com.chiran.ordermanagementservice.external.client.PaymentService;
import com.chiran.ordermanagementservice.external.client.ProductService;
import com.chiran.ordermanagementservice.external.request.PaymentRequest;
import com.chiran.ordermanagementservice.external.response.PaymentResponse;
import com.chiran.ordermanagementservice.model.OrderRequest;
import com.chiran.ordermanagementservice.model.OrderResponse;
import com.chiran.ordermanagementservice.model.PaymentDetail;
import com.chiran.ordermanagementservice.model.ProductDetail;
import com.chiran.ordermanagementservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ProductService productService;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public long placeOrder(OrderRequest orderRequest) {

        log.info("Placing order {}",orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());

        log.info("Creating order with status CREATED");

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .quantity(orderRequest.getQuantity())
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .build();
        order = orderRepository.save(order);

        log.info("Calling payment service");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;

        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully. Order status change into PLACED");
            orderStatus = "PLACED";

        }catch(Exception e){
            log.error("Exception {} ",e.getMessage());
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order place successfully with order Id {}",order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(Long orderId) {
        log.info("Get order details for order Id {}",orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new CustomException("Order not found for order Id:"+orderId,"NOT_FOUND",404));

        log.info("Invoking the product service to fetch product Id {}",order.getProductId());

        ProductDetail productDetail = restTemplate.getForObject(
                "http://product-management-service/product/"+order.getProductId(),
                ProductDetail.class
        );

        log.info("Getting payment info from payment service");

        PaymentResponse paymentResponse = restTemplate.getForObject(
                "http://payment-management-service/payment/order/"+order.getId(),
                PaymentResponse.class
        );


        ProductDetail product = ProductDetail.builder()
                .productName(productDetail.getProductName())
                .productId(productDetail.getProductId())
                .build();

        PaymentDetail payment = PaymentDetail.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetail(product)
                .paymentDetail(payment)
                .build();
        log.info("Order response :{}",orderResponse);
        return orderResponse;
    }
}
