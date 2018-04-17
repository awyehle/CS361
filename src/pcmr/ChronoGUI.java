package pcmr;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JLabel;

public class ChronoGUI {
	
	private static Chronotimer _chrono;

	private JFrame frame;
	private JTextField txtChronotimer;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		_chrono =  new Chronotimer();
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
		
		textField_1 = new JTextField();
		textField_1.setBounds(488, 43, 130, 133);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		/* This is an example of how all buttons and action listeners
		 * should be implemented
		 * ~Steven
		 */
		JButton btnPower = new JButton("Power");
		btnPower.setBounds(10, 11, 101, 23);
		frame.getContentPane().add(btnPower);
		class Power implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] commandArray = new String[2];
				commandArray[0] = "-";
				commandArray[1] = "POWER";
				_chrono.runCommand(commandArray);
			}
			
		}
		Power powerListener = new Power();
		btnPower.addActionListener(powerListener);
		
		// End Power button example
		
		//Begin Printer Power
		
		JButton btnPrinterPwr = new JButton("Printer Pwr");
		btnPrinterPwr.setBounds(502, 11, 101, 23);
		frame.getContentPane().add(btnPrinterPwr);
		
		class Printer implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] commandArray = new String[2];
				commandArray[0] = "-";
				commandArray[1] = "PRINT";
				_chrono.runCommand(commandArray);
			}
			
		}
		Printer printerListener = new Printer();
		btnPrinterPwr.addActionListener(printerListener);
		
		//end printer
		
		txtChronotimer = new JTextField(){
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
		
		JButton btnFunction = new JButton("Function");
		btnFunction.setBounds(10, 250, 101, 23);
		frame.getContentPane().add(btnFunction);
		
		JButton btnSwap = new JButton("Swap");
		btnSwap.setBounds(10, 375, 101, 23);
		frame.getContentPane().add(btnSwap);
		
		JButton button = new JButton("1");
		button.setBounds(488, 221, 41, 45);
		frame.getContentPane().add(button);
		
		JButton button_9 = new JButton("*");
		button_9.setBounds(488, 353, 41, 45);
		frame.getContentPane().add(button_9);
		
		JButton button_10 = new JButton("0");
		button_10.setBounds(527, 353, 41, 45);
		frame.getContentPane().add(button_10);
		
		JButton button_11 = new JButton("#");
		button_11.setBounds(562, 353, 41, 45);
		frame.getContentPane().add(button_11);
		
		textField = new JTextField();
		textField.setBounds(190, 221, 239, 177);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
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
		
		JRadioButton radioButton = new JRadioButton("");
		radioButton.setBounds(239, 78, 27, 23);
		frame.getContentPane().add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("");
		radioButton_1.setBounds(275, 78, 27, 23);
		frame.getContentPane().add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("");
		radioButton_2.setBounds(312, 78, 27, 23);
		frame.getContentPane().add(radioButton_2);
		
		JRadioButton radioButton_3 = new JRadioButton("");
		radioButton_3.setBounds(349, 78, 27, 23);
		frame.getContentPane().add(radioButton_3);
		
		JRadioButton radioButton_4 = new JRadioButton("");
		radioButton_4.setBounds(238, 153, 27, 23);
		frame.getContentPane().add(radioButton_4);
		
		JRadioButton radioButton_5 = new JRadioButton("");
		radioButton_5.setBounds(275, 153, 27, 23);
		frame.getContentPane().add(radioButton_5);
		
		JRadioButton radioButton_6 = new JRadioButton("");
		radioButton_6.setBounds(312, 153, 27, 23);
		frame.getContentPane().add(radioButton_6);
		
		JRadioButton radioButton_7 = new JRadioButton("");
		radioButton_7.setBounds(349, 153, 27, 23);
		frame.getContentPane().add(radioButton_7);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(474, 78, 157, 120);
		frame.getContentPane().add(panel);
		
		JButton button_20 = new JButton("<");
		button_20.setBounds(10, 284, 50, 23);
		frame.getContentPane().add(button_20);
		
		JButton button_21 = new JButton(">");
		button_21.setBounds(72, 284, 50, 23);
		frame.getContentPane().add(button_21);
		
		JButton button_22 = new JButton("^");
		button_22.setBounds(10, 320, 50, 23);
		frame.getContentPane().add(button_22);
		
		JButton button_23 = new JButton("\u02C5");
		button_23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_23.setBounds(72, 320, 50, 23);
		frame.getContentPane().add(button_23);
		
		JButton button_1 = new JButton("2");
		button_1.setBounds(527, 221, 41, 45);
		frame.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("3");
		button_2.setBounds(562, 221, 41, 45);
		frame.getContentPane().add(button_2);
		
		JButton button_3 = new JButton("4");
		button_3.setBounds(488, 265, 41, 45);
		frame.getContentPane().add(button_3);
		
		JButton button_4 = new JButton("5");
		button_4.setBounds(527, 265, 41, 45);
		frame.getContentPane().add(button_4);
		
		JButton button_5 = new JButton("6");
		button_5.setBounds(562, 265, 41, 45);
		frame.getContentPane().add(button_5);
		
		JButton button_6 = new JButton("7");
		button_6.setBounds(488, 309, 41, 45);
		frame.getContentPane().add(button_6);
		
		JButton button_7 = new JButton("8");
		button_7.setBounds(527, 309, 41, 45);
		frame.getContentPane().add(button_7);
		
		JButton button_8 = new JButton("9");
		button_8.setBounds(562, 309, 41, 45);
		frame.getContentPane().add(button_8);
		
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
}
