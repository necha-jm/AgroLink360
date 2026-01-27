package com.app.agroli;

public class Product {
    public String name;
    public String category;
    public double price;
    public double quantity;
    public String location;

    public Product(String name, String category, double price, double quantity, String location) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.location = location;
    }
}
