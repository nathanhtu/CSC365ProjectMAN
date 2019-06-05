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
				System.out.print("Are you a (1) Student, (2) Librarian, (3) Manager? Enter (4) to quit: ");
				while (!scan.hasNextInt()){
					System.out.println("Invalid entry. Please try again.");
					scan.next();
				}
				authority = scan.nextInt();
			} while (authority < 1 || authority > 4);

			if (authority == 1){
				StudentMenu.run();
			}

			else if (authority == 2){
				LibrarianMenu.run();
			}

			else if (authority == 3){
				ManagerMenu.run();
			}
			else if (authority == 4) {
				break;
			}
		}


	}

}
