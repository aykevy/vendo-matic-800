package com.techelevator.view;

public class Item
{
    private String name;
    private double price;
    private String type;
    private int quantity = MAX_QUANTITY;

    private static final int MAX_QUANTITY = 5;

    /*
        Constructor for an item object.
    */
    public Item(String name, double price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }
    /*
        Prints out the message based on item type.
    */
    public void printUniqueTypeMessage()
    {
        switch (this.type.toLowerCase())
        {
            case "chip":
                System.out.println("Crunch Crunch, Yum!");
                break;
            case "candy":
                System.out.println("Munch Munch, Yum!");
                break;
            case "drink":
                System.out.println("Glug Glug, Yum!");
                break;
            case "gum":
                System.out.println("Chew Chew, Yum!");
                break;
            default:
                break;
        }
    }

    /*
        Below are all getters and setters for an item object.
    */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}