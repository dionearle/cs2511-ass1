package unsw.venues;

import java.time.LocalDate;
import java.util.List;

public class Reservation {
	
	private String id;
	private LocalDate startDate;
	private LocalDate endDate;
	private Venue venue;
	private List<Room> rooms;
	
	public Reservation(String id, LocalDate startDate, LocalDate endDate, Venue venue, List<Room> rooms) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.venue = venue;
		this.rooms = rooms;
		
		for (int i=0; i < rooms.size(); i++) {
			rooms.get(i).setReservedDates(startDate, endDate);
		}
	}
	
	public String getId() {
		return id;
	}
	
	public Venue getVenue() {
		return venue;
	}
	
	public List<Room> getRooms() {
		return rooms;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	
	
	
}
