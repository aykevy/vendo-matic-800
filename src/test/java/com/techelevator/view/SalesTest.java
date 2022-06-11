package com.techelevator.view;

import org.junit.Assert;
import org.junit.Test;

public class SalesTest {

    //This tests a normal expected value where we have 1 dollar and 25 cents to equal 5 quarters in change.
    @Test
    public void one_as_whole_number_25_as_decimal_returns_array_with_5_quarters_0_dimes_0_nickels()
    {
        String[] suv = {"1", "25"};
        int[] result = Sales.getChange(suv);
        int[] expected = {5, 0, 0};
        Assert.assertArrayEquals(expected, result);
    }

    //This will test a value where we have 1 whole dollar and 2 digit decimal to equal 4 quarters, 2 dimes in change. This
    //makes sure that we always add a 0 for 1 digit decimals in order to be accurate with change.
    @Test
    public void one_as_whole_number_single_digit_2_as_decimal_returns_array_with_4_quarters_2_dimes_0_nickels()
    {
        String[] suv = {"1", "2"};
        int[] result = Sales.getChange(suv);
        int[] expected = {4, 2, 0};
        Assert.assertArrayEquals(expected, result);
    }

    //This tests a normal expected value where we have 1 dollar and 20 cents to equal 4 quarters, 2 dimes in change.
    @Test
    public void one_as_whole_number_two_digit_20_as_decimal_returns_array_with_4_quarters_2_dimes_0_nickels()
    {
        String[] suv = {"1", "20"};
        int[] result = Sales.getChange(suv);
        int[] expected = {4, 2, 0};
        Assert.assertArrayEquals(expected, result);
    }

    //This tests a normal expected value where we have 0 dollar and 0 cents to equal 0 in everything.
    @Test
    public void zero_as_whole_number_zero_as_decimal_returns_array_with_0_quarters_0_dimes_0_nickels()
    {
        String[] suv = {"0", "0"};
        int[] result = Sales.getChange(suv);
        int[] expected = {0, 0, 0};
        Assert.assertArrayEquals(expected, result);
    }

    //This tests a value where we have 0 whole dollars but 50 in decimal to equal 2 quarters.
    @Test
    public void zero_as_whole_number_fifty_as_decimal_returns_array_with_2_quarters_0_dimes_0_nickels()
    {
        String[] suv = {"0", "50"};
        int[] result = Sales.getChange(suv);
        int[] expected = {2, 0, 0};
        Assert.assertArrayEquals(expected, result);
    }

    //This tests a normal expected value where we have 1 dollar and 65 cents to equal 5 quarters,
    //1 dime, 1 nickel in change.
    @Test
    public void one_as_whole_number_sixty_five_as_decimal_returns_array_with_6_quarters_1_dimes_1_nickels()
    {
        String[] suv = {"1", "65"};
        int[] result = Sales.getChange(suv);
        int[] expected = {6, 1, 1};
        Assert.assertArrayEquals(expected, result);
    }
}