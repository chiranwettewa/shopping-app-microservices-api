package com.chiran.productmanagementservice.service;

import com.chiran.productmanagementservice.model.ProductRequest;
import com.chiran.productmanagementservice.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
