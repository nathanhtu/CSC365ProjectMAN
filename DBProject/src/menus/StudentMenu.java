package menus;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Scanner;
import util.*;
import entity.*;

public class StudentMenu {
    private ResultSet resultSet = null;
	private PreparedStatement prepStatement = null;
	private Connection connect = null;
	private Student student;
	private String entry;

	public StudentMenu() {		
	}
	
    public void run() {
        int operation;
        DBConnector dbc = new DBConnector();
        connect = dbc.getConnection();      
        student = getStudent();
        Scanner scan = new Scanner(System.in);
        while (true) {        	
            showStudentOptions();          
            do{
                System.out.print("Enter the number of the desired operation: ");
                while (!scan.hasNextInt()){
                    System.out.println("Invalid entry. Please try again: ");                 
                    scan.next();
                }
                operation = scan.nextInt();
            } while (operation < 1 || operation > 7);

            if (operation == 1) {
                bookCheckout();
            }
            else if (operation == 2) {
                bookReturn();
            }
            else if (operation == 3) {
            	bookReserve();
            }
            else if (operation == 4) {
            	bookExtendDueDate();
            }
            else if (operation == 5) {
                bookSearch();
            }
            else if (operation == 6) {
            	viewCheckouts();
            }
            else if (operation == 7) {
            	scan.close();
                System.out.println("Exiting student menu.");
                System.out.println();
                break;
            }
        }
        dbc.close();
    }
    
    public int queryBooks() {
    	Scanner scan = new Scanner(System.in);
    	int choice = 0;
    	String entry; 	
    	try {
	    	while (choice != -1){	 		        
		        do{
		            System.out.print("Search by (1)serial, (2)title, (3)author, (4)genre, or (5)cancel: ");
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
		        else if (choice == 5){
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
    	} finally {
    		close();
    	}
    	return choice;
    }
    
    public void bookCheckout() {
    	java.util.Date utilDate = new java.util.Date();
    	int choice = 0, curStock, curReserved;
    	Scanner scan = new Scanner(System.in);
    	System.out.println("in book checkout");
    	try {
	        while (choice != -1) {
	        	System.out.println("checkpoint 1");
	            //checks if student has max books checked checkedOut
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
    	Scanner scan = new Scanner(System.in);
    	try {
	    	System.out.println("Returning.");
	        //show all books checked out
	        prepStatement = connect.prepareStatement("select * from Checkout where checkinDate is NULL and studentID = ?");
	        //needs studentID from student object
	        prepStatement.setInt(1, student.getStudentID());
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
    	} catch (Exception e)  {   		
    	} finally {
    		close();
    	}
    }
    
    public void bookReserve() {
    	Scanner scan = new Scanner(System.in);
    	try {
	    	System.out.println("Reserving.");
	        //needs to use same search function in checkout
	        //user search function
	        System.out.print("Enter the serial of the book you want to reserve or 0 to cancel");
	        entry = scan.nextLine();
	
	        //inserts a reservation
	        prepStatement = connect.
	            prepareStatement("insert into Reservations (serial, studentID) values (?, ?)");
	        prepStatement.setString(1, entry);
	        //needs studentID from Student object
	        prepStatement.setInt(2, student.getStudentID());
	        prepStatement.executeUpdate();
    	} catch (Exception e) {
    	} finally {
    		close();
    	}
    }
    
    public void bookExtendDueDate() {
    	Scanner scan = new Scanner(System.in);
    	try {
	        System.out.println("Extending due date.");
	        //brings up existing checkouts
	        prepStatement = connect.
	            prepareStatement("select * from Checkout where studentID = ? and checkinDate is NULL");
	        //needs studentID from Student object
	        prepStatement.setInt(1, student.getStudentID());
	        resultSet = prepStatement.executeQuery();
	        DBTablePrinter.printResultSet(resultSet);
	
	        System.out.print("Enter the serial of the book you want to extend or 0 to cancel");
	        entry = scan.nextLine();
    	} catch (Exception e) {
    	} finally {
    		close();
    	}
    }
    
    public void bookSearch()  {
    	System.out.println("Searching.");
    }
    
    public void viewCheckouts()  {
    	try {
	        System.out.println("Showing all currently checked out books.");
	        prepStatement = connect.
	            prepareStatement("select * from Checkout where studentID = ? and checkinDate is NULL");
	        prepStatement.setInt(1, student.getStudentID());
	        resultSet = prepStatement.executeQuery();
	        DBTablePrinter.printResultSet(resultSet);
    	} catch (Exception e) {
    	}
    }

    public void showStudentOptions() {
        System.out.println();
        System.out.println("-- Student Operations-- ");
        System.out.println("(1) Checkout a book.");
        System.out.println("(2) Return a book.");
        System.out.println("(3) Make a reservation.");
        System.out.println("(4) Extend due date on a checkout.");
        System.out.println("(5) Search book availability.");
        System.out.println("(6) View checkout history.");
        System.out.println("(7) Quit.");
        System.out.println();
    }

    public Student getStudent() {
    	Scanner newScanner = new Scanner(System.in);
        Student student = new Student();   
    	try {   	
    		resultSet = null;
    		while (resultSet == null) {
		        //scan for first and last name
		        //needs a student object
		    	System.out.print("Enter your student ID: ");
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

