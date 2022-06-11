package com.techelevator.view;

import org.junit.Assert;
import org.junit.Test;

public class ItemTest {

    //Tests that the constructor works and so is our getters.
    @Test
    public void item_constructor_returns_all_correct_values() {
        Item suv = new Item("Chicken Pie", 1.25, "Candy");
        String result1 = suv.getName();
        double result2 = suv.getPrice();
        String result3 = suv.getType();
        int result4 = suv.getQuantity();
        Assert.assertEquals("Chicken Pie", result1);
        Assert.assertEquals(1.25, result2, 0.0001);
        Assert.assertEquals("Candy", result3);
        Assert.assertEquals(5, result4);
    }

    //Tests that the setter for name changes the name.
    @Test
    public void set_name_returns_new_set_name_using_getter() {
        Item suv = new Item("Chicken Pie", 1.25, "Candy");
        suv.setName("Ice Pop");
        String result1 = suv.getName();
        Assert.assertEquals("Ice Pop", result1);
    }

    //Tests that the setter for price changes the price.
    @Test
    public void set_price_returns_new_set_price_using_getter() {
        Item suv = new Item("Chicken Pie", 1.25, "Candy");
        suv.setPrice(2.55);
        double result1 = suv.getPrice();
        Assert.assertEquals(2.55, result1, 0.0001);
    }

    //Tests that the setter for price changes the price.
    @Test
    public void set_type_returns_new_set_type_using_getter() {
        Item suv = new Item("Chicken Pie", 1.25, "Candy");
        suv.setType("Gum");
        String result1 = suv.getType();
        Assert.assertEquals("Gum", result1);
    }

    //Tests that the setter for quantity changes the quantity.
    @Test
    public void set_quantity_returns_new_set_quantity_using_getter() {
        Item suv = new Item("Chicken Pie", 1.25, "Candy");
        suv.setQuantity(4);
        int result1 = suv.getQuantity();
        Assert.assertEquals(4, result1);
    }
}
