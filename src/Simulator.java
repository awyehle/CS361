import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Simulator {
	
	private static Chronotimer _environment;
	
	static final String _fileLocation = "InputCommands.txt";
	static Scanner stdin = new Scanner(System.in);
			
	private static final ArrayList<String> _VALIDCOMMANDS = new ArrayList<String>();
	
	static
	{
		_VALIDCOMMANDS.add("POWER");
		_VALIDCOMMANDS.add("EXIT");
		_VALIDCOMMANDS.add("RESET");
		_VALIDCOMMANDS.add("TIME");
		_VALIDCOMMANDS.add("DNF");
		_VALIDCOMMANDS.add("CANCEL");
		_VALIDCOMMANDS.add("TOG");
		_VALIDCOMMANDS.add("TRIG");
		_VALIDCOMMANDS.add("START");
		_VALIDCOMMANDS.add("FINISH");
	}

	public static void main(String[] args)
	{
		/// Initialize testing values
		
		_environment = new Chronotimer();
		// Start the simulation loop
		simulationLoop();
	}
	
	 private static void simulationLoop() {
		 System.out.println("[C]onsole, [F]ile, or [E]xit");
		 String input = stdin.next();
		 if(input.equals("C") || input.equals("c")) consoleInput();
		 else if(input.equals("F") || input.equals("f")) fileInput();
		 else if(input.equals("E") || input.equals("e")) exitSim();
		 simulationLoop();
	 }
	 
	 private static void consoleInput() {
		 System.out.println("Input ([E]xit):");
		 String input = stdin.nextLine();
		 if(input.equals("E") || input.equals("e")) simulationLoop();
		 inputCommand(input);
		 consoleInput();
	 }
	 
	 private static void fileInput() {
		 Scanner fileScanner = null;
		 InputStream in = Simulator.class.getResourceAsStream(_fileLocation);
		 String input;
		 
		 try {
				fileScanner = new Scanner(in);
			} catch (Exception e) {
					System.out.println("Failed at opening resource " + e);
					System.exit(0);
			}
		 while(fileScanner.hasNextLine()) {
			 input = fileScanner.nextLine();
			 inputCommand(input);
		 }
		 try {
			in.close();
		} catch (IOException e) {
			System.out.println("Failed at closing resource " + e);
		}
		 simulationLoop();
	 }
	 
	 private static void inputCommand(String input) {
		 String[] inputArray = input.split(" ");
		 String command;
		 String mod;
		 boolean goodCommand = false;
		 
		 if(inputArray.length < 2 || inputArray.length > 3) return;
		 if(inputArray.length == 2) {
			 command = inputArray[0].toUpperCase();
			 mod = inputArray[1].toUpperCase();
		 }
		 else {
			 command = inputArray[1].toUpperCase();
			 mod = inputArray[2].toUpperCase();
		 }
		 switch(command) {
		 	case "CARDREAD" :
		 	case "NUM" :
		 	case "DIS" :
		 	case "PRINT" : goodCommand = true;
		 		break;
		 	case "BUTTON" :
		 		switch(mod) {
		 			case "W" :
		 			case "CB" :
		 			case "CANCEL" :
		 			case "D" : goodCommand = true;
		 				break;
		 		}
		 }
		 if(goodCommand) _environment.runCommand(inputArray);
	 }
	 
	 private static void exitSim() {
		 stdin.close();
		 System.exit(0);
	 }
	
	private static void runCommand(String command)
	{
		Timestamp ts = new Timestamp(new Date().getTime());
		Time timestamp = new Time(ts.toString().split(" ")[1]);
		
	}
	
	private static void runFileCommand(String command)
	{
		String[] cmd = command.split(" ");
		//if()
		Time timestamp = new Time(cmd[0]);
	}
	
}
