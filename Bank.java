/**
 * This class will be an interface for bank workers.
 * 
 * @Original author Tony Quang
 * @version 3/15/07
 */

import java.util.Scanner;
import java.util.*;

public class Bank
{
	Scanner scan = new Scanner(System.in);
	//private BankAcct[] allAccounts;// = new BankAcct[20];
	private List<BankAcct> allAccounts = new ArrayList<BankAcct>();
	/**
	 * Constructor for objects of class Bank
	 */
	public Bank()
	{


		allAccounts.add(new BankAcct("cyrus", 700, 0));
		allAccounts.add(new BankAcct("test", 20, 0));
		allAccounts.add(new BankAcct("Ali", 12314.34, 0));
		allAccounts.add(new BankAcct("seven", 0, 0));
		allAccounts.add(new BankAcct("cyrus", 9.9, 0));
		allAccounts.add(new BankAcct("cyrus", 9.9, 0));

		String optionString="";
		System.out.println("WELCOME TO OUR BANK");

		while (!optionString.equals("g")) {
			optionString=options();
			if (optionString.equals("a"))
			{
				createAccount();
			}
			//selects individual account
			else if (optionString.equals("b"))
			{
				selectAccount();
			}
			//consolidates accounts
			else if (optionString.equals("c"))
			{
				consolidateAccounts();
			}
			//transfer funds
			else if (optionString.equals("d"))
			{
				transferFunds();
			}
			//lists open accounts
			else if (optionString.equals("e"))
			{
				listOpenAccounts();
			}
			//lists bank info
			else if (optionString.equals("f"))
			{
				listBankInfo();
			}
			else if (optionString.equals("g")) {
				break;
			}
			else {
				System.out.println("Bad input, try again");
			}

		}

	}

	//This method displays the user options
	public String options() {
		System.out.println("\nChoose one\n"+
				"a. create new account\n"+
				"b. select individual account\n"+
				"c. consolidate accounts\n"+
				"d. transfer funds\n"+
				"e. list open accounts\n"+
				"f. list bank info\n"+
				"g. exit");
		return readString();    
	}

	public String readString() {
		scan = new Scanner(System.in);
		String input=scan.nextLine();
		return input;
	}

	//If there is an available account (one that is null), it will read in username and balance and create a new account
	public void createAccount() {
		print("You are creating a new account.\nPlease enter a name for the account, or null for a null account: ");
		String name = readString();
		if (!name.contains("CLOSED") && !name.isEmpty() && !name.equals("null")) {
			print("And the initial deposit: ");
			try {
				double funds = ((int)(Double.parseDouble(readString())*100)/100.0);
				print("New account created under " + name + ".\n");
				allAccounts.add(new BankAcct(name, funds, 0));
			}
			catch (NumberFormatException e) {
				print("\nBad Input " + e.getMessage());
			}
		}
		else if (name.equals("null")) {
			allAccounts.add(new BankAcct());
		}
		else {
			print("Bad input, account names cannot contain `CLOSED` or be empty".replace('`','"'));
		}
	}

	//THIS IS THE TRICKY ONE - Display the accounts (listOpenAccounts) and read in the user which account they want to work on
	//Then ask if they would like to make a deposit or withdrawl or getbalance
	//Do what they ask     
	public void selectAccount() {
		while (true) {
			print("There are " + BankAcct.getNumOfAccounts() + " accounts, would you like to search by name or number? [y/n] ");
			String answer = readString();
			if (answer.toLowerCase().equals("y")) {
				print("Please enter a name to search for, or an account number or partial number: ");
				String search = readString().toLowerCase();
				try {
					int number = Integer.parseInt(search);
					if (!searchByNumber(number)) {
						print ("No accounts found\n");
						break;
					}
				}
				catch (NumberFormatException e) {
					listOpenAccounts(search);
				}
			}
			else if (answer.toLowerCase().equals("n")) {
				print("Printing all accounts\n");
				listOpenAccounts();
			}
			else {
				print("Bad input\n");
				break;
			}
			print("Select an account by entering their full account number: ");
			String selection = readString();
			try {
				int number = Integer.parseInt(selection);
				if (number < 1000 || number > allAccounts.size() + 1000) {
					print("Bad selection\n");
					break;
				}
				print ("You selected account number " + selection + ", under " + allAccounts.get(number - 1000).acctHolder + "\n");
				print ("Would you like to [d]eposit, [w]ithdraw, [c]lose, or [e]xit? ");
				String input = readString().toLowerCase();
				if (input.equals("d")) {
					print ("How much? ");
					try {
						double amount = Double.parseDouble(readString());
						if (amount > 0) {
							allAccounts.get(number - 1000).deposit(amount);
							break;
						}
						else {
							print ("Sorry, that is not a valid amount\n");
						}
					}
					catch (NumberFormatException e) {
						print ("Sorry, that is not a valid amount\n");
					}
				}
				else if (input.equals("w")) {
					print ("How much? ");
					try {
						double amount = Double.parseDouble(readString());
						if (amount > 0 && amount < allAccounts.get(number - 1000).getBalance()) {
							allAccounts.get(number - 1000).withdraw(amount);
							break;
						}
						else {
							print ("Sorry, that is not a valid amount\n");
						}
					}
					catch (NumberFormatException e) {
						print ("Sorry, that is not a valid amount");
					}
				}
				else if (input.equals("c")) {
					allAccounts.get(number - 1000).close();
					break;
				}
				else if (input.equals("e")) {
					break;
				}
			}
			catch (NumberFormatException e) {
				print("Bad selection\n");
			}
		}
	}

