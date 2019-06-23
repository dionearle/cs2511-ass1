/**
 *
 */
package unsw.venues;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Venue Hire System for COMP2511.
 *
 * A basic prototype to serve as the "back-end" of a venue hire system. Input
 * and output is in JSON format.
 *
 * @author Robert Clifton-Everest
 *
 */
public class VenueHireSystem {

    /**
     * Constructs a venue hire system. Initially, the system contains no venues,
     * rooms, or bookings.
     */
	
	private ReservationManager reservationManager = new ReservationManager();
	private VenueManager venueManager = new VenueManager();
	
    public VenueHireSystem() {
        // TODO Auto-generated constructor stub
    }

    private void processCommand(JSONObject json) {
        switch (json.getString("command")) {

        case "room":
        {	
            String venue = json.getString("venue");
            String room = json.getString("room");
            String size = json.getString("size");
            addRoom(venue, room, size);
        }
            break;

        case "request":
        {
            String id = json.getString("id");
            LocalDate start = LocalDate.parse(json.getString("start"));
            LocalDate end = LocalDate.parse(json.getString("end"));
            int small = json.getInt("small");
            int medium = json.getInt("medium");
            int large = json.getInt("large");

            JSONObject result = request(id, start, end, small, medium, large);
            
            System.out.println(result.toString(2));
        }
            break;

        case "change":
        {
        	String id = json.getString("id");
            LocalDate start = LocalDate.parse(json.getString("start"));
            LocalDate end = LocalDate.parse(json.getString("end"));
            int small = json.getInt("small");
            int medium = json.getInt("medium");
            int large = json.getInt("large");

            JSONObject result = change(id, start, end, small, medium, large);

            System.out.println(result.toString(2));
        }
            break;
        	
        case "cancel":
        {
            String id = json.getString("id");

            JSONObject result = cancel(id);
            
            System.out.println(result.toString(2));
        }
            break;
        	
        case "list":
        {
        	String venue = json.getString("venue");

            JSONArray result = list(venue);
            
            System.out.println(result.toString(2));
        }
            break;
        	
        }
    }

    private void addRoom(String venue, String room, String size) {
        
    	// If the venue doesn't already exist, create it
    	if (!venueManager.findVenue(venue)) {
    		venueManager.addVenue(venue);
    	}
    	    	
    	// Now we know the venue exists, we add the room to it
    	Venue currentVenue = venueManager.getVenue(venue);
    	venueManager.addRoom(currentVenue, room, size);
    }

    public JSONObject request(String id, LocalDate start, LocalDate end,
            int small, int medium, int large) {
        
    	JSONObject result = new JSONObject();

    	boolean available = venueManager.checkRooms(start, end, small, medium, large);
    	
    	if (available) {
    		Venue venue = venueManager.getVenue(start, end, small, medium, large);
    		List<Room> rooms = venueManager.getRooms(start, end, small, medium, large);
    		reservationManager.addReservation(id, start, end, venue, rooms);
    		
    		result.put("status", "success");
    		result.put("venue", venue.getName());
    		
    		JSONArray outputRooms = new JSONArray();
    		for (int i = 0; i < rooms.size(); i++) {
    			outputRooms.put(rooms.get(i).getName());
    		}
    		
    		result.put("rooms", outputRooms);
    	} else {
    		result.put("status", "rejected");
    	}
        
        return result;
    }
    
    public JSONObject change(String id, LocalDate start, LocalDate end,
            int small, int medium, int large) {
        
    	JSONObject result = new JSONObject();
    	
    	if (!reservationManager.findReservation(id)) {
    		result.put("status", "rejected");
    		return result;
    	}
    	
    	// first we make a copy of info from current version of reservation id
    	Reservation oldReservation = reservationManager.getReservation(id);
    	Venue venue = oldReservation.getVenue();
    	List<Room> rooms = oldReservation.getRooms();
    	
    	// then we cancel reservation id
    	cancel(id);
    	
    	// then we request new reservation
    	JSONObject attemptedReservation = request(id, start, end, small, medium, large);
    	
    	String success = (String) attemptedReservation.get("status");
    	
    	// if it succeeds, then we simply return new JSON output
    	if (success.equals("success")) {
    		return attemptedReservation;
    	}
    	
    	// if it fails, then we recreate old copy of reservation
    	reservationManager.addReservation(id, start, end, venue, rooms);
    			
    	result.put("status", "rejected");	
    	
        return result;
    }
    
    public JSONObject cancel(String id) {
    	
    	JSONObject result = new JSONObject();
    	
    	if (!reservationManager.findReservation(id)) {
    		result.put("status", "rejected");
    		return result;
    	}
    	    	
    	Reservation reservation = reservationManager.getReservation(id);
    	reservationManager.cancelReservation(id, reservation);
    	reservation = null;
    	result.put("status", "success");

        return result;
    }
    
public JSONArray list(String venue) {
        
    	JSONArray result = new JSONArray();
    	
    	Venue currentVenue = venueManager.getVenue(venue);
    	List<Room> rooms = currentVenue.getRooms();
    	
		for (int i = 0; i < rooms.size(); i++) {

			JSONObject outputRooms = new JSONObject();
			JSONArray outputReservations = new JSONArray();
			List<Reservation> reservations = reservationManager.getReservations();

			Collections.sort(reservations, new Comparator<Reservation>() {
		        public int compare(Reservation first, Reservation second) {
		            return first.getStartDate().compareTo(second.getStartDate());
		        }
		    }
		    );
			
			for (int j = 0; j < reservations.size(); j++) {
				
				List<Room> reservationRooms = reservations.get(j).getRooms();
				for (int k=0; k < reservationRooms.size(); k++) {
					if (reservationRooms.get(k).getName().equals(rooms.get(i).getName())
					&& reservations.get(j).getVenue().getName().equals(venue)) {
						JSONObject reservation = new JSONObject();
						reservation.put("id", reservations.get(j).getId());
						reservation.put("start", reservations.get(j).getStartDate());
						reservation.put("end", reservations.get(j).getEndDate());

						
						outputReservations.put(reservation);
					}
				}
			}
			
			outputRooms.put("room", rooms.get(i).getName());
			outputRooms.put("reservations", outputReservations);
			result.put(outputRooms);
		}
		
        return result;
    }

    public static void main(String[] args) {
        VenueHireSystem system = new VenueHireSystem();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.trim().equals("")) {
                JSONObject command = new JSONObject(line);
                system.processCommand(command);
            }
        }
        sc.close();
    }

}
