package project4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * This class stores the data of the calendar and events. It also contains references to the views.
 * This class is responsible for updating the views whenever it updates the data of the calendar.
 * @author Chris Vo
 * @Version 1.0
 */
@SuppressWarnings("serial")
public class CalendarModel implements Serializable {
	private Calendar calendar;
	private ArrayList<Event> events;
	private ArrayList<ChangeListener> listeners;
	
	/**
	 * Constructor #1: This method construct a new instance of CalendarModel with class variables initialized to 0 or equivalent.
	 * @param none
	 * @return none
	 */
	public CalendarModel() {
		calendar = new GregorianCalendar();
		events = new ArrayList<Event>();
		listeners = new ArrayList<ChangeListener>();
	}
	
	/**
	 * This method handles the creation of an event. User will have to properly input based on instruction and format
	 * and the method will perform several tasks to convert the input to a new event. This method also checks whether
	 * the given date and time conflicts with an existing event in the list. A date with only start time and specific tasks
	 * to work around with it is also provided in this method. (Note: This create method only applies to one day event, like
	 * what the assignment has stated)
	 * @param mycal  MyCalendar object to create the calendar
	 * @return none
	 */
	public boolean Create(String title, String starttime, String endtime) {
		//method variable declarations
		boolean flag = true;
		String beginning[] = starttime.split(":");
		String ending[] = endtime.split(":");
		
		//if the input starttime contains am or pm in the end
		if(beginning[1].endsWith("am") || beginning[1].endsWith("pm")) {
			if(beginning[1].endsWith("pm")) {
				beginning[0] = Integer.toString(Integer.parseInt(beginning[0]) + 12);
			}
			beginning[1] = beginning[1].substring(0, beginning[1].length() - 2);
		}
		
		//if the input endtime contains am or pm in the end
		if(ending[1].endsWith("am") || ending[1].endsWith("pm")) {
			if(ending[1].endsWith("pm")) {
				ending[0] = Integer.toString(Integer.parseInt(ending[0]) + 12);
			}
			ending[1] = ending[1].substring(0, ending[1].length() - 2);
		}
			
		Calendar start = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), Integer.parseInt(beginning[0]), Integer.parseInt(beginning[1]));
		Calendar end = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), Integer.parseInt(ending[0]), Integer.parseInt(ending[1]));
			
		if(start.getTime().after(end.getTime())) { //if end time is before start time
			flag = false; //sets flag to false and repeat the loop again
		}
		else {
			for(int i = 0; i < events.size(); i++) { //loop to check each existing event with given event
				//this large if statement checks whether either start or end time of defined event conflicts with an existing event
				//(before, equals or after existing event's time slot)
				if(events.get(i).getStartTime().getTime().equals(start.getTime()) || //if start time of existing event is equal to the start time of given event
					events.get(i).getEndTime().getTime().equals(end.getTime()) || //if end time of existing event is equal to the end time of given event
					events.get(i).getStartTime().getTime().before(start.getTime()) && 
					events.get(i).getEndTime().getTime().after(start.getTime()) || //if the existing event's start time and end time is between the start time and end time of the given event
					events.get(i).getStartTime().getTime().before(end.getTime()) &&
					events.get(i).getEndTime().getTime().after(end.getTime())) { //if the existing event's start time and end time is between the start time and end time of the given event
						flag = false; //sets flag to false and repeat the loop again
						break; //break loop so it doesn't have to check for more events
				}
			}
			
			if(flag == true) {//if there is no conflicts create the new event with user defined date and time
				events.add(new Event(title, start, end));
				//updates the view after an event has been created
				for(ChangeListener listener : listeners)
					listener.stateChanged(new ChangeEvent(this));
			}
		}
		
		Comparator<Event> comp = new Comparator<Event>() { //create a new anonymous comparator to sort events by chronological order
			public int compare(Event event1, Event event2) {
				return event1.getStartTime().compareTo(event2.getStartTime());
			}
		};
		
		Collections.sort(events, comp); //sorting in chronological order
		
		return flag;
	}
	
	/**
	 * This method returns the calendar
	 * @param none
	 * @return calendar  a calendar object that stores the date
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * This method returns the total events
	 * @param none
	 * @return events  the total events within a data structure
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}
	
	/**
	 * This method returns the current day of the date
	 * @param none
	 * @return int value  the current day of the date
	 */
	public int getCalendarDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * This method sets the day of the month to the next or previous day. Listeners will
	 * be notified afterwards.
	 * @param day  the day to modify the day of month
	 * @return none
	 */
	public void setCalendarDay(int day) {
		calendar.set(Calendar.DAY_OF_MONTH, day);
		
		for(ChangeListener listener : listeners)
			listener.stateChanged(new ChangeEvent(this));
	}

	/**
	 * This method sets the arraylist of events to a new value
	 * @param events  the total events within a data structure
	 * @return none
	 */
	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

	/**
	 * This method adds a new event to the arraylist. The listeners will be notified
	 * of this change.
	 * @param event  an event
	 * @return none
	 */
	public void AddEvent(Event event) {
		events.add(event);
		
		for(ChangeListener listener : listeners)
			listener.stateChanged(new ChangeEvent(this));
	}
	
	/**
	 * This method adds a view to the model
	 * @param listener  the view variable: to update GUI whenever the model has been changed
	 * @return none
	 */
	public void AddListener(ChangeListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * This method loads in the events from a text file using serialization
	 * @param mycal  MyCalendar object to create the calendar
	 * @return none
	 */
	@SuppressWarnings("unchecked")
	public void Load() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("events.txt"));
			events = ((ArrayList<Event>) in.readObject()); //read in the object from textfile by casting it to the object of choice and set it to the current list of events
			in.close();
			System.out.println("Events have been loaded.");
		} catch(FileNotFoundException e) {
			System.out.println("No file 'event.txt' has been found. This is the first test run."); //if there is no events.txt file, print a message indicating that it is the first run
		} catch (IOException e) {
			System.out.println("IOException: Unable to load the events from a text file.");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: Unable to load the events from a text file.");
		}
	}
	
	/**
	 * This method saves the current list of events to a text file using serialization
	 * @param mycal  MyCalendar object to create the calendar
	 * @return none
	 */
	public void Save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("events.txt"));
			out.writeObject(events); //write object to a text.file, serializing it
			out.close();
			System.out.println("Events have been saved.");
		} catch (IOException e) {
			System.out.println("IOException: Unable to save the events to a text file.");
		}
	}
	
}