	//If there are atleast 2 accounts and atleast one open account (one thats null) show the user the accounts (listOpenAccounts) and then
	//ask the user which accounts to consolidate.  Then consolidate them (creating a new accnt) telling them the new account info
	public void consolidateAccounts() {
		if (allAccounts.size() >= 2) {
			if (!listOpenAccounts("null")) {
				allAccounts.add(new BankAcct());
			}
			
			listBankInfo();
			
			listOpenAccounts();
			print ("Enter the account numbers of the accounts to be consolidated, pressing enter in between:\n");			
			try {
				BankAcct firstAcct = allAccounts.get((Integer.parseInt(readString()) - 1000));
				BankAcct secondAcct = allAccounts.get((Integer.parseInt(readString()) - 1000));
				for (int i=0; i<=allAccounts.size(); i++) {
					if (i == allAccounts.size()) {
						listBankInfo();
						print("Failure");
					}
					else {
						String accountName = allAccounts.get(i).acctHolder;
						if (accountName.contains("null")) {
							listBankInfo();
							allAccounts.set(i, BankAcct.consolidate(firstAcct, secondAcct));
							print(allAccounts.get(i).getAcctNum() + "\n");
							listBankInfo();
							break;
						}
					}
				}

			}
			catch (NumberFormatException e) {
				print ("Bad entry\n");
			}
		}
		else {
			print ("No available accounts\n");
		}
	}

	//transfers funds If there are atleast 2 accounts 
	//ask the user which accounts to transfer money and then ask how much money.  
	//Then transfer the money    
	public void transferFunds() {
		print ("Which accounts would you like to transfer between?\nEnter each on their own line, sending then receiving:\n");
		String acct1 = readString();
		String acct2 = readString();
		print ("How much? ");
		String amount = readString();
		try {
			BankAcct sending = allAccounts.get(Integer.parseInt(acct1) - 1000);
			BankAcct receiving = allAccounts.get(Integer.parseInt(acct2) - 1000);
			double realAmount = (int)(Double.parseDouble(amount)*100)/100.0;
			print ("Accounts currently:\n");
			print (allAccounts.get(Integer.parseInt(acct1) - 1000).toString() + "\n");
			print (allAccounts.get(Integer.parseInt(acct2) - 1000).toString() + "\n");
			BankAcct.transfer(sending, receiving, realAmount);
			print ("Accounts after transfer:\n");
			print (allAccounts.get(Integer.parseInt(acct1) - 1000).toString() + "\n");
			print (allAccounts.get(Integer.parseInt(acct2) - 1000).toString() + "\n");
		}
		catch (NumberFormatException e) {
			print("Bad amount or bad account numbers\n");
		}

	}

	//this method lists the open bank accounts 
	public void listOpenAccounts() {
		listOpenAccounts("");
	}

	public boolean listOpenAccounts(String name) {
		int count = 0;
		for (int i=0; i<allAccounts.size(); i++) {
			String accountName = allAccounts.get(i).acctHolder;
			if (!accountName.contains("CLOSED") && !accountName.contains("null") && accountName.contains(name)) {
				print (allAccounts.get(i).toString() + "\n");
				count++;
			}
		}
		if (count == 0) {
			if (!name.equals("null")) {
				print("Sorry, no accounts founds under your search terms\n");
			}
			return false;
		}
		else {
			return true;
		}
	}

	//takes an integer and finds bank accounts that start with that number sequence
	//good for finding a specific account or a group of accounts such as the first 100, 'searchByNumber(10)'
	//would print accounts 1000, 1001, 1002.... 1098, 1099 because they all start with '10'
	public boolean searchByNumber(int search) {
		if (search < allAccounts.size() + 1000) {
			int count = 0;
			for (int i=0; i<allAccounts.size();) {
				int digits = String.valueOf(search).length();
				int accDigits = String.valueOf(allAccounts.get(i).getAcctNum()).length();
				int getDig = (int)Math.pow( 10, (accDigits - digits) );
				int digSet = allAccounts.get(i).getAcctNum()/getDig;
				if (search == digSet) {
					print (allAccounts.get(i).toString() + "\n");
					i++;
					count++;
				}
				else {
					i += getDig;
				}
			}
			if (count == 0) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			print("Bad account number, no accounts found");
			return false;
		}
	}

	//this method will print all the bank info - how many accounts, how much money they hold
	public void listBankInfo() {
		for (int i=0; i<allAccounts.size(); i++) {
			print (allAccounts.get(i).toString() + "\n");
		}
		print (allAccounts.size() + " accounts\n");
		print ("Total bank worth $" + BankAcct.getTotalMoney() + "\n");
	}

	public void print(String s) {
		System.out.print(s);
	}

	public static void main(String[] args) {
		Bank b=new Bank();
	}
}