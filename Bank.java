

import java.util.Scanner;
import java.util.*;

public class Bank {
	private List<BankAccount> allAccounts = new ArrayList<BankAccount>();

	public Bank()
	{

		allAccounts.add(new BankAccount("cyrus", 700));
		allAccounts.add(new BankAccount("test", 20));
		allAccounts.add(new BankAccount("Al", 12314.34));
		allAccounts.add(new BankAccount("seven", 0));
		allAccounts.add(new BankAccount("cyrus", 9.9));
		allAccounts.add(new BankAccount("cyrus", 9.9));

		//String input;
		while (true) {
			String input = options();

			if (input.equals("a"))
			{
				createAccount(false);
			}
			//selects individual account
			if (input.equals("b"))
			{
				selectAccount(true);
			}
			//consolidates accounts
			if (input.equals("c"))
			{
				consolidateAccounts();
			}
			//transfer funds
			if (input.equals("d"))
			{
				transferFunds();
			}
			//lists open accounts
			if (input.equals("e"))
			{
				listOpenAccounts(false, false);
			}
			//lists bank info
			if (input.equals("f"))
			{
				listBankInfo();
			}
			if (input.equals("g")) {
				break;
			}
		}


	}

	//If there is an available account (one that is null), it will read in username and balance and create a new account
	public void createAccount(boolean isNull) {
		if (!isNull) {
			print ("Enter the name for this account: ");
			String name = readString();
			if (name.toLowerCase().contains("CLOSED") || name.toLowerCase().contains("null") ||name.isEmpty()) {
				print ("Error while naming\n");
				return;
			}
			print ("Enter the initial deposit: ");
			double initial = -1;
			try {
				initial = BankAccount.realAmount(Double.parseDouble(readString()));
			}
			catch (NumberFormatException e) {
				print ("Bad amount\n");
				return;
			}
			if (initial >= 0) {
				allAccounts.add(new BankAccount(name, initial));
				print ("You have created an account:\n" + allAccounts.get(allAccounts.size() -1).toString() + "\n");
			}
		}
		else {
			allAccounts.add(new BankAccount("null", 0));
		}
	}

