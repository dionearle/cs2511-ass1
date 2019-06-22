package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VenueManager {
	
	private List<Venue> venues;
	
	public VenueManager() {
		this.venues= new ArrayList<>();
	}
	
	public boolean findVenue(String venue) {
		int numVenues = venues.size();
		for (int i=0; i < numVenues; i++) {
			if (venue.equals(venues.get(i).getName())) {
				return true;
			}
		}
		return false;
	}
	
	public void addVenue(String venue) {
		venues.add(new Venue(venue));
	}
	
	public void addRoom(Venue venue, String room, String size) {
		venue.addRoom(room, size);
	}
	
	public Venue getVenue(String venue) {
		int numVenues = venues.size();
		for (int i=0; i < numVenues; i++) {
			if (venue.equals(venues.get(i).getName())) {
				return venues.get(i);
			}
		}
		return null;
	}
	
	public boolean checkRooms(LocalDate start, LocalDate end, int small, int medium, int large) {
		
		boolean available = false;
		int numVenues = venues.size();
		for (int i=0; i < numVenues; i++) {
			int numRooms = venues.get(i).getRooms().size();
			int a, b, c;
			a = b = c = 0;
			for (int j=0; j < numRooms; j++) {
				
				if (venues.get(i).getRooms().get(j).isAvailable(start, end)) {
					
					// Room is available
					// Now determine what type and add to either a, b or c
					if (venues.get(i).getRooms().get(j) instanceof SmallRoom && (a < small)) {
						a++;
					} else if (venues.get(i).getRooms().get(j) instanceof MediumRoom && (b < medium)) {
						b++;
					} else if (c < large) {
						c++;
					}
				}
				
				
				//System.out.print(venues.get(i).getRooms().get(j).getName());
			}
			if (a == small && b == medium && c == large) {
				available = true;
				break;
			}
		}
		
		
		return available;
	}
	
	public List<Room> getRooms(LocalDate start, LocalDate end, int small, int medium, int large) {
		
		int numVenues = venues.size();
		for (int i=0; i < numVenues; i++) {
			
			List<Room> rooms = new ArrayList<> ();
			
			int numRooms = venues.get(i).getRooms().size();
			int a, b, c;
			a = b = c = 0;
			for (int j=0; j < numRooms; j++) {
				
				if (venues.get(i).getRooms().get(j).isAvailable(start, end)) {
					
					// Room is available
					// Now determine what type and add to either a, b or c
					if (venues.get(i).getRooms().get(j) instanceof SmallRoom && (a < small)) {
						a++;
						rooms.add(venues.get(i).getRooms().get(j));
					} else if (venues.get(i).getRooms().get(j) instanceof MediumRoom && (b < medium)) {
						b++;
						rooms.add(venues.get(i).getRooms().get(j));
					} else if (c < large) {
						c++;
						rooms.add(venues.get(i).getRooms().get(j));
					}
				}
				
				
				//System.out.print(venues.get(i).getRooms().get(j).getName());
			}
			if (a == small && b == medium && c == large) {
				return rooms;
			}
		}
		
		
		return null;
	}

	public Venue getVenue(LocalDate start, LocalDate end, int small, int medium, int large) {
	
	int numVenues = venues.size();
	for (int i=0; i < numVenues; i++) {
		int numRooms = venues.get(i).getRooms().size();
		int a, b, c;
		a = b = c = 0;
		for (int j=0; j < numRooms; j++) {
			
			if (venues.get(i).getRooms().get(j).isAvailable(start, end)) {
				
				// Room is available
				// Now determine what type and add to either a, b or c
				if (venues.get(i).getRooms().get(j) instanceof SmallRoom && (a < small)) {
					a++;
				} else if (venues.get(i).getRooms().get(j) instanceof MediumRoom && (b < medium)) {
					b++;
				} else if (c < large) {
					c++;
				}
			}
			
			
			//System.out.print(venues.get(i).getRooms().get(j).getName());
		}
		if (a == small && b == medium && c == large) {
			return venues.get(i);
		}
	}
	
	
	return null;
}
}
