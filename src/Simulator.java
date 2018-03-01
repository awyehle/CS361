import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Simulator {
	
	private static Chronotimer _environment;
	
	static final String _fileLocation = "Commands.txt";
	static Scanner stdin = new Scanner(System.in);
			
	private static final ArrayList<String> _VALIDCOMMANDS = new ArrayList<String>();
	
	static
	{
		_VALIDCOMMANDS.add("POWER");
		_VALIDCOMMANDS.add("RESET");
		_VALIDCOMMANDS.add("TIME");
		_VALIDCOMMANDS.add("DNF");
		_VALIDCOMMANDS.add("CANCEL");
		_VALIDCOMMANDS.add("TOG");
		_VALIDCOMMANDS.add("TRIG");
		_VALIDCOMMANDS.add("START");
		_VALIDCOMMANDS.add("FINISH");
		_VALIDCOMMANDS.add("EVENT");
		_VALIDCOMMANDS.add("ENDRUN");
		_VALIDCOMMANDS.add("NEWRUN");
		_VALIDCOMMANDS.add("NUM");
		_VALIDCOMMANDS.add("PRINT");
		_VALIDCOMMANDS.add("CONN");
	}

	public static void main(String[] args)
	{
		_environment = new Chronotimer();
		simulationLoop();
	}
	
	 private static void simulationLoop() {
		 System.out.println("[C]onsole, [F]ile, or [E]xit");
		 String input = stdin.nextLine();
		 if(input.equals("C") || input.equals("c")) consoleInput();
		 else if(input.toUpperCase().equals("F")) fileInput();
		 else if(input.toUpperCase().equals("E")) exitSim();
		 simulationLoop();
	 }
	 
	 private static void consoleInput() {
		 System.out.println("Input [Time command]:");
		 String input = stdin.nextLine();
		 inputCommand(input);
		 consoleInput();
	 }
	 
	 private static void fileInput() {
		 Scanner fileScanner = null;
		 System.out.println("Enter in either 'Commands1.txt' or 'Commands.txt'");
		 String filename = stdin.next();
		 InputStream in = Simulator.class.getResourceAsStream(filename);
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
		 String[] commandArray = input.split(" |	");
		 if(commandArray.length < 2 || commandArray.length > 4 || commandArray[1] == null) {
			 System.out.println("Invalid Command Format");
			 return;
		 }
		 if(commandArray[0].equals("-"))
			 commandArray[0] = new Time().convertRawTime();
		 if(commandArray[1].toUpperCase().equals("EXIT")) exitSim(input);
		 if(_VALIDCOMMANDS.contains(commandArray[1].toUpperCase())) _environment.runCommand(commandArray);
		 else System.out.println("Invalid command " + commandArray[1]);
	 }
	 
	 private static void exitSim() {
		 stdin.close();
		 System.exit(0);
	 }
	 
	 private static void exitSim(String input) {
		 String[] commandArray = input.split(" |	");
		 System.out.println(commandArray[0] + " The simulation has terminated");
		 stdin.close();
		 System.exit(0);
	 }
}
