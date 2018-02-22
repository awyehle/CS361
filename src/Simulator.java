import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Simulator {
	
	private Chronotimer _environment;
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
