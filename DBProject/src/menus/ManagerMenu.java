package menus;

import java.util.Scanner;
import util.*;
import java.sql.*;

public class ManagerMenu {
    private ResultSet resultSet = null;
    private PreparedStatement prepStatement = null;
    private Connection connect = null;
    
    private String longQuery = "SELECT DISTINCT b.serial, b.title, \n" + 
    		"	IF (JAN.jan IS NOT NULL, JAN.jan, 0) as Jan,\n" + 
    		"	IF (FEB.feb IS NOT NULL, FEB.feb, 0) as Feb,\n" + 
    		"	IF (MAR.mar IS NOT NULL, MAR.mar, 0) as Mar,\n" + 
    		"	IF (APR.apr IS NOT NULL, APR.apr, 0) as Apr,\n" + 
    		"	IF (MAY.may IS NOT NULL, MAY.may, 0) as May,\n" + 
    		"	IF (JUN.jun IS NOT NULL, JUN.jun, 0) as Jun,\n" + 
    		"	IF (JUL.jul IS NOT NULL, JUL.jul, 0) as Jul,\n" + 
    		"	IF (AUG.aug IS NOT NULL, AUG.aug, 0) as Aug,\n" + 
    		"	IF (SEP.sep IS NOT NULL, SEP.sep, 0) as Sep,\n" + 
    		"	IF (OCT.oct IS NOT NULL, OCT.oct, 0) as Oct,\n" + 
    		"	IF (NOV.nov IS NOT NULL, NOV.nov, 0) as Nov,\n" + 
    		"	IF (DECE.dece IS NOT NULL, DECE.dece, 0) as Dece,\n" + 
    		"	(IF (JAN.jan IS NOT NULL, JAN.jan, 0) + \n" + 
    		"	IF (FEB.feb IS NOT NULL, FEB.feb, 0) +\n" + 
    		"	IF (MAR.mar IS NOT NULL, MAR.mar, 0) +\n" + 
    		"	IF (APR.apr IS NOT NULL, APR.apr, 0) +\n" + 
    		"	IF (MAY.may IS NOT NULL, MAY.may, 0) +\n" + 
    		"	IF (JUN.jun IS NOT NULL, JUN.jun, 0) +\n" + 
    		"	IF (JUL.jul IS NOT NULL, JUL.jul, 0) +\n" + 
    		"	IF (AUG.aug IS NOT NULL, AUG.aug, 0) +\n" + 
    		"	IF (SEP.sep IS NOT NULL, SEP.sep, 0) +\n" + 
    		"	IF (OCT.oct IS NOT NULL, OCT.oct, 0) +\n" + 
    		"	IF (NOV.nov IS NOT NULL, NOV.nov, 0) +\n" + 
    		"	IF (DECE.dece IS NOT NULL, DECE.dece, 0)) as total\n" + 
    		"FROM Checkout c \n" + 
    		"LEFT JOIN Books b ON c.serial = b.serial\n" + 
    		"LEFT JOIN\n" + 
    		"(SELECT c.serial, COUNT(*) as jan \n" + 
    		"From Checkout c\n" + 
    		"WHERE checkoutDate BETWEEN '2018-01-01' AND '2018-01-31'\n" + 
    		"GROUP BY serial) as JAN\n" + 
    		"ON c.serial = JAN.serial\n" + 
    		"LEFT JOIN\n" + 
    		"(SELECT c.serial, COUNT(*) as feb\n" + 
    		"From Checkout c\n" + 
    		"WHERE checkoutDate BETWEEN '2018-02-01' AND '2018-02-28'\n" + 
    		"GROUP BY serial) as FEB\n" + 
    		"ON c.serial = FEB.serial\n" + 
    		"LEFT JOIN\n" + 
    		"(SELECT c.serial, COUNT(*) as mar\n" + 
    		"From Checkout c\n" + 
    		"WHERE checkoutDate BETWEEN '2018-03-01' AND '2018-03-31'\n" + 
    		"GROUP BY serial) as MAR\n" + 
    		"ON c.serial = MAR.serial\n" + 
    		"LEFT JOIN\n" + 
    		"(SELECT c.serial, COUNT(*) as apr\n" + 
    		"From Checkout c\n" + 
    		"WHERE checkoutDate BETWEEN '2018-04-01' AND '2018-04-30'\n" + 
    		"GROUP BY serial) as APR\n" + 
    		"ON c.serial = APR.serial\n" + 
    		"LEFT JOIN\n" + 
    		"(SELECT c.serial, COUNT(*) as may\n" + 
    		"From Checkout c\n" + 
    		"WHERE checkoutDate BETWEEN '2018-05-01' AND '2018-05-31'\n" + 
    		"GROUP BY serial) as MAY\n" + 
    		"ON c.serial = MAY.serial\n" + 
    		"LEFT JOIN\n" + 
    		"(SELECT c.serial, COUNT(*) as jun\n" + 
    		"From Checkout c\n" + 
    		"WHERE checkoutDate BETWEEN '2018-06-01' AND '2018-06-30'\n" + 
    		"GROUP BY serial) as JUN\n" + 
    		"ON c.serial = JUN.serial\n" + 
    		"LEFT JOIN\n" + 
    		"(SELECT c.serial, COUNT(*) as jul\n" + 
    		"From Checkout c\n" + 
    		"WHERE checkoutDate BETWEEN '2018-07-01' AND '2018-07-31'\n" + 
    		"GROUP BY serial) as JUL\n" + 
    		"ON c.serial = JUL.serial\n" + 
    		"LEFT JOIN\n" + 
    		"(SELECT c.serial, COUNT(*) as aug\n" + 
    		"From Checkout c\n" + 
    		"WHERE checkoutDate BETWEEN '2018-08-01' AND '2018-08-31'\n" + 
    		"GROUP BY serial) as AUG\n" + 
    		"ON c.serial = AUG.serial\n" + 
    		"LEFT JOIN\n" + 
    		"(SELECT c.serial, COUNT(*) as sep\n" + 
    		"From Checkout c\n" + 
    		"WHERE checkoutDate BETWEEN '2018-09-01' AND '2018-09-30'\n" + 
    		"GROUP BY serial) as SEP\n" + 
    		"ON c.serial = SEP.serial\n" + 
    		"LEFT JOIN\n" + 
    		"(SELECT c.serial, COUNT(*) as oct\n" + 
    		"From Checkout c\n" + 
    		"WHERE checkoutDate BETWEEN '2018-10-01' AND '2018-10-31'\n" + 
    		"GROUP BY serial) as OCT\n" + 
    		"ON c.serial = OCT.serial\n" + 
    		"LEFT JOIN\n" + 
    		"(SELECT c.serial, COUNT(*) as nov\n" + 
    		"From Checkout c\n" + 
    		"WHERE checkoutDate BETWEEN '2018-11-01' AND '2018-11-30'\n" + 
    		"GROUP BY serial) as NOV\n" + 
    		"ON c.serial = NOV.serial\n" + 
    		"LEFT JOIN\n" + 
    		"(SELECT c.serial, COUNT(*) as dece\n" + 
    		"From Checkout c\n" + 
    		"WHERE checkoutDate BETWEEN '2018-12-01' AND '2018-12-31'\n" + 
    		"GROUP BY serial) as DECE\n" + 
    		"ON c.serial = DECE.serial\n" + 
    		"ORDER BY b.title";

