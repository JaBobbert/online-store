package com.pluralsight;

public class Product {
    private String id;
    private String item;
    private double price;
    private String department;

    public Product(String id, String item, double price, String department){
        this.id = id;
        this.item = item;
        this.price = price;
        this.department = department;
    }
    public String id() {
        return id;
    }

    public String item() {
        return item;
    }

    public double price() {
        return price;
    }

    public String department() {
        return department;
    }

    @Override
    public String toString() {
        return id + " | " + item + " | " + price + " | " + department + " | ";
    }
}
