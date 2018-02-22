import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * System for timer...
 * @author Andrew Huelsman, whomever else
 *
 */
public class Chronotimer {

	private boolean _isOn;
	private final int _CHANNELS = 8;
	private Time _time;
	private boolean[] _channelOn = new boolean[_CHANNELS];
	private boolean[] _channelTripped = new boolean[_CHANNELS];
	private Sensor[] _sensorsConnected = new Sensor[_CHANNELS];
	
	public Chronotimer()
	{
		_time = new Time();
		_channelOn[0] = true;
		_sensorsConnected[0] = new Sensor();
	}

	public void runCommand(String... command)
	{
		switch(command[1])
		{
		case "POWER":
		{
			_isOn = !_isOn;
			break;
		}
		case "TOG":
		{
			try
			{
				_channelOn[Integer.parseInt(command[2])]=!_channelOn[Integer.parseInt(command[2])];
			}
			catch(NumberFormatException e) {}
			break;
		}
		case "TRIG":
		{
			try
			{
				_channelTripped[Integer.parseInt(command[2])]=true;
			}
			catch(NumberFormatException e) {}
			break;
		}
		case "RESET":
		{
			_channelOn = new boolean[_CHANNELS];
			_channelTripped = new boolean[_CHANNELS];
		}
		case "TIME":
		{
			_time = new Time(command[2]);
		}
		default: {System.out.println("This should not be able to happen!"); break;}
		}
	}

	public int getConnection(Sensor sensor)
	{
		for(int i = 0; i< _CHANNELS; ++i)
		{
			if(_sensorsConnected[i]==sensor)
				return i;
		}
		return -1;
	}
}
