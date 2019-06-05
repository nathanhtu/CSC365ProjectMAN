package menus;

import java.util.Scanner;

public class ManagerMenu {

    public static void run() {
        int operation;
        Scanner scan = new Scanner(System.in);
        while (true) {
            showManagerOptions();
            do{
                System.out.print("Enter the number of the desired operation: ");
                while (!scan.hasNextInt()){
                    System.out.print("Invalid entry. Please try again: ");
                    scan.next();
                }
                operation = scan.nextInt();
            } while (operation < 1 || operation > 4);

            if (operation == 1) {
                System.out.println("Showing month by month revenue.");
            }
            else if (operation == 2) {
                System.out.println("Displaying all checked out books.");
            }
            else if (operation == 3) {
                System.out.println("Displaying all overdue books.");
            }
            else if (operation == 4) {
                System.out.println("Exiting student menu.");
                System.out.println();
                break;
            }
        }
    }

    public static void showManagerOptions() {
        System.out.println();
        System.out.println("-- Manager Operations-- ");
        System.out.println("(1) Display month by month overview of revenue.");
        System.out.println("(2) Display all checked out books.");
        System.out.println("(3) Display all overdue books.");
        System.out.println("(4) Quit.");
        System.out.println();
    }
}
