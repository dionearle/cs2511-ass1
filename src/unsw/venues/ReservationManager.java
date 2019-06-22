package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {
	
	private List<Reservation> reservations;
	
	public ReservationManager() {
		this.reservations = new ArrayList<>();
	}
	
	public boolean findReservation(String id) {
		int numReservations = reservations.size();
		for (int i=0; i < numReservations; i++) {
			if (id.equals(reservations.get(i).getId())) {
				return true;
			}
		}
		return false;
	}
	
	public Reservation getReservation(String id) {
		int numReservations = reservations.size();
		for (int i=0; i < numReservations; i++) {
			if (id.equals(reservations.get(i).getId())) {
				return reservations.get(i);
			}
		}
		return null;
	}
	
	public void addReservation(String id, LocalDate start, LocalDate end, Venue venue, List<Room> rooms) {
		
		Reservation newReservation = new Reservation(id, start, end, venue, rooms);
		reservations.add(newReservation);
	}
	
	public void cancelReservation(String id, Reservation reservation) {
		List<Room> rooms = reservation.getRooms();
		LocalDate start = reservation.getStartDate();
		LocalDate end = reservation.getEndDate();
		
		for (int i=0; i < rooms.size(); i++) {
			rooms.get(i).removeReservedDates(start, end);
		}
		reservations.remove(reservation);
		
	}
	
	public List<Reservation> getReservations() {
		return reservations;
	}
}	
