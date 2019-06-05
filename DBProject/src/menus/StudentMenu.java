package menus;

import java.util.Scanner;

public class StudentMenu {

    public static void run() {
        int operation, ID;
        Scanner scan = new Scanner(System.in);
        
        private ResultSet resultSet = null;
        private PreparedStatement prepStatement = null;
        
        //scan for first and last name
        System.out.print("Enter your student ID: ");
        ID = scan.nextInt();

        //not yet valid without the connect object
        prepStatement = connect.prepareStatement("select status, numBooks from Students where studentID = ?");
        prepStatement.setInt(1, ID);
        
        //execute Query
        resultSet = prepStatement.executeQuery();
        //take the information out of result set and add it to an object or variables
        //probably need to make this a loop in case the student ID doesnt exist or the
        //query returns an empty table
        
        while (true) {
            showStudentOptions();
            do{
                System.out.print("Enter the number of the desired operation: ");
                while (!scan.hasNextInt()){
                    System.out.print("Invalid entry. Please try again: ");
                    scan.next();
                }
                operation = scan.nextInt();
            } while (operation < 1 || operation > 6);

            if (operation == 1) {
                System.out.println("Checking out.");
            }
            else if (operation == 2) {
                System.out.println("Returning.");
            }
            else if (operation == 3) {
                System.out.println("Reserving.");
            }
            else if (operation == 4) {
                System.out.println("Extending due date.");
            }
            else if (operation == 5) {
                System.out.println("Searching.");
            }
            else if (operation == 6) {
                System.out.println("Exiting student menu.");
                System.out.println();
                break;
            }
        }
    }

    public static void showStudentOptions() {
        System.out.println();
        System.out.println("-- Student Operations-- ");
        System.out.println("(1) Checkout a book.");
        System.out.println("(2) Return a book.");
        System.out.println("(3) Make a reservation.");
        System.out.println("(4) Extend due date on a checkout.");
        System.out.println("(5) Search book availability.");
        System.out.println("(6) Quit.");
        System.out.println();
    }
        
}
