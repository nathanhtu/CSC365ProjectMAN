import util.*;
import menus.*;
import java.util.Scanner;

public class Main {


	public static void main(String[] args) {
		DBConnector dbc = new DBConnector();
		Integer studentID;
		String firstName, lastName;

		int authority, status;
		Scanner scan = new Scanner(System.in);
		while (true) {
			do{
				System.out.print("Are you a (1) Student, (2) Librarian, (3) Manager?: ");
				while (!scan.hasNextInt()){
					System.out.println("Invalid entry. Please try again.");
					scan.next();
				}
				authority = scan.nextInt();
			} while (authority < 1 || authority > 3);

			if (authority == 1){
				StudentMenu.run();
			}

			if (authority == 2){
			}

			if (authority == 3){
			}
		}


	}

}
