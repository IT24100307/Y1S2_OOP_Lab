import java.util.Scanner;
public class Question5 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String a[] = new String[5]; //defining array what user want add5 words
        int total = 0; //for calculate total characters

        //getting input for array
        for (int i = 0; i < 5; i++) {
            System.out.print("Enter word " + (i + 1) + ": ");
            a[i] = in.nextLine();
            total += a[i].length(); //calculating total words
        }

        String longword = a[0]; //for find the longest word

        //finding the longest word
        for (int i = 0; i < a.length; i++) {
            if (a[i].length() > longword.length()) {
                longword = a[i];
            }
        }

        //displaying longest word & total number of characters
        System.out.println("\nThe longest word is: " + longword);
        System.out.println("The total number of characters in all the words combined = " + total);

        //Identifying and displaying the words with an even number of characters
        System.out.print("Words with an even number of characters: ");
        for (int i = 0; i < a.length; i++) {
            if (a[i].length() % 2 == 0) {
                System.out.print(a[i] + " ");
            }

        }
    }
}

