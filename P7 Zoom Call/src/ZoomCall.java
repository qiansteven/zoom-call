/**
 * Course: 		CS300 - Summer 2021
 * Program:		Program 7 Zoom Call
 * Name: 		Steven Qian
 * Wisc Email: 		sqian@epic.com
 * Web Sources: 	N/A
 * Personal Help: 	N/A (No partner)
 * @see 	LinkedList, LinkedListIterator, BreakoutRoom, ListADT, Node, ZoomCall, ZoomCallMainMenu, ZoomParticipant
 */

import java.util.Iterator;

// Class header  and some javadocs missing due to space restrictions

public class ZoomCall {
	private String callName;		// the name of the call 
	private LinkedList<ZoomParticipant> mainRoomList;  // the list of participants
	private boolean inBreakoutMode; // states if we are currently in breakout mode
	private BreakoutRoom [] breakoutRooms; // an array of breakout rooms

	// constructor
	public ZoomCall(String callName) {
		this.callName = callName;
		this.mainRoomList = new LinkedList<ZoomParticipant>();
		this.inBreakoutMode = false;
		this.breakoutRooms = null;
	}

	/**
	 * @return the number of participants in mainRoomList (does not include moderator)
	 */
	public int getMainRoomSize() {
		return this.mainRoomList.size();
	}	

	/**
	 * instantiates a ZoomParticipant, muted, and adds at end of mainRoomList
	 * @param name
	 */
	public void addToCall(String name) { 
		//create new ZoomParticipant from name (muted by default)
		ZoomParticipant newParticipant = new ZoomParticipant(name);
		
		//add participant to main room
		mainRoomList.addAtEnd(newParticipant);
    }

	
	/**
	 * overloaded method to call when using main room
	 * if the call is in breakout room, return null
	 * @param index
	 * @return the ZoomParticipant at this index in mainRoomList
	 * @throws IllegalArgumentException if index is out of range
	 */
	public ZoomParticipant dropFromCall(int index) throws IllegalArgumentException{
		//invalid index
		if (index < 0 || index >= mainRoomList.size()) {
			throw new IllegalArgumentException("Invalid index!");
		}
		
		if (inBreakoutMode) {
			return null;
		}
		
		//remove at index and retrieve participant
		ZoomParticipant droppedParticipant = mainRoomList.remove(index);
		
		return droppedParticipant;
	}
	
	/**
	 * sets the ZoomParticipant at index in the main room to muted
	 * @param index
	 * @throws IllegalArgumentException if index is out of range
	 */
	public void mute(int index) throws IllegalArgumentException { 
		//invalid index
		if (index < 0 || index >= mainRoomList.size()) {
			throw new IllegalArgumentException("Invalid index!");
		}
		
		//retrieve participant to be muted
		ZoomParticipant mutedParticipant = mainRoomList.get(index);
		
		//set participant to muted -- if already muted nothing will change
		mutedParticipant.setMuted(true);
    }
	
	// uses an iterator to mute all participants in the main room (WE WILL CHECK)
	public void muteAll() { 
		// create iterator
		LinkedListIterator<ZoomParticipant> mainRoomIterator = mainRoomList.iterator();
		
		// iterate through entire room to set muted status = true
		while (mainRoomIterator.hasNext()) {
			ZoomParticipant currentParticipant = mainRoomIterator.next();
			currentParticipant.setMuted(true);
		}
    }
	
	// uses an iterator to unmute all participants in the main room (WE WILL CHECK)
	public void unMuteAll() {
		// create iterator
		LinkedListIterator<ZoomParticipant> mainRoomIterator = mainRoomList.iterator();
		
		// iterate through entire room to set muted status = false
		while (mainRoomIterator.hasNext()) {
			ZoomParticipant currentParticipant = mainRoomIterator.next();
			currentParticipant.setMuted(false);
		}
	}
	
	// prints the participants in the main room
	public void printMainRoom() {
		System.out.println("\n" + this.callName + " Main Room:");
		mainRoomList.print();
	}
	
	/**
	 * starts breakout rooms and places participants one at a time into a room
	 * pattern: room 0, room 1, room 2, room 0, room 1, room 2
	 * sets inBreakoutMode to true
	 * when added to a breakout room, unmute a participant and remove it from the main room
	 * @param numRooms
	 * @throws IllegalArgumentException if numRooms is <= 0
	 */
	public void startBreakoutRooms(int numRooms) throws IllegalArgumentException { 
		if (numRooms <= 0) {
			throw new IllegalArgumentException("Invalid number of rooms");
		}
		
		this.inBreakoutMode = true;
		
		// create iterator
		LinkedListIterator<ZoomParticipant> mainRoomIterator = mainRoomList.iterator();
		
		this.breakoutRooms = new BreakoutRoom[numRooms];
		
		for (int i = 0; i < breakoutRooms.length; i++) {
			BreakoutRoom newBreakout = new BreakoutRoom("Room " + i);
			breakoutRooms[i] = newBreakout;
		}
		
		int roomCount = 0; //start at room 0
				
		// iterate through entire room and move to breakout rooms
		while (mainRoomIterator.hasNext()) {
			//get participant
			ZoomParticipant currentParticipant = mainRoomIterator.next();
			
			//put into current room
			breakoutRooms[roomCount].addToRoom(currentParticipant);
			
			//unmute participant
			currentParticipant.setMuted(false);
			
			//move to next room
			roomCount++;
			
			//go back to room 0 if this is last room
			if (roomCount >= numRooms) {
				roomCount = 0;
			}
			
			//always remove front participant from main room
			mainRoomList.removeFromFront();
		}
    }


	/**
	 * if there are no breakout rooms, return 0
	 * @return the number of current breakout rooms
	 */
	public int getNumBreakoutRooms() {
		
		if (!inBreakoutMode) {
			return 0;
		}
		
		return breakoutRooms.length;
	}

	/**
	 * sets the value of inBreakoutMode to false
	 * removes participants from each room, one room at a time,
	 * mutes each participant, and adds them to main room
	 * sets the array this.breakoutRooms to null
	 */
	public void endBreakoutRooms() {
		
		if (inBreakoutMode) {
			ZoomParticipant removedParticipant = null;
			// remove participants out of each room and add to main room
			for (int i = 0; i < breakoutRooms.length; i++) {
				while (breakoutRooms[i].getSize() > 0) {
					removedParticipant = breakoutRooms[i].removeFromRoom(0);
					removedParticipant.setMuted(true);
					mainRoomList.addAtEnd(removedParticipant);
				}
			}
			inBreakoutMode = false;
			breakoutRooms = null;
		}
	}

	/**
	 * overloaded method to call when using breakout rooms
	 * if the call is not in breakout mode, return null
	 * @param room the room number
	 * @param index the index of the person in the room
	 * @return the ZoomParticipant at this index in mainRoomList
	 * @throws IllegalArgumentException if index is out of range OR room is out of range
	 */
	public ZoomParticipant dropFromCall(int room, int index) throws IllegalArgumentException{
		// TODO: COMPLETE THIS METHOD
		if (room < 0 || room >= breakoutRooms.length) {
			throw new IllegalArgumentException ("Invalid room number");
		}
		
		if (index < 0 || index >= breakoutRooms[index].getSize()) {
			throw new IllegalArgumentException ("Invalid index");
		}
		
		if (!inBreakoutMode) {
			return null;
		}
		
		ZoomParticipant droppedParticipant = breakoutRooms[room].removeFromRoom(index);
		return droppedParticipant;
	}

        /**
	 * if inBreakoutMode, do nothing, otherwise change the name
	 * @param index the index of the participant in the mainRoom
	 * @param newName the new name for this participant
	 * @throws IllegalArgumentException if index is out of range
	 */
	public void changeName(int index, String newName) throws IllegalArgumentException {
		//invalid index
		if (index < 0 || index >= mainRoomList.size()) {
			throw new IllegalArgumentException("Invalid index!");
		}
		
		//set new name when not in breakout mode
		if (!inBreakoutMode) {
			ZoomParticipant foundParticipant = mainRoomList.get(index);
			foundParticipant.setName(newName);
		}
    }

	/**
	 * moves a participant from one room to another
	 * if not in breakoutMode, do nothing
	 * @param roomNumber the original room
	 * @param indexNumber the index of this participant in the breakout room
	 * @param newRoomNumber the new room
	 * @throws IndexOutOfBoundsException if any parameter is out of bounds
	 */
	public void changeRooms(int roomNumber, int indexNumber, int newRoomNumber) throws IndexOutOfBoundsException {
		// invalid original room
		if (roomNumber < 0 || roomNumber >= breakoutRooms.length) {
			throw new IllegalArgumentException("Invalid original room number");
		}
		
		// invalid new room
		if (newRoomNumber < 0 || newRoomNumber >= breakoutRooms.length) {
			throw new IllegalArgumentException("Invalid new room number");
		}
		
		// invalid index
		if (indexNumber < 0 || indexNumber >= breakoutRooms[roomNumber].getSize()) {
			throw new IllegalArgumentException("Invalid index");
		}
		
		//remove from old room
		ZoomParticipant movedParticipant = breakoutRooms[roomNumber].removeFromRoom(indexNumber);
		
		//add to new room
		breakoutRooms[newRoomNumber].addToRoom(movedParticipant);
	}
	
	/**
	 * prints out each room and the participants..
	 */
	public void printBreakoutRooms() {
		System.out.println("\n" + this.callName + " Breakout Rooms:");
		for (BreakoutRoom room : breakoutRooms) {
			room.printBreakoutRoom();
		}
	}
}
