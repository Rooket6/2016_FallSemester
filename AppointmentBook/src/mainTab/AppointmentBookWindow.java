package mainTab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import appointmentBookController.AppointmentBookController;

public class AppointmentBookWindow implements ActionListener {
	
	// For the starting size of the calendar
	private int calendarWidth = 200;
	private int calendarHeight = 450;
	
	// The controller acts as a calendar and also handles appointments
	private AppointmentBookController controller;

	private CalendarComponent calendarComponent; // The visual component of the calendar
	private JPanel headerComponent;
	private JButton leftButton, rightButton, createAppointment;
	private JLabel headerLabel;
	
	public AppointmentBookWindow() {
		
		controller = new AppointmentBookController();
		calendarComponent = new CalendarComponent();
		
	}
	
	private JPanel createContentPane() {
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		createHeaderComponent();
		headerComponent.setMaximumSize(new Dimension(Short.MAX_VALUE, 60)); // Infinitely wide, but confined to 60px high
		contentPane.add(headerComponent);
		
		calendarComponent.setSize(300, 450);
		contentPane.add(calendarComponent);
		
		return contentPane;
		
	}
	
	private void createHeaderComponent() {
		
		headerComponent = new JPanel();
		
		headerComponent.setLayout(new BorderLayout());
		
		leftButton = new JButton(" < ");
		leftButton.addActionListener(this);
		headerComponent.add(leftButton, BorderLayout.WEST);
		
		// Shows the current month and year
		headerLabel = new JLabel(controller.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + ", " + controller.get(Calendar.YEAR));
		headerLabel.setHorizontalAlignment(JLabel.CENTER);
		headerComponent.add(headerLabel);
		
		rightButton = new JButton(" > ");
		rightButton.addActionListener(this);
		headerComponent.add(rightButton, BorderLayout.EAST);
		
	}
	
	private void createFooterComponent() {
		
	}
	
	private void updateCalendarComponent() {
		
//		calendarComponent.cell
		
	}
	
	// Updates current month and year above calendar
	private void updateHeaderLabel() {
		
		headerLabel.setText(controller.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + ", " + controller.get(Calendar.YEAR));
		
	}
	
	private String[] getDaysOfWeek() {
		
		String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		return daysOfWeek;
		
	}
	
	private String[][] getDaysOfMonth() {
		
		String[][] daysOfMonth = new String[6][7];
		
		return daysOfMonth;
		
	}
	
	private void createAndShowGUI() {
		
		JFrame frame = new JFrame("Appointment Book");
		
		frame.setContentPane(createContentPane());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(250, 550));
		frame.pack();
		frame.setVisible(true);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == leftButton) {
			
			controller.add(Calendar.MONTH, -1);
			updateHeaderLabel();
			System.out.println("Left button fired");
			
		} else if (e.getSource() == rightButton) {
			
			controller.add(Calendar.MONTH, 1);
			updateHeaderLabel();
			System.out.println("Right button fired");
			
		}
		
	}
	
	@SuppressWarnings("serial")
	private class CalendarComponent extends JPanel {
		
		private String[] daysOfWeek = getDaysOfWeek();
		
		@Override
		protected void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, Integer.MAX_VALUE, 450);
			
		}
		
	}
	
	public static void main(String[] args) {
		
		AppointmentBookWindow frame = new AppointmentBookWindow();
		frame.createAndShowGUI();
		
		
	}
	
}
