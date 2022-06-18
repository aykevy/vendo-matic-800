package com.techelevator.view;

public class FormatDoubles
{
    /*
		Sometimes doubles have like a lot of leading zeroes like 15.500000000002 after doing some
		arithmetic, so this fixes that by making it to two decimal points, so it would be 15.50.
	    Doubles use IEEE 754 floating point arithmetic, which is inherently inaccurate.
	*/
    public static double twoDecimals(double money)
    {
        String toTwoDecimals = String.format("%.2f", money);
        return Double.parseDouble(toTwoDecimals);
    }

    /*
        Sometimes doubles will not have a leading zero, so we can format it and add a leading 0.
        Example: 15.5 -> 15.50
                 10.0 -> 10.00
    */
    public static String formatDouble(double money)
    {
        String moneyInString = Double.toString(money);
        String[] parts = moneyInString.split("\\.");
        String dollars = parts[0];
        String cents = parts[1];
        if (cents.length() == 1)
        {
            cents += "0";
        }
        return dollars + "." + cents;
    }
}