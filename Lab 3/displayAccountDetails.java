import java.util.Scanner;

public class displayAccountDetails {
    public static void main(String[] args) {
        Scanner I=new Scanner(System.in);

        SavingsAccount s1 =new SavingsAccount();

        double diposit=0,withdrawl=0;
        String name;
        int AccNumber;

        System.out.print("Enter Account Number: ");
        AccNumber=I.nextInt();
        I.nextLine();
        s1.setAccountNumber(AccNumber);

        System.out.print("Enter account holder name: ");
        name=I.nextLine();
        s1.setAccountName(name);

        System.out.print("Enter initial deposit: ");
        diposit=I.nextDouble();

        System.out.print("Deposited "+s1.deposit(diposit)+" ");
        System.out.println("New Balance: "+s1.getAccountBalnce());

        System.out.print("Enter deposit amount: ");
        diposit=I.nextDouble();
        s1.deposit(diposit);

        System.out.print("Deposited "+diposit+" ");
        System.out.println("New Balance: "+s1.getAccountBalnce());

        System.out.print("Enter Withdrawal amount: ");
        withdrawl=I.nextDouble();
        s1.withdraw(withdrawl);

        System.out.print("Withdrawn: "+withdrawl+" ");
        System.out.println("New Balance: "+s1.getAccountBalnce());

        System.out.println("\nFinal Account Derails:");
        System.out.println("Account Number: "+s1.getAccountName());
        System.out.println("Account Holder: "+s1.getAccountName());
        System.out.println("Balance: "+s1.getAccountBalnce());
    }
}
