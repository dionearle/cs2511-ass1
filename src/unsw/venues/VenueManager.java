package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VenueManager {
	
	private List<Venue> venues;
	
	public VenueManager() {
		this.venues= new ArrayList<>();
	}
	
	public void addVenue(String venue) {
		venues.add(new Venue(venue));
	}
	
	public void addRoom(Venue venue, String room, String size) {
		venue.addRoom(room, size);
	}
	
	public Venue getVenue(String venue) {
		
		for (int i=0; i < venues.size(); i++) {
			
			Venue currVenue = venues.get(i);
			if (venue.equals(currVenue.getName())) {
				return currVenue;
			}
		}
		
		return null;
	}
	
	public Venue getAvailableVenue(LocalDate start, LocalDate end, int small, int medium, int large) {
		
		for (int i=0; i < venues.size(); i++) {
			
			int a, b, c;
			a = b = c = 0;
			Venue venue = venues.get(i);
			for (int j=0; j < venue.getNumRooms(); j++) {

				if (venue.isRoomAvailable(j, start, end)) {
					
					Room room = venue.getRoom(j);
					
					// Room is available
					// Now determine what type and add to either a, b or c
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
	
	public List<Room> getAvailableRooms(LocalDate start, LocalDate end, int small, int medium, int large, Venue venue) {
			
		List<Room> rooms = new ArrayList<> ();
		
		int a, b, c;
		a = b = c = 0;
		for (int j=0; j < venue.getNumRooms(); j++) {
				
			if (venue.isRoomAvailable(j, start, end)) {
				
				Room room = venue.getRoom(j);
					
				// Room is available
				// Now determine what type and add to either a, b or c
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
