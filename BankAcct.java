public class BankAcct {
	private double balance;
	String acctHolder;
	private int acctNum;
	private double interestRate;
	private static int numOfAccounts = 0;
	private static double totalMoney = 0;
	
	public BankAcct(String acctHolder, double balance, double interestRate) {
		this.acctHolder = acctHolder;
		this.balance = balance;
		this.interestRate = interestRate;
		acctNum = numOfAccounts+1000;
		numOfAccounts++;
		totalMoney += balance;
	}
	
	public void deposit(double amount) {
		balance += amount;
		totalMoney += amount;
	}
	
	public String withdraw(double amount) {
		if (amount <= balance) {
			balance -= amount;
			totalMoney -= amount;
			return "You withdrew " + amount + " dollars";
		}
		else {
			return "You do not have that much money in your account";
		}
	}
	
	public int getAcctNum() {
		return acctNum;
	}

	public double getBalance() {
		return balance;
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double rate) {
		interestRate = rate;
	}
	public void addInterest() {
		balance += (interestRate * balance);
		totalMoney += (interestRate * balance);
	}
	
	public static int getNumOfAccounts() {
		return numOfAccounts;
	}
	
	public static double getTotalMoney() {
		return (int)(totalMoney*100)/100.0;
	}
	
	public void close() {
		acctHolder += " - CLOSED";
		totalMoney -= balance;
		balance = 0.0;
	}
	
	public static void consolidate() {
		
	}
	
	public String toString() {
		return "Account " + getAcctNum() + ": " + acctHolder + " - $" + getBalance();
	}
}
