package com.techelevator.view;

import org.junit.Assert;
import org.junit.Test;

public class LogTest {

    /* Tests that the OS path is returned correctly based on machine. */
    @Test
    public void os_path_returns_based_on_machine()
    {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("win"))
        {
            Assert.assertEquals(System.getProperty("user.dir") + "\\" + "vendingmachine.csv", Log.getOSPathVendingMachineCSV());
        }
        else if ((os.toLowerCase().contains("mac")))
        {
            Assert.assertEquals(System.getProperty("user.dir") + "/" + "vendingmachine.csv", Log.getOSPathVendingMachineCSV());
        }
    }

    //No other tests for other functions since they are all void methods.
}
