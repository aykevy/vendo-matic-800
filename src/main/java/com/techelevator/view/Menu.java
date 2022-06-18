package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import static com.techelevator.view.FormatDoubles.formatDouble;

public class Menu
{
	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	/*
		This is mainly for the purchase menu since we take in money to display it below the options.
	*/
	public Object getChoiceFromOptionsWithMoney(Object[] options, double money) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptionsWithMoney(options, money);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			//Made it so that when it gets to 3, it doesn't list as option to make it hidden for sales report.
			if (i != 3)
			{
				int optionNum = i + 1;
				out.println(optionNum + ") " + options[i]);
			}
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}

	/*
		This is mainly for the purchase menu since we take in money to display it below the options.
	*/
	private void displayMenuOptionsWithMoney(Object[] options, double money) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Current Money Provided: $" + formatDouble(money) + "\n\n" + "Please choose an option >>> ");
		out.flush();
	}
}