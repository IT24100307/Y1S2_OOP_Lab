public class Distance{
    public static void main(String[]args){

        /*a) Declare two integer variables, miles and yards, and one double variable for kilometers*/

        int miles;
        int yards;
        double kilometers;

        /*b) Initialize the variables to hold the number of miles and yards in a marathon respectively (miles to 26 and yards to 385).*/

        miles = 26;
        yards = 385;

        /*c) Write an expression to calculate kilometers from miles and yards.*/

        kilometers = (miles + (yards / 1760.0)) * 1.609;

        /*d) Save the result of the expression in the variable kilometers.*/

        System.out.println("The distance of a marathon in kilometers is: " + kilometers);
    }
}
