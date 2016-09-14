package appointmentBookController;

import java.util.GregorianCalendar;

@SuppressWarnings("serial")
public class AppointmentBookController extends GregorianCalendar {
	
	public AppointmentBookController() {
		
		super();
		
		// This is useful to AppointmentBookWindow for finding the name of the first day of the month so it can populate the calendar properly
		this.set(DAY_OF_MONTH, 1);
		
	}
	
	private class Onetime extends Appointment {

		public Onetime(int month, int day, int year, String description) {
			super(month, day, year, description);
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

		public Monthly(int month, int day, int year, String description) {
			super(month, day, year, description);
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

		public Daily(int month, int day, int year, String description) {
			super(month, day, year, description);
		}

		@Override
		public boolean occursOn(int month, int day, int year) {

			return true;
			
		}
		
	}
	
}
