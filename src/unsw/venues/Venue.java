package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for a venue that contains rooms to be booked.
 * @author z5205292
 *
 */
public class Venue {
	
	private String name;
	private List<Room> rooms;
	
	/**
	 * Constructor for the venue, which creates an empty arraylist of rooms.
	 * @param name the name of the venue
	 */
	public Venue(String name) {
		this.name = name;
		this.rooms = new ArrayList<>();
	}
	
	/**
	 * Getter method for name of the venue.
	 * @return the name of the venue
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter method for the list of rooms in this venue.
	 * @return the list of rooms in this venue
	 */
	public List<Room> getRooms() {
		return rooms;
	}
	
	/**
	 * Given a room name and the required size, adds a room for this venue
	 * @param name the name of the room to be added
	 * @param size the size (either small, medium or large) of the room
	 */
	public void addRoom(String name, String size) {
		
		if (size.equals("small")) {
			Room newRoom = new SmallRoom(name);
			rooms.add(newRoom);
		} else if (size.equals("medium")) {
			Room newRoom = new MediumRoom(name);
			rooms.add(newRoom);
		} else {
			Room newRoom = new LargeRoom(name);
			rooms.add(newRoom);
		}
	}
	
	/**
	 * Gets the number of rooms the venue has
	 * @return the size of the rooms list
	 */
	public int getNumRooms() {
		return rooms.size();
	}
	
	/**
	 * Given a room for this venue and a time period, determines if the room is available for those dates
	 * @param room the room to be checked if it is available
	 * @param start the start date of the time period
	 * @param end the end date of the time period
	 * @return a boolean variable which indicated whether it is available or not
	 */
	public boolean isRoomAvailable(int room, LocalDate start, LocalDate end) {
		Room currentRoom = rooms.get(room);
		return currentRoom.isAvailable(start, end);
	}
	
	/**
	 * Given the index of a room in this venue's list of rooms, returns the room object for it.
	 * @param room the index of a room in the list of rooms
	 * @return the room object for the room at this part of the list
	 */
	public Room getRoom(int room) {
		return rooms.get(room);
	}
	
}
