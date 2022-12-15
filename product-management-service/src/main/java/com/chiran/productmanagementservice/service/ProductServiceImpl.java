package com.chiran.productmanagementservice.service;

import com.chiran.productmanagementservice.entity.Product;
import com.chiran.productmanagementservice.exception.ProductServiceCustomException;
import com.chiran.productmanagementservice.model.ProductRequest;
import com.chiran.productmanagementservice.model.ProductResponse;
import com.chiran.productmanagementservice.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product started");

        Product product =
                Product.builder()
                        .productName(productRequest.getName())
                        .price(productRequest.getPrice())
                        .quantity(productRequest.getQuantity())
                        .build();
        productRepository.save(product);
        log.info("Product {} added.",product.getProductId());

        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Searching product {} started",productId);
       Product product = productRepository.findById(productId)
               .orElseThrow(()->new ProductServiceCustomException("Product with given id not found", "PRODUCT_NOT_FOUND"));

       ProductResponse productResponse = ProductResponse.builder()
               .productId(product.getProductId())
               .productName(product.getProductName())
               .price(product.getPrice())
               .quantity(product.getQuantity())
               .build();
        log.info("Product {} searched",productId);
       return productResponse;

    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce quantity {} for product id",quantity,productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(()->
                        new ProductServiceCustomException("Product with given id not found","PRODUCT_NOT_FOUND"));

        if(product.getQuantity()<quantity){
            throw new ProductServiceCustomException("Product doesn't have sufficient quantity","INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("Quantity reduced successfully.");

    }
}
