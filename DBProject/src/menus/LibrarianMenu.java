package menus;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Scanner;
import util.*;
import entity.*;

public class LibrarianMenu {
    private ResultSet resultSet = null;
	private PreparedStatement prepStatement = null;
	private Connection connect = null;
	private DBConnector dbc;
	private String entry;
	
	public LibrarianMenu() {
        dbc = new DBConnector();
        connect = dbc.getConnection();     
	}
	
    public void run() {
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
                bookCheckout();
            }
            else if (operation == 2) {
            	bookReturn();
            }
            else if (operation == 3) {
                System.out.println("Exiting librarian menu.");
                System.out.println();
                break;
            }
        }
    }

    public Student getStudent() {
        Scanner newScanner = new Scanner(System.in);
        Student student = new Student();
        try {
            resultSet = null;
            while (resultSet == null) {
                //scan for first and last name
                //needs a student object
                System.out.print("Enter the student's ID: ");
                int ID = newScanner.nextInt();
                newScanner.nextLine();

                System.out.println("Prepping statement. with ID = " + ID);
                //prepare query with student ID.
                prepStatement = connect.prepareStatement("select * from Students where studentID = ?");
                prepStatement.setInt(1, ID);
                System.out.println("Getting result set.");
                //execute the prepared statement query
                resultSet = prepStatement.executeQuery();
                resultSet.next();
                System.out.println("Placing result set into Student object");
                //check if the student ID exists and create Student if so
                //add all columns into student object
                student.setStudentID(resultSet.getInt("studentID"));
                student.setFirstName(resultSet.getString("firstName"));
                student.setLastName(resultSet.getString("lastName"));
                student.setStatus(resultSet.getString("status"));
                student.setNumBooks(resultSet.getInt("numBooks"));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return student;
    }

    public int queryBooks() {
    	Scanner scan = new Scanner(System.in);
    	int choice = 0;
    	String entry;
    	try {
	    	while (choice != -1){
		        do{
		        	System.out.println("\nHow would you like to search for a book?");
		            System.out.print("Search by (1)serial, (2)title, (3)author, (4)genre, (5)continue or (6)cancel: ");
		            while (!scan.hasNextInt()){
		                System.out.print("Invalid entry. Please try again: ");
		                scan.next();
		            }
		            choice = scan.nextInt();
		            scan.nextLine();
		        } while (choice < 1 || choice > 5);

		        if (choice == 1){
		            System.out.print("Enter the serial number: ");
		            entry = scan.nextLine();
		            prepStatement = connect.prepareStatement("select * from Books where serial LIKE ?");
			        prepStatement.setString(1, "%" + entry + "%");
			        resultSet = prepStatement.executeQuery();
		        }
		        else if (choice == 2){
		            System.out.print("Enter the title: ");
		            entry = scan.nextLine();
		            prepStatement = connect.prepareStatement("select * from Books where title LIKE ?");
			        prepStatement.setString(1, "%" + entry + "%");
			        resultSet = prepStatement.executeQuery();
		        }
		        else if (choice == 3){
		            System.out.print("Enter the author: ");
		            entry = scan.nextLine();
		            prepStatement = connect.prepareStatement("select * from Books where author LIKE ?");
			        prepStatement.setString(1, "%" + entry + "%");
			        resultSet = prepStatement.executeQuery();
		        }
		        else if (choice == 4){
		            System.out.print("Enter the genre: ");
		            entry = scan.nextLine();
		            prepStatement = connect.prepareStatement("select * from Books where genre LIKE ?");
			        prepStatement.setString(1, "%" + entry + "%");
			        resultSet = prepStatement.executeQuery();
		        }
		        else if (choice == 5) {
		        	System.out.println("Continuing without search.");
		        	break;
		        }
		        else if (choice == 6){
		            System.out.println("Canceling search.");
		            choice = -1;
		            continue;
		        }

		        //prints result set if not empty
		        if (resultSet.next() == false){
		            System.out.println("The search returned empty, try again.");
		            continue;
		        }

		        //move pointer back to where it was before printing
	            resultSet.previous();
	            DBTablePrinter.printResultSet(resultSet);

	            //prompt for another search
	            System.out.println("Would you like to search again? (y/n)");
	            entry = scan.nextLine();
	            if (entry.equals("y")){
	            	continue;
	            }
	            break;
	    	}
	    	return choice;
    	} catch (Exception e) {
    		System.err.println(e);
    	} finally {
    		close();
    	}
    	return choice;
    }

    public void bookCheckout() {
        java.util.Date utilDate = new java.util.Date();
        int choice = 0, curStock, curReserved;
        Scanner scan = new Scanner(System.in);
        System.out.println("In librarian checkout.");
        Student student = getStudent();
        try {
            while (choice != -1) {
                //checks if student has max books checked
                if ( (student.getNumBooks() == 3 && student.getStatus().equals("UG")) ||
                        (student.getNumBooks()  == 5 && student.getStatus().equals("GR")) ){
                    System.out.println("Reached maximum number of checkouts.");
                    choice = -1;
                    continue;
                }
                System.out.println("checkpoint 2");

                //query books to see ID's
                int temp = queryBooks();
                if (temp == -1 || temp == 0) {
                    choice = -1;
                    System.out.println("Canceling check out.");
                    continue;
                }

                System.out.print("Enter the serial of the book you want to checkout or type 0 to go back: ");
                entry = scan.nextLine();
                if (entry.equals("0")) {
                    choice = -1;
                    continue;
                }

                //following checks if stock == 0
                prepStatement = connect.prepareStatement("select * from Books where serial = ?");
                prepStatement.setString(1, entry);
                resultSet = prepStatement.executeQuery();
                resultSet.next();
                curStock = resultSet.getInt("stock");
                if (curStock == 0){
                    System.out.println("That book is currently out of stock");
                    continue;
                }

                //following checks if reservations > stock
                prepStatement = connect.prepareStatement("select count(*) as reserved from Reservations where serial = ? and checkedOut is NULL");
                prepStatement.setString(1, entry);
                resultSet = prepStatement.executeQuery();
                resultSet.next();
                curReserved = resultSet.getInt("reserved");
                if (curReserved >= curStock){
                    System.out.println("That book is currently reserved for other students");
                    continue;
                }

                //prepStatement to add into Checkout
                prepStatement = connect.
                    prepareStatement("insert into Checkout (serial, studentID, checkoutDate, dueDate) values (?, ?, ?, ?)");
                prepStatement.setString(1, entry);
                prepStatement.setInt(2, student.getStudentID());
                if (student.getStatus().equals("UG")) {
                    prepStatement.setDate(3, new java.sql.Date(utilDate.getTime()));
                    prepStatement.setDate(4, new java.sql.Date(utilDate.getTime() + 604800000));
                } else {
                    prepStatement.setDate(3, new java.sql.Date(utilDate.getTime()));
                    prepStatement.setDate(4, new java.sql.Date(utilDate.getTime() + 1209600000));
                }
                prepStatement.executeUpdate();
                choice = -1;
            }
        } catch (Exception e) {
            System.err.print(e);
        } finally {
            close();
        }
    }
    
    public void bookReturn() {
        int choice = 0;
        Scanner scan = new Scanner(System.in);
        Student student = getStudent();
        student.display();
        try {
            while (choice != -1) {
                prepStatement = connect.prepareStatement("select * from Checkout where checkinDate is NULL and studentID = ?");
                prepStatement.setInt(1, student.getStudentID());
                resultSet = prepStatement.executeQuery();

                if (resultSet.next()) {
                    resultSet.previous();
                    DBTablePrinter.printResultSet(resultSet);
                } else {
                    System.out.println("No books are currently checked out for user. ");
                    choice = -1;
                    continue;
                }

                System.out.print("Enter the serial of the book you want to return or 0 to cancel: ");
                entry = scan.nextLine();
                if (entry.equals("0")) {
                    choice = -1;
                    continue;
                }

                prepStatement = connect.
                    prepareStatement("update Checkout set checkinDate = CURDATE() where serial = ? and checkinDate is NULL");
                prepStatement.setString(1, entry);
		        if ((prepStatement.executeUpdate()) == 0) {
		        	System.out.println("Books does not exist in the system. ");
		        	choice = -1;
		        	continue;
		        }
                System.out.println("Books has successfully been returned. Thank you!");
                student.setNumBooks(student.getNumBooks() - 1);
                choice = -1;
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            close();
        }
    }
    

    public static void showLibrarianOptions() {
        System.out.println();
        System.out.println("-- Librarian Operations --");
        System.out.println("(1) Checkout a book.");
        System.out.println("(2) Return a book.");
        System.out.println("(3) Quit.");
        System.out.println("--------------------------");
        System.out.println();
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (prepStatement != null) {
                prepStatement.close();
            }
        } catch (Exception e) {

        }
    }
}
