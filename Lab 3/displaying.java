import java.util.Scanner;

public class displaying {
    public static void main(String[] args) {
        Scanner I=new Scanner(System.in);

        employee a1=new employee();

        int EmployeeID,rating;
        double Salary;
        String name;

        System.out.print("Enter Employee ID: ");
        EmployeeID=I.nextInt();
        I.nextLine();
        a1.setEmployeeID(EmployeeID);

        System.out.print("Enter Employee Name: ");
        name=I.nextLine();
        a1.setName(name);

        System.out.print("Enter Basic Salary: ");
        Salary=I.nextDouble();
        a1.setSalary(Salary);

        System.out.print("Enter Performance Rating (1-5): ");
        rating=I.nextInt();
        double Bonus=a1.calculateBonus(rating);

        System.out.println("\nEmployee Details: ");
        System.out.println("Employee ID: "+a1.getEmployeeID());
        System.out.println("Name: "+a1.getName());
        System.out.println("Performance Rating: "+rating);
        System.out.println("Bonus: "+Bonus);
        System.out.println("Total Salary: "+a1.calculateTotalSalary(Bonus));


    }
}
