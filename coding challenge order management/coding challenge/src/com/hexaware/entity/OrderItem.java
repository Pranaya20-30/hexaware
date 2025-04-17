package com.hexaware.entity;

public class OrderItem {
    private int itemId;
    private int productId;
    private int quantity;

    
    public OrderItem(int itemId, int productId, int quantity) {
        this.itemId = itemId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Optional: another constructor if itemId is auto-generated
    public OrderItem(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
