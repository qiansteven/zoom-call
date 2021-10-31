import java.util.Scanner;

/**
 * Runs menu to maintain a Zoom Call.
 * One option of the main menu is to run breakout rooms.
 * In this case, a submenu runs until the breakout rooms close.
 * This code will not compile and run until the ZoomCall class is working correctly.
 * @author Andy Kuemmel
 */
public class ZoomCallMainMenu {
	// static class variables .. available to all static methods
	private static final int MAX_ROOMS = 9;
	private static ZoomCall call = null;
	private static Scanner kb = null;

	// TODO: you need to add a try/catch block in this method to catch the following:
	// NumberFormatException with System.out.println("Please enter an integer.");
	// IllegalArgumentException with System.out.println(e.getMessage());
	public static void main(String[] args) {
		call = new ZoomCall("CS 300");
		boolean done = false;
		kb = new Scanner(System.in);
		int userInput = 0;
		while (!done) {
			printMainRoomMenu();
			try {
			userInput = Integer.parseInt(kb.nextLine());
			switch (userInput) {
			case 1:
				System.out.println("Add a participant.");
				System.out.println("Enter a name for this participant: ");
				String name = kb.nextLine();
				call.addToCall(name);
				break;
			case 2:
				System.out.println("Remove a participant.");
				System.out.println("Enter the participant's index: ");
				ZoomParticipant p = call.dropFromCall(Integer.parseInt(kb.nextLine()));
				break;
			case 3:
				System.out.println("Mute a participant.");
				System.out.println("Enter the participant's index: ");
				call.mute(Integer.parseInt(kb.nextLine()));
				break;
			case 4:
				//System.out.println("Mute all participants.");
				call.muteAll();
				break;
			case 5:
				//System.out.println("Unmute all participants.");
				call.unMuteAll();
				break;
			case 6:
				int numRooms = 0;
				boolean validEntry = false;
				do { // TODO: make sure to repeat this code only until the number is valid
					System.out.println("Enter the number of breakout rooms (1-" + MAX_ROOMS + ") : ");
					numRooms = Integer.parseInt(kb.nextLine());
					if (numRooms >= 1 && numRooms <= MAX_ROOMS) {
						validEntry = true;
					}
					else {
						System.out.println("Number of rooms not valid");
					}
				} while (!validEntry); // TODO: change the boolean expression
				call.startBreakoutRooms(numRooms);
				runBreakoutRooms();
				break;
			case 7:
				System.out.println("Change a participant's name.");
				System.out.println("Enter the participant's index: ");
				int index = Integer.parseInt(kb.nextLine());
				System.out.println("Enter the participant's new name: ");
				String newName = kb.nextLine();
				call.changeName(index, newName);
				break;
			case 8:
				done = true;
				break;
			default:
				System.out.println("The number " + userInput + " is not an option.");
			} // switch
			} catch (NumberFormatException n) {
				System.out.println("Please enter an integer");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} // while
		System.out.println("Thank you for using the Zoom Call app!");
	}// method

	private static void printMainRoomMenu() {
		call.printMainRoom();
		System.out.println("\nYou are in the Zoom Main Room. Here are your choices:");
		System.out.println("1. Add a participant.");
		System.out.println("2. Remove a participant.");
		System.out.println("3. Mute a participant.");
		System.out.println("4. Mute all participants.");
		System.out.println("5. Unmute all participants.");
		System.out.println("6. Begin Breakout Rooms.");
		System.out.println("7. Change a participant's name.");
		System.out.println("8. End the call.");
		System.out.println("---------------------------------");
		System.out.println("What is your choice? ");
	}

	private static void printBreakoutRoomMenu() {
		call.printBreakoutRooms();
		System.out.println("\nYou are in the Zoom Breakout Rooms. Here are your choices:");
		System.out.println("1. Remove a participant.");
		System.out.println("2. Change a participant's room.");
		System.out.println("3. End breakout rooms.");
		System.out.println("---------------------------------");
		System.out.println("What is your choice? ");
	}

	// note: you need to add a try/catch/catch block in this method to catch the following:
	// NumberFormatException with System.out.println("Please enter an integer.");
	// IllegalArgumentException with System.out.println(e.getMessage());
	private static void runBreakoutRooms() {
		boolean done = false; // done is a local variable so we can reuse it
		int userInput = 0;
		while (!done) {
			printBreakoutRoomMenu();
			try {
			userInput = Integer.parseInt(kb.nextLine());
			switch (userInput) {
			case 1: // remove
				System.out.println("Remove a participant.");
				System.out.println("Enter a room number.");
				int roomNumber = Integer.parseInt(kb.nextLine());
				System.out.println("Enter a participant's index number.");
				int indexNumber = Integer.parseInt(kb.nextLine());
				call.dropFromCall(roomNumber,indexNumber);
				break;
			case 2: // change
				System.out.println("Change a participant's room.");
				System.out.println("Enter a room number.");
				roomNumber = Integer.parseInt(kb.nextLine());
				System.out.println("Enter a participant's index number.");
				indexNumber = Integer.parseInt(kb.nextLine());
				System.out.println("Enter the new room number.");
				int newRoomNumber = Integer.parseInt(kb.nextLine());
				call.changeRooms(roomNumber,indexNumber,newRoomNumber);
				break;
			case 3: // end
				call.endBreakoutRooms();
				done = true;
				break;
			default:
				System.out.println("The number " + userInput + " is not an option.");
			} // switch
			} catch (NumberFormatException n) {
				System.out.println("Please enter an integer.");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} // while
	}// method
}
