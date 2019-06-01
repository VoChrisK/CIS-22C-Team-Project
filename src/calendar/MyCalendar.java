/**
 * This class builds a representation of a calendar in either day view or month view. It contains methods to properly traverse through
 * the calendar, day by day, and tell the user of events when possible. It also stores and modifies a list of events.
 * @author Chris Vo
 * @Version 1.0
 */

package calendar;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@SuppressWarnings("serial")
public class MyCalendar implements Serializable {
	//class variable declarations
	private ArrayList<Event> events; //a collection of events is stored here
	private String[] months; //array of months by name
	private String[] days; //array of days by name
	private SimpleDateFormat time; //to format the time of a given Date object
	
	/**
	 * Constructor #1: This method construct a new instance of MyCalendar with class variables initialized to 0 or equivalent.
	 * @param none
	 * @return none
	 */
	public MyCalendar() {
		events = new ArrayList<Event>();
		months = new String[] {"January", "February", "March", "April", "May", "June", "July", 
				"August", "September", "October", "November", "December"};
		days = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		time = new SimpleDateFormat("HH:mm"); //the capital H's are to format the time in a 24-hour format
	}
	
	/**
	 * This method sets the value of the list of events to the value of the parameter
	 * @param events  the list/collection of events
	 * @return none
	 */
	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}
	
	/**
	 * This method returns the list of events
	 * @param none
	 * @return events  the list/collection of events
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}
	
	/**
	 * This method returns the array of months
	 * @param none
	 * @return months  array of months by name
	 */
	public String[] getMonths() {
		return months;
	}
	
	/**
	 * This method returns the array of days
	 * @param none
	 * @return days  array of days by name
	 */
	public String[] getDays() {
		return days;
	}
	
	/**
	 * This method gets the next day of the current date and print the calendar again either in day or month format.
	 * This method allows the user to traverse forward in the calendar.
	 * @param calendar  a calendar object that stores the current (and modified through traversal) date
	 * @param input     user input to choose to print the calendar in day view or month view
	 * @return none
	 */
	public void getNext(Calendar calendar, String input) {
		calendar.add(Calendar.DAY_OF_MONTH, 1); //increments the day of the current month to 1
		
		if(input.equals("M")) //if input equals M (from menu) display calendar by month
			CalendarByMonth(calendar);
		else //otherwise display by day (no need to check for "D" input since it's already been handled in menu)
			CalendarByDay(calendar);
	}
	
	/**
	 * This method gets the previous day of the current date and print the calendar again either in day or month format.
	 * This method allows the user to traverse backwards in the calendar.
	 * @param calendar  a calendar object that stores the current (and modified through traversal) date
	 * @param input     user input to choose to print the calendar in day view or month view
	 * @return none
	 */
	public void getPrev(Calendar calendar, String input) {
		calendar.add(Calendar.DAY_OF_MONTH, -1); //decrements the day of the current month to 1
		
		if(input.equals("M")) //if input equals M (from menu) display calendar by month
			CalendarByMonth(calendar);
		else //otherwise display by day (no need to check for "D" input since it's already been handled in menu)
			CalendarByDay(calendar);
	}
	
	/**
	 * This method builds the day view of the calendar. The current day of the month will be displayed to screen alongside with
	 * any events occurring in that particular day.
	 * @param calendar  a calendar object that stores the current (and modified through traversal) date
	 * @return none
	 */
	public void CalendarByDay(Calendar calendar) {
		//method variable declarations
		boolean flag = false;
		
		System.out.print("\n");
		//prints out the name of the day, month (abbreviated) of the day, the day of month, and year of the current date
		System.out.println(days[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + months[calendar.get(Calendar.MONTH)].substring(0, 3) 
				+ " " + calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR));
		System.out.println("----------------------------------------------"); //for formatting purposes
		
		if(events.isEmpty()) //if list is empty, print error message and return to menu
			System.out.println("ERROR: The list of events is empty.\\n");
		else {
			for(int i = 0; i < events.size(); i++) { //for loop to check for any events that occurs in the current day
				if(calendar.get(Calendar.MONTH) == events.get(i).getStartTime().get(Calendar.MONTH) && //if current date's month and day equals to the month of day of an event
						calendar.get(Calendar.DAY_OF_MONTH) == events.get(i).getStartTime().get(Calendar.DAY_OF_MONTH)) {
					System.out.println(events.get(i).getEvent() + ": " + time.format(events.get(i).getStartTime().getTime()) +
							" - " + time.format(events.get(i).getEndTime().getTime())); //prints an event by name, start time, and end time
					flag = true; //if at least one event is found, set flag to true
				}
			}
			if(flag == false)
				System.out.println("There are currently no scheduled events on this day. ");
			else
				System.out.print("\n");
		}
	}
	
	/**
	 * This method builds the month view of the calendar. All the days in the current month are displayed in a calendar-like
	 * display. It also highlights the current day and factors in whether events occur on that particular day or not.
	 * @param calendar  a calendar object that stores the current (and modified through traversal) date
	 * @return none
	 */
	public void CalendarByMonth(Calendar calendar) { 
		//method variable declarations
		int daysinmonth; //stores the amount of days in the current month
		int currentday; //stores the current day of the date
		int currentmonth; //stores the current month of the date
		int currentyear; //stores the current year of the date
		int week = 0; //counter variable to newline the next week once the end of week is reached
		
		//prints the current month and the current year
		System.out.println(String.format("%20s",(months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR))));
		
		for(int i = 0; i < days.length; i++) //prints out the abbreviated days before populating the days of the month
			System.out.print(days[i].substring(0, 2) + "   ");
		
		System.out.print("\n");
		//stores the current date in three separate variables
		currentday = calendar.get(Calendar.DAY_OF_MONTH);
		currentmonth = calendar.get(Calendar.MONTH);
		currentyear = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.DAY_OF_MONTH, 1); //set the date to the first day of month to start populating the calendar from the beginning
		//the current date has to be stored in separate variables and set it back afterwards or calendar will hold the last day of the month
		//before returning due to static variables
		
		for(int i = 0; i < calendar.get(Calendar.DAY_OF_WEEK) - 1; i++) { //to properly indent the first day of the month
			System.out.print("     ");
			week++;
		}
		
		//stores the days of the current month for more consistency (static variable will always hold the highest number)
		daysinmonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		for(int i = 1; i <= daysinmonth; i++) //for loop to populate the days in the current month and display them
			week = FormatMonth(i, currentday, week, calendar); //method call to make the for loop cleaner
		
		calendar.set(currentyear, currentmonth, currentday); //set the calendar back to the current date.
		System.out.print("\n");
	}
	
	/**
	 * This method is specifically used within the CalendarByMonth method only (thus it is private). It handles the formatting process
	 * for each day in the current month. It also does the highlighting of current day and factoring in whether events occur 
	 * on that particular day or not (all enclosed in a for loop).
	 * @param i           the current counter number from the for loop
	 * @param currentday  the current day of the date that was passed from CalendarByMonth 
	 * @param week        counter to format the weeks when end of week has occurred
	 * @param calendar    a calendar object that stores the current (and modified through traversal) date
	 * @return integer    incremented week if end of week has not been reached or reset it to 0 if it did
	 */
	private int FormatMonth(int i, int currentday, int week, Calendar calendar) {
		if(i == currentday) { //when i is equal to current day, highlight that day with a pair of brackets, either square or curly
			boolean flag = false; //to choose whether to use square brackets or curly brackets
			
			for(int j = 0; j < events.size(); j++) { //check if there's any event in the current day
				if(calendar.get(Calendar.MONTH) == events.get(j).getStartTime().get(Calendar.MONTH) &&
						calendar.get(Calendar.DAY_OF_MONTH) == events.get(j).getStartTime().get(Calendar.DAY_OF_MONTH)) {
				System.out.print(String.format("%-5s", "{" + calendar.get(Calendar.DAY_OF_MONTH) + "}")); //highlight with curly brackets
				flag = true;
				break; //break the loop if at least one event occurs in the current day
				}
			}
			
			if(flag == false) //highlight the current day with square brackets instead, indicating that there's no events on this day
				System.out.print(String.format("%-5s", "[" + calendar.get(Calendar.DAY_OF_MONTH) + "]"));
		}
		else //if i is not the current day, just print out the days without any brackets
			System.out.print(String.format("%-5s", calendar.get(Calendar.DAY_OF_MONTH)));
		
		calendar.add(Calendar.DAY_OF_MONTH, 1); //increments the days of the current month by 1
		
		if(week < 6) //if end of week has not been reached
			return ++week; //return incremented week
		else { //else if end of week has been reached
			System.out.print("\n"); //make a new line for the next week to be populated
			return 0; //return 0, effectively reseting the week counter
		}		
	}
}
