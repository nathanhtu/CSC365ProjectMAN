package menus;

import java.util.Scanner;

public class LibrarianMenu {

    public static void run() {
        int operation;
        Scanner scan = new Scanner(System.in);
        while (true) {
            showLibrarianOptions();
            do{
                System.out.print("Enter the number of the desired operation: ");
                while (!scan.hasNextInt()){
                    System.out.print("Invalid entry. Please try again: ");
                    scan.next();
                }
                operation = scan.nextInt();
            } while (operation < 1 || operation > 3);

            if (operation == 1) {
                System.out.println("Checking out.");
            }
            else if (operation == 2) {
                System.out.println("Returning.");
            }
            else if (operation == 3) {
                System.out.println("Exiting student menu.");
                System.out.println();
                break;
            }
        }
    }

    public static void showLibrarianOptions() {
        System.out.println();
        System.out.println("-- Librarian Operations-- ");
        System.out.println("(1) Checkout a book.");
        System.out.println("(2) Return a book.");
        System.out.println("(3) Quit.");
        System.out.println();
    }
}
