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
	
	/**
	 * _startTimes and _finishTimes need to be eliminated. Racer times will be held in the racer class
	 * PRINT will take a racer for an argument
	 * There will be a queue of racers which, when a sensor is triggered, will take the time for themselves
	 * @author huelsma2
	 *
	 */
	
	private static enum EVENTS {IND, PARIND, GRP, PARGRP};
	private EVENTS event = EVENTS.IND;
	private boolean _eventRunning;
	
	private Printer _printer = new Printer();
	
	
	/**
	 * stupid ugly printer class it echos crap
	 * @author Andrew Huelsman
	 *
	 */
	private class Printer
	{
		public void println(String echo)
		{
			System.out.println(echo);
		}
	}
	
	public Chronotimer()
	{
		_time = new Time();
		//_channelOn[0] = true;
		//_sensorsConnected[0] = new Sensor();
	}

	
	/**
	 * Method which contains cases for the command sent to this Chronotimer.
	 * @param command array of strings which acts as the command with arguments
	 */
	public void runCommand(String... command)
	{
		if(!command[1].toUpperCase().equals("POWER") && !_isOn) return;
		switch(command[1].toUpperCase())
		{
		case "POWER":
		{
			_isOn = !_isOn;
			_printer.println(command[0] + " Power is turned " + (_isOn? "on" : "off"));
			if(!_isOn) 
			{
				_channelOn = new boolean[_CHANNELS];
				_channelTripped = new boolean[_CHANNELS];
				_eventRunning=false;
				event=EVENTS.IND;
			}
			break;
		}
		case "NEWRUN":
		{
			if(_eventRunning == true) 
			{
				_printer.println(command[0] + " There is an event already going on. End the current run to begin a new one.");
				break;
			}
			_eventRunning=true;
			_printer.println("Event type " + event.toString() +" has been started");
			break;
		}
		case "ENDRUN":
		{
			_eventRunning=false;
			break;
		}
		case "CONN":
		{
			try
			{
				int channel = Integer.parseInt(command[3])-1;
				_sensorsConnected[channel] = new Sensor();
				_printer.println(command[0] + " Channel " + (channel+1) + " has a sensor connected");
			}
			catch(NumberFormatException e) {_printer.println(command[0] + " Error connecting the sensor");}
			break;
		}
		case "EVENT":
		{
			switch(command[2].toUpperCase())
			{
			case "IND":
			{
				event = EVENTS.IND;
				_printer.println(command[0] +" Event type set to " + event.toString());
				break;
			}
			case "PARIND": case "GRP": case "PARGRP":
			default: System.out.println("That type of event is either [A]: Not supported (yet) or [B]: Not a valid event");
			}
			break;
		}
		case "TOG":
		{
			try
			{
				int channel = Integer.parseInt(command[2])-1;
				_channelOn[channel]=!_channelOn[channel];
				_printer.println(command[0] + " Channel " + (channel+1) + " has been turned " + (_channelOn[channel]? "on" : "off"));
			}
			catch(NumberFormatException e) {_printer.println(command[0] + " Error turning on or off the channel");}
			break;
		}
		case "TRIG":
		{
			try
			{
				int i = Integer.parseInt(command[2])-1;
				if(/*_sensorsConnected[i] == null || */!_channelOn[i])
					break;
				_channelTripped[i]=true;
				_printer.println(command[0] + " Channel " + (i+1) + " has been tripped!");
				if(i%2==0) 
				{
					_startTimes[i/2] = new Time(command[0]);
				}
				else 
				{
					_finishTimes[i/2] = new Time(command[0]);
				}
			}
			catch(NumberFormatException e) {_printer.println("Reeeee");}
			break;
		}
		case "RESET":
		{
			_channelOn = new boolean[_CHANNELS];
			_channelTripped = new boolean[_CHANNELS];
			_printer.println(command[0] + " All channels have been reset and turned off");
			break;
		}
		case "TIME":
		{
			_time = new Time(command[2]);
			_printer.println(command[0] + " Time reset");
			break;
		}
		case "DNF":
		{
			_finishTimes[0] = new Time(null);
			_printer.println(command[0] + " Racer for channels [1] and [2] did not finish (DNF)");
			break;
		}
		case "CANCEL":
		{
			_channelTripped[0] = false;
			_channelTripped[1] = false;
			_startTimes[0]=null;
			_finishTimes[0]=null;
			_printer.println(command[0] + "Run for channels [1] and [2] has been canceled");
			break;
		}
		case "START":
		{
			runCommand(command[0], "TRIG",""+1);
			break;
		}
		case "FINISH":
		{
			runCommand(command[0], "TRIG",""+2);
			break;
		}
		case "PRINT":
		{
			if(command.length < 3) {
				printRun(0);
				printRun(1);
				printRun(2);
				printRun(3);
			}
			else {
				try
				{
					int i = Integer.parseInt(command[2])-1;
					printRun(i);
				}
				catch(NumberFormatException e) {_printer.println("Invalid Channel");}
			}
			break;
		}
		default: {System.out.println("Invalid command entered. Contact a Software Engineer to solve this problem"); break;}
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
	
	public void printRun(int startChannel)
	{
		if(startChannel<0 || startChannel>3) return;
		String time = Time.difference(_startTimes[startChannel], _finishTimes[startChannel]).convertRawTime();
		_printer.println("Time for racer on channels ["+((startChannel*2)+1)+"] and ["+((startChannel*2)+2)+"] is " + time);
	}
	
	public boolean isOn(){
		
		return _isOn;
	}
	
	public boolean isReset(){
		
		for(int i = 0; i<_CHANNELS; i++){
			if(_channelOn[i] != true && _channelTripped[i] != true){
				continue; 
			}else{return false;}
		}
		return true;
	}
	
	public boolean eventIsStarted(){
		
		return _channelTripped[0];
		
	}
	
	public boolean eventIsFinished(){
		
		return _channelTripped[1];
	}
	
	public boolean isCancled(){
        //only checking 2 channel so subtract 6 from total channels
        for(int i = 0; i<_CHANNELS - 6; i++){ 
          if(_channelTripped[i] != true && _channelTripped[i] != true && _startTimes[0] == null && _finishTimes[0] == null){
            continue; 
          }else{return false;}
        }
        return true;
      }
	
	public boolean isNewRunTriggered(){
		
		return _eventRunning;
		
	}
	
	public String getEvent(){

		return this.event.toString();
		
	}
	
	public boolean isToggled(){
        
	    int channel1 = 0;
	    int channel2 = 1;
	    if(!_channelOn[channel1] && !_channelOn[channel2])  return false ;
	    
	    return true;
	  
	}
	
	public boolean isConnected() {
	    
	    int channel1 = 0;
	    int channel2 = 1;
	    int channel3 = 2;
	    int channel4 = 4;
	    if(_sensorsConnected[channel1] == null && _sensorsConnected[channel2] == null && 
	       _sensorsConnected[channel3] == null && _sensorsConnected[channel4] == null) {
	      return false;
	    }
	        
	    return true;
	  }
	
}
