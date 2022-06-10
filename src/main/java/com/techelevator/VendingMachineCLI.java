package com.techelevator;

import com.techelevator.view.Item;
import com.techelevator.view.Log;
import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VendingMachineCLI {

	/* Variables for main menu */
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };

	/* Variables for purchase menu */
	private static final String PURCHASE_PROCESS_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_PROCESS_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_PROCESS_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_PROCESS_OPTIONS = {PURCHASE_PROCESS_OPTION_FEED_MONEY, PURCHASE_PROCESS_OPTION_SELECT_PRODUCT, PURCHASE_PROCESS_OPTION_FINISH_TRANSACTION};

	private Menu menu;
	private Map<String, Item> availableItems = new HashMap<String, Item>();
	private double currentMoney = 0.00;

	//============Constructor=================
	public VendingMachineCLI(Menu menu)
	{
		this.menu = menu;
	}

	//============Helper Functions============
	public void logAction(String action)
	{
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a");
		String formattedDate = myDateObj.format(myFormatObj);
		Log.log(formattedDate + action);
	}

	public double twoDecimals(double money)
	{
		String toTwoDecimals = String.format("%.2f", money);
		return Double.parseDouble(toTwoDecimals);
	}

	public void displayChange(int quarters, int dimes, int nickels)
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
	//========================================

	//============Main Functions==============
	/*
		Takes a file and populates the hashmap using the slot location as key and stores other information into an Item as value
	*/
	public void populate()
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
				availableItems.put(slotLocation, item);
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem with populate: " + e.getMessage());
		}
	}

	/*
		Sorts through the hashmap and displays every item along with their properties ordered by slot location.
	*/
	public void displayMenu()
	{
		System.out.println("========================MENU========================");
		Map<String, Item> sortedMap = new TreeMap<String, Item>(availableItems);
		for (Map.Entry<String, Item> service : sortedMap.entrySet())
		{
			String itemName = service.getValue().getName();
			double itemPrice = service.getValue().getPrice();
			int quantity = service.getValue().getQuantity();
			if (quantity == 0)
			{
				System.out.println(service.getKey() + ": " + itemName + " | " + "Price: $" + itemPrice + " | Remaining: SOLD OUT" );
			}
			else
			{
				System.out.println(service.getKey() + ": " + itemName + " | " + "Price: $" + itemPrice + " | Remaining: " + quantity);
			}
		}
		System.out.println("====================================================");
	}

	/*
		Displays the purchase menu with the option to feed money, select product, and finish transaction.
	*/
	public void purchaseMenu()
	{
		System.out.println("");
		System.out.println("Current Money Provided: $" + currentMoney);
		String choice2 = (String) menu.getChoiceFromOptions(PURCHASE_PROCESS_OPTIONS);
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
			-Logs money insert into the log.txt - NOT DONE YET
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
				double beforeMoney = this.currentMoney;
				this.currentMoney += money;
				double afterMoney = this.currentMoney;
				logAction(" FEED MONEY: $" + beforeMoney + " $" + afterMoney);
				System.out.println("You added: $" + money + "! Current Money Provided: $" + this.currentMoney);
			}
			else
			{
				System.out.println("The amount of money you have given is not in whole dollars, try again!");
			}
		}
		catch (Exception e)
		{
			System.out.println("The amount you entered is an invalid input!");
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

		if (!availableItems.containsKey(answer))
		{
			System.out.println("Sorry, that product code doesn't exist!");
			purchaseMenu();
		}
		else
		{
			Item item = availableItems.get(answer);
			if (item.getQuantity() == 0)
			{
				System.out.println("Sorry, that item is sold out!");
				purchaseMenu();
			}
			else
			{
				if (currentMoney >= item.getPrice())
				{
					double beforeMoney = this.currentMoney;
					this.currentMoney -= item.getPrice();
					double roundToTwoDecimals = twoDecimals(this.currentMoney);
					this.currentMoney = roundToTwoDecimals;
					double afterMoney = this.currentMoney;

					String itemType = item.getType();
					availableItems.get(answer).setQuantity(availableItems.get(answer).getQuantity() - 1);
					System.out.println("Dispensing item.....");
					switch (itemType)
					{
						case "Chip":
							System.out.println("Crunch Crunch, Yum!");
							break;
						case "Candy":
							System.out.println("Munch Munch, Yum!");
							break;
						case "Drink":
							System.out.println("Glug Glug, Yum!");
							break;
						case "Gum":
							System.out.println("Chew Chew, Yum!");
							break;
					}
					logAction(" " + item.getName() + " " + answer + " $" + beforeMoney + " $" + afterMoney);
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
		System.out.println("Current Money Provided: $" + currentMoney);
		int numberOfQuarters = 0;
		int numberOfDimes = 0;
		int numberOfNickels = 0;
		String balanceInString = String.valueOf(currentMoney);
		String[] wholeDollarAndChange = balanceInString.split("\\.");

		//Deal with whole dollars.
		int wholeDollars =  Integer.parseInt(wholeDollarAndChange[0]);
		numberOfQuarters += wholeDollars * 4;

		//Deal with change.
		int change = Integer.parseInt(wholeDollarAndChange[1]);

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
		double beforeMoney = this.currentMoney;
		this.currentMoney = 0;
		double afterMoney = this.currentMoney;
		logAction(" GIVE CHANGE: $" + beforeMoney + " $" + afterMoney);
		displayChange(numberOfQuarters, numberOfDimes, numberOfNickels);
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
			//OPTION 4 HERE
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		Log log = new Log();
		cli.populate();
		cli.run();
	}
}