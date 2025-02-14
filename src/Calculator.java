public class Calculator {
    //creating method for sum two numbers
    public int add(int x, int y){
        return x + y;
    }
    //creating method for multiply two numbers
    public int multiply(int x, int y){
        return x * y;
    }
    //creating method for multiplying the number by itself~
    public int square(int x){
        return (int) Math.pow(x,2);
    }

    public static void main(String[]args){
        Calculator cal = new Calculator();

        //expression 1 is calculating
        int a1 = cal.multiply(3,4);
        int a2 = cal.multiply(5,7);
        int a3 = cal.add(a1,a2);
        int a4 = cal.square(a3);

        System.out.println("(3 ∗ 4 + 5 ∗ 7)^2 result is = "+a4);

        //expression 2 is calculating
        int d1 = cal.add(4,7);
        int d2 = cal.square(d1);
        int d3 = cal.add(8,3);
        int d4 = cal.square(d3);
        int d5 = cal.add(d2,d4);

        System.out.println("(4 + 7)^2 + (8 + 3)^2 result is = "+d5);
    }
}