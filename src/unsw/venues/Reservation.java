package unsw.venues;

import java.time.LocalDate;
import java.util.List;

/**
 * Reservation class. Is used to hold information about a particular booking made by the user at a venue,
 * using a set of rooms for a given window of time.
 * @author z5205292
 *
 */
public class Reservation {
	
	private String id;
	private LocalDate startDate;
	private LocalDate endDate;
	private Venue venue;
	private List<Room> rooms;
	
	/**
	 * Constructor takes in id of reservation, the start date, end date, the venue which is accommodating the booking
	 * and the rooms to be used at this venue.
	 * @param id the identifier of the reservation
	 * @param startDate the date in which the reservation begins
	 * @param endDate the date in which the reservation ends
	 * @param venue the venue where it is held
	 * @param rooms the rooms at this venue that will be used
	 */
	public Reservation(String id, LocalDate startDate, LocalDate endDate, Venue venue, List<Room> rooms) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.venue = venue;
		this.rooms = rooms;
		setRoomReservations(rooms, startDate, endDate);
	}
	
	/**
	 * setRoomReservations is called during the creation of a reservation, and given a list of rooms and a start
	 * and end date, will mark these rooms as reserved for all dates during this period.
	 * @param rooms the list of rooms used for this reservation
	 * @param start the start date of the reservation
	 * @param end the end date of the reservation
	 */
	public void setRoomReservations(List<Room> rooms, LocalDate start, LocalDate end) {
		
		for (int i=0; i < rooms.size(); i++) {
			Room room = rooms.get(i);
			room.setReservedDates(start, end);
		}
	}
	
	/**
	 * Getter method for id.
	 * @return the identifier of this reservation
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Getter method for venue.
	 * @return the venue of this reservation
	 */
	public Venue getVenue() {
		return venue;
	}
	
	/**
	 * Getter method for rooms.
	 * @return the list of rooms used for this reservation
	 */
	public List<Room> getRooms() {
		return rooms;
	}
	
	/**
	 * Getter method for startDate.
	 * @return the start date of the reservation
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * Getter method for endDate.
	 * @return the end date of the reservation
	 */
	public LocalDate getEndDate() {
		return endDate;
	}
	
	/**
	 * Given the venue of this reservation, returns the string
	 * value for its name.
	 * @return Returns the string value for the venue
	 */
	public String getVenueName() {
		return venue.getName();
	}
	
	
	
}
