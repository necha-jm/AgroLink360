package com.app.agroli;

public class Product {

    private String id;
    private String productName;
    private String category;
    private double price;
    private double quantity;
    private String location;

    // Required empty constructor for Firebase
    public Product() {}

    // Correct constructor (USED when saving)
    public Product(String productName, String category,
                   double price, double quantity, String location) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.location = location;
    }

    // Getters
    public String getProductName() { return productName; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public double getQuantity() { return quantity; }
    public String getLocation() { return location; }
    public String getId() { return id; }

    //Setters
    public void setProductName(String productName) { this.productName = productName; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public void setLocation(String location) { this.location = location; }
    public void setId(String id) { this.id = id; }
}