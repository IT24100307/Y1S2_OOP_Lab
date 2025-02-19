import java.util.Scanner;
public class arrays1 {
    public static void main(String[] args) {
        Scanner I=new Scanner(System.in);

        int a[]=new int[5]; //defining array

        //getting inputs for the array
        for(int i=0;i<a.length;i++){
            System.out.print("Enter "+(i+1)+" number = ");
            a[i]=I.nextInt();
        }

        //finding large value what user input
        int b = a[0];
        for(int i=0;i<a.length;i++){
            if(a[i]>b){
                b=a[i];
            }
        }

        //calculating sum of all array elements
        int total =0;
        for(int i=0;i<a.length;i++){
            total=total+a[i];
        }

        //display commands
        System.out.println("\nThe largest value in the array = " +b);
        System.out.print("The sum of all elements in the array = "+total);
    }
}
