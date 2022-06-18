package com.techelevator;

import com.techelevator.view.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.techelevator.view.FormatDoubles.formatDouble;
import static com.techelevator.view.FormatDoubles.twoDecimals;

public class VendingMachineCLI {

	/* Variables for main menu */
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_HIDDEN = "Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_HIDDEN};

	/* Variables for purchase menu */
	private static final String PURCHASE_PROCESS_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_PROCESS_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_PROCESS_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_PROCESS_OPTIONS = {PURCHASE_PROCESS_OPTION_FEED_MONEY, PURCHASE_PROCESS_OPTION_SELECT_PRODUCT, PURCHASE_PROCESS_OPTION_FINISH_TRANSACTION};

	private Menu menu;
	private Map<String, Item> inventory = new HashMap<String, Item>();
	private double currentMoney = 0.00;

	/* Constructor for VendingMachineCLI that takes in one parameter, menu. */
	public VendingMachineCLI(Menu menu)
	{
		this.menu = menu;
	}

	/*
		Takes a file using a path according to your OS and populates the hashmap using the slot location
		as key and stores other information	into an Item as value.
	*/
	public void populate()
	{
		String path = Log.getOSPathVendingMachineCSV();
		File vendingMachineItems = new File(path);
		try (Scanner content = new Scanner(vendingMachineItems))
		{
			while (content.hasNextLine())
			{
				String currentLine = content.nextLine();
				String[] informationParsed = currentLine.split("\\|");

				String slotLocation = informationParsed[0];
				String itemName = informationParsed[1];
				double itemPrice = Double.parseDouble(informationParsed[2]);
				String itemType = informationParsed[3];

				Item item = new Item(itemName, itemPrice, itemType);
				inventory.put(slotLocation, item);
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem with populate: " + e.getMessage());
		}
	}

	/* Sorts through the hashmap and displays every item along with their properties ordered by slot location. */
	public void displayMenu()
	{
		System.out.println("========================MENU========================");
		Map<String, Item> sortedMap = new TreeMap<String, Item>(inventory);
		for (Map.Entry<String, Item> service : sortedMap.entrySet())
		{
			String itemName = service.getValue().getName();
			double itemPrice = service.getValue().getPrice();
			int quantity = service.getValue().getQuantity();
			String display = service.getKey() + ": " + itemName + " | " + "Price: $" + formatDouble(itemPrice) + " | Remaining: ";
			String displayQuantity = (quantity == 0) ? "SOLD OUT" : String.valueOf(quantity);
			System.out.println(display + displayQuantity);
		}
		System.out.println("====================================================");
	}

	/* Displays the purchase menu with the option to feed money, select product, and finish transaction. */
	public void purchaseMenu()
	{
		String choice2 = (String) menu.getChoiceFromOptionsWithMoney(PURCHASE_PROCESS_OPTIONS, currentMoney);
		if (choice2.equals(PURCHASE_PROCESS_OPTION_FEED_MONEY))
		{
			feedMoney();
		}
		else if (choice2.equals(PURCHASE_PROCESS_OPTION_SELECT_PRODUCT))
		{
			selectProduct();
		}
		else if (choice2.equals(PURCHASE_PROCESS_OPTION_FINISH_TRANSACTION))
		{
			finishTransaction();
		}
	}

	/*
		1) Feed Money, takes whole number inputs and adds to the customer balance.
			-Logs money insert into the log.txt
	*/
	public void feedMoney()
	{
		try
		{
			System.out.print("How much money do you want to put in (whole dollar amounts): ");
			Scanner userInput = new Scanner(System.in);
			String answer = userInput.nextLine();
			double money = Double.parseDouble(answer);
			if ((int) money == money)
			{
				double beforeMoney = currentMoney;
				currentMoney += money;
				double afterMoney = currentMoney;
				Log.logAction(" FEED MONEY: $" + formatDouble(beforeMoney) + " $" + formatDouble(afterMoney));
				System.out.println("You added: $" + formatDouble(money) + "! Current Money Provided: $" + formatDouble(currentMoney));
				purchaseMenu();
			}
			else
			{
				System.out.println("The amount of money you have given is not in whole dollars, try again!");
				purchaseMenu();
			}
		}
		catch (Exception e)
		{
			System.out.println("The amount you entered is an invalid input!");
			purchaseMenu();
		}
	}

	/*
		2) Allows customer to select product.
			-Handles user input if produce code doesn't exist
			-Handles user input if item is out of stock
			-Displays appropriate message based on item type.
			-Logs purchase into log.txt
	*/
	public void selectProduct()
	{
		displayMenu();
		System.out.print("Which product would you like to select: ");
		Scanner userInput = new Scanner(System.in);
		String answer = userInput.nextLine().toUpperCase();

		if (!inventory.containsKey(answer))
		{
			System.out.println("Sorry, that product code doesn't exist!");
			purchaseMenu();
		}
		else
		{
			Item item = inventory.get(answer);
			if (item.getQuantity() == 0)
			{
				System.out.println("Sorry, that item is sold out!");
				purchaseMenu();
			}
			else
			{
				if (currentMoney >= item.getPrice())
				{
					double beforeMoney = currentMoney;

					currentMoney -= item.getPrice();
					double roundToTwoDecimals = twoDecimals(currentMoney);
					currentMoney = roundToTwoDecimals;

					double afterMoney = currentMoney;
					inventory.get(answer).setQuantity(inventory.get(answer).getQuantity() - 1);

					System.out.println("Dispensing item: " + item.getName() + ", Price: $" + item.getPrice() + ", Money Remaining : $" + formatDouble(currentMoney)); //CHANGE STUFF HERE
					item.printUniqueTypeMessage();
					Log.logAction(" " + item.getName() + " " + answer + " $" + formatDouble(beforeMoney) + " $" + formatDouble(afterMoney));
					purchaseMenu();
				}
				else
				{
					System.out.println("You don't have enough money.");
					purchaseMenu();
				}
			}
		}
	}

	/*
		3) Finish customer purchases and gives them the change based on the fewest coins needed.
			-Returns to first menu after finishing transaction.
	*/
	public void finishTransaction()
	{
		System.out.println("Current Money Provided: $" + formatDouble(currentMoney));
		String balanceInString = String.valueOf(currentMoney);
		String[] wholeDollarAndChange = balanceInString.split("\\.");

		int[] coins = Sales.getChange(wholeDollarAndChange);

		double beforeMoney = currentMoney;
		currentMoney = 0;
		double afterMoney = currentMoney;

		Log.logAction(" GIVE CHANGE: $" + formatDouble(beforeMoney) + " $" + formatDouble(afterMoney));
		Sales.displayChange(coins[0], coins[1], coins[2]);
	}

	/*
		Runs the program and displays the main option.
			-Has a hidden 4th option for sales report.
	*/
	public void run()
	{
		while (true)
		{
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS))
			{
				displayMenu();
			}
			else if (choice.equals(MAIN_MENU_OPTION_PURCHASE))
			{
				purchaseMenu();
			}
			else if(choice.equals(MAIN_MENU_OPTION_EXIT))
			{
				break;
			}
			else if (choice.equals(MAIN_MENU_OPTION_HIDDEN))
			{
				System.out.println("Sales Report Created.");
				Log.salesReport(inventory);
			}
		}
	}

	/*
		Executable main function for the program. Used to run the vending machine.
	*/
	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		Log log = new Log();
		cli.populate();
		cli.run();
	}
}