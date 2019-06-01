package project4;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class creates an event using the name, start time, and end time of the user-defined event.
 * @author Chris Vo
 * @Version 1.0
 */
@SuppressWarnings("serial")
public class Event implements Serializable {
	//class variable declarations
	private String event; //the name of an event
	private Calendar starttime; //the starting time of an event
	private Calendar endtime; //the ending time of an event
	
	/**
	 * Constructor #1: This method construct a new instance of Event with class variables initialized to 0 or equivalent.
	 * @param none
	 * @return none
	 */
	public Event() {
		event = new String();
		starttime = new GregorianCalendar();
		endtime = new GregorianCalendar();
	}
	
	/**
	 * Constructor #2: This constructor defines all the class variables with the given parameters.
	 * @param event      the name of the event
	 * @param starttime  the starting time of the event
	 * @param endtime    the end time of the event
	 * @return none
	 */
	public Event(String event, Calendar starttime, Calendar endtime) {
		this.event = event;
		this.starttime = starttime;
		this.endtime = endtime;
	}

	/**
	 * This method sets the value of the event's name to the value of the parameter
	 * @param event  the name of the event
	 * @return none
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * This method returns the event's name
	 * @param none
	 * @return event  the name of the event
	 */
	public void setEvent(String event) {
		this.event = event;
	}
	
	/**
	 * This method sets the value of the event's starting time to the value of the parameter
	 * @param starttime  the starting time of the event
	 * @return none
	 */
	public Calendar getStartTime() {
		return starttime;
	}
	
	/**
	 * This method returns the event's starting time
	 * @param none
	 * @return starttime  the starting time of the event
	 */
	public void setStartTime(Calendar starttime) {
		this.starttime = starttime;
	}
	
	/**
	 * This method sets the value of the event's ending time to the value of the parameter
	 * @param endtime  the end time of the event
	 * @return none
	 */
	public Calendar getEndTime() {
		return endtime;
	}
	
	/**
	 * This method returns the event's ending time
	 * @param none
	 * @return endtime  the ending time of the event
	 */
	public void setEndTime(Calendar endtime) {
		this.endtime = endtime;
	}
}