import java.util.Scanner;

public class Ques1 {
    public static void main(String[] args) {
        Scanner I=new Scanner(System.in);

        int EmployeType;
        double salary,OThours,Total;
        int OtRate=0;

        System.out.print("Enter your Type:- ");
        EmployeType=I.nextInt();

        if(EmployeType==1){
            OtRate=1000;
        }
        if(EmployeType==2){
            OtRate=1500;
        }
        if(EmployeType==3){
            OtRate=1700;
        }
        else {

        }

        System.out.print("Enter your Basic Salary:- ");
        salary=I.nextDouble();

        System.out.print("Enter you spend Overtime Hours:- ");
        OThours=I.nextDouble();

        Total=salary+(OThours*OtRate);
        System.out.println("Total salary after including overtime pay:- "+Total);
    }
}
