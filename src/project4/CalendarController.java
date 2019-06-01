package project4;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * This class attaches a mouse listener to the calendar view to make each day in the month view clickable.
 * @author Chris Vo
 * @Version 1.0
 */
@SuppressWarnings("serial")
public class CalendarController extends JComponent {
	private CalendarView calendar;
	private Point mousePoint;
	
	/**
	 * Constructor #1: This method construct a new instance of CalendarController, saving a reference
	 * to the calendar view
	 * @param none
	 * @return none
	 */
	public CalendarController(CalendarView calendar) {
		this.calendar = calendar;
		mousePoint = new Point();
		addMouseListener(new MouseListener());
	}
	
	   /**
	   * This method overwrites the paintComponent method in order to draw the entirety of the
	   * calendar
	   * @param g    Graphics necessary to draw shapes and text
	   * @return none
	   */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		calendar.drawCalendar(g2);
	}
	
	/**
	 * This class checks checks if a user chooses a day (and not anywhere else on the calendar) and
	 * set the day to that particular day
	 * @author Chris Vo
	 * @Version 1.0
	 */
	private class MouseListener extends MouseAdapter {
		
		   /**
		   * This method checks if the user clicks on a day in the month view of the calendar. If true,
		   * the calendar will be set to that day.
		   * @param event  whenever a mouse performs an event of some kind
		   * @return none
		   */
		public void mouseClicked(MouseEvent e) {
			mousePoint = e.getPoint();
			for(int i = 0; i < calendar.getMonthDays().size(); i++) {
				if(calendar.getMonthDays().get(i).contains(mousePoint)) {
					calendar.getModel().setCalendarDay(i + 1);
					break;
				}
			}
		}
		
	}
}
