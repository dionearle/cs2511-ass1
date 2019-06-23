package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager class for all venues in the system
 * @author z5205292
 *
 */
public class VenueManager {
	
	private List<Venue> venues;
	
	/**
	 * Constructor for the venue manager, which creates a new empty arraylist for venues.
	 */
	public VenueManager() {
		this.venues= new ArrayList<>();
	}
	
	/**
	 * Given a string for the name, creates a new venue and adds it to the list of venues
	 * @param venue
	 */
	public void addVenue(String venue) {
		venues.add(new Venue(venue));
	}
	
	/**
	 * Given a venue, room name and room size, adds this room to the venue's list of rooms
	 * @param venue the venue object for the room to be added to
	 * @param room the name of the room to be added
	 * @param size the desired size of the room
	 */
	public void addRoom(Venue venue, String room, String size) {
		venue.addRoom(room, size);
	}
	
	/**
	 * Searches through all venues in the system to see if there exists one with a matching name to the string 'venue'.
	 * @param venue the name of the venue to be searched for
	 * @return a boolean value that indiciates whether the venue exists in the system
	 */
	public Venue getVenue(String venue) {
		
		for (int i=0; i < venues.size(); i++) {
			
			Venue currVenue = venues.get(i);
			if (venue.equals(currVenue.getName())) {
				return currVenue;
			}
		}
		
		return null;
	}
	
	/**
	 * given the information for a requested reservation, searches through all venues and their rooms to see if
	 * any venues can accommodate the reservation.
	 * @param start the start date of the reservation
	 * @param end the end date of the reservation
	 * @param small the number of small rooms needed
	 * @param medium the number of medium rooms needed
	 * @param large the number of large rooms needed
	 * @return returns a venue object for the venue which can make this reservation. If none can, returns null
	 */
	public Venue getAvailableVenue(LocalDate start, LocalDate end, int small, int medium, int large) {
		
		/**
		 * loops through all venues that exist
		 */
		for (int i=0; i < venues.size(); i++) {
			
			int a, b, c;
			a = b = c = 0;
			Venue venue = venues.get(i);
			/**
			 * for all rooms in the current venue...
			 */
			for (int j=0; j < venue.getNumRooms(); j++) {

				/**
				 * if this room is available for all dates during the required time period...
				 */
				if (venue.isRoomAvailable(j, start, end)) {
					
					Room room = venue.getRoom(j);
					
					/**
					 * We now know the room is available. We now determine what size room and if we need
					 * this type of room still for the reservation.
					 */
					if (room instanceof SmallRoom && (a < small)) {
						a++;
					} else if (room instanceof MediumRoom && (b < medium)) {
						b++;
					} else if (room instanceof LargeRoom && (c < large)) {
						c++;
					}
				}
			}
			if (a == small && b == medium && c == large) {
				return venue;
			}
		}
		
		return null;
	}
	
	/**
	 * given a chosen venue for a reservation and other information, determines the rooms that will be booked for this venue
	 * @param start the start date of the reservation
	 * @param end the end date of the reservation
	 * @param small the number of small rooms needed
	 * @param medium the number of medium rooms needed
	 * @param large the number of large rooms needed
	 * @param venue the venue in which we are choosing rooms from
	 * @return returns a list of the rooms that will be booked from this venue
	 */
	public List<Room> getAvailableRooms(LocalDate start, LocalDate end, int small, int medium, int large, Venue venue) {
			
		List<Room> rooms = new ArrayList<> ();
		
		int a, b, c;
		a = b = c = 0;
		/**
		 * for all rooms in the current venue...
		 */
		for (int j=0; j < venue.getNumRooms(); j++) {
				
			/**
			 * if this room is available for all dates during the required time period...
			 */
			if (venue.isRoomAvailable(j, start, end)) {
				
				Room room = venue.getRoom(j);
					
				/**
				 * We now know the room is available. We now determine what size room and if we need
				 * this type of room still for the reservation.
				 * If so, we add it to the list of rooms too be returned.
				 */
				if (room instanceof SmallRoom && (a < small)) {
					a++;
					rooms.add(room);
				} else if (room instanceof MediumRoom && (b < medium)) {
					b++;
					rooms.add(room);
				} else if (room instanceof LargeRoom && (c < large)) {
					c++;
					rooms.add(room);
				}
			}
		}
		
		return rooms;
	}
}
