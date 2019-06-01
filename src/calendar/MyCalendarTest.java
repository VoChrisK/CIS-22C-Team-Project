/**
 * This class is the main method of Assignment 2. This class properly tests the other classes through a series of client interactions.
 * Switch functions are used to indicate multiple options and within each option lies a different functionality from the other classes.
 * @author Chris Vo
 * @Version 1.0
 */

package calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class MyCalendarTest {

	public static void main(String[] args) {
		//class variable declarations
		GregorianCalendar current = new GregorianCalendar(); //GregorianCalendar that stores the current date from the System
		MyCalendar mycal = new MyCalendar();
		MyCalendarFunctions functions = new MyCalendarFunctions();
		Scanner in = new Scanner(System.in);
		String choice1;
		String choice2;
		
		//method call indicating the initial screen showing the current date of the System and highlighting the current day
		mycal.CalendarByMonth(current);
		
		do {
			MenuInterface();
			choice1 = in.nextLine();
			
			//switch function to handle various MyCalendar functionalities based on user's input
			switch(choice1) {
				case "L": //[L]oad
					functions.Load(mycal); //to load the list of events from file into the current list of events
					break;
				case "V": //[V]iew By
					System.out.println("[D]ay View or [M]onth View");
					System.out.print("Input: ");
					choice2 = in.nextLine();
					if(choice2.equals("D") || choice2.equals("M")) //if input is either "D" or "M", else print error message
						//to view the calendar in either day format or month format. Also traverses through the calendar forward or backward
						ViewByOption(mycal, in, current, choice2);
					else
						System.out.println("Incorrect input! Please input a listed option between the brackets.\n");
					break;
				case "C": //[C]reate
					functions.Create(mycal); //to create a new event for the calendar and store it in the list of events
					break;
				case "G": //[G]o To
					functions.GoTo(mycal); //to go to a date and print it and its events based on user's defined date
					break;
				case "E": //[E]vent List
					functions.EventList(mycal); //to print the list of events in chronological order
					break;
				case "D": //[D]elete
					System.out.println("[S]elected (delete specified event) or [A]ll (delete all events in date)");
					System.out.print("Input: ");
					choice2 = in.nextLine();
					
					if(choice2.equals("S"))
						functions.DeleteAnEvent(mycal); //to delete a specific event in a chosen date
					else if(choice2.equals("A"))
						functions.DeleteAllEvents(mycal); //to delete all the events in a chosen date
					else
						System.out.println("Incorrect input! Please input a listed option between the brackets.\n");
					break;
				case "Q": //[Q]uit
					functions.Save(mycal);
					System.out.println("Exiting the program...");
					break;
				default: //prints error message if input other than the options is entered
					System.out.println("Incorrect input! Please input a listed option between the brackets.\n");
			}
		} while(choice1.equals("Q") == false);
		
		in.close();
	}
	
	/**
	 * This method is used to print the menu interface each time it is invoked.
	 * @param none
	 * @return none
	 */
	private static void MenuInterface() {
		System.out.println("Select one of the following options:");
		System.out.println("[L]oad | [V]iew | [C]reate | [G]o to | [E]vent List | [D]elete | [Q]uit");
		System.out.print("Input: ");
	}
	
	/**
	 * This method handles the traversal process in the View By option. The switch function is used to go forwards or
	 * go backwards in the calendar depending on user's input
	 * @param mycal    MyCalendar object to create the calendar
	 * @param in       to read in user input
	 * @param current  a calendar object that stores the current (and modified through traversal) date
	 * @param choice2  user input to choose to print the calendar in day view or month view
	 * @return none
	 */
	private static void ViewByOption(MyCalendar mycal, Scanner in, Calendar current, String choice2) {
		//method variable declaration
		String choice;
		
		//print the calendar once, highlighting the current date, before user begins the traversal process
		if(choice2.equals("M")) {
			mycal.CalendarByMonth(current);
			System.out.println("- Note that curly brackets represents event(s) within the date and square brackets represent no event within the date.");
		}
		else
			mycal.CalendarByDay(current);
		
		do {
			System.out.println("[P]revious | [N]ext | [M]ain Menu");
			System.out.print("Input: ");
			choice = in.nextLine();
		
			switch(choice) {
				case "P":
					mycal.getPrev(current, choice2); //to traverse the calendar backward by one day
					break;
				case "N":
					mycal.getNext(current, choice2); //to traverse the calendar forward by one day
					break;
				case "M":
					System.out.println("Exiting...");
					break;
				default:
					System.out.println("Incorrect input! Please input a listed option between the brackets.\n");
			}
		} while(choice.equals("M") == false);
	}
}
