package de.tuxchan;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.*;

public class Mathematics extends JFrame {
	private Container c;
	private JTabbedPane tp;
	private Date time;
	private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss 'Uhr'");
	private JToolBar tb;
	private JLabel showTime;
	private Thread timeThread;
	
	// Tab 1: Primzahlen
	private JPanel prims;
	private JLabel s, e;                 // helptexts: s for start and e for end
	private JTextField start, end;
	private JButton calc;
	private JTextArea code;
	private JScrollPane sp;
	
	// fonts, etc.
	private final Font font = new Font("Courier New", Font.PLAIN, 16);
	
	private class TimeThread extends Thread {
		
		public void run() {
			boolean run = true;
			while(run) {
				if(isInterrupted()) {
					run = false;
				}
				
				time = new Date();
				showTime.setText(sdf.format(time));
				
				try {
					sleep(100);
				}
				catch(InterruptedException e) {
					
				}
			}
		}
	}
	
	private class ClickButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == calc || e.getSource() == start || e.getSource() == end) {
				try {
					int von = Integer.parseInt(start.getText());
					int bis = Integer.parseInt(end.getText());
					
					code.setText(primList(von, bis));
				}
				catch(NumberFormatException exp) {
					JOptionPane.showMessageDialog(c, "Just numbers!");
				}
			}
		}
	}
	
	public Mathematics() {
		c = getContentPane();
		
		// Set GUI
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
		}
		
		tp = new JTabbedPane();
		
		// Initialize JToolBar below
		tb = new JToolBar("Clock");
		
		time = new Date();
		
		showTime = new JLabel(sdf.format(time));
		showTime.setFont(font);
		
		tb.add(Box.createGlue());
		tb.add(showTime);
		
		timeThread = new TimeThread();
		timeThread.start();
		
		// Initialize Tab 1 "Primzahlen"
		prims = new JPanel(new GridLayout(3, 1, 5, 5));
		
		
		// Panel for start, end and the help texts.
		JPanel intervallInputs = new JPanel(new GridLayout(2, 2));
		
		s = new JLabel("From: ");
		s.setHorizontalAlignment(JLabel.CENTER);
		s.setFont(font);
		
		start = new JTextField();
		start.setFont(font);
		start.addActionListener(new ClickButton());
		
		e = new JLabel("To: ");
		e.setHorizontalAlignment(JLabel.CENTER);
		e.setFont(font);
		
		end = new JTextField();
		end.setFont(font);
		end.addActionListener(new ClickButton());
		
		intervallInputs.add(s);
		intervallInputs.add(start);
		intervallInputs.add(e);
		intervallInputs.add(end);
		
		calc = new JButton("Calculate prims");
		calc.setFont(font);
		calc.addActionListener(new ClickButton());
		
		code = new JTextArea();
		code.setLineWrap(true);
		code.setWrapStyleWord(true);
		code.setFont(font);
		sp = new JScrollPane(code);
		
		prims.add(intervallInputs);
		prims.add(calc);
		prims.add(sp);
		
		tp.addTab("Prims", prims);
		
		// add tabs to GUI
		
		c.add(BorderLayout.CENTER, tp);
		c.add(BorderLayout.SOUTH, tb);
		
	}		
	
	public static boolean isInteger(double zahl) {
		int iZahl = (int) zahl;
		
		if((zahl - iZahl) != 0.0) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isEven(double zahl) {
				
		int iZahl = (int) zahl;
		
		if((iZahl % 2) == 0) {
			return true;
		}
		
		return false;
	}
	
	public static double fermat(double n) {
		double a = Math.ceil(Math.sqrt(n));
		double bSquared = Math.pow(a, 2) - n;
		
		if(isEven(n)) {
			return 2.0;
		}
		else {
			while(!isInteger(Math.sqrt(bSquared))) {
				a++;
				bSquared = Math.pow(a, 2) - n;
			}
		}
		
		return (a - Math.sqrt(bSquared));
	}
	
	public static boolean isPrim(double n) {
		
		if(fermat(n) == 1) {
			if(n == 1)
				return false;
			else
				return true;
		}
		else if(n == 2) {
			return true;
		}
		
		return false;
	}
	
	public static String primList(int von, int bis) {
		String primzahlen = "";
		
		if(von > bis) {
			return "";
		}
		
		for(int i = von; i <= bis; i++) {
			if(isPrim(i)) {
				primzahlen += i + " ";
			}
		}
		
		return primzahlen;
	}
	
	public static void main (String[] args) {
		Mathematics window = new Mathematics();
		window.setTitle("Mathematics");
		window.setSize(750, 400);
		window.setResizable(false);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}