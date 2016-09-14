package mainTab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import appointmentBookController.AppointmentBookController;

@SuppressWarnings("serial")
public class AppointmentBookWindow extends JFrame implements ActionListener {

	private int buttonHeight = 30;
	private int calendarHeight = 550;
	
	String[][] daysOfMonth;
	
	// The controller acts as a calendar and also handles appointments
	private AppointmentBookController controller;

	private JPanel contentPane;
	private CalendarComponent calendarComponent; // The visual component of the calendar
	private JPanel headerComponent, footerComponent; // The buttons and label above and below calendar
	private JButton leftButton, rightButton, makeAppointmentButton;
	private JLabel headerLabel;
	
	public AppointmentBookWindow() {

		super();
		
		daysOfMonth  = new String[6][7];
		controller = new AppointmentBookController();
		
		updateDaysOfMonth();
		
	}
	
	private void createContentPane() {
		
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS)); // Infinitely wide, dynamically high
		
		createHeaderComponent();
		headerComponent.setMaximumSize(new Dimension(Short.MAX_VALUE, buttonHeight)); // Infinitely wide, but confined to 60px high
		contentPane.add(headerComponent);

		calendarComponent = new CalendarComponent();
		calendarComponent.setMaximumSize(new Dimension(Short.MAX_VALUE, calendarHeight + 5));
		contentPane.add(calendarComponent);
		
		createFooterComponent();
		footerComponent.setMaximumSize(new Dimension(Short.MAX_VALUE, buttonHeight + 5));
		contentPane.add(footerComponent);
		
	}
	
	private void createHeaderComponent() {
		
		headerComponent = new JPanel();
		
		headerComponent.setLayout(new BorderLayout());
		
		leftButton = new JButton(" < ");
		leftButton.addActionListener(this); // Triggers event when clicked
		headerComponent.add(leftButton, BorderLayout.WEST);
		
		// Shows the current month and year
		headerLabel = new JLabel(controller.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + ", " + controller.get(Calendar.YEAR));
		headerLabel.setHorizontalAlignment(JLabel.CENTER);
		headerComponent.add(headerLabel, BorderLayout.CENTER);
		
		rightButton = new JButton(" > ");
		rightButton.addActionListener(this);
		headerComponent.add(rightButton, BorderLayout.EAST);
		
	}
	
	private void createFooterComponent() {
		
		footerComponent = new JPanel();
		
		makeAppointmentButton = new JButton("Make Appointment");
		makeAppointmentButton.setMaximumSize(new Dimension(Short.MAX_VALUE, buttonHeight));
		makeAppointmentButton.addActionListener(this);
		footerComponent.add(makeAppointmentButton);
		
	}
	
	// Updates current month and year above calendar
	private void updateHeaderLabel() {
		
		headerLabel.setText(controller.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + ", " + controller.get(Calendar.YEAR));
		
	}
	
	private void updateDaysOfMonth() {

		// Where to start and where to stop listing calendar numbers
		int startDay = controller.get(Calendar.DAY_OF_WEEK) - 1; // -1 makes it so Sunday is 0
		int endDay = controller.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		int dayCounter = 1; // Keeps track of day of month
		for (int r = 0; r < daysOfMonth.length; r++) {
			for (int c = 0; c < daysOfMonth[0].length; c++) {
				
				// starts and ends at populating calendar numbers at appropriate times
				if ((r != 0 || c >= startDay) && (dayCounter <= endDay)) {
					daysOfMonth[r][c] = Integer.toString(dayCounter);
					dayCounter++;
				} else {
					daysOfMonth[r][c] = null;
				}
				
			}
		}
		
	}
	
	private void createAndShowGUI() {
		
		createContentPane();
		setContentPane(contentPane);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(600, 2*buttonHeight + calendarHeight + 49)); // 550 and 49 are arbitrary for visual appeal
		setPreferredSize(new Dimension(800, getMinimumSize().height)); // 650 arbitrary, height is minimum height
		setSize(getPreferredSize());
		setVisible(true);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Handles header button presses
		// Extra if statement or repeating 3 lines of code, which is better? Other option?
		if (e.getSource() == leftButton || e.getSource() == rightButton) {
			
			if (e.getSource() == leftButton) {
				
				controller.add(Calendar.MONTH, -1);
				
			// I could have just used else instead of else if, but I wanted to be specific to make it more readable
				// Is this a good idea?
			} else if (e.getSource() == rightButton) {
				
				controller.add(Calendar.MONTH, 1);
				
			}
			
			updateHeaderLabel();
			updateDaysOfMonth();
			calendarComponent.repopulateDaysOfMonth();
			calendarComponent.repaint();
			
		} else if (e.getSource() == makeAppointmentButton) {
			
			MakeAppointmentPanel makeAppointmentPanel = new MakeAppointmentPanel();
			
			Object[] message = {new MakeAppointmentPanel()};
			
		    int userOption = JOptionPane.showConfirmDialog(this,
		    		message,
		    		"Make Appointment",
		    		JOptionPane.OK_CANCEL_OPTION
		    );
		    
		    if (userOption ==  JOptionPane.OK_OPTION) {
		    	
		    	// TODO: Implement selected type and custom description
		    	controller.addAppointment(0, "Appointment", makeAppointmentPanel.getMonth(), makeAppointmentPanel.getDay(), makeAppointmentPanel.getYear());
		    	
		    }

			
//			controller.addAppointment(type, description, month, day, year);
			
		}
		
	}
	
	@SuppressWarnings("serial")
	private class CalendarComponent extends JPanel {
		
		private final String[] DAYS_OF_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		
		public CalendarComponent() {
			
			super();
			
			this.setBackground(Color.WHITE);
			this.setLayout(new GridBagLayout());
			
			repopulateDaysOfMonth();
			
		}
		
		private void populateDaysOfWeek() {
			
			GridBagConstraints constraints = new GridBagConstraints();
			
			constraints.anchor = GridBagConstraints.CENTER; // Pinned to center of cell
			constraints.weightx = 0.5; // So day names are not clumped in center
			constraints.ipady = 15; // Cell height
			constraints.insets = new Insets(0, 0, 0, 15); // Extra padding on the right because GridBagLayout is frustrating
			
			
			for(int i = 0; i < 7; i++) {
				
				constraints.gridx = i;
				constraints.gridy = 0;
				
				JLabel dayOfWeekLabel = new JLabel(DAYS_OF_WEEK[i]);
				this.add(dayOfWeekLabel, constraints);
				
			}
			
		}
		
		private void populateDaysOfMonth() {
			
			String[][] daysOfMonth = getDaysOfMonth();
			
			GridBagConstraints constraints = new GridBagConstraints();

			constraints.anchor = GridBagConstraints.NORTHEAST; // Pinned to top-right of grid
			constraints.insets = new Insets(0, 0, 0, 15); // Extra padding on the right because GridBagLayout is frustrating
			constraints.weightx = 0.5; // So days are not clumped in center
			constraints.weighty = 0.5; // So days are not clumped in center
			
			for (int r = 0; r < 6; r++) {
				for (int c = 0; c < 7; c++) {
					
					constraints.gridx = c;
					constraints.gridy = r + 1;
					
					JLabel dayOfMonthLabel;
					
					if (daysOfMonth[r][c] != null) {

						dayOfMonthLabel = new JLabel(daysOfMonth[r][c]);
						
					} else {

						dayOfMonthLabel = new JLabel(" ");
						
					}
					
					this.add(dayOfMonthLabel, constraints);
					
				}
			}
			
		}
		
		public void repopulateDaysOfMonth() {
			
			this.removeAll();
			populateDaysOfWeek();
			populateDaysOfMonth();
			
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			
			// Grid
			g.setColor(Color.BLACK);
			// Vertical Lines
			int horizontalSpaceBetweenLines = this.getWidth() / 7;
			for (int i = 1; i < 7; i++) {
				g.drawLine(i * horizontalSpaceBetweenLines, 0, i * horizontalSpaceBetweenLines, calendarHeight);
			}
			// Horizontal Lines
			g.drawLine(0, 0, this.getWidth(), 0);
			int verticalSpaceBetweenLines = (calendarHeight - 30) / 6; // Starts lower because of weekday names column
			for (int i = 0; i < 7; i++) {
				g.drawLine(0, i * verticalSpaceBetweenLines + 30, this.getWidth(), i * verticalSpaceBetweenLines + 30);
			}
			
		}
		
	}
	
	// Panel that appears when "makeAppointmentButton" is pressed
	private class MakeAppointmentPanel extends JPanel implements ActionListener, FocusListener {
		
		private GregorianCalendar calendar;
		
		private JPanel monthPanel;
		private JLabel monthLabel;
		private JComboBox<String> monthDropDown;
		
		private JPanel dayPanel;
		private JLabel dayLabel;
		private JComboBox<String> dayDropDown;
		
		private JPanel yearPanel;
		private JLabel yearLabel;
		private JTextField yearTextField;
		
		private final String[] MONTHS = {"Janurary", "Feburary", "March", "April", "June", "July", "August", "September", "October", "November", "December"};
		
		public MakeAppointmentPanel() {
			
			setLayout(new BorderLayout());
			
			calendar = new GregorianCalendar();
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
			
			monthPanel = new JPanel();
			
			monthLabel = new JLabel("Month: ");
			monthPanel.add(monthLabel, BorderLayout.LINE_START);
			
			monthDropDown = new JComboBox<String>();
			for (String month : MONTHS) {
				monthDropDown.addItem(month);
			}
			monthDropDown.addActionListener(this);
			monthPanel.add(monthDropDown, BorderLayout.LINE_END);
			
			add(monthPanel, BorderLayout.LINE_START);
			
	
			dayPanel = new JPanel();
			
			dayLabel = new JLabel("Day: ");
			dayPanel.add(dayLabel, BorderLayout.LINE_START);
			
			dayDropDown = new JComboBox<String>();
			populateDayDropDown();
			dayDropDown.addActionListener(this);
			dayPanel.add(dayDropDown, BorderLayout.LINE_END);
			
			add(dayPanel, BorderLayout.CENTER);

			
			yearPanel = new JPanel();
			
			yearLabel = new JLabel("Year: ");
			yearPanel.add(yearLabel, BorderLayout.LINE_START);
			
			yearTextField = new JTextField();
			yearTextField.setText(Integer.toString(calendar.get(Calendar.YEAR)));
			yearTextField.addFocusListener(this);
			yearPanel.add(yearTextField, BorderLayout.LINE_END);
			
			add(yearPanel, BorderLayout.LINE_END);
			
		}
		
		private void populateDayDropDown() {
			
			dayDropDown.removeAllItems();
			
			int daysInSelectedMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			for (int n = 1; n <= daysInSelectedMonth; n++)
				dayDropDown.addItem(Integer.toString(n));
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// If selected month changed, update calendar month so "updateDayDropDown" will get the accurate number of days in month
			if (e.getSource() == monthDropDown) {

				String selectedMonth = (String) monthDropDown.getSelectedItem();

				switch (selectedMonth) {
				
					case "Janurary":
						calendar.set(Calendar.MONTH, Calendar.JANUARY);
						break;
					case "Feburary":
						calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
						break;
					case "March":
						calendar.set(Calendar.MONTH, Calendar.MARCH);
						break;
					case "April":
						calendar.set(Calendar.MONTH, Calendar.APRIL);
						break;
					case "May":
						calendar.set(Calendar.MONTH, Calendar.MAY);
						break;
					case "June":
						calendar.set(Calendar.MONTH, Calendar.JUNE);
						break;
					case "July":
						calendar.set(Calendar.MONTH, Calendar.JULY);
						break;
					case "August":
						calendar.set(Calendar.MONTH, Calendar.AUGUST);
						break;
					case "September":
						calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
						break;
					case "October":
						calendar.set(Calendar.MONTH, Calendar.OCTOBER);
						break;
					case "November":
						calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
						break;
					case "December":
						calendar.set(Calendar.MONTH, Calendar.DECEMBER);
						break;
				
				}
				
				populateDayDropDown();				
				
			} else if (e.getSource() == dayDropDown) {
				
				int selectedDay = Integer.parseInt((String) dayDropDown.getSelectedItem());
				
				calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
				
			}
			
		}

		@Override
		public void focusLost(FocusEvent e) {

			if (e.getSource() == yearTextField) {
				
				int selectedYear;
				
				try {
					
					selectedYear = Integer.parseInt(yearTextField.getText());
					
					if (selectedYear < 0)
						throw new NumberFormatException();
					
				} catch (NumberFormatException ex) {
					
					JOptionPane.showMessageDialog(this, "Must enter a positive integer");
					return;
					
				}
				
				calendar.set(Calendar.YEAR, selectedYear);
				
				populateDayDropDown();
				
			}
			
		}
		
		public int getMonth() {
			return calendar.get(Calendar.MONTH);
		}
		
		public int getDay() {
			return calendar.get(Calendar.DAY_OF_MONTH);
		}
		
		public int getYear() {
			return calendar.get(Calendar.YEAR);
		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private String[][] getDaysOfMonth() {
		return daysOfMonth;
	}
	
	public static void main(String[] args) {
		
		AppointmentBookWindow frame = new AppointmentBookWindow();
		frame.createAndShowGUI();
		
	}
	
}
