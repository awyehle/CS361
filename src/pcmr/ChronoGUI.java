package pcmr;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JLabel;

public class ChronoGUI {
	
	private  Chronotimer _chrono;

	private JFrame frame;
	private JTextField txtChronotimer;
	private JTextArea mainTextArea;
	private JTextArea printerTextArea;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	private JRadioButton radioButton_3;
	private JRadioButton radioButton_4;
	private JRadioButton radioButton_5;
	private JRadioButton radioButton_6;
	private JRadioButton radioButton_7;
	private boolean functionBool = false;
	private String[] mainDisplay = new String[11];
	private String[] functionDisplay = new String[11];
	private boolean power = false;
	private Thread updaterThread;
	private int functionLine;
	private int functionLength;
	private boolean event = false;
	private int numLength;
	private boolean num = false;
	private boolean time = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChronoGUI window = new ChronoGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChronoGUI() {
		_chrono =  new Chronotimer();
		initialize();
	}
	
	public ChronoGUI(Chronotimer chrono) {
		_chrono = chrono;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(700, 500);
		frame.setResizable(false);
		frame.setTitle("ChronoTimer");
		//frame.setBounds(100, 100, 450, 300);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		mainTextArea = new JTextArea();
		mainTextArea.setBounds(190, 221, 239, 177);
		frame.getContentPane().add(mainTextArea);
		mainTextArea.setRows(10);
		mainTextArea.setText("\n\n\n\n\n\n\n\n\n\n");
		mainTextArea.setEditable(false);
		
		printerTextArea = new JTextArea();
		printerTextArea.setBounds(488, 43, 130, 133);
		frame.getContentPane().add(printerTextArea);
		printerTextArea.setRows(7);
		printerTextArea.setText("\n\n\n\n\n\n\n");
		printerTextArea.setEditable(false);
		
		/* This is an example of how all buttons and action listeners
		 * should be implemented
		 * ~Steven
		 */
		JButton btnPower = new JButton("Power");
		btnPower.setBounds(10, 11, 101, 23);
		frame.getContentPane().add(btnPower);
		btnPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] commandArray = new String[2];
				commandArray[0] = "-";
				commandArray[1] = "POWER";
				_chrono.runCommand(commandArray);
				power = !power;
				if(!power) {
					clearMainDisplay();
					functionReset();
				}
				threader();
				updaterThread.start();
			}
		});
				
		
		// End Power button example
		
		//Begin Printer Power
		
		JButton btnPrinterPwr = new JButton("Printer Pwr");
		btnPrinterPwr.setBounds(502, 11, 101, 23);
		frame.getContentPane().add(btnPrinterPwr);
		btnPrinterPwr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_chrono.powerPrinter();
			}
		});
				
		//end printer
		
		txtChronotimer = new JTextField(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override public void setBorder(Border border) {
		        // No!
		    }
		};
		txtChronotimer.setBackground(SystemColor.control);
		txtChronotimer.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtChronotimer.setHorizontalAlignment(SwingConstants.CENTER);
		txtChronotimer.setText("CHRONOTIMER 1009");
		txtChronotimer.setBounds(228, 12, 148, 20);
		frame.getContentPane().add(txtChronotimer);
		txtChronotimer.setColumns(10);
		txtChronotimer.setEditable(false);
		
		// Begin Function Button
		
		JButton btnFunction = new JButton("Function");
		btnFunction.setBounds(10, 250, 101, 23);
		frame.getContentPane().add(btnFunction);
		btnFunction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				functionBtn();
			}
		});
		setFunctionDisplay();
		
		// End Function Button
		
		JButton btnSwap = new JButton("Swap");
		btnSwap.setBounds(10, 375, 101, 23);
		frame.getContentPane().add(btnSwap);
		btnSwap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_chrono.runCommand("-", "SWAP");
			}
		});
		
		JButton button = new JButton("1");
		button.setBounds(488, 221, 41, 45);
		frame.getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numButtons(1);
			}
		});
		
		JButton button_9 = new JButton("*");
		button_9.setBounds(488, 353, 41, 45);
		frame.getContentPane().add(button_9);
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numButtons(10);
			}
		});
		
		JButton button_10 = new JButton("0");
		button_10.setBounds(527, 353, 41, 45);
		frame.getContentPane().add(button_10);
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numButtons(0);
			}
		});
		
		JButton button_11 = new JButton("#");
		button_11.setBounds(562, 353, 41, 45);
		frame.getContentPane().add(button_11);
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numButtons(11);
			}
		});
		
		JButton button_12 = new JButton("");
		button_12.setBounds(238, 48, 27, 23);
		frame.getContentPane().add(button_12);
		
		JButton button_13 = new JButton("");
		button_13.setBounds(275, 48, 27, 23);
		frame.getContentPane().add(button_13);
		
		JButton button_14 = new JButton("");
		button_14.setBounds(312, 48, 27, 23);
		frame.getContentPane().add(button_14);
		
		JButton button_15 = new JButton("");
		button_15.setBounds(349, 48, 27, 23);
		frame.getContentPane().add(button_15);
		
		JButton button_16 = new JButton("");
		button_16.setBounds(238, 123, 27, 23);
		frame.getContentPane().add(button_16);
		
		JButton button_17 = new JButton("");
		button_17.setBounds(275, 123, 27, 23);
		frame.getContentPane().add(button_17);
		
		JButton button_18 = new JButton("");
		button_18.setBounds(312, 123, 27, 23);
		frame.getContentPane().add(button_18);
		
		JButton button_19 = new JButton("");
		button_19.setBounds(349, 123, 27, 23);
		frame.getContentPane().add(button_19);
		
		radioButton = new JRadioButton("");
		radioButton.setBounds(239, 78, 27, 23);
		frame.getContentPane().add(radioButton);
		
		radioButton_1 = new JRadioButton("");
		radioButton_1.setBounds(275, 78, 27, 23);
		frame.getContentPane().add(radioButton_1);
		
		radioButton_2 = new JRadioButton("");
		radioButton_2.setBounds(312, 78, 27, 23);
		frame.getContentPane().add(radioButton_2);
		
		radioButton_3 = new JRadioButton("");
		radioButton_3.setBounds(349, 78, 27, 23);
		frame.getContentPane().add(radioButton_3);
		
		radioButton_4 = new JRadioButton("");
		radioButton_4.setBounds(238, 153, 27, 23);
		frame.getContentPane().add(radioButton_4);
		
		radioButton_5 = new JRadioButton("");
		radioButton_5.setBounds(275, 153, 27, 23);
		frame.getContentPane().add(radioButton_5);
		
		radioButton_6 = new JRadioButton("");
		radioButton_6.setBounds(312, 153, 27, 23);
		frame.getContentPane().add(radioButton_6);
		
		radioButton_7 = new JRadioButton("");
		radioButton_7.setBounds(349, 153, 27, 23);
		frame.getContentPane().add(radioButton_7);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(474, 78, 157, 120);
		frame.getContentPane().add(panel);
		
		JButton button_20 = new JButton("<");
		button_20.setBounds(10, 284, 50, 23);
		frame.getContentPane().add(button_20);
		button_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton button_21 = new JButton(">");
		button_21.setBounds(72, 284, 50, 23);
		frame.getContentPane().add(button_21);
		button_21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton button_22 = new JButton("^");
		button_22.setBounds(10, 320, 50, 23);
		frame.getContentPane().add(button_22);
		button_22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upBtn();
			}
		});
		
		JButton button_23 = new JButton("\u02C5");
		button_23.setBounds(72, 320, 50, 23);
		frame.getContentPane().add(button_23);
		button_23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downBtn();
			}
		});
		
		JButton button_1 = new JButton("2");
		button_1.setBounds(527, 221, 41, 45);
		frame.getContentPane().add(button_1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numButtons(2);
			}
		});
		
		JButton button_2 = new JButton("3");
		button_2.setBounds(562, 221, 41, 45);
		frame.getContentPane().add(button_2);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numButtons(3);
			}
		});
		
		JButton button_3 = new JButton("4");
		button_3.setBounds(488, 265, 41, 45);
		frame.getContentPane().add(button_3);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numButtons(4);
			}
		});
		
		JButton button_4 = new JButton("5");
		button_4.setBounds(527, 265, 41, 45);
		frame.getContentPane().add(button_4);
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numButtons(5);
			}
		});
		
		JButton button_5 = new JButton("6");
		button_5.setBounds(562, 265, 41, 45);
		frame.getContentPane().add(button_5);
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numButtons(6);
			}
		});
		
		JButton button_6 = new JButton("7");
		button_6.setBounds(488, 309, 41, 45);
		frame.getContentPane().add(button_6);
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numButtons(7);
			}
		});
		
		JButton button_7 = new JButton("8");
		button_7.setBounds(527, 309, 41, 45);
		frame.getContentPane().add(button_7);
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numButtons(8);
			}
		});
		
		JButton button_8 = new JButton("9");
		button_8.setBounds(562, 309, 41, 45);
		frame.getContentPane().add(button_8);
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numButtons(9);
			}
		});
		
		JLabel lblStart = new JLabel("Start");
		lblStart.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStart.setBounds(189, 57, 39, 14);
		frame.getContentPane().add(lblStart);
		
		JLabel lblFinish = new JLabel("Finish");
		lblFinish.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFinish.setBounds(190, 132, 39, 14);
		frame.getContentPane().add(lblFinish);
		
		JLabel label = new JLabel("Enable/Disable");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setBounds(132, 162, 90, 14);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Enable/Disable");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_1.setBounds(132, 87, 90, 14);
		frame.getContentPane().add(label_1);
		
		textField_2 = new JTextField() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void setBorder(Border border) {
			}
		};
		textField_2.setText("1");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		textField_2.setColumns(10);
		textField_2.setBackground(SystemColor.menu);
		textField_2.setBounds(238, 32, 27, 14);
		frame.getContentPane().add(textField_2);
		
		textField_3 = new JTextField() {
			private static final long serialVersionUID = 1L;

			public void setBorder(Border border) {
			}
		};
		textField_3.setText("3");
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		textField_3.setColumns(10);
		textField_3.setBackground(SystemColor.menu);
		textField_3.setBounds(275, 32, 27, 14);
		frame.getContentPane().add(textField_3);
		
		textField_4 = new JTextField() {
			private static final long serialVersionUID = 1L;

			public void setBorder(Border border) {
			}
		};
		textField_4.setText("5");
		textField_4.setHorizontalAlignment(SwingConstants.CENTER);
		textField_4.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		textField_4.setColumns(10);
		textField_4.setBackground(SystemColor.menu);
		textField_4.setBounds(312, 32, 27, 14);
		frame.getContentPane().add(textField_4);
		
		textField_5 = new JTextField() {
			private static final long serialVersionUID = 1L;

			public void setBorder(Border border) {
			}
		};
		textField_5.setText("7");
		textField_5.setHorizontalAlignment(SwingConstants.CENTER);
		textField_5.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		textField_5.setColumns(10);
		textField_5.setBackground(SystemColor.menu);
		textField_5.setBounds(349, 32, 27, 14);
		frame.getContentPane().add(textField_5);
		
		textField_6 = new JTextField() {
			private static final long serialVersionUID = 1L;

			public void setBorder(Border border) {
			}
		};
		textField_6.setText("4");
		textField_6.setHorizontalAlignment(SwingConstants.CENTER);
		textField_6.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		textField_6.setColumns(10);
		textField_6.setBackground(SystemColor.menu);
		textField_6.setBounds(275, 108, 27, 14);
		frame.getContentPane().add(textField_6);
		
		textField_7 = new JTextField() {
			private static final long serialVersionUID = 1L;

			public void setBorder(Border border) {
			}
		};
		textField_7.setText("6");
		textField_7.setHorizontalAlignment(SwingConstants.CENTER);
		textField_7.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		textField_7.setColumns(10);
		textField_7.setBackground(SystemColor.menu);
		textField_7.setBounds(312, 108, 27, 14);
		frame.getContentPane().add(textField_7);
		
		textField_8 = new JTextField() {
			private static final long serialVersionUID = 1L;

			public void setBorder(Border border) {
			}
		};
		textField_8.setText("8");
		textField_8.setHorizontalAlignment(SwingConstants.CENTER);
		textField_8.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		textField_8.setColumns(10);
		textField_8.setBackground(SystemColor.menu);
		textField_8.setBounds(349, 108, 27, 14);
		frame.getContentPane().add(textField_8);
		
		textField_9 = new JTextField() {
			private static final long serialVersionUID = 1L;
			public void setBorder(Border border) {
			}
		};
		textField_9.setText("2");
		textField_9.setHorizontalAlignment(SwingConstants.CENTER);
		textField_9.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		textField_9.setColumns(10);
		textField_9.setBackground(SystemColor.menu);
		textField_9.setBounds(238, 108, 27, 14);
		frame.getContentPane().add(textField_9);
	}
	
	private String createMainTextString(String[] stringArray) {
		String output = "";
		for(String s: stringArray) {
			if(s == null) s = "";
			output = output + s + "\n";
		}
		output = output.substring(0, output.length() - 2);
		return output;
	}
	
	private void clearMainDisplay() {
		mainTextArea.setText("\n\n\n\n\n\n\n\n\n\n");
	}

	public void addPrinterLine(String s) {
	    int lineCount = printerTextArea.getLineCount();

	    if (lineCount <= printerTextArea.getRows()) {                
	        printerTextArea.append(s + "\n");    
	    } else if (lineCount > printerTextArea.getRows()) {

	        String output = printerTextArea.getText() + "\n" + s;    
	        int begin = output.indexOf("\n");    
	        output = output.substring(begin + 1);    
	        printerTextArea.setText(output);    
	    }
	}
	
	// Start FUNCTION functionality ------------------------------------------------------------
	
	private void functionReset() {
		functionBool = false;
		setFunctionDisplay();
		functionLine = 0;
		functionLength = 0;
		event = false;
		numLength = 0;
		num = false;
		time = false;
	}
	private void functionBtn() {
		if(!power) return;
		if(functionBool) {
			if(num) return;
			if(functionLine == 5) {
				event = true;
				eventDisplay();
				return;
			}
			function(event, functionLine);
		}
		else {
			functionBool = true;
			functionLine = 0;
			setFunctionDisplay();
			mainTextArea.setText(createMainTextString(functionDisplay));
		}
	}
	
	private void functionReturn() {
		event = false;
		functionBool = false;
		mainTextArea.setText(createMainTextString(mainDisplay));
	}
	
	private void eventDisplay() {
		functionLine = 0;
		functionLength = 4;
		functionDisplay[0] = "* IND";
		functionDisplay[1] = "  PARIND";
		functionDisplay[2] = "  GRP";
		functionDisplay[3] = "  PARGRP (Not Implemented)";
		for(int i = 4; i < 10; ++i) {
			functionDisplay[i] = "";
		}
		mainTextArea.setText(createMainTextString(functionDisplay));
	}
	
	private void function(boolean event, int funID) {
		if(event) {
			switch(funID) {
			case(1):{
				_chrono.runCommand("-", "EVENT", "PARIND");
				functionReturn();
				break;
			}
			case(2):{
				_chrono.runCommand("-", "EVENT", "GRP");
				functionReturn();
				break;
			}
			case(3):{
				_chrono.runCommand("-", "EVENT", "PARGRP");
				functionReturn();
				break;
			}
			default:{
				_chrono.runCommand("-", "EVENT", "IND");
				functionReturn();
				break;
			}
			}
		}
		else {
			switch(funID) {
			case(0): {
				_chrono.runCommand("-","DNF");
				functionReturn();
				break;
			}
			case(1): {
				for(int i = 0; i < 10; ++i) {
					functionDisplay[i] = "";
				}
				functionDisplay[0] = "Enter a racer number";
				functionDisplay[1] = "# to Enter, * to Clear";
				functionDisplay[2] = "bib: ";
				numLength = 0;
				num = true;
				mainTextArea.setText(createMainTextString(functionDisplay));
				break;
			}
			case(2):{
				_chrono.runCommand("-", "CANCEL");
				functionReturn();
				break;
			}
			case(3): {
				_chrono.runCommand("-", "NEWRUN");
				functionReturn();
				break;
			}
			case(4): {
				_chrono.runCommand("-", "ENDRUN");
				functionReturn();
				break;
			}
			case(6): {
				for(int i = 0; i < 10; ++i) {
					functionDisplay[i] = "";
				}
				functionDisplay[0] = "Enter a mm:ss.ms Timestamp";
				functionDisplay[1] = "# to Enter, * to Clear";
				functionDisplay[2] = "Time: ";
				numLength = 0;
				num = true;
				time = true;
				mainTextArea.setText(createMainTextString(functionDisplay));
				break;
			}
			case(7): {
				_chrono.runCommand("-", "EXPORT");
				functionReturn();
				break;
			}
			case(8): {
				_chrono.runCommand("-", "RESET");
				functionReturn();
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
				radioButton_5.setSelected(false);
				radioButton_6.setSelected(false);
				radioButton_7.setSelected(false);
				break;
			}
			case(9): {
				functionReturn();
				break;
			}
			case(10): {
				_chrono.runCommand("-", "NUM", functionDisplay[2].substring(5));
				num = false;
				functionReturn();
				break;
			}
			case(11): {
				_chrono.runCommand("-", "TIME", functionDisplay[2].substring(6));
				num = false;
				time = false;
				functionReturn();
				break;
			}
			default:
				break;
			}
		}
	}
	
	private void numButtons(int i) {
		if((num && time && (numLength < 8 || i == 10 || i == 11)) || (num && !time && (numLength < 4 || i == 10 || i == 11))) {
			++numLength;
			if(i == 10) {
				if(!time)functionDisplay[2] = "Bib: ";
				else functionDisplay[2] = "Time: ";
				numLength = 0;
			}
			else if(time && numLength == 3) {
				if(i < 10) functionDisplay[2] =  functionDisplay[2] + ":" + i;
				++numLength;
			}
			else if(time && numLength == 6) {
				if(i < 10) functionDisplay[2] =  functionDisplay[2] + "." + i;
				++numLength;
			}
			else if(!time && i == 11) {
				function(false, 10);
				numLength = 0;
			}
			else if(time && i == 11) {
				function(false, 11);
				numLength = 0;
			}
			else if(i < 10) functionDisplay[2] =  functionDisplay[2] + i;
			mainTextArea.setText(createMainTextString(functionDisplay));
		}
	}
	
	private void setFunctionDisplay() {
		functionLength = 10;
		functionDisplay[0] = "* DNF";
		functionDisplay[1] = "  NUM";
		functionDisplay[2] = "  CANCEL";
		functionDisplay[3] = "  NEWRUN";
		functionDisplay[4] = "  ENDRUN";
		functionDisplay[5] = "  EVENT";
		functionDisplay[6] = "  TIME";
		functionDisplay[7] = "  EXPORT";
		functionDisplay[8] = "  RESET";
		functionDisplay[9] = "  EXIT Function Screen";
	}
	
	private void upBtn() {
		if(!num && !time && functionBool && functionLine > 0) {
			functionDisplay[functionLine] = " " + functionDisplay[functionLine].substring(1, functionDisplay[functionLine].length());
			functionDisplay[functionLine-1] = "*" + functionDisplay[functionLine-1].substring(1, functionDisplay[functionLine-1].length());
			mainTextArea.setText(createMainTextString(functionDisplay));
			-- functionLine;
		}
	}
	
	private void downBtn() {
		if(!num && !time && functionBool && functionLine < functionLength -1) {
		functionDisplay[functionLine] = " " + functionDisplay[functionLine].substring(1, functionDisplay[functionLine].length());
		functionDisplay[functionLine+1] = "*" + functionDisplay[functionLine+1].substring(1, functionDisplay[functionLine+1].length());
		mainTextArea.setText(createMainTextString(functionDisplay));
		++ functionLine;
		}
	}
	
	// End FUNCTION functionality ---------------------------------------------------------------------
	
	/** This method gets the latest display data from {@link pcmr.Chronotimer} including a queue of racers, current racer(s)
	 *  in the race, and the last finish(es) depending on the event (more information in Sprint 3 document at bottom of page).
	 *  At most, 3 lines are dedicated to the race queue, 2 lines to the current racers, and 2 lines to the last finisher(s)
	 *  with spaces between the categories. The data is loaded into the {@link #mainDisplay} variable and processed with
	 *  {@link #createMainTextString(String[])}
	 * 
	 */
	private void updateDisplay() {
		// TODO: Chronotimer needs a getDisplay() method
		// or this method needs to get individual data from chronotimer and update the mainDisplay array, then add this line
		// mainTextArea.setText(createMainTextString(mainDisplay)); and remove the next line if this line was used
		// mainDisplay = _chrono.getDisplay();
		if(!functionBool) mainTextArea.setText(createMainTextString(mainDisplay));
	}
	
	/** This method gets the latest printer data from {@link pcmr.Chronotimer} (which should be stored in ArrayList format)
	 *  any new entries to the ArrayList should be added on a line by line basis in order from oldest to newest
	 *  with {@link #addPrinterLine(String)}
	 */
	private void updatePrinter() {
		//TODO: Chronotimer needs a getPrinterFeed() method
		// or this method needs to get printer things from chronotimer in some other way and add them to printer with addPrinterLine(String s);
	}
	
	/** This method is called from the separate {@link #threader()} thread
	 */
	private void update() {
		updateDisplay();
		updatePrinter();
	}
	/** This method creates a new thread that starts with the power button
	 * It calls {@link #update()} every 1 millisecond
	 */
	private void threader() {
		updaterThread = new Thread() {
			public void run() {
				while(power) {
					update();
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					}
				}
				
			}
		};
	}
}
