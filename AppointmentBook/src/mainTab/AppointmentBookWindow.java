package mainTab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import appointmentBookController.AppointmentBookController;

public class AppointmentBookWindow implements ActionListener {

	private int buttonHeight = 30;
	private int calendarHeight = 550;
	
	// The controller acts as a calendar and also handles appointments
	private AppointmentBookController controller;

	private JPanel contentPane;
	private CalendarComponent calendarComponent; // The visual component of the calendar
	private JPanel headerComponent, footerComponent; // The buttons and label above and below calendar
	private JButton leftButton, rightButton, makeAppointmentButton;
	private JLabel headerLabel;
	
	public AppointmentBookWindow() {
		
		controller = new AppointmentBookController();
		
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
	private String[][] getDaysOfMonth() {
		
		String[][] daysOfMonth = new String[6][7];
		
		int startDay = controller.get(Calendar.DAY_OF_WEEK) - 1; // -1 so Sunday is 0
		int endDay = controller.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		int dayCounter = 1; // Keeps track of day of month
		for (int r = 0; r < daysOfMonth.length; r++) {
			for (int c = 0; c < daysOfMonth[0].length; c++) {
				
				if (r != 0 || c >= startDay) {
					daysOfMonth[r][c] = Integer.toString(dayCounter);
					dayCounter++;
				}
				if (dayCounter > endDay) {
					return daysOfMonth;
				}
				
			}
		}
		
		return daysOfMonth;
		
	}
	
	private void createAndShowGUI() {
		
		JFrame frame = new JFrame("Appointment Book");
		
		createContentPane();
		frame.setContentPane(contentPane);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(550, 2*buttonHeight + calendarHeight + 49)); // 550 and 49 are arbitrary for visual appeal
		frame.pack();
		frame.setVisible(true);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Handles header button presses
		if (e.getSource() == leftButton || e.getSource() == rightButton) {
			
			if (e.getSource() == leftButton) {
				
				controller.add(Calendar.MONTH, -1);
				
			} else if (e.getSource() == rightButton) {
				
				controller.add(Calendar.MONTH, 1);
				
			}
			
			updateHeaderLabel();
			calendarComponent.updateCalendarDays();
			calendarComponent.repaint();
			
		}
		
	}
	
	@SuppressWarnings("serial")
	private class CalendarComponent extends JPanel {
		
		private final String[] DAYS_OF_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		
		private JLabel[] daysOfWeekLabels;
		private JLabel[][] daysOfMonthLabels;
		
		public CalendarComponent() {
			
			super();
			this.setLayout(new GridBagLayout());
			
			daysOfWeekLabels = new JLabel[7];
			daysOfMonthLabels = new JLabel[6][7];
			
			updateCalendarDays();
			
		}
		
		private void populateDaysOfWeek() {
			
			GridBagConstraints constraints = new GridBagConstraints();
			
			constraints.weightx = 0.5;
			constraints.ipady = 15; // Cell height
			
			
			for(int i = 0; i < daysOfWeekLabels.length; i++) {
				
				constraints.gridx = i;
				constraints.gridy = 0;
				
				daysOfWeekLabels[i] = new JLabel(DAYS_OF_WEEK[i]);
				this.add(daysOfWeekLabels[i], constraints);
				
			}
			
		}
		
		private void populateDaysOfMonth() {
			
			String[][] daysOfMonth = getDaysOfMonth();
			
			GridBagConstraints constraints = new GridBagConstraints();

			constraints.weightx = 0.5;
			constraints.weighty = 0.5;
			
			for (int r = 0; r < daysOfMonthLabels.length; r++) {
				for (int c = 0; c < daysOfMonthLabels[0].length; c++) {
					
					constraints.gridx = c;
					constraints.gridy = r + 1;
					
					daysOfMonthLabels[r][c] = null;
					if (daysOfMonth[r][c] != null) {

						daysOfMonthLabels[r][c] = new JLabel(daysOfMonth[r][c]);
						this.add(daysOfMonthLabels[r][c], constraints);
						
					} else {

						daysOfMonthLabels[r][c] = new JLabel(" ");
						this.add(daysOfMonthLabels[r][c], constraints);
						
					}
					
				}
			}
			
		}
		
		public void updateCalendarDays() {
			
			this.removeAll();
			populateDaysOfWeek();
			populateDaysOfMonth();
			
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			
			// White background
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, Short.MAX_VALUE, calendarHeight);
			
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
	
	public static void main(String[] args) {
		
		AppointmentBookWindow frame = new AppointmentBookWindow();
		frame.createAndShowGUI();
		
		
	}
	
}
