public class BankAcct {
	private double balance;
	String acctHolder;
	private int acctNum;
	private double interestRate;
	private static int numOfAccounts = 0;
	private static double totalMoney = 0;
	
	public BankAcct() {
		this("null", 0, 0);
	}

	public BankAcct(String acctHolder, double balance, double interestRate) {
		this.acctHolder = acctHolder;
		this.balance = balance;
		this.interestRate = interestRate;
		acctNum = numOfAccounts+1000;
		numOfAccounts++;
		totalMoney += balance;
	}

	public void deposit(double amount) {
		double realAmount = (int)(amount*100)/100.0;
		balance += realAmount;
		totalMoney += realAmount;
		System.out.println("You deposited " + realAmount + " dollars.");
	}

	public void withdraw(double amount) {
		if (amount <= balance) {
			double realAmount = (int)(amount*100)/100.0;
			balance -= realAmount;
			totalMoney -= realAmount;
			System.out.println("You withdrew " + realAmount + " dollars.");
		}
		else {
			System.out.println("You do not have that much money in your account");
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
		System.out.println("You have closed account " + acctNum + " under " + acctHolder);
		acctHolder += " - CLOSED";
		numOfAccounts --;
		totalMoney -= balance;
		balance = 0.0;
		
	}

	public static BankAcct consolidate(BankAcct acct1, BankAcct acct2) {
		if (acct1.acctHolder.equals(acct2.acctHolder) && acct1.getAcctNum() != acct2.getAcctNum()) {
			double funds = acct1.getBalance() + acct2.getBalance();
			String name = acct1.acctHolder;
			double interest = (acct1.getInterestRate() + acct2.getInterestRate())/2.0;
			acct1.close();
			acct2.close();
			return new BankAcct(name, funds, interest);
		}
		else {
			System.out.println("You can not consolidate those accounts");
			return null;
		}
	}
	
	public void transfer(BankAcct receivingAcct, double amount) {
		transfer(this, receivingAcct, amount);
	}
	
	public static void transfer(BankAcct sendingAcct, BankAcct receivingAcct, double amount) {
		if (!sendingAcct.acctHolder.contains("CLOSED") && !receivingAcct.acctHolder.contains("CLOSED") && amount > 0 && amount <= sendingAcct.balance) {
			double realAmount = (int)(amount*100)/100.0;
			sendingAcct.withdraw(realAmount);
			receivingAcct.deposit(realAmount);
		}
		else {
			System.out.println("Sorry, you do not have the funds to do that, or are attempting to transfer an illegal amount");
		}
	}

	public String toString() {
		return "Account " + getAcctNum() + ": " + acctHolder + " - $" + getBalance();
	}
}
