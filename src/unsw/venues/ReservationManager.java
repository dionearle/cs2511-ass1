package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {
	
	private List<Reservation> reservations;
	
	public ReservationManager() {
		this.reservations = new ArrayList<>();
	}
	
	public Reservation getReservation(String id) {
		
		for (int i=0; i < reservations.size(); i++) {
			
			Reservation reservation = reservations.get(i);
			if (id.equals(reservation.getId())) {
				return reservation;
			}
		}
		
		return null;
	}
	
	public void addReservation(String id, LocalDate start, LocalDate end, Venue venue, List<Room> rooms) {
		
		reservations.add(new Reservation(id, start, end, venue, rooms));
	}
	
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
	
	public List<Reservation> listReservations() {
		return reservations;
	}
	
	public Venue getVenueFromId(String id) {
		Reservation reservation = getReservation(id);
		return reservation.getVenue();
	}
	
	public List<Room> getRoomsFromId(String id) {
		Reservation reservation = getReservation(id);
		return reservation.getRooms();
	}
}	
