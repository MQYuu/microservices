package com.yuu.customer_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    private Long id;
    private String name;
    private double price;
    private int quantity;
    private String imageUrl;
    private String description;
    private boolean validQuantity;
    private boolean validPrice;

    // Getter and Setter methods
    
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @JsonProperty("quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("validQuantity")
    public boolean isValidQuantity() {
        return validQuantity;
    }

    public void setValidQuantity(boolean validQuantity) {
        this.validQuantity = validQuantity;
    }

    @JsonProperty("validPrice")
    public boolean isValidPrice() {
        return validPrice;
    }

    public void setValidPrice(boolean validPrice) {
        this.validPrice = validPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", validQuantity=" + validQuantity +
                ", validPrice=" + validPrice +
                '}';
    }
}
