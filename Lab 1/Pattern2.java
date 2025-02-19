public class Pattern2{
    public static void main(String[] args) {

        // Outer loop for rows
        for (int i = 1; i <= 5; i++) {
            // Inner loop for spaces
            for (int j = 1; j <= 5 - i; j++) {
                System.out.print("  "); // Print spaces
            }

            // Inner loop for stars
            for (int k = 1; k <= 2 * i - 1; k++) {
                System.out.print("* "); // Print stars
            }
            // Move to the next line after each row
            System.out.println();
        }
    }
}
