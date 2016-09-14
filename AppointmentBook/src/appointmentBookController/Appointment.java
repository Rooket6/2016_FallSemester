package appointmentBookController;

import java.util.Date;

public abstract class Appointment {

	private int month;
	private int day;
	private int year;
	private String description;
	// This is appended before the description to identify the type of the appointment
		// This is for user convenience as well as allowing equals() to work correctly
	private String descriptionPrefix;
	
	public Appointment(String descriptionPrefix, String description, int month, int day, int year) {
		
		this.descriptionPrefix = descriptionPrefix;
		
		setMonth(month);
		setDay(day);
		setYear(year);
		setDescription(description);
		
	}
	
	public abstract boolean occursOn(int month, int day, int year);
	
	@Override
	public boolean equals(Object obj) {
		
		Appointment that = (Appointment) obj;
		
		if (that.getDescription() != this.getDescription())
			return false;
		if (that.getDay() != this.getDay())
			return false;
		if (that.getMonth() != this.getMonth())
			return false;
		if (that.getYear() != this.getYear())
			return false;
		return true;
		
	}
	
	@Override
	public int hashCode() {
		
		return getDescription().length() + getDay() + getMonth() + getYear();
		
	}
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = descriptionPrefix + description;
	}
	
}
