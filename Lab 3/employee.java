public class employee {
    private int EmployeeID;
    private String Name;
    private double Salary;

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public double getSalary() {
        return Salary;
    }
    public void setSalary(double Salary){
        this.Salary=Salary;
    }

    public double calculateBonus(int rating) {
        if (rating > 5 || rating < 1) {
            System.out.print("ensuring that performance ratings are within a valid range (1 to 5).");

        }
        double bonus=0;
        if(rating==5){
            bonus=(Salary*20)/100;
            System.out.println(bonus);

        }
        switch (rating) {
            case 5:
                bonus = Salary * 0.20;
                break;
            case 4:
                bonus = Salary * 0.15;
                break;
            case 3:
                bonus = Salary * 0.10;
                break;
            case 2:
                bonus = Salary * 0.05;
                break;
            case 1:
                bonus = 0.0; // No bonus for rating 1
                break;
        }
        System.out.println(bonus);
        return bonus;

    }

    public double calculateTotalSalary(double bonus){
        Salary=Salary+bonus;
        return Salary;
    }



}
