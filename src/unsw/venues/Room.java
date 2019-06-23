package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for a room in a venue.
 * @author z5205292
 *
 */
public class Room {
	
	private String name;
	private List<LocalDate> reservedDates;
	
	/**
	 * Constructor for the room object. Creates an empty arraylist for reserved dates.
	 * @param name the name of the room
	 */
	public Room(String name) {
		this.name = name;
		this.reservedDates = new ArrayList<> ();
	}

	/**
	 * Getter method for the room's name
	 * @return the String for the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Given a start date and an end date, sets all dates during this period as reserved
	 * @param start the start of the reservation
	 * @param end the end of the reservation
	 */
	public void setReservedDates(LocalDate start, LocalDate end) {
		
		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
			reservedDates.add(date);
		}
	}
	
	/**
	 * Given a start date and an end date, removes all reserved dates during this period
	 * @param start the start of the reservation to be removed
	 * @param end the end of the reservation to be removed
	 */
	public void removeReservedDates(LocalDate start, LocalDate end) {
		
		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
			reservedDates.remove(date);
		}
	}

	/**
	 * Given a start date and an end date, checks if all dates during this period are free for the current room
	 * @param startDate the beginning of the proposed reservation
	 * @param endDate the end of the proposed reservation
	 * @return a boolean variable which will indicate whether the room is free to be booked during this time
	 */
	public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
		
		for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
			if (reservedDates.contains(date)) {
				return false;
			}
		}
	    
		return true;
	}
}
