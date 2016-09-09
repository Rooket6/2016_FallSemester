package appointmentBookController;

import java.util.GregorianCalendar;
import java.util.Locale;

public class AppointmentBookController {

	private GregorianCalendar cal;
	
	public AppointmentBookController() {
		cal = new GregorianCalendar();
	}
	
	public String getCurrentDayOfWeek() {
		return cal.getDisplayName(cal.DAY_OF_WEEK, cal.LONG, Locale.US);
	}
	
	public int getCurrentDayOfMonth() {
		return cal.get(cal.DAY_OF_MONTH);
	}
	
	public String getCurrentMonth() {
		return cal.getDisplayName(cal.MONTH, cal.LONG, Locale.US);
	}
	
	public int getCurrentYear() {
		return cal.get(cal.YEAR);
	}
	
}
