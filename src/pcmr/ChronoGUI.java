package pcmr;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import data.Time;

import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

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
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	private JRadioButton radioButton_3;
	private JRadioButton radioButton_4;
	private JRadioButton radioButton_5;
	private JRadioButton radioButton_6;
	private JRadioButton radioButton_7;
	private JRadioButton radioButton_8;
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
	private JTextField txtUsbPort;

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
		frame.setSize(700, 550);
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
		printerTextArea.setFont(new Font("Times New Roman", Font.PLAIN, 10));
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
		
		JButton Channel1 = new JButton("");
		Channel1.setBounds(238, 48, 27, 23);
		frame.getContentPane().add(Channel1);
		
		class Channel1 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] commandArray = new String[3];
				commandArray[0] = new Time().convertRawTime();
				commandArray[1] = "TRIG";
				commandArray[2] = "1";
				_chrono.runCommand(commandArray);
			
			}
			
			
		}
		Channel1 channel1Listener = new Channel1();
		Channel1.addActionListener(channel1Listener);
		
		
		JButton Channel3 = new JButton("");
		Channel3.setBounds(275, 48, 27, 23);
		frame.getContentPane().add(Channel3);
		
		class Channel3 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] commandArray = new String[3];
				commandArray[0] = new Time().convertRawTime();
				commandArray[1] = "TRIG";
				commandArray[2] = "3";
				_chrono.runCommand(commandArray);
			}
			
		}
		Channel3 channel3Listener = new Channel3();
		Channel3.addActionListener(channel3Listener);
		
		
		JButton Channel5 = new JButton("");
		Channel5.setBounds(312, 48, 27, 23);
		frame.getContentPane().add(Channel5);
		
		class Channel5 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] commandArray = new String[3];
				commandArray[0] = new Time().convertRawTime();
				commandArray[1] = "TRIG";
				commandArray[2] = "5";
				_chrono.runCommand(commandArray);
			}
			
		}
		Channel5 channel5Listener = new Channel5();
		Channel5.addActionListener(channel5Listener);
		
		
		JButton Channel7 = new JButton("");
		Channel7.setBounds(349, 48, 27, 23);
		frame.getContentPane().add(Channel7);
		
		class Channel7 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] commandArray = new String[3];
				commandArray[0] = new Time().convertRawTime();
				commandArray[1] = "TRIG";
				commandArray[2] = "7";
				_chrono.runCommand(commandArray);
			}
			
		}
		Channel7 channel7Listener = new Channel7();
		Channel7.addActionListener(channel7Listener);
		
		
		
		JButton Channel2 = new JButton("");
		Channel2.setBounds(238, 123, 27, 23);
		frame.getContentPane().add(Channel2);
		
		class Channel2 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] commandArray = new String[3];
				commandArray[0] = new Time().convertRawTime();
				commandArray[1] = "TRIG";
				commandArray[2] = "2";
				_chrono.runCommand(commandArray);
			}
			
		}
		Channel2 channel2Listener = new Channel2();
		Channel2.addActionListener(channel2Listener);
		
		JButton Channel4 = new JButton("");
		Channel4.setBounds(275, 123, 27, 23);
		frame.getContentPane().add(Channel4);
		
		class Channel4 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] commandArray = new String[3];
				commandArray[0] = new Time().convertRawTime();
				commandArray[1] = "TRIG";
				commandArray[2] = "4";
				_chrono.runCommand(commandArray);
			}
			
		}
		
		Channel4 channel4Listener = new Channel4();
		Channel4.addActionListener(channel4Listener);
		
		
		JButton Channel6 = new JButton("");
		Channel6.setBounds(312, 123, 27, 23);
		frame.getContentPane().add(Channel6);
		
		class Channel6 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] commandArray = new String[3];
				commandArray[0] = new Time().convertRawTime();
				commandArray[1] = "TRIG";
				commandArray[2] = "6";
				_chrono.runCommand(commandArray);
			}
			
		}
		
		Channel6 channel6Listener = new Channel6();
		Channel6.addActionListener(channel6Listener);
		
		JButton Channel8 = new JButton("");
		Channel8.setBounds(349, 123, 27, 23);
		frame.getContentPane().add(Channel8);
		
		class Channel8 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] commandArray = new String[3];
				commandArray[0] = new Time().convertRawTime();
				commandArray[1] = "TRIG";
				commandArray[2] = "8";
				_chrono.runCommand(commandArray);
			}
			
		}
		
		Channel8 channel8Listener = new Channel8();
		Channel8.addActionListener(channel8Listener);
		

		
		radioButton_1 = new JRadioButton("");
		radioButton_1.setBounds(239, 78, 27, 23);
		frame.getContentPane().add(radioButton_1);
		
		class radioButton_1 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!_chrono.isOn()) radioButton_1.setSelected(false);
				String[] commandArray = new String[3];
				commandArray[0] = "-";
				commandArray[1] = "TOG";
				commandArray[2] = "1";
				_chrono.runCommand(commandArray);
				}
				
		}
		
		radioButton_1 radioButton_1Listener = new radioButton_1();
		radioButton_1.addActionListener(radioButton_1Listener);
		
		radioButton_3 = new JRadioButton("");
		radioButton_3.setBounds(275, 78, 27, 23);
		frame.getContentPane().add(radioButton_3);
		
		class radioButton_3 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!_chrono.isOn()) radioButton_3.setSelected(false);
				String[] commandArray = new String[3];
				commandArray[0] = "-";
				commandArray[1] = "TOG";
				commandArray[2] = "3";
				_chrono.runCommand(commandArray);
			}
			
		}
		
		radioButton_3 radioButton_3Listener = new radioButton_3();
		radioButton_3.addActionListener(radioButton_3Listener);
		
		radioButton_5 = new JRadioButton("");
		radioButton_5.setBounds(312, 78, 27, 23);
		frame.getContentPane().add(radioButton_5);
		
		class radioButton_5 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!_chrono.isOn()) radioButton_5.setSelected(false);
				String[] commandArray = new String[3];
				commandArray[0] = "-";
				commandArray[1] = "TOG";
				commandArray[2] = "5";
				_chrono.runCommand(commandArray);
			}
			
		}
		
		radioButton_5 radioButton_5Listener = new radioButton_5();
		radioButton_5.addActionListener(radioButton_5Listener);
		
		radioButton_7 = new JRadioButton("");
		radioButton_7.setBounds(349, 78, 27, 23);
		frame.getContentPane().add(radioButton_7);
		
		class radioButton_7 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!_chrono.isOn()) radioButton_7.setSelected(false);
				String[] commandArray = new String[3];
				commandArray[0] = "-";
				commandArray[1] = "TOG";
				commandArray[2] = "7";
				_chrono.runCommand(commandArray);
			}
			
		}
		
		radioButton_7 radioButton_7Listener = new radioButton_7();
		radioButton_7.addActionListener(radioButton_7Listener);
		
		radioButton_2 = new JRadioButton("");
		radioButton_2.setBounds(238, 153, 27, 23);
		frame.getContentPane().add(radioButton_2);
		
		class radioButton_2 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!_chrono.isOn()) radioButton_2.setSelected(false);
				String[] commandArray = new String[3];
				commandArray[0] = "-";
				commandArray[1] = "TOG";
				commandArray[2] = "2";
				_chrono.runCommand(commandArray);
			}
			
		}
		
		radioButton_2 radioButton_2Listener = new radioButton_2();
		radioButton_2.addActionListener(radioButton_2Listener);
		
		radioButton_4 = new JRadioButton("");
		radioButton_4.setBounds(275, 153, 27, 23);
		frame.getContentPane().add(radioButton_4);
		
		class radioButton_4 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!_chrono.isOn()) radioButton_4.setSelected(false);
				String[] commandArray = new String[3];
				commandArray[0] = "-";
				commandArray[1] = "TOG";
				commandArray[2] = "4";
				_chrono.runCommand(commandArray);
			}
			
		}
		
		radioButton_4 radioButton_4Listener = new radioButton_4();
		radioButton_4.addActionListener(radioButton_4Listener);
		
		radioButton_6 = new JRadioButton("");
		radioButton_6.setBounds(312, 153, 27, 23);
		frame.getContentPane().add(radioButton_6);
		
		class radioButton_6 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!_chrono.isOn()) radioButton_6.setSelected(false);
				String[] commandArray = new String[3];
				commandArray[0] = "-";
				commandArray[1] = "TOG";
				commandArray[2] = "6";
				_chrono.runCommand(commandArray);
			}
			
		}
		
		radioButton_6 radioButton_6Listener = new radioButton_6();
		radioButton_6.addActionListener(radioButton_6Listener);
		
		radioButton_8 = new JRadioButton("");
		radioButton_8.setBounds(349, 153, 27, 23);
		frame.getContentPane().add(radioButton_8);
		
		class radioButton_8 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!_chrono.isOn()) radioButton_8.setSelected(false);
				String[] commandArray = new String[3];
				commandArray[0] = "-";
				commandArray[1] = "TOG";
				commandArray[2] = "8";
				_chrono.runCommand(commandArray);
			}
			
		}
		
		radioButton_8 radioButton_8Listener = new radioButton_8();
		radioButton_8.addActionListener(radioButton_8Listener);
		
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
		
		JButton sensorChan1 = new JButton("1");
		sensorChan1.setBounds(30, 449, 41, 23);
		frame.getContentPane().add(sensorChan1);
		
		class sensorChan1 implements MouseListener{
			
			@Override
			public void mousePressed(MouseEvent e) {
				String[] commandArray = new String[4];
				final JPopupMenu popup = new JPopupMenu();
		        popup.add(new JMenuItem(new AbstractAction("Connect") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "GATE";
						commandArray[3] = "1";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.add(new JMenuItem(new AbstractAction("Remove") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "NONE";
						commandArray[3] = "1";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {

			}
		}
		sensorChan1 sensorChannel1_Listener = new sensorChan1();
		sensorChan1.addMouseListener(sensorChannel1_Listener);
		
		JButton sensorChan2 = new JButton("2");
		sensorChan2.setBounds(30, 475, 41, 23);
		frame.getContentPane().add(sensorChan2);
		
		class sensorChan2 implements MouseListener{
			
			@Override
			public void mousePressed(MouseEvent e) {
				String[] commandArray = new String[4];
				final JPopupMenu popup = new JPopupMenu();
		        popup.add(new JMenuItem(new AbstractAction("Connect") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "EYE";
						commandArray[3] = "2";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.add(new JMenuItem(new AbstractAction("Remove") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "NONE";
						commandArray[3] = "2";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {

			}
		}
		sensorChan2 sensorChannel2_Listener = new sensorChan2();
		sensorChan2.addMouseListener(sensorChannel2_Listener);
		
		JButton sensorChan3 = new JButton("3");
		sensorChan3.setBounds(81, 449, 41, 23);
		frame.getContentPane().add(sensorChan3);
		
		class sensorChan3 implements MouseListener{
			
			@Override
			public void mousePressed(MouseEvent e) {
				String[] commandArray = new String[4];
				final JPopupMenu popup = new JPopupMenu();
		        popup.add(new JMenuItem(new AbstractAction("Connect") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "GATE";
						commandArray[3] = "3";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.add(new JMenuItem(new AbstractAction("Remove") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "NONE";
						commandArray[3] = "3";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {

			}
		}
		sensorChan3 sensorChannel3_Listener = new sensorChan3();
		sensorChan3.addMouseListener(sensorChannel3_Listener);
		
		JButton sensorChan4 = new JButton("4");
		sensorChan4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		sensorChan4.setBounds(81, 475, 41, 23);
		frame.getContentPane().add(sensorChan4);
		
		class sensorChan4 implements MouseListener{
			
			@Override
			public void mousePressed(MouseEvent e) {
				String[] commandArray = new String[4];
				final JPopupMenu popup = new JPopupMenu();
		        popup.add(new JMenuItem(new AbstractAction("Connect") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "EYE";
						commandArray[3] = "4";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.add(new JMenuItem(new AbstractAction("Remove") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "NONE";
						commandArray[3] = "4";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {

			}
		}
		sensorChan4 sensorChannel4_Listener = new sensorChan4();
		sensorChan4.addMouseListener(sensorChannel4_Listener);
		
		JButton sensorChan5 = new JButton("5");
		sensorChan5.setBounds(132, 449, 41, 23);
		frame.getContentPane().add(sensorChan5);
		
		class sensorChan5 implements MouseListener{
			
			@Override
			public void mousePressed(MouseEvent e) {
				String[] commandArray = new String[4];
				final JPopupMenu popup = new JPopupMenu();
		        popup.add(new JMenuItem(new AbstractAction("Connect") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "GATE";
						commandArray[3] = "5";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.add(new JMenuItem(new AbstractAction("Remove") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "NONE";
						commandArray[3] = "5";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {

			}
		}
		sensorChan5 sensorChannel5_Listener = new sensorChan5();
		sensorChan5.addMouseListener(sensorChannel5_Listener);
		
		JButton sensorChan6 = new JButton("6");
		sensorChan6.setBounds(132, 475, 41, 23);
		frame.getContentPane().add(sensorChan6);
		
		class sensorChan6 implements MouseListener{
			
			@Override
			public void mousePressed(MouseEvent e) {
				String[] commandArray = new String[4];
				final JPopupMenu popup = new JPopupMenu();
		        popup.add(new JMenuItem(new AbstractAction("Connect") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "EYE";
						commandArray[3] = "6";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.add(new JMenuItem(new AbstractAction("Remove") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "NONE";
						commandArray[3] = "6";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {

			}
		}
		sensorChan6 sensorChannel6_Listener = new sensorChan6();
		sensorChan6.addMouseListener(sensorChannel6_Listener);
		
		JButton sensorChan7 = new JButton("7");
		sensorChan7.setBounds(181, 449, 41, 23);
		frame.getContentPane().add(sensorChan7);
		
		class sensorChan7 implements MouseListener{
			
			@Override
			public void mousePressed(MouseEvent e) {
				String[] commandArray = new String[4];
				final JPopupMenu popup = new JPopupMenu();
		        popup.add(new JMenuItem(new AbstractAction("Connect") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "GATE";
						commandArray[3] = "7";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.add(new JMenuItem(new AbstractAction("Remove") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "NONE";
						commandArray[3] = "7";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {

			}
		}
		sensorChan7 sensorChannel7_Listener = new sensorChan7();
		sensorChan7.addMouseListener(sensorChannel7_Listener);
		
		JButton sensorChan8 = new JButton("8");
		sensorChan8.setBounds(181, 475, 41, 23);
		frame.getContentPane().add(sensorChan8);
		
		class sensorChan8 implements MouseListener{
			
			@Override
			public void mousePressed(MouseEvent e) {
				String[] commandArray = new String[4];
				final JPopupMenu popup = new JPopupMenu();
		        popup.add(new JMenuItem(new AbstractAction("Connect") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "EYE";
						commandArray[3] = "8";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.add(new JMenuItem(new AbstractAction("Remove") {
		        	
		            public void actionPerformed(ActionEvent d) {
		            	if(!_chrono.isOn()) return;
						commandArray[0] = "-";
						commandArray[1] = "CONN";
						commandArray[2] = "NONE";
						commandArray[3] = "8";
						_chrono.runCommand(commandArray);
		            }
		        }));
		        popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {

			}
		}
		sensorChan8 sensorChannel8_Listener = new sensorChan8();
		sensorChan8.addMouseListener(sensorChannel8_Listener);
		
		txtUsbPort = new JTextField();
		txtUsbPort.setBackground(Color.LIGHT_GRAY);
		txtUsbPort.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsbPort.setText("USB Port");
		txtUsbPort.setBounds(343, 462, 86, 20);
		txtUsbPort.setEditable(false);
		frame.getContentPane().add(txtUsbPort);
		txtUsbPort.setColumns(10);
		
		JTextField txtChan = new JTextField(){
			
			@Override public void setBorder(Border border) {
		    }
		};
		txtChan.setBackground(SystemColor.menu);
		txtChan.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtChan.setHorizontalAlignment(SwingConstants.CENTER);
		txtChan.setText("CHAN");
		txtChan.setBounds(10, 418, 86, 20);
		frame.getContentPane().add(txtChan);
		txtChan.setColumns(10);
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
				// TODO: Need to enter in a run number here
				functionReturn();
				break;
			}
			case(8): {
				_chrono.runCommand("-", "RESET");
				functionReturn();
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
				radioButton_5.setSelected(false);
				radioButton_6.setSelected(false);
				radioButton_7.setSelected(false);
				radioButton_8.setSelected(false);
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
	
	// End FUNCTION functionality --------------------------------------------------------------------
	
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
		// mainDisplay = _chrono.getDisplay();mainTextArea.setText(createMainTextString(mainDisplay));
		//else
		if(!functionBool) 
		{
			mainTextArea.setText(_chrono.getQueueDisplay() + "\n\n" + _chrono.getRunningDisplay() + "\n\n" + 
						_chrono.getRanDisplay() + "\n\nRun: " + _chrono.getRun());
		}
	}
	
	/** This method gets the latest printer data from {@link pcmr.Chronotimer} (which should be stored in ArrayList format)
	 *  any new entries to the ArrayList should be added on a line by line basis in order from oldest to newest
	 *  with {@link #addPrinterLine(String)}
	 */
	private void updatePrinter() {
		String[] disp = _chrono.getPrinterStrings();
		String displayer = "";
		for(int i = 0; i<10; ++i)
		{
			if(9-i < disp.length) displayer += disp[9-i];
			displayer += "\n";
		}
		printerTextArea.setText(displayer);
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
