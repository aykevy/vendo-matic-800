package com.techelevator.view;

import java.io.*;
import java.nio.charset.Charset;

public class Log {
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

}

