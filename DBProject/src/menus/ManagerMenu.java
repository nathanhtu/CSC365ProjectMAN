package menus;

import java.util.Scanner;
import util.*;
import java.sql.*;

public class ManagerMenu {
    private ResultSet resultSet = null;
    private PreparedStatement prepStatement = null;
    private Connection connect = null;
    private String longQuery = "SELECT DISTINCT b.serial, b.title,
	IF (JAN.jan IS NOT NULL, JAN.jan, 0) as Jan,
	IF (FEB.feb IS NOT NULL, FEB.feb, 0) as Feb,
	IF (MAR.mar IS NOT NULL, MAR.mar, 0) as Mar,
	IF (APR.apr IS NOT NULL, APR.apr, 0) as Apr,
	IF (MAY.may IS NOT NULL, MAY.may, 0) as May,
	IF (JUN.jun IS NOT NULL, JUN.jun, 0) as Jun,
	IF (JUL.jul IS NOT NULL, JUL.jul, 0) as Jul,
	IF (AUG.aug IS NOT NULL, AUG.aug, 0) as Aug,
	IF (SEP.sep IS NOT NULL, SEP.sep, 0) as Sep,
	IF (OCT.oct IS NOT NULL, OCT.oct, 0) as Oct,
	IF (NOV.nov IS NOT NULL, NOV.nov, 0) as Nov,
	IF (DECE.dece IS NOT NULL, DECE.dece, 0) as Dece,
	(IF (JAN.jan IS NOT NULL, JAN.jan, 0) +
	IF (FEB.feb IS NOT NULL, FEB.feb, 0) +
	IF (MAR.mar IS NOT NULL, MAR.mar, 0) +
	IF (APR.apr IS NOT NULL, APR.apr, 0) +
	IF (MAY.may IS NOT NULL, MAY.may, 0) +
	IF (JUN.jun IS NOT NULL, JUN.jun, 0) +
	IF (JUL.jul IS NOT NULL, JUL.jul, 0) +
	IF (AUG.aug IS NOT NULL, AUG.aug, 0) +
	IF (SEP.sep IS NOT NULL, SEP.sep, 0) +
	IF (OCT.oct IS NOT NULL, OCT.oct, 0) +
	IF (NOV.nov IS NOT NULL, NOV.nov, 0) +
	IF (DECE.dece IS NOT NULL, DECE.dece, 0)) as total
    FROM Checkout c
    LEFT JOIN Books b ON c.serial = b.serial
    LEFT JOIN
    (SELECT c.serial, COUNT(*) as jan
    From Checkout c
    WHERE checkoutDate BETWEEN '2018-01-01' AND '2018-01-31'
    GROUP BY serial) as JAN
    ON c.serial = JAN.serial
    LEFT JOIN
    (SELECT c.serial, COUNT(*) as feb
    From Checkout c
    WHERE checkoutDate BETWEEN '2018-02-01' AND '2018-02-28'
    GROUP BY serial) as FEB
    ON c.serial = FEB.serial
    LEFT JOIN
    (SELECT c.serial, COUNT(*) as mar
    From Checkout c
    WHERE checkoutDate BETWEEN '2018-03-01' AND '2018-03-31'
    GROUP BY serial) as MAR
    ON c.serial = MAR.serial
    LEFT JOIN
    (SELECT c.serial, COUNT(*) as apr
    From Checkout c
    WHERE checkoutDate BETWEEN '2018-04-01' AND '2018-04-30'
    GROUP BY serial) as APR
    ON c.serial = APR.serial
    LEFT JOIN
    (SELECT c.serial, COUNT(*) as may
    From Checkout c
    WHERE checkoutDate BETWEEN '2018-05-01' AND '2018-05-31'
    GROUP BY serial) as MAY
    ON c.serial = MAY.serial
    LEFT JOIN
    (SELECT c.serial, COUNT(*) as jun
    From Checkout c
    WHERE checkoutDate BETWEEN '2018-06-01' AND '2018-06-30'
    GROUP BY serial) as JUN
    ON c.serial = JUN.serial
    LEFT JOIN
    (SELECT c.serial, COUNT(*) as jul
    From Checkout c
    WHERE checkoutDate BETWEEN '2018-07-01' AND '2018-07-31'
    GROUP BY serial) as JUL
    ON c.serial = JUL.serial
    LEFT JOIN
    (SELECT c.serial, COUNT(*) as aug
    From Checkout c
    WHERE checkoutDate BETWEEN '2018-08-01' AND '2018-08-31'
    GROUP BY serial) as AUG
    ON c.serial = AUG.serial
    LEFT JOIN
    (SELECT c.serial, COUNT(*) as sep
    From Checkout c
    WHERE checkoutDate BETWEEN '2018-09-01' AND '2018-09-30'
    GROUP BY serial) as SEP
    ON c.serial = SEP.serial
    LEFT JOIN
    (SELECT c.serial, COUNT(*) as oct
    From Checkout c
    WHERE checkoutDate BETWEEN '2018-10-01' AND '2018-10-31'
    GROUP BY serial) as OCT
    ON c.serial = OCT.serial
    LEFT JOIN
    (SELECT c.serial, COUNT(*) as nov
    From Checkout c
    WHERE checkoutDate BETWEEN '2018-11-01' AND '2018-11-30'
    GROUP BY serial) as NOV
    ON c.serial = NOV.serial
    LEFT JOIN
    (SELECT c.serial, COUNT(*) as dece
    From Checkout c
    WHERE checkoutDate BETWEEN '2018-12-01' AND '2018-12-31'
    GROUP BY serial) as DECE
    ON c.serial = DECE.serial
    ORDER BY b.title"

    public static void run() {
        int operation;
        DBConnector dbc = new DBConnector();
        connect = dbc.getConnection();
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
