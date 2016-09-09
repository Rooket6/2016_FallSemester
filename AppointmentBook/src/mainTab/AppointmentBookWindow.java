package mainTab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import appointmentBookController.AppointmentBookController;

public class AppointmentBookWindow implements ActionListener {
	
	private int calendarWidth = 200;
	private int calendarHeight = 450;
	
	private AppointmentBookController controller;
	
	JButton leftButton, rightButton, createAppointment;
	CalendarComponent calendarComponent;
	
	public AppointmentBookWindow() {
		
		controller = new AppointmentBookController();
		calendarComponent = new CalendarComponent();
		
	}
	
	private JPanel createContentPane() {
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		Component headerComponent = createHeaderComponent();
		headerComponent.setMaximumSize(new Dimension(Short.MAX_VALUE, 60));
		contentPane.add(headerComponent);
		
		contentPane.add(calendarComponent);
		
		return contentPane;
		
	}
	
	private Component createHeaderComponent() {
		
		JPanel headerComponent = new JPanel();
		
		headerComponent.setLayout(new BorderLayout());
		
		leftButton = new JButton(" < ");
		leftButton.addActionListener(this);
		headerComponent.add(leftButton);
		
		
		return headerComponent;
		
	}
	
	private void updateCalendarComponent() {
		
//		calendarComponent.cell
		
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
		frame.pack();
		frame.setVisible(true);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == leftButton) {
			controller.set(Calendar.MONTH, controller.MONTH - 1);
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