	//THIS IS THE TRICKY ONE - Display the accounts (listOpenAccounts) and read in the user which account they want to work on
	//Then ask if they would like to make a deposit or withdrawl or getbalance
	//Do what they ask     
	public void selectAccount(boolean list) {
		if (list) {
			listOpenAccounts(false, false);
		}

		int selection = -1;

		print ("Please select an account by full account number, or `e` to exit: ".replace('`', '"'));
		String read = readString();
		try {
			selection = Integer.parseInt(read);
		}
		catch (NumberFormatException e) {
			if (read.equals("e")) {
				return;
			}
			else {
				print ("Bad selection\n");
				selectAccount(false);
			}
		}

		if (selection < 1000 || selection > allAccounts.size() + 1000 || allAccounts.get(selection - 1000).acctHolder.contains("CLOSED") || allAccounts.get(selection - 1000).acctHolder.contains("null")) {
			print ("Bad selection\n");
			selectAccount(false);
		}
		else {
			print ("You selected account number " + selection + ", under " + allAccounts.get(selection - 1000).acctHolder + "\n");
			while (true) {
				print ("Would you like to [d]eposit, [w]ithdraw, [g]etBalance, [c]lose, or [e]xit? ");
				String input = readString();
				if (input.equals("d")) {
					print ("How much? ");
					try {
						double amount = Double.parseDouble(readString());
						if (amount > 0) {
							allAccounts.get(selection - 1000).deposit(amount, false);
							print ("You deposited $" + amount + "\n");
							//continue;
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
						if (amount > 0 && amount < allAccounts.get(selection - 1000).getBalance()) {
							allAccounts.get(selection - 1000).withdraw(amount);
							print ("You withdrew $" + amount + "\n");
							//continue;
						}
						else {
							print ("Sorry, that is not a valid amount\n");
						}
					}
					catch (NumberFormatException e) {
						print ("Sorry, that is not a valid amount");
					}
				}

				else if (input.equals("g")) {
					print ("$" + allAccounts.get(selection - 1000).getBalance() + "\n");
				}

				else if (input.equals("c")) {
					allAccounts.get(selection - 1000).close();
					print ("Account closed\n");
					break;
				}
				else if (input.equals("e")) {
					print ("exiting\n");
					break;
				}
				else {
					print ("Bad input\n");
					continue;
				}
			}
		}
	}

	//If there are atleast 2 accounts and atleast one open account (one thats null) show the user the accounts (listOpenAccounts) and then
	//ask the user which accounts to consolidate.  Then consolidate them (creating a new accnt) telling them the new account info
	public void consolidateAccounts() {
		if (allAccounts.size() > 2) {
			if (listOpenAccounts(true, false) != -1) {
				int selection1 = -1;
				int selection2 = -1;
				print ("Please enter each account number on its own line, or `e` to exit:\n".replace('`', '"'));
				String read = readString();
				try {
					selection1 = Integer.parseInt(read);
					selection2 = Integer.parseInt(readString());
				}
				catch (NumberFormatException e) {
					if (read.equals("e")) {
						return;
					}
					else {
						print ("Bad selection\n");
						consolidateAccounts();
					}
				}

				if ( (selection1 < 1000 || selection1 > allAccounts.size() + 1000 || allAccounts.get(selection1 - 1000).acctHolder.contains("CLOSED") || allAccounts.get(selection1 - 1000).acctHolder.contains("null")) || (selection2 < 1000 || selection2 > allAccounts.size() + 1000 || allAccounts.get(selection2 - 1000).acctHolder.contains("CLOSED") || allAccounts.get(selection2 - 1000).acctHolder.contains("null")) ) {
					print ("Bad selection\n");
					consolidateAccounts();
				}
				else {
					allAccounts.add(BankAccount.consolidate(allAccounts.get(selection1 - 1000), allAccounts.get(selection2 - 1000)));
					print ("Accounts consolidated. New account:\n" + allAccounts.get(allAccounts.size() -1).toString() + "\n");
				}
			}
			else {
				consolidateAccounts();
			}
		}
	}

	//transfers funds If there are atleast 2 accounts 
	//ask the user which accounts to transfer money and then ask how much money.  
	//Then transfer the money
	public void transferFunds() {
		transferFunds(null, null, 0);
	}

	public void transferFunds(BankAccount sa, BankAccount ra, double amount) {
		if (sa != null && ra != null) {
			BankAccount.transfer(sa, ra, amount);
		}
		else {
			listOpenAccounts(false, false);
			print ("Which accounts would you like to transfer between? Enter each account number on its own line\n");
			int acct1 = -1;
			int acct2 = -1;
			try {
				acct1 = Integer.parseInt(readString());
				acct2 = Integer.parseInt(readString());
			}
			catch (NumberFormatException e) {
				print ("Bad selection\n");
			}
			if ( (acct1 < 1000 || acct1 > allAccounts.size() + 1000 || allAccounts.get(acct1 - 1000).acctHolder.contains("CLOSED") || allAccounts.get(acct1 - 1000).acctHolder.contains("null")) || (acct2 < 1000 || acct2 > allAccounts.size() + 1000 || allAccounts.get(acct2 - 1000).acctHolder.contains("CLOSED") || allAccounts.get(acct2 - 1000).acctHolder.contains("null")) ) {
				print ("Bad selection\n");
				transferFunds(null, null, 0);
			}
			else {
				BankAccount account1 = allAccounts.get(acct1 - 1000);
				BankAccount account2 = allAccounts.get(acct2 - 1000);
				print ("How much? ");
				double transfer = 0;
				try {
					transfer = Double.parseDouble(readString());
					double complete = account2.getBalance();
					transferFunds(account1, account2, transfer);
					if (account2.getBalance() > complete) {
						print ("You transferred $" + transfer + " from " + account1.getAcctNum() + " to " + account2.getAcctNum() + "\n");
					}
					else {
						print ("Error bad amount\n");
					}
				}
				catch (NumberFormatException e) {
					print ("Bad input\n");
				}
			}
		}

	}

	//this method lists the open bank accounts 
	public int listOpenAccounts(boolean search, boolean all) {
		if (!search) {
			int count = 0;
			for (int i=0; i<allAccounts.size(); i++) {
				if (all || (!allAccounts.get(i).acctHolder.contains("CLOSED") && !allAccounts.get(i).acctHolder.contains("null")) ) {
					print (allAccounts.get(i).toString() + "\n");
					count++;
				}
			}
			return count;
		}
		else {
			print("There are " + BankAccount.getNumOfAccounts() + " accounts, would you like to search by name or number? [y/n] ");
			String input = readString().toLowerCase();
			if (input.equals("n")) {
				listOpenAccounts(false, false);
				return 0;
			}
			else if (input.equals("y")) {
				print ("Enter a name or number or partial to search for: ");
				input = readString();
				int count = 0;
				try {
					int number = Integer.parseInt(input);
					for (int i=0; i<allAccounts.size(); i++) {
						if (Integer.toString(allAccounts.get(i).getAcctNum()).startsWith(Integer.toString(number))) {
							print (allAccounts.get(i).toString() + "\n");
							count ++;
						}
					}

				}
				catch (NumberFormatException e) {
					for (int i=0; i<allAccounts.size(); i++) {
						if (allAccounts.get(i).acctHolder.startsWith(input)) {
							print (allAccounts.get(i).toString() + "\n");
							count ++;
						}
					}
				}
				return count;
			}
			else {
				print ("bad input\n");
				return -1;
			}
		}
	}

	//this method will print all the bank info - how many accounts, how much money they hold
	public void listBankInfo() {
		listOpenAccounts(false, true);
		print ("There are " + BankAccount.getNumOfAccounts() + " open accounts\n");
		print ("Total bank worth is $" + BankAccount.getTotalMoney() + "\n");
	}



	public String readString() {
		Scanner scan = new Scanner(System.in);
		//scan.close();
		return scan.nextLine();
	}

	public void print(String s) {
		System.out.print(s);
	}

	public String options () {
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




	public static void main(String[] args) {
		Bank b=new Bank();

	}

}