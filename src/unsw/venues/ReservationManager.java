package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A manager that holds all reservations made by the user
 * @author z5205292
 *
 */
public class ReservationManager {
	
	private List<Reservation> reservations;
	
	/**
	 * Constructor for manager that assigns a new arraylist for the reservations
	 */
	public ReservationManager() {
		this.reservations = new ArrayList<>();
	}
	
	/**
	 * Given the id of a reservation, searches for it in the the managers list of reservations
	 * and returns the object if found.
	 * @param id the unique identifier of the reservation to be found
	 * @return the reservation object if found, null otherwise
	 */
	public Reservation getReservation(String id) {
		
		for (int i=0; i < reservations.size(); i++) {
			
			Reservation reservation = reservations.get(i);
			if (id.equals(reservation.getId())) {
				return reservation;
			}
		}
		
		return null;
	}
	
	/**
	 * Given information about a reservation, add it to the systems list of reservations
	 * @param id unique identifier of the reservation
	 * @param start the start date of the reservation
	 * @param end the end date of the reservation
	 * @param venue the venue in which it is held
	 * @param rooms the rooms at this venue that will be used
	 */
	public void addReservation(String id, LocalDate start, LocalDate end, Venue venue, List<Room> rooms) {
		
		reservations.add(new Reservation(id, start, end, venue, rooms));
	}
	
	/**
	 * Given a reservation object, it is cancelled and removed from the system so these time
	 * slots can be used by other reservations
	 * @param reservation the reservation to be cancelled
	 */
	public void cancelReservation(Reservation reservation) {
		
		List<Room> rooms = reservation.getRooms();
		LocalDate start = reservation.getStartDate();
		LocalDate end = reservation.getEndDate();
		
		for (int i=0; i < rooms.size(); i++) {
			Room room = rooms.get(i);
			room.removeReservedDates(start, end);
		}
		
		reservations.remove(reservation);
		
	}
	
	/**
	 * Getter method for reservation list.
	 * @return the systems list of reservations
	 */
	public List<Reservation> listReservations() {
		return reservations;
	}
	
	/**
	 * Given the id of a reservation, find the venue used by this reservation.
	 * @param id the unique identifier of the reservation
	 * @return the venue object used for this reservation
	 */
	public Venue getVenueFromId(String id) {
		Reservation reservation = getReservation(id);
		return reservation.getVenue();
	}
	
	/**
	 * Given the id of a reservation, returns the list of rooms used by this reservation.
	 * @param id the unique identifier for this reservation
	 * @return the list of rooms for this reservation
	 */
	public List<Room> getRoomsFromId(String id) {
		Reservation reservation = getReservation(id);
		return reservation.getRooms();
	}
}	
