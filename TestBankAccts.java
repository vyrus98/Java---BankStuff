import java.util.Scanner;

//***********************************************************
// TestBankAccts1
// A simple program to test the numAccts method of the 
// BankAcct class.  
//***********************************************************
public class TestBankAccts
{
	BankAcct acct1,acct2,acct3,acct4,acct5,acct6;

	public TestBankAccts()
	{
		System.out.println("Creating bank accounts");
		acct1=new BankAcct("Jamie", 154.34, 0);
		acct2=new BankAcct("Connor", 344, 0);
		acct3=new BankAcct("Julia", 30, 0);        
		acct4=new BankAcct("Jamie", 555, 0);                  

		printInfo();
	}	

	public void printInfo()
	{
		System.out.println(acct1);
		System.out.println(acct2);
		System.out.println(acct3);
		System.out.println(acct4);
		if (acct5!=null)
			System.out.println(acct5);
		if (acct6!=null)
			System.out.println(acct6);
		System.out.println("Total number of BankAcct is " + BankAcct.getNumOfAccounts());
		System.out.println("Total funds " + BankAcct.getTotalMoney());

	}
	
	

	public void testCloseAccount()
	{
		System.out.println("Attempting to close account 2");
		pause();
		acct2.close();                
		printInfo();    

	}
	
	public void consolidate(){
		System.out.println("Attempting to consolidate accounts 1 and 4");
		pause();
		//acct5=BankAcct.consolidate(acct1,acct4);  
		printInfo();         
	}
	
	public void testTransfer()
	{
		//acct6=new BankAcct(2253, "TJ");                  
		System.out.println("Active accounts");
		pause();                
		System.out.println(acct3);
		System.out.println(acct5);
		System.out.println(acct6);       

		System.out.println();        
		System.out.println("Attempting transfer of $200 from acct3 to acct6");
		pause();             
		//acct3.transfer(acct6,200);
		System.out.println(acct3);
		System.out.println(acct5);
		System.out.println(acct6); 

		System.out.println();        
		System.out.println("Attempting transfer of $200 from acct3 to acct6");
		pause();             
		//BankAcct.transfer(acct3,acct6,200);
		System.out.println(acct3);
		System.out.println(acct5);
		System.out.println(acct6);   

	}		

	public void pause()
	{
		System.out.println("Hit enter to continue:");
		Scanner scan=new Scanner (System.in);
		scan.nextLine();
	}

	public static void main(String args[])
	{
		TestBankAccts t = new TestBankAccts();
		t.printInfo();
		t.testCloseAccount();
		//t.consolidate();
		//t.testTransfer();
	}
}