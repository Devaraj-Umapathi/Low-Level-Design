package models;

import enums.ProductType;

public class Product {
    private Integer productId;
    private String name;
    private Double price;
    private ProductType productType;

    public Product(Integer productId, String name, Double price, ProductType productType) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.productType = productType;
    }

    //getters
    public Double getPrice() {
        return price;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public ProductType getProductType() {
        return productType;
    }
}
