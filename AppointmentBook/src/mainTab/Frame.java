package mainTab;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import appointmentBookController.AppointmentBookController;

public class Frame {
	
	private int calendarWidth = 200;
	private int calendarHeight = 450;
	
	private AppointmentBookController controller;
	
	public Frame() {
		controller = new AppointmentBookController();
	}
	
	private JPanel createContentPane() {
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		Component headerComponent = createHeaderComponent();
		contentPane.add(headerComponent);
		
		Component calendarComponent = createCalendarComponent();
		contentPane.add(calendarComponent);
		
		return contentPane;
		
	}
	
	private Component createHeaderComponent() {
		
		JPanel headerComponent = new JPanel();
		
		headerComponent.setLayout(new BorderLayout());
		
		JButton LeftButton = new JButton(" < ");
//		LeftButton.addActionListener(headerComponent);
//		headerComponent.add
		
		
		return headerComponent;
		
	}
	
	private Component createCalendarComponent() {
		
		JTable calendarComponent = new JTable(new CalendarTableModel());
		
		calendarComponent.setSize(calendarWidth, calendarHeight);
		calendarComponent.setRowHeight(calendarHeight / calendarComponent.getRowCount());
		
		return calendarComponent;
		
	}
	
	private void createAndShowGUI() {
		
		JFrame frame = new JFrame("Appointment Book");
		
		frame.setContentPane(createContentPane());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
				
	}
	
	public static void main(String[] args) {
		
		Frame frame = new Frame();
		frame.createAndShowGUI();
		
		
	}
	
	@SuppressWarnings("serial")
	public class CalendarTableModel extends AbstractTableModel {

		@Override
		public int getRowCount() {
			return 10;
		}

		@Override
		public int getColumnCount() {
			return 7;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return null;
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
		
		
		
	}
	
}
