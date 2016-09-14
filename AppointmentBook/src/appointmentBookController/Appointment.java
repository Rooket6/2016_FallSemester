package appointmentBookController;

import java.util.Date;

public abstract class Appointment {

	private int month;
	private int day;
	private int year;
	private String description;
	
	public Appointment(int month, int day, int year, String description) {
		
		this.month = month;
		this.day = day;
		this.year = year;
		this.description = description;
		
	}
	
	public abstract boolean occursOn(int month, int day, int year);
	
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
		this.description = description;
	}
	
}
