public class Question2{
    public static void main(String[]args){

        //defining arrays
        int A[]={10,20,30,40,50};
        int B[]={34,67,12,89,12};
        int C[]=new int[5];

        //Calculate A+B and store in Array C.
        for(int i=0;i<A.length;i++){
            C[i]=A[i]+B[i];
        }

        //printing output
        System.out.print("Array C: [");

        for(int i=0;i<A.length;i++){
            System.out.print(C[i]+",");
        }
        System.out.print("]");
    }
}

