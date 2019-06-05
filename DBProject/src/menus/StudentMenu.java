package menus;

import java.util.Scanner;

public class StudentMenu {

    public static void run() {
        int operation, ID;
        Scanner scan = new Scanner(System.in);
        
        private Statement statement = null;
        private ResultSet resultSet = null;
        private 
            
            
            aredStatement prepStatement = null;
        String entry;
        
        //scan for first and last name
        //needs a student object
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
                System.out.println("Checking out.")
                statement = createStatement();
                resultSet = statement.executeQuery("select * from Books);
                DBTablePrinter.printResultSet(resultSet);

                //loop for checkout search
                do{
                    System.out.print("Would you like to search by (1)serial, (2)title, (3)author, (4)genre? ");
                    while (!scan.hasNextInt()){
                        System.out.print("Invalid entry. Please try again: ");
                        scan.next();
                    }
                    operation = scan.nextInt();
                } while (operation < 1 || operation > 4);

                //needs a loop if set returns empty
                if (operation == 1){
                    System.out.print("Enter the serial number: ");
                }
                else if (operation == 2){
                    System.out.print("Enter the title: ");
                }
                else if (operation == 3){
                    System.out.print("Enter the author: ");
                }
                else if (operation == 4){
                    System.out.print("Enter the genre: ");
                }
                entry = scan.nextLine();
                //skeleton set up. Needs to be either distributed in each operation choice
                //or made into a general statement with variables
                prepStatement = connect.prepareStatement("select * from Books where ? = ?");
                prepStatement.setString(1, operation);
                prepStatement.setString(2, entry);
                resultSet = prepStatement.executeQuery();
                DBTablePrinter.printResultSet(resultSet);
                
                //if resultSet not empty
                System.out.print("Enter the serial of the book you want to reserve or type 0 to go back: ");
                entry = scan.nextLine();
                //if entry == 0, loop
                //check if stock == 0 or reservations > stock
                Book b = getBookBySerial(entry);
                
                prepStatement = connect.
                    prepareStatement("insert into Checkout (serial, studentID, checkoutDate, dueDate) values (?, ?, ?, ?));
                prepStatement.setString(1, entry);
                //needs studentID from student object
                prepStatement.setInt(2, studentID);
                //CURDATE() used in mysql not java.sql
                prepStatement.setString(3, CURDATE());
                prepStatement.setString(4, CURDATE());
                prepStatement.executeUpdate();
                //triggers should take care to update Books.stock
                //also needs trigger to upddate Reservations.checkedOut if user had previous reservation
                
            }
            else if (operation == 2) {
                System.out.println("Returning.");
                //show all books cheked out
                prepStatement = connect.prepareStatement("select * from Checkout where checkinDate is NULL and studentID = ?);
                //needs studentID from student object
                prepStatement.setString(1, studentID);
                resultSet = prepStatement.executeQuery();
                //needs check if set is empty before printing or asking to return book
                DBTablePrinter.printResultSet(resultSet);
                
                System.out.print("Enter the serial of the book you want to return or 0 to cancel");
                entry = scan.nextLine();
                //needs loop for incorrect entries
                prepStatement = connect.
                    prepareStatement("update Checkout set checkinDate = CURDATE() where serial = ? and checkinDate is NULL");
                prepStatement.setString(1, entry);
                prepStatement.executeUpdate();
            }
            else if (operation == 3) {
                System.out.println("Reserving.");
                //
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
