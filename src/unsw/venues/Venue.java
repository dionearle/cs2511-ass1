package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venue {
	
	private String name;
	private List<Room> rooms;
	
	public Venue(String name) {
		this.name = name;
		this.rooms = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
	
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
	
	public int getNumRooms() {
		return rooms.size();
	}
	
	public boolean isRoomAvailable(int room, LocalDate start, LocalDate end) {
		Room currentRoom = rooms.get(room);
		return currentRoom.isAvailable(start, end);
	}
	
	public Room getRoom(int room) {
		return rooms.get(room);
	}
	
}
