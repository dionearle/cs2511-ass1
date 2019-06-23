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
	
	private ReservationManager reservationManager;
	private VenueManager venueManager;
	
	/**
	 * Constructor for the venue hire system. Instantiates the reservation manager and venue manager.
	 */
    public VenueHireSystem() {
    	this.reservationManager = new ReservationManager();
        this.venueManager = new VenueManager();
    }

    /**
     * Used to read in json commands, call the corresponding function and return a json object
     * @param json the input command
     */
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

    /**
     * the function used when the input command is to add a room
     * @param venue the venue where this room should be added to
     * @param room the name of the room to be added
     * @param size the size of the room to be added
     */
    private void addRoom(String venue, String room, String size) {
        
    	/**
    	 * If the venue doesn't already exist, create it
    	 */
    	if (venueManager.getVenue(venue) == null) {
    		venueManager.addVenue(venue);
    	}
    	    	
    	/**
    	 * Now that we know the venue exists, we add the room to it
    	 */
    	venueManager.addRoom(venueManager.getVenue(venue), room, size);
    }

    /**
     * used to request a reservation
     * @param id the unique identifier for the reservation
     * @param start the start date of the reservation
     * @param end the end date of the reservation
     * @param small the number of small rooms needed
     * @param medium the number of medium rooms needed
     * @param large the number of large rooms needed
     * @return returns the output JSON object
     */
    public JSONObject request(String id, LocalDate start, LocalDate end,
            int small, int medium, int large) {
        
    	JSONObject result = new JSONObject();
    	
    	Venue venue = venueManager.getAvailableVenue(start, end, small, medium, large);
    	
    	/**
    	 * if there is an available venue for this request...
    	 */
    	if (venue != null) {
    
    		/**
    		 * find the rooms to be booked in this venue
    		 */
    		List<Room> rooms = venueManager.getAvailableRooms(start, end, small, medium, large, venue);
    		
    		/**
    		 * create the reservation using this info
    		 */
    		reservationManager.addReservation(id, start, end, venue, rooms);
    		
    		/**
    		 * create the output json object to be returned
    		 */
    		result.put("status", "success");
    		result.put("venue", venue.getName());
    		
    		JSONArray outputRooms = new JSONArray();
    		for (int i = 0; i < rooms.size(); i++) {
    			Room room = rooms.get(i);
    			outputRooms.put(room.getName());
    		}
    		
    		result.put("rooms", outputRooms);
    		
    	} else {
    		result.put("status", "rejected");
    	}
        
        return result;
    }
    
    /**
     * used to change a reservation already made
     * @param id the unique identifier of the existing reservation to be changed
     * @param start the start date of the reservation
     * @param end the end date of the reservation
     * @param small the number of small rooms needed
     * @param medium the number of medium rooms needed
     * @param large the number of large rooms needed
     * @return the output JSON object
     */
    public JSONObject change(String id, LocalDate start, LocalDate end,
            int small, int medium, int large) {
        
    	JSONObject result = new JSONObject();
    	
    	/**
    	 * We first check if the reservation id exists
    	 */
    	if (reservationManager.getReservation(id) == null) {
    		result.put("status", "rejected");
    		return result;
    	}
    	
    	/**
    	 * first we make a copy of info from current version of reservation id
    	 */
    	Venue venue = reservationManager.getVenueFromId(id);
    	List<Room> rooms = reservationManager.getRoomsFromId(id);
    	
    	/**
    	 * then we cancel reservation id
    	 */
    	cancel(id);
    	
    	/**
    	 * then we request new reservation
    	 */
    	JSONObject newReservationResult = request(id, start, end, small, medium, large);
    	
    	String status = (String) newReservationResult.get("status");
    	
    	/**
    	 * if it succeeds, then we simply return new JSON output
    	 */
    	if (status.equals("success")) {
    		return newReservationResult;
    	}
    	
    	/**
    	 * if it fails, then we recreate old copy of reservation
    	 */
    	reservationManager.addReservation(id, start, end, venue, rooms);
    			
    	result.put("status", "rejected");	
    	
        return result;
    }
    
    /**
     * Given the id of an existing reservation, cancel it so other reservations can use the timeslot
     * @param id the unique identifier of the reservation to be cancelled
     * @return the output json object
     */
    public JSONObject cancel(String id) {
    	JSONObject result = new JSONObject();
    	
    	/**
    	 * We first check to see if the reservation id exists
    	 */
    	if (reservationManager.getReservation(id) == null) {
    		result.put("status", "rejected");
    		return result;
    	}
    	    	
    	/**
    	 * Once we know it exists, we simply cancel it and return our json object
    	 */
    	Reservation reservation = reservationManager.getReservation(id);
    	reservationManager.cancelReservation(reservation);
    	reservation = null;
    	
    	result.put("status", "success");

        return result;
    }
    
    /**
     * given a venue in the system, displays the reservations for each room in this venue
     * @param venue the venue to display the reservations of
     * @return the output json object
     */
    public JSONArray list(String venue) {
        
    	JSONArray result = new JSONArray();
    	
    	/**
    	 * determines the current venue from this string, and then the list of rooms at this venue
    	 */
    	Venue currentVenue = venueManager.getVenue(venue);
    	List<Room> rooms = currentVenue.getRooms();
    	
    	/**
    	 * for all rooms at the venue...
    	 */
		for (int i = 0; i < rooms.size(); i++) {
			
			Room currentRoom = rooms.get(i);

			JSONObject outputRooms = new JSONObject();
			JSONArray outputReservations = new JSONArray();
			List<Reservation> reservations = reservationManager.listReservations();
			
			/**
			 * Sorts all the reservations by date
			 */
			Collections.sort(reservations, new Comparator<Reservation>() {
		        public int compare(Reservation res1, Reservation res2) {
		        	LocalDate first = res1.getStartDate();
		        	LocalDate second = res2.getStartDate();
		            return first.compareTo(second);
		        }
		    }
		    );

			/**
			 * for all reservations in the system...
			 */
			for (int j = 0; j < reservations.size(); j++) {
				
				Reservation currentReservation = reservations.get(j);
				
				List<Room> reservationRooms = currentReservation.getRooms();
				
				/**
				 * for all rooms in the current reservation...
				 */
				for (int k=0; k < reservationRooms.size(); k++) {
					
					Room room = reservationRooms.get(k);
					String roomName = room.getName();
					String venueName = currentReservation.getVenueName();
					
					/**
					 * if we have matched the correct room, then produce the json output
					 */
					if (roomName.equals(currentRoom.getName())
					&& venueName.equals(venue)) {
						
						JSONObject reservation = new JSONObject();
						reservation.put("id", currentReservation.getId());
						reservation.put("start", currentReservation.getStartDate());
						reservation.put("end", currentReservation.getEndDate());

						outputReservations.put(reservation);
					}
				}
			}
			Room room = rooms.get(i);
			outputRooms.put("room", room.getName());
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
