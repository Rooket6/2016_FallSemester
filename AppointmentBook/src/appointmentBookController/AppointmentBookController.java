package appointmentBookController;

import java.util.GregorianCalendar;
import java.util.HashSet;

@SuppressWarnings("serial")
public class AppointmentBookController extends GregorianCalendar {
	
	private HashSet<Appointment> appointments;
	
	public AppointmentBookController() {
		
		super();
		
		// This is useful to AppointmentBookWindow for finding the name of the first day of the month so it can populate the calendar properly
		this.set(DAY_OF_MONTH, 1);
		
		appointments = new HashSet<Appointment>();
		
	}
	
	// Type 0 for Onetime, 1 for monthly, 2 for daily
	public void addAppointment(int type, String description, int month, int day, int year) {
		
		if (type == 0)
			appointments.add(new Onetime(description, month, day, year));
		else if (type == 1)
			appointments.add(new Monthly(description, month, day, year));
		else if (type == 2)
			appointments.add(new Daily(description, month, day, year));
		else
			throw new ArgumentOutOfBoundsException();
		
	}
	
	private class Onetime extends Appointment {

		public Onetime(String description, int month, int day, int year) {
			
			super("(Onetime) ", description, month, day, year);
			
		}

		@Override
		public boolean occursOn(int month, int day, int year) {
			
			// If month, day, and year are not the same return false, else return true
			if (month != getMonth())
				return false;
			if (day != getDay())
				return false;
			if (year != getYear())
				return false;
			
			return true;
			
		}
		
	}
	
	private class Monthly extends Appointment {

		public Monthly(String description, int month, int day, int year) {
			
			super("(Monthly) ", description, month, day, year);
			
		}

		@Override
		public boolean occursOn(int month, int day, int year) {
			
			// If the given date is not before the appointment date, and the given day is the same as the appointment day, then return true, else return false
			if (year < getYear())
				return false;
			if (year == getYear()) {
				
				// Same year, but other appointment has earlier month, return false
				if (month < getMonth())
					return true;
				if (month == getMonth()) {
					// Same year, same month, but other appointment has earlier day, return false
					if (day < getDay())
						return false;
				}
						
			}
			
			if (day == getDay())
				return true;
			
			return false;

		}
		
	}
	
	private class Daily extends Appointment {

		public Daily(String description, int month, int day, int year) {
			
			super("(Daily) ", description, month, day, year);
			
		}

		@Override
		public boolean occursOn(int month, int day, int year) {

			return true;
			
		}
		
	}
	
	private class ArgumentOutOfBoundsException extends RuntimeException {
		
		public ArgumentOutOfBoundsException() {
			super();
		}
		
		public ArgumentOutOfBoundsException(String message) {
			super(message);
		}
		
	}
	
}
