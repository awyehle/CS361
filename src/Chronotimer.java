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
	private Time[] _startTimes = new Time[_CHANNELS/2];
	private Time[] _finishTimes = new Time[_CHANNELS/2];
	
	
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
				int i = Integer.parseInt(command[2]);
				_channelTripped[i]=true;
				if(i%2==0) _startTimes[i/2] = new Time(command[0]);
				else _finishTimes[i/2+1] = new Time(command[0]);
			}
			catch(NumberFormatException e) {}
			break;
		}
		case "RESET":
		{
			_channelOn = new boolean[_CHANNELS];
			_channelTripped = new boolean[_CHANNELS];
			break;
		}
		case "TIME":
		{
			_time = new Time(command[2]);
			break;
		}
		case "DNF":
		{
			_finishTimes[1] = new Time(null);
			break;
		}
		case "CANCEL":
		{
			break;
		}
		case "START":
		{
			runCommand(command[0], "TRIG",""+0);
			break;
		}
		case "FINISH":
		{
			runCommand(command[0], "TRIG",""+1);
			break;
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
