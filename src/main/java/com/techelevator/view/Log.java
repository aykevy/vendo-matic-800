package com.techelevator.view;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Log
{
    /*
        This function returns us the String path to the csv file depending on your machine OS.
    */
    public static String getOSPathVendingMachineCSV()
    {
        String path = "";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("win"))
        {
            path = System.getProperty("user.dir") + "\\" + "vendingmachine.csv";
        }
        else if (os.toLowerCase().contains("mac"))
        {
            path = System.getProperty("user.dir") + "/" + "vendingmachine.csv";
        }
        return path;
    }

    /*
        This function allows us to access the log file in order to write messages to it.
    */
    public static void log(String message)
    {
        File capstoneDirectory = new File(System.getProperty("user.dir"));
        try
        {
            if (capstoneDirectory.exists())
            {
                File log = new File(capstoneDirectory, "log.txt");
                if (!log.exists())
                {
                    log.createNewFile();
                }
                try (PrintWriter writer = new PrintWriter(new FileOutputStream(log, true)))
                {
                    writer.println(message);
                }
                catch(FileNotFoundException e)
                {
                    System.out.println("File not found.");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Unable to create log.txt");
        }
    }

    /*
        This function formats a message depending on the action taken and calls Log.log.
    */
    public static void logAction(String action)
    {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a");
        String formattedDate = myDateObj.format(myFormatObj);
        Log.log(formattedDate + action);
    }

    /*
        This function creates a sales report when the user selects the hidden option on the main menu.
    */
    public static void salesReport(Map<String, Item> availableItems)
    {
        File capstoneDirectory = new File(System.getProperty("user.dir"));
        try
        {
            if (capstoneDirectory.exists())
            {
                String reportName = "sales-report";
                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss-");
                String formattedDate = myDateObj.format(myFormatObj);

                File log = new File(capstoneDirectory, formattedDate + reportName);
                if (!log.exists())
                {
                    log.createNewFile();
                }
                try (PrintWriter writer = new PrintWriter(new FileOutputStream(log, true)))
                {
                    double total = 0.00;
                    for (Map.Entry<String, Item> service : availableItems.entrySet())
                    {
                        String itemName = service.getValue().getName();
                        double itemPrice = service.getValue().getPrice();
                        int quantity = service.getValue().getQuantity();
                        int quantitySold = 5 - quantity;
                        total += itemPrice * quantitySold;
                        writer.println(itemName + "|" + quantitySold);
                    }
                    writer.println("");
                    writer.print("TOTAL SALES: " + FormatDoubles.formatDouble(total));
                }
                catch(FileNotFoundException e)
                {
                    System.out.println("File not found.");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}