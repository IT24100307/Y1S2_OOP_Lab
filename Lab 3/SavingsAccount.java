public class SavingsAccount {
   private int AccountNumber;
   private String AccountName;
   private double AccountBalnce;

   public int getAccountNumber(){
      return AccountNumber;
   }

   public void setAccountNumber(int AccountNumber) {
      this.AccountNumber = AccountNumber;
   }
   public String getAccountName(){
      return AccountName;
   }
   public void setAccountName(String AccountName){
      this.AccountName=AccountName;
   }

   public double getAccountBalnce(){
      return AccountBalnce;
   }

   public double deposit(double amount){
      if(amount<0){
         System.exit(0);
      }
      AccountBalnce=amount+AccountBalnce;
      return AccountBalnce;

   }
   public double withdraw(double amount){
      if(amount>AccountBalnce){
         System.exit(0);
      }
      AccountBalnce=AccountBalnce-amount;
      return AccountBalnce;
   }

}