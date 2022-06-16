package com.techelevator.view;

import org.junit.Assert;
import org.junit.Test;

public class FormatDoublesTest {

    //This test cuts the float value with at least 1 whole number to two decimal places.
    @Test
    public void big_float_value_returns_float_value_with_two_decimals(){
        double suv = 15.0000000002;
        double result = FormatDoubles.twoDecimals(suv);

        Assert.assertEquals(15.00, result, 0.0001);
    }

    //This test cuts the float value with at least 0 as the whole number to two decimal places.
    @Test
    public void big_float_value_with_zero_whole_number_returns_float_value_with_two_decimals(){
        double suv = 0.1000000002;
        double result = FormatDoubles.twoDecimals(suv);

        Assert.assertEquals(0.10, result, 0.0001);
    }

    //This test cuts the float value with no whole number to two decimal places.
    @Test
    public void float_value_with_no_whole_number_returns_float_value_with_two_decimals()
    {
        double suv = .15000000000000006;
        double result = FormatDoubles.twoDecimals(suv);
        Assert.assertEquals(0.15, result, 0.0001);
    }

    //This test makes a single digit be formatted with two decimal points in string form.
    @Test
    public void float_value_with_no_decimal_returns_string_formatted_float_with_two_decimals()
    {
        double suv = 1;
        String result = FormatDoubles.formatDouble(suv);
        Assert.assertEquals("1.00", result);
    }

    //This test makes a float with 1 decimal be formatted with two decimal points in string form.
    @Test
    public void float_value_with_one_decimal_returns_string_formatted_float_with_two_decimals()
    {
        double suv = 0.1;
        String result = FormatDoubles.formatDouble(suv);
        Assert.assertEquals("0.10", result);
    }

    //This test makes the number 0 turn into 0.00 in string form.
    @Test
    public void float_value_with_no_value_returns_string_formatted_float_with_two_decimals()
    {
        double suv = 0;
        String result = FormatDoubles.formatDouble(suv);
        Assert.assertEquals("0.00", result);
    }
}