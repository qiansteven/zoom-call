/**
 * A class that represents one Breakout room.  
 * @author Andy Kuemmel
 * @see also LinkedList, ZoomParticipant
 */
public class BreakoutRoom {
	private LinkedList<ZoomParticipant> roomList;
	private String name;
	
	/**
	 * constructor
	 * @param name
	 */
	public BreakoutRoom(String name) {
		this.name = name;
		roomList = new LinkedList<ZoomParticipant>();
	}

	/**
	 * adds participant to this room's roomList and unmutes
	 * @param participant
	 */
	public void addToRoom(ZoomParticipant participant) {
		participant.setMuted(false);
		roomList.addAtEnd(participant);
	}
	
	/**
	 * removes the participant at index from this room
	 * @param index
	 * @return the participant removed
	 * @throws IllegalArgumentException if index is out of bounds
	 */
	public ZoomParticipant removeFromRoom(int index) throws IllegalArgumentException { 
		//invalid index
		if (index < 0 || index >= roomList.size()) {
			throw new IllegalArgumentException("Invalid index!");
		}
		
		//remove at index and retrieve participant
		ZoomParticipant droppedParticipant = roomList.remove(index);
		
		return droppedParticipant;
    }
	
	/**
	 * @return the size of the room list
	 */
	public int getSize() { 
		return roomList.size();
    }

	/**
	 * prints out the info in roomList
	 */
	public void printBreakoutRoom() {
		System.out.print(this.name + ":\t");
		this.roomList.print();
	}
}
