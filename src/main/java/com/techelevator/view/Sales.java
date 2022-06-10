package com.techelevator.view;

public class Sales {
    public static int[] getChange(String[] wholeDollarAndChange)
    {
        int numberOfQuarters = 0;
        int numberOfDimes = 0;
        int numberOfNickels = 0;
        //Deal with whole dollars.
        int wholeDollars =  Integer.parseInt(wholeDollarAndChange[0]);
        numberOfQuarters += wholeDollars * 4;

        //Deal with change.
        String changeInString = wholeDollarAndChange[1];

        //In case the floating point value is like .7, we want to add a 0 to make it 70
        if (changeInString.length() == 1)
        {
            changeInString += "0";
        }
        int change = Integer.parseInt(changeInString);

        //See if you can get the remaining change in quarters.
        if (change % 25 == 0)
        {
            numberOfQuarters += change / 25;
        }
        else
        {
            //Get possible quarters otherwise.
            int possibleQuarters = change / 25;
            numberOfQuarters += possibleQuarters;
            change -= (possibleQuarters * 25);

            //See if you can get the remaining change in dimes.
            if (change % 10 == 0)
            {
                numberOfDimes += change / 10;
            }

            else
            {
                //Get possible dimes otherwise.
                int possibleDimes = change / 10;
                numberOfDimes += possibleDimes;
                change -= (possibleDimes * 10);

                //Get the remaining in nickels.
                numberOfNickels += change / 5;
                change = 0;
            }
        }
        int[] coins = {numberOfQuarters, numberOfDimes, numberOfNickels};
        return coins;
    }

    public static void displayChange(int quarters, int dimes, int nickels)
    {
        System.out.println("Your change: ");
        if (quarters > 0)
        {
            System.out.println("Number of Quarters: " + quarters);
        }
        if (dimes > 0)
        {
            System.out.println("Number Of Dimes: " + dimes);
        }
        if (nickels > 0)
        {
            System.out.println("Number Of Nickels: " + nickels);
        }
    }
}
