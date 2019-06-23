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
		setRoomReservations(rooms, startDate, endDate);
	}
	
	public void setRoomReservations(List<Room> rooms, LocalDate start, LocalDate end) {
		
		for (int i=0; i < rooms.size(); i++) {
			Room room = rooms.get(i);
			room.setReservedDates(start, end);
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
	
	public String getVenueName() {
		return venue.getName();
	}
	
	
	
}
