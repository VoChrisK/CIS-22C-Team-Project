package project4;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class draws the calendar in both month view and day view. The calendar will display
 * the days of the month in the correct format and the events under a certain day.
 * @author Chris Vo
 * @Version 1.0
 */
public class CalendarView {
	private final String[] months; //array of months by name
	private final String[] days; //array of days by name
	private ArrayList<Rectangle2D> monthdays;
	private CalendarModel model;
	private SimpleDateFormat time; //to format the time of a given Date object
	
	/**
	 * Constructor #1: This method construct a new instance of CalendarView, saving a reference
	 * of the calendar model.
	 * @param Model  a reference of the calendar model, used to get values from the model
	 * @return none
	 */
	public CalendarView(CalendarModel model) {
		this.model = model;
		monthdays = new ArrayList<Rectangle2D>();
		months = new String[] {"January", "February", "March", "April", "May", "June", "July", 
				"August", "September", "October", "November", "December"};
		days = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		time = new SimpleDateFormat("hh:mm a");
	}
	
	/**
	 * This method returns the reference of the calendar model
	 * @param none
	 * @return model  a reference of the calendar model, used to get values from the model
	 */
	public CalendarModel getModel() {
		return model;
	}
	
	/**
	 * This method returns the days of the months
	 * @param none
	 * @return monthdays the days of the months enclosed in rectangles
	 */
	public ArrayList<Rectangle2D> getMonthDays() {
		return monthdays;
	}
	
	/**
	 * This method draws the month view of the calendar. It aligns each day of the month in
	 * a correct fashion. It also provides a highlighter to highlight the current day.
	 * @param g2  Graphics2D necessary to draw shapes and text
	 * @return none
	 */
	public void drawCalendar(Graphics2D g2) {
		Rectangle2D highlight = new Rectangle2D.Double();
		monthdays.clear();
		
		//method variable declarations
		int daysinmonth; //stores the amount of days in the current month
		int currentday; //stores the current day of the date
		int currentmonth; //stores the current month of the date
		int currentyear; //stores the current year of the date
		int week = 0; //counter variable to newline the next week once the end of week is reached
		final int fontsize = 28;
		
		g2.setFont(new Font("Serif", Font.BOLD, fontsize));
		//draws the current month and the current year
		g2.drawString(String.format("%20s",(months[model.getCalendar().get(Calendar.MONTH)] + " " + model.getCalendar().get(Calendar.YEAR))), fontsize + 30, fontsize);
		
		for(int i = 0; i < days.length; i++) //prints out the abbreviated days before populating the days of the month
			g2.drawString(days[i].substring(0, 2) + "   ", fontsize + ((fontsize + 10) * (i + 1)), fontsize * 2);
		
		//stores the current date in three separate variables
		currentday = model.getCalendar().get(Calendar.DAY_OF_MONTH);
		currentmonth = model.getCalendar().get(Calendar.MONTH);
		currentyear = model.getCalendar().get(Calendar.YEAR);
		model.getCalendar().set(Calendar.DAY_OF_MONTH, 1); //set the date to the first day of month to start populating the calendar from the beginning
		//the current date has to be stored in separate variables and set it back afterwards or calendar will hold the last day of the month
		//before returning due to static variables
		
		for(int i = 0; i < model.getCalendar().get(Calendar.DAY_OF_WEEK) - 1; i++) //to properly indent the first day of the month
			week++;
		
		//stores the days of the current month for more consistency (static variable will always hold the highest number)
		daysinmonth = model.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
		
		int increment = fontsize * 3;
		
		for(int i = 1; i <= daysinmonth; i++) { //for loop to populate the days in the current month and display them
			
			Rectangle2D temp = new Rectangle2D.Double(((fontsize + 10) * week) + 75, increment - 25, fontsize, fontsize);
			
			if(i == currentday) {
				highlight.setRect((fontsize + 10) * (week + 2), increment - 20, 28, 28);
				g2.setColor(Color.GRAY);
				g2.fill(highlight);
				g2.setColor(Color.BLACK);
				g2.draw(highlight);
			}
			
			g2.drawString(String.format("%-5s", model.getCalendar().get(Calendar.DAY_OF_MONTH)), (int)(temp.getMaxX() - 25), (int)(temp.getMaxY()));
			monthdays.add(temp);
			
			model.getCalendar().add(Calendar.DAY_OF_MONTH, 1); //increments the days of the current month by 1
			
			if(week < 6) //if end of week has not been reached
				week++; //return incremented week
			else { //else if end of week has been reached
				week = 0; //return 0, effectively reseting the week counter
				increment += fontsize;
			}		
		}
		
		model.getCalendar().set(currentyear, currentmonth, currentday); //set the calendar back to the current date.
		CalendarByDay(g2, 28);
	}
	
	/**
	 * This method draws the day view of the calendar. The current day of the month will be displayed to screen alongside with
	 * any events occurring in that particular day.
	 * @param g2  Graphics2D necessary to draw shapes and text
	 * @param fontsize  the size of the font, also used to position the objects to be drawn
	 * @return none
	 */
	public void CalendarByDay(Graphics2D g2, int fontsize) {
		//method variable declarations
		boolean flag = false;
		int increment = fontsize * 2;
		
		//prints out the name of the day, month (abbreviated) of the day, the day of month, and year of the current date
		g2.drawString(days[model.getCalendar().get(Calendar.DAY_OF_WEEK) - 1] + " " + (model.getCalendar().get(Calendar.MONTH) + 1)
				+ "/" + model.getCalendar().get(Calendar.DAY_OF_MONTH), fontsize + 325, fontsize);
		
		g2.setFont(new Font("Serif", Font.PLAIN, fontsize - 6));
		fontsize += 25;
		for(int i = 0; i < model.getEvents().size(); i++) { //for loop to check for any events that occurs in the current day
			if(model.getCalendar().get(Calendar.MONTH) == model.getEvents().get(i).getStartTime().get(Calendar.MONTH) && //if current date's month and day equals to the month of day of an event
					model.getCalendar().get(Calendar.DAY_OF_MONTH) == model.getEvents().get(i).getStartTime().get(Calendar.DAY_OF_MONTH)) {
				g2.drawString(model.getEvents().get(i).getEvent() + ": " + time.format(model.getEvents().get(i).getStartTime().getTime()) +
						" - " + time.format(model.getEvents().get(i).getEndTime().getTime()), fontsize + 325, increment); //prints an event by name, start time, and end time
				flag = true; //if at least one event is found, set flag to true
				increment += 20;
			}
			
		}
		if(flag == false)
			g2.drawString("There are currently no scheduled events on this day. ", fontsize + 325, fontsize);
	}
}
