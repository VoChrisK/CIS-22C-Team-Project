package project4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class contains the main method. It tests the functionalities of all the other classes. Additionally,
 * it creates a frame and appropriate buttons to handle several addition functionalities.
 * @authors Chris Vo
 * @Version 1.0
 */
public class SimpleCalendar {

	public static void main(String[] args) {
		final int WIDTH = 1000;
		final int HEIGHT = 800;
		CalendarModel model = new CalendarModel();
		model.Load(); //load the events in the beginning if any
		CalendarController calendar = new CalendarController(new CalendarView(model));
		
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		JButton previous = new JButton("<");
		JButton next = new JButton(">");
		JButton create = new JButton("Create");
		JPanel buttonpanel = new JPanel();
		create.setBackground(Color.RED);
		JButton quit = new JButton("Quit");
		quit.setBackground(Color.WHITE);
		
		ChangeListener changeday = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				calendar.repaint();
			}
		};
		
		previous.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.setCalendarDay(model.getCalendarDay() - 1);
			}
			
		});
		
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.setCalendarDay(model.getCalendarDay() + 1);
			}
			
		});
		
		//the create ActionListener will create a new frame, letting the user create a new event
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				create.setEnabled(false);
				JFrame frame2 = new JFrame();
				frame2.setSize(WIDTH - 400, HEIGHT - 650);
				frame2.setLocation(WIDTH/2, HEIGHT/2);
				JTextField text = new JTextField("Untitled Event");
				Font font = new Font("Serif", Font.BOLD, 28);
				text.setFont(font);
				JTextField date = new JTextField(model.getCalendar().get(Calendar.MONTH) + "/" + model.getCalendar().get(Calendar.DAY_OF_MONTH) + "/" + Integer.toString(model.getCalendar().get(Calendar.YEAR)).substring(2,4));
				date.setEditable(false);
				date.setSize(50, 50);
				date.setFont(font);
				JTextField start = new JTextField("start time");
				start.setSize(50, 50);
				start.setFont(font);
				JTextField to = new JTextField("to");
				to.setEditable(false);
				JTextField end = new JTextField("end time");
				end.setSize(50, 50);
				end.setFont(font);
				JButton save = new JButton("Save");
				save.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						JFrame frame3 = new JFrame();
						frame3.setLocation(WIDTH/2, HEIGHT/2);
						JTextArea message = new JTextArea();
						message.setFont(new Font("Serif", Font.BOLD, 28));
						message.setEnabled(false);
						if(model.Create(text.getText(), start.getText(), end.getText())) {
							message.setText("Successfully created a new event into the calendar!");
							frame3.add(message);
						}
						else {
							message.setText("Error: Conflicting event. Please try again!");
							frame3.add(message);
						}
						
						frame3.pack();
						frame3.setVisible(true);
						frame2.dispose();
						create.setEnabled(true);
					}
					
				});
				
				JPanel panel2 = new JPanel();
				panel2.add(date);
				panel2.add(start);
				panel2.add(to);
				panel2.add(end);
				panel2.add(save);
				frame2.add(text, BorderLayout.NORTH);
				frame2.add(panel2, BorderLayout.CENTER);
				frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame2.setVisible(true);
			}
		
		});
		
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.Save(); //saves the events to a text file before ending the program
				frame.dispose();
			}
		
		});
		
		model.AddListener(changeday);
		frame.add(calendar);
		buttonpanel.add(previous);
		buttonpanel.add(next);
		buttonpanel.add(create);
		buttonpanel.add(quit);
		frame.add(buttonpanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
