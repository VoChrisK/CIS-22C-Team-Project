/**
 * This class provides functionalities to work alongside with MyCalendar. Creating, deleting, printing, loading, and saving
 * event(s) are all contained within this class to split the responsibilities between the two classes.
 * @author Chris Vo
 * @Version 1.0
 */

package calendar;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class MyCalendarFunctions {
	//class variable declarations
	Scanner in;
	SimpleDateFormat time; //to format the time of a given Date object
	
	/**
	 * Constructor #1: This method construct a new instance of Event with class variables initialized to 0 or equivalent.
	 * @param none
	 * @return none
	 */
	public MyCalendarFunctions() {
		in = new Scanner(System.in);
		time = new SimpleDateFormat("HH:mm"); //the capital H's are to format the time in a 24-hour format
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
	public void Create(MyCalendar mycal) {
		//method variable declarations
		boolean flag;
		String[] date;
		String[] starttime;
		String[] endtime;
		String input;
		
		System.out.println("Enter the title of the event:");
		System.out.print("Input: ");
		String title = in.nextLine();
	
		//do while loop for inputting and checking input. If a defined event conflicts with another event, repeat the entire process
		do {
			flag = true;
			date = new String[2];
			
			System.out.println("Enter the date of the event:");
			System.out.println("(Format: MM/dd/yyyy)");
			System.out.print("Input: ");
			date = in.nextLine().split("/"); //splits the input into 3 separate Strings: Month, Day, and Year
		
			System.out.println("Enter the start time of the event:");
			System.out.println("(Format: 00:00, from 00:00 to 23:59)");
			System.out.print("Input: ");
			starttime = in.nextLine().split(":"); //splits the input into 2 separate Strings: Hour and Minute
			//create a temporary calendar with user defined date and start time
			Calendar start = new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[0]) - 1,
					Integer.parseInt(date[1]), Integer.parseInt(starttime[0]), Integer.parseInt(starttime[1]));
		
			//if there is no end time, user will input "none". This will go to a different set of if statements
			System.out.println("Enter the end time of the event (input none if there's no end time):");
			System.out.println("(Format: 00:00, from 00:00 to 23:59)");
			System.out.print("Input: ");
		
			//a series of if/else statements to handle whether the event conflicts with another event or not. Afterwards, create the new event
			if((input = in.nextLine()).equals("none") == false) { //if input has a defined end time
				endtime = input.split(":");
				//create a temporary calendar with user defined date and end time
				Calendar end = new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[0]) - 1, 
						Integer.parseInt(date[1]), Integer.parseInt(endtime[0]), Integer.parseInt(endtime[1]));
				
				if(start.getTime().after(end.getTime())) { //if end time is before start time
					System.out.println("Your End Time cannot be before the Start Time");
					flag = false; //sets flag to false and repeat the loop again
				}
				else {
					for(int i = 0; i < mycal.getEvents().size(); i++) { //loop to check each existing event with given event
						//this large if statement checks whether either start or end time of defined event conflicts with an existing event
						//(before, equals or after existing event's time slot)
						if(mycal.getEvents().get(i).getStartTime().getTime().equals(start.getTime()) || //if start time of existing event is equal to the start time of given event
								mycal.getEvents().get(i).getEndTime().getTime().equals(end.getTime()) || //if end time of existing event is equal to the end time of given event
								mycal.getEvents().get(i).getStartTime().getTime().before(start.getTime()) && 
								mycal.getEvents().get(i).getEndTime().getTime().after(start.getTime()) || //if the existing event's start time and end time is between the start time and end time of the given event
								mycal.getEvents().get(i).getStartTime().getTime().before(end.getTime()) &&
								mycal.getEvents().get(i).getEndTime().getTime().after(end.getTime())) { //if the existing event's start time and end time is between the start time and end time of the given event
							System.out.println("Your defined event conflicts with an existing event. Please try again.\n"); //error message
							flag = false; //sets flag to false and repeat the loop again
							break; //break loop so it doesn't have to check for more events
						}
					}	
					if(flag == true) //if there is no conflicts create the new event with user defined date and time
						mycal.getEvents().add(new Event(title, new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[0]) - 1, 
								Integer.parseInt(date[1]), Integer.parseInt(starttime[0]), Integer.parseInt(starttime[1])), 
								new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[0]) - 1, Integer.parseInt(date[1]), 
										Integer.parseInt(endtime[0]), Integer.parseInt(endtime[1]))));
					}	
				}
			else { //else if event does not have an end time
				for(int i = 0; i < mycal.getEvents().size(); i++) { //loop to check each existing event with given event
					if(start.getTime().equals(mycal.getEvents().get(i).getStartTime().getTime()) || //if start time of existing event is equal to the start time of a given event
							start.getTime().after(mycal.getEvents().get(i).getStartTime().getTime()) && 
							start.getTime().before(mycal.getEvents().get(i).getEndTime().getTime())) { //if the given event's start time is in between an existing event's start time and end time
						System.out.println("Your defined event conflicts with an existing event. Please try again.\n"); //error message
						flag = false; //sets flag to false and repeat the loop again
						break; //break loop so it doesn't have to check for more events
					}
				}
				
				if(flag == true) //if there is no conflicts create the new event with user defined date and time
					//create a new event with the end time defined as the start time (so the starting time is also the ending time)
					mycal.getEvents().add(new Event(title, new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[0]) - 1, 
							Integer.parseInt(date[1]), Integer.parseInt(starttime[0]), Integer.parseInt(starttime[1])), 
							new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[0]) - 1,
									Integer.parseInt(date[1]), Integer.parseInt(starttime[0]), Integer.parseInt(starttime[1]))));
			}
		} while(flag != true);	
		
		System.out.println("Success!\n"); //prints a success message if the loop is completed without any problems.
	}
	
	/**
	 * This method handles the deletion process of an event within a given date. It checks for any events within the given date and
	 * prints them to screen. User is then able to choose which event they would like to delete by the number of the event that is 
	 * stored in the list.
	 * @param mycal  MyCalendar object to create the calendar
	 * @return none
	 */
	public void DeleteAnEvent(MyCalendar mycal) {
		//method variable declarations
		String[] date;
		SimpleDateFormat dateformat;
		boolean flag;
		
		if(mycal.getEvents().isEmpty()) //if list is empty, print error message and return to menu
			System.out.println("ERROR: The list of events is empty.\n");
		else {
			System.out.println("Enter the date you want to delete:");
			System.out.println("(Format: MM/dd/yyyy)");
			System.out.print("Input: ");
			date = in.nextLine().split("/"); //splits the input into 3 separate Strings: Month, Day, and Year
			
			//create a temporary calendar with user defined date
			Calendar temp = new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[0]) - 1, Integer.parseInt(date[1]));
			dateformat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			flag = false; //to check if the given date has no events
		
			for(int i = 0; i < mycal.getEvents().size(); i++) { //loop to check each event in list
				if(temp.get(Calendar.YEAR) == mycal.getEvents().get(i).getStartTime().get(Calendar.YEAR) && //if current date's year, month, and day equals to the year, month, and day of an event
						temp.get(Calendar.MONTH) == mycal.getEvents().get(i).getStartTime().get(Calendar.MONTH) &&
						temp.get(Calendar.DAY_OF_MONTH) == mycal.getEvents().get(i).getStartTime().get(Calendar.DAY_OF_MONTH)) {
					System.out.println(i + " - " + dateformat.format(mycal.getEvents().get(i).getStartTime().getTime()) + " - " //print out the date of the event with the specified format
							+ dateformat.format(mycal.getEvents().get(i).getEndTime().getTime()) + ": " + mycal.getEvents().get(i).getEvent());
					flag = true;
				}
			}
		
			if(flag == false) //if the given date has no events, print error message
				System.out.println("ERROR: There are no events in the defined date.\n");
			else { //else, user input a number of the event shown on screen to delete the specific event
				System.out.println("Please enter the # of the event you would like to delete:");
				System.out.print("Input: ");
				mycal.getEvents().remove(in.nextInt());
				in.nextLine(); //clears keyboard buffer
				System.out.println("Delete complete.\n");
			}
		}
	}
	
	/**
	 * This method handles the deletion process of all event within a given date. It checks for any events within the given date and
	 * deletes each one until all of them are deleted.
	 * @param mycal  MyCalendar object to create the calendar
	 * @return none
	 */
	public void DeleteAllEvents(MyCalendar mycal) {
		//method variable declarations
		String[] date;
		boolean flag;
		
		if(mycal.getEvents().isEmpty()) //if list is empty, print error message and return to menu
			System.out.println("ERROR: The list of events is empty.\n");
		else {
			System.out.println("Enter the date you want to delete:");
			System.out.println("(Format: MM/dd/yyyy)");
			System.out.print("Input: ");
			date = in.nextLine().split("/"); //splits the input into 3 separate Strings: Month, Day, and Year
			//create a temporary calendar with user defined date
			Calendar temp = new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[0]) - 1, Integer.parseInt(date[1]));
			flag = false;
		
			for(int i = 0; i < mycal.getEvents().size(); i++) { //loop to check each event in list
				if(temp.get(Calendar.YEAR) == mycal.getEvents().get(i).getStartTime().get(Calendar.YEAR) && //if current date's year, month, and day equals to the year, month, and day of an event
						temp.get(Calendar.MONTH) == mycal.getEvents().get(i).getStartTime().get(Calendar.MONTH) &&
						temp.get(Calendar.DAY_OF_MONTH) == mycal.getEvents().get(i).getStartTime().get(Calendar.DAY_OF_MONTH)) {
					mycal.getEvents().remove(i); //delete the event and continue the checking process
					/* when the event is deleted, the event after the deleted event will be pointed and once i is increment,
					 * it will skip that event, decrement i so it points to the previous event. Then it will properly increment
					 * to the next event.
					 */
					i--;
					flag = true;
				}
			}	
			if(flag == false) //if the given date has no events, print error message
				System.out.println("ERROR: There are no events in the defined date.\n");
			else //else print success message
				System.out.println("Delete complete.\n");
		}
	}
	
	/**
	 * This method handles the display process of a given date. User is asked for a date and MyCalendar will send to screen
	 * the specified day in day view alongside any events that occurs in that day.
	 * @param mycal  MyCalendar object to create the calendar
	 * @return none
	 */
	public void GoTo(MyCalendar mycal) {
		//method variable declarations
		String[] date;;
		
		if(mycal.getEvents().isEmpty()) //if list is empty, print error message and return to menu
			System.out.println("ERROR: The list of events is empty.\n");
		else {
			date = new String[2];
		
			System.out.println("Enter the date you want to view:");
			System.out.println("(Format: MM/dd/yyyy)");
			System.out.print("Input: ");
			date = in.nextLine().split("/"); //splits the input into 3 separate Strings: Month, Day, and Year

			//call the CalendarByDay method with user defined date
			mycal.CalendarByDay(new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[0]) - 1, Integer.parseInt(date[1])));
		}
	}
	
	/**
	 * This method handles the display process of all defined events in the list/collection in chronological order.
	 * @param mycal  MyCalendar object to create the calendar
	 * @return none
	 */
	public void EventList(MyCalendar mycal) {
		if(mycal.getEvents().isEmpty()) //if list is empty, print error message and return to menu
			System.out.println("ERROR: The list of events is empty.\n");
		else {
			Comparator<Event> comp = new Comparator<Event>() { //create a new anonymous comparator to sort events by chronological order
				public int compare(Event event1, Event event2) {
					return event1.getStartTime().compareTo(event2.getStartTime());
				}
			};
		
			Collections.sort(mycal.getEvents(), comp); //sorting in chronological order
		
			if(mycal.getEvents().isEmpty() == false) //if the list is empty, do not display this format
				System.out.println("--------------------------------------------------");
		
			for(int i = 0; i < mycal.getEvents().size(); i++) {	//loop to check each event
				//print an event with specified format
				System.out.print(mycal.getDays()[mycal.getEvents().get(i).getStartTime().get(Calendar.DAY_OF_WEEK) - 1] + " " + 
						mycal.getMonths()[mycal.getEvents().get(i).getStartTime().get(Calendar.MONTH)] + " " + 
						mycal.getEvents().get(i).getStartTime().get(Calendar.DAY_OF_MONTH) + ", " + 
						mycal.getEvents().get(i).getStartTime().get(Calendar.YEAR) + ": " + mycal.getEvents().get(i).getEvent() + ", "
						+ time.format(mycal.getEvents().get(i).getStartTime().getTime()));
				if(mycal.getEvents().get(i).getStartTime().equals(mycal.getEvents().get(i).getEndTime())) //do not display the end time if the event does not have an end time
					System.out.print("\n");
				else
					System.out.print(" - " + time.format(mycal.getEvents().get(i).getEndTime().getTime()) + "\n"); //display the end time if it does
			}

			if(mycal.getEvents().isEmpty() == false) //if the list is empty, do not display this format
				System.out.println("--------------------------------------------------");
		}
	}
	
	/**
	 * This method loads in the list of events from a text file using serialization and populate them in the current list of events
	 * @param mycal  MyCalendar object to create the calendar
	 * @return none
	 */
	@SuppressWarnings("unchecked")
	public void Load(MyCalendar mycal) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("events.txt"));
			mycal.setEvents((ArrayList<Event>) in.readObject()); //read in the object from textfile by casting it to the object of choice and set it to the current list of events
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
	public void Save(MyCalendar mycal) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("events.txt"));
			out.writeObject(mycal.getEvents()); //write object to a text.file, serializing it
			out.close();
			System.out.println("Events have been saved.");
		} catch (IOException e) {
			System.out.println("IOException: Unable to save the events to a text file.");
		}
	}
}
