/**
 * class that represents one Zoom Participant with fields for name  and muted 
 * @author Andy Kuemmel, CS 300
 */
public class ZoomParticipant {
	private String name;
	private boolean muted;
	/**
	 * constructor, name only which calls the other constructor to set muted to true
	 * @param name the name of this participant
	 */
	public ZoomParticipant(String name) {
		this(name,true); //zoom participant by default is new
	}
	
	/**
	 * constructor with name and muted property
	 * @param name
	 * @param muted
	 */
	public ZoomParticipant(String name, boolean muted) {
		this.name = name;
		this.muted = muted;
	}

	/**
	 * @return the value of muted
	 */
	public boolean isMuted() {  return muted; }

	/**
	 * @param muted the new value of muted
	 */
	public void setMuted(boolean muted) {this.muted = muted; }

	/**
	 * @return the value of name
	 */
	public String getName() { return name; }
	
	/**
	 * allows participants to change their name
	 * @param name the new value of name
	 */
	public void setName(String name) {this.name = name; }
	
	@Override
	/**
	 * returns Name, or Name(muted) based on muted property
	 * @return  name + "(muted)" if this Participant is currently muted
	 */
	public String toString() {
		String display = "";
		
		// if participant is muted, display muted status
		if (this.muted) {
			display = name + "(muted)";
		}
		
		// if participant is not muted, just display name
		if (!this.muted) {
			display = name;
		}
		
		return display;
	}
}
