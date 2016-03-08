
public class BankAccount {
	//Statics for BankAccount as a whole
	private static int openAccounts = 0;
	private static int allAccounts = 0;
	private static double totalMoney = 0;
	//Variables local to each instance of BankAccount
	private double balance;
	String acctHolder;
	private int acctNum;

	public BankAccount() {
		this("null", 0);
	}

	public BankAccount(String name, double balance) {
		acctNum = 1000 + allAccounts;
		acctHolder = name;
		this.balance = realAmount(balance);
		totalMoney += this.balance;
		openAccounts ++;
		allAccounts ++;
	}

	public static int getNumOfAccounts() {
		return openAccounts;
	}

	public static double getTotalMoney() {
		return totalMoney;
	}

	public static double realAmount(double amount) {
		return (int)(amount*100)/100.0;
	}

	public void deposit(double amount) {
		if (amount > 0 && !(acctHolder.contains("null") || acctHolder.contains("CLOSED"))) {
			double realAmount = realAmount(amount);
			balance += realAmount;
			totalMoney += realAmount;
		}
	}

	public void withdraw(double amount) {
		if (amount > 0 && amount <= getBalance() && !(acctHolder.contains("null") || acctHolder.contains("CLOSED"))) {
			deposit(-amount);
		}
	}

	public double getBalance() {
		return balance;
	}

	public int getAcctNum() {
		return acctNum;
	}
	
	public void transfer(BankAccount ra, double amount) {
		transfer(this, ra, amount);
	}

	public static void transfer(BankAccount sa, BankAccount ra, double amount) {
		if (!(sa.acctHolder.contains("null") || sa.acctHolder.contains("CLOSED") || ra.acctHolder.contains("null") || ra.acctHolder.contains("CLOSED"))) {
			double realAmount = realAmount(amount);
			sa.withdraw(realAmount);
			ra.deposit(realAmount);
		}
	}
	
	public void close() {
		acctHolder += " - CLOSED";
		totalMoney -= balance;
		balance = 0.0;
		openAccounts --;
	}
	
	public static BankAccount consolidate(BankAccount acct1, BankAccount acct2) {
		if (!acct1.acctHolder.contains("CLOSED") && !acct1.acctHolder.contains("null") && !acct2.acctHolder.contains("CLOSED") && !acct2.acctHolder.contains("null") && acct1.acctHolder.equals(acct2.acctHolder) && acct1.getAcctNum() != acct2.getAcctNum()) {
			
		}
		else {
			
		}
		return null;
	}
	
	public String toString() {
		return "Account " + getAcctNum() + ": " + acctHolder + " - $" + getBalance();
	}


}