    public ManagerMenu() {
    	
    }
    
    public void run() {
        int operation;
        DBConnector dbc = new DBConnector();
        connect = dbc.getConnection();
        Scanner scan = new Scanner(System.in);
        try {
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
	
	            // Month by month revenue
	            if (operation == 1) {
	                prepStatement = connect.prepareStatement(longQuery);
	                resultSet = prepStatement.executeQuery();
	                DBTablePrinter.printResultSet(resultSet);
	                System.out.println("Showing month by month revenue.");
	            }
	            //Checked out books
	            else if (operation == 2) {
	                prepStatement = connect.prepareStatement("SELECT checkoutID, serial as book, dueDate FROM Checkout WHERE checkinDate IS NULL");
	                resultSet = prepStatement.executeQuery();
	                DBTablePrinter.printResultSet(resultSet);
	                System.out.println("Displaying all checked out books.");
	            }
	            else if (operation == 3) {
	                prepStatement = connect.prepareStatement("SELECT checkoutID, serial as book, dueDate FROM Checkout WHERE CURDATE() > dueDate AND checkinDate IS NULL");
	                resultSet = prepStatement.executeQuery();
	                DBTablePrinter.printResultSet(resultSet);
	                System.out.println("Displaying all overdue books.");
	            }
	            else if (operation == 4) {
	                System.out.println("Exiting manager menu.");
	                System.out.println();
	                break;
	            }
	        }
	        scan.close();
	        prepStatement.close();
	        resultSet.close();
        } catch (Exception e) {
        	System.err.print(e);
        }
    }

    public static void monthByMonth() {

    }

    public static void checkedOut() {

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
