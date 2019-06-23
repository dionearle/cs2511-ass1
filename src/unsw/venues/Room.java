package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room {
	
	private String name;
	private List<LocalDate> reservedDates;
	
	public Room(String name) {
		this.name = name;
		this.reservedDates = new ArrayList<> ();
	}

	public String getName() {
		return name;
	}
	
	public void setReservedDates(LocalDate start, LocalDate end) {
		
		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
			reservedDates.add(date);
		}
	}
	
	public void removeReservedDates(LocalDate start, LocalDate end) {
		
		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
			reservedDates.remove(date);
		}
	}

	public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
		
		for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
			if (reservedDates.contains(date)) {
				return false;
			}
		}
	    
		return true;
	}
}
