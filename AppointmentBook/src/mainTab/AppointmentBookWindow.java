package mainTab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
		
		int startDay = controller.get(Calendar.DAY_OF_WEEK) - 1; // -1 makes it so Sunday is 0
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
		frame.setMinimumSize(new Dimension(600, 2*buttonHeight + calendarHeight + 49)); // 550 and 49 are arbitrary for visual appeal
		frame.setPreferredSize(new Dimension(800, frame.getMinimumSize().height)); // 650 arbitrary, height is minimum height
		frame.setSize(frame.getPreferredSize());
		frame.setVisible(true);
				
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
			calendarComponent.updateCalendarDays();
			calendarComponent.repaint();
			
		}
		
	}
	
	@SuppressWarnings("serial")
	private class CalendarComponent extends JPanel {
		
		private final String[] DAYS_OF_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		
		public CalendarComponent() {
			
			super();
			
			this.setBackground(Color.WHITE);
			this.setLayout(new GridBagLayout());
			
			updateCalendarDays();
			
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
		
		public void updateCalendarDays() {
			
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
	
	public static void main(String[] args) {
		
		AppointmentBookWindow frame = new AppointmentBookWindow();
		frame.createAndShowGUI();
		
		
	}
	
}
