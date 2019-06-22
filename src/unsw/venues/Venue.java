package unsw.venues;

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
			Room newRoom = new SmallRoom(name, this);
			rooms.add(newRoom);
		} else if (size.equals("medium")) {
			Room newRoom = new MediumRoom(name, this);
			rooms.add(newRoom);
		} else {
			Room newRoom = new LargeRoom(name, this);
			rooms.add(newRoom);
		}
	}
	
}
