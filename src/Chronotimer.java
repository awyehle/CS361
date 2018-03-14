import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import com.google.gson.Gson;

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
	
	@SuppressWarnings("unchecked")
	private LinkedList<Time>[] _startTimes = new LinkedList[_CHANNELS/2]; 
	@SuppressWarnings("unchecked")
	private LinkedList<Time>[] _finishTimes = new LinkedList[_CHANNELS/2];
	
	private int _runNumber = 0;
	private ArrayList<Result> _run = new ArrayList<Result>();
	private RaceQueuer _racerList12 = new RaceQueuer();
	private RaceQueuer _racerList34 = new RaceQueuer();
	private boolean laneOne = true;
	
	
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
		resetTimes();
		//_channelOn[0] = true;
		//_sensorsConnected[0] = new Sensor();
	}

	private void resetTimes()
	{
		for(int i = 0; i < _startTimes.length; ++i)
			_startTimes[i]=new LinkedList<Time>();
		for(int i = 0; i < _finishTimes.length; ++i)
			_finishTimes[i]=new LinkedList<Time>();
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
			power(command);
			break;
		}
		case "NEWRUN":
		{
			newrun(command);
			break;
		}
		case "ENDRUN":
		{
			endrun();
			break;
		}
		case "CONN":
		{
			connect(command);
			break;
		}
		case "EVENT":
		{
			event(command);
			break;
		}
		case "TOG":
		{
			toggle(command);
			break;
		}
		case "TRIG":
		{
			trigger(command);
			break;
		}
		case "RESET":
		{
			reset(command);
			break;
		}
		case "TIME":
		{
			time(command);
			break;
		}
		case "DNF":
		{
			dnf(command);
			break;
		}
		case "CANCEL":
		{
			cancel(command);
			break;
		}
		case "START":
		{
			trigger(command[0], "TRIG",""+1);
			break;
		}
		case "FINISH":
		{
			trigger(command[0], "TRIG",""+2);
			break;
		}
		case "PRINT":
		{
			print(command);
			break;
		}
		case "EXPORT":
		{
				export(command);
			break;
		}
		case "NUM":
		{
			addRacer(command);
			break;
		}
		default: {System.out.println("Invalid command entered. Contact a Software Engineer to solve this problem"); break;}
		}
		
	}

	private void addRacer(String... command) {
		if(event == EVENTS.PARIND){
			if(laneOne){
				try{
				Racer newRacer = new Racer(Integer.parseInt(command[2]));
				if(!_racerList12.push(newRacer)) _printer.println("Racer already in queue or ran!");
				else 
					{
					_printer.println("Racer " + newRacer.getBib() + " has been added to the queue.");
					}
				}catch(NumberFormatException e){
					_printer.println("Invalid Bib Number Entered");
				}catch(IllegalArgumentException e){
					_printer.println(e.getMessage());
				}
			}else{
				try{
					Racer newRacer = new Racer(Integer.parseInt(command[2]));
					if(!_racerList34.push(newRacer)) _printer.println("Racer already in queue or ran!");
					else 
						{
						_printer.println("Racer " + newRacer.getBib() + " has been added to the queue.");
						}
					}catch(NumberFormatException e){
						_printer.println("Invalid Bib Number Entered");
					}catch(IllegalArgumentException e){
						_printer.println(e.getMessage());
					}
			}
			laneOne = !laneOne;
		}else if (event == EVENTS.IND){
			try{
				Racer newRacer = new Racer(Integer.parseInt(command[2]));
				if(!_racerList12.push(newRacer)) _printer.println("Racer already in queue or ran!");
				else 
					{
					_printer.println("Racer " + newRacer.getBib() + " has been added to the queue.");
					}
				}catch(NumberFormatException e){
					_printer.println("Invalid Bib Number Entered");
				}catch(IllegalArgumentException e){
					_printer.println(e.getMessage());
				}
		}
	}

	private void export(String... command) {
		try
		{
			int i = Integer.parseInt(command[2]);
			if(i > _run.size() || i < 1) throw new ArrayIndexOutOfBoundsException();
			try(PrintWriter writer = new PrintWriter(new FileOutputStream("Run_"+i+".json",false)))
			{
				writer.println(new Gson().toJson(_run.get(i-1)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			_printer.println("Run " + i + " has been saved");
		}
		catch(NumberFormatException e) {_printer.println("Invalid Channel");}
		catch(ArrayIndexOutOfBoundsException er) {_printer.println(command[0] + " Not a valid run");}
	}

	private void print(String... command) {
		if(command.length < 3) {
			runCommand(command[0],command[1], ""+_runNumber);
		}
		else {
			try
			{
				int i = Integer.parseInt(command[2]);
				if(i > _run.size() || i < 1) throw new IndexOutOfBoundsException();
				_printer.println(_run.get(i-1).toString());
			}
			catch(NumberFormatException e) {_printer.println("Invalid Channel");}
			catch(IndexOutOfBoundsException er) {_printer.println(command[0] + " Not a valid run");}
		}
	}

	private void cancel(String... command) {
		_channelTripped[0] = false;
		_channelTripped[1] = false;
		if(_startTimes[0].size()>0)
			_startTimes[0].removeLast();
		if(_finishTimes[0].size()>0)
			_finishTimes[0].removeLast();
		_printer.println(command[0] + " Run for channels [1] and [2] has been canceled");
	}

	private void dnf(String... command) {
		_finishTimes[0].add(new Time(null));
		_printer.println(command[0] + " Racer for channels [1] and [2] did not finish (DNF)");
	}

	private void time(String... command) {
		try{
		_time = new Time(command[2]);
		}catch(ArrayIndexOutOfBoundsException e){System.out.println("Please enter a valid time"); return;}
		_printer.println(command[0] + " Time reset");
	}

	private void reset(String... command) {
		_channelOn = new boolean[_CHANNELS];
		_channelTripped = new boolean[_CHANNELS];
		_run.clear();
		_runNumber=0;
		endrun();
		_printer.println(command[0] + " All channels have been reset and turned off. Runs have been erased");
	}

	private void trigger(String... command) {
		try
		{
			int channel = Integer.parseInt(command[2])-1;
			if(/*_sensorsConnected[i] == null || */!_channelOn[channel])
				return;
			_channelTripped[channel]=true;
			_printer.println(command[0] + " Channel " + (channel+1) + " has been tripped!");
			if(event == EVENTS.PARIND){
				if(channel%2==0) 
				{
					if(channel == 0){
						try{
							_racerList12.popWait();
						}catch(NullPointerException e){return;}
					}else{
						try{
							_racerList34.popWait();
						}catch(NullPointerException e){return;}
					}
					_startTimes[channel/2].add(new Time(command[0]));
				}
				else 
				{
					_finishTimes[channel/2].add(new Time(command[0]));
					if(channel==1){
						try{
							_run.get(_runNumber-1).addResult(""+_racerList12.peek().getBib(), getRacerTime(channel/2));
						}catch(NullPointerException e){return;}
					}else if(channel==3){
						try{
							_run.get(_runNumber-1).addResult(""+_racerList34.peek().getBib(), getRacerTime(channel/2));
						}catch(NullPointerException e){return;}
					}
				}
			}else if(event==EVENTS.IND){
				if(channel==0) 
				{
					try{
						_racerList12.popWait();
					}catch(NullPointerException e){_printer.println("No racers in queue");return;}
					_startTimes[0].add(new Time(command[0]));
					
				}
				else if(channel==1) 
				{
					_finishTimes[0].add(new Time(command[0]));
					try{
						_run.get(_runNumber-1).addResult(""+_racerList12.peek().getBib(), getRacerTime(0));
					}catch(NullPointerException e){_printer.println("No racers in queue");return;}
				}
			}
		}
		catch(NumberFormatException e) {_printer.println("Error triggering");}
		catch(ArrayIndexOutOfBoundsException er) {_printer.println(command[0] + " Not a valid channel");}
	}

	private void toggle(String... command) {
		try
		{
			int channel = Integer.parseInt(command[2])-1;
			_channelOn[channel]=!_channelOn[channel];
			_printer.println(command[0] + " Channel " + (channel+1) + " has been turned " + (_channelOn[channel]? "on" : "off"));
		}
		catch(NumberFormatException e) {_printer.println(command[0] + " Error turning on or off the channel");}
		catch(ArrayIndexOutOfBoundsException er) {_printer.println(command[0] + " Not a valid channel");}
	}

	private void event(String... command) {
		switch(command[2].toUpperCase())
		{
		case "IND":
		{
			event = EVENTS.IND;
			_printer.println(command[0] +" Event type set to " + event.toString());
			break;
		}
		case "PARIND":
		{
			event = EVENTS.PARIND;
			_printer.println(command[0] +" Event type set to " + event.toString());
			break;
		}
		case "GRP": case "PARGRP":
		default: System.out.println("That type of event is either [A]: Not supported (yet) or [B]: Not a valid event");
		}
	}

	private void connect(String... command) {
		try
		{
			int channel = Integer.parseInt(command[3])-1;
			_sensorsConnected[channel] = new Sensor();
			_printer.println(command[0] + " Channel " + (channel+1) + " has a sensor connected");
		}
		catch(NumberFormatException e) {_printer.println(command[0] + " Error connecting the sensor");}
		catch(ArrayIndexOutOfBoundsException er) {_printer.println(command[0] + " Not a valid channel");}
	}

	private void endrun() {
		_eventRunning=false;
		_racerList12 = new RaceQueuer();
		_racerList34 = new RaceQueuer();
		laneOne = true;
	}

	private void newrun(String... command) {
		if(_eventRunning == true) 
		{
			_printer.println(command[0] + " There is an event already going on. End the current run to begin a new one.");
			return;
		}
		++_runNumber;
		_run.add(new Result(command[0], event.toString()));
		_eventRunning=true;
		resetTimes();
		_printer.println("Event type " + event.toString() +" has been started");
	}

	private void power(String... command) {
		_isOn = !_isOn;
		_printer.println(command[0] + " Power is turned " + (_isOn? "on" : "off"));
		if(!_isOn) 
		{
			_channelOn = new boolean[_CHANNELS];
			_channelTripped = new boolean[_CHANNELS];
			_sensorsConnected = new Sensor[_CHANNELS];
			resetTimes();
			_eventRunning=false;
			event=EVENTS.IND;
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
	
	@Deprecated
	public void printRun(int startChannel)
	{
		if(startChannel<0 || startChannel>3) return;
		while(_startTimes[startChannel].size()!=0)
		{
			try{
				String time = Time.difference(_startTimes[startChannel].remove(), _finishTimes[startChannel].remove()).convertRawTime();
				_printer.println("Time for racer on channels ["+((startChannel*2)+1)+"] and ["+((startChannel*2)+2)+"] is " + time);
			}catch(NoSuchElementException e){
				System.out.println("You must start a race before printing times.");
			}
		}
	}
	
	public String getRacerTime(int startChannel)
	{
		try{
			String time = Time.difference(_startTimes[startChannel].remove(), _finishTimes[startChannel].remove()).convertRawTime();
			return time;
		}catch(NoSuchElementException e){
			System.out.println("You must start a race before printing times.");
			return "";
		}
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
	
	public int getResultSize(int run)
	{
		return _run.get(run-1).results();
	}
	
	public boolean eventIsFinished(){
		
		return _channelTripped[1];
	}
	
	public int getTimeSize(int channel){
		--channel;
		if(channel%2 == 0)
			return _startTimes[channel/2].size();
		return _finishTimes[channel/2].size();
      }
	
	public boolean isNewRunTriggered(){
		
		return _eventRunning;
		
	}
	
	public String getEvent(){

		return this.event.toString();
		
	}
	
	public int getRun()
	{
		return _runNumber;
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
	    int channel4 = 3;
	    if(_sensorsConnected[channel1] == null && _sensorsConnected[channel2] == null && 
	       _sensorsConnected[channel3] == null && _sensorsConnected[channel4] == null) {
	      return false;
	    }
	        
	    return true;
	  }

	public ArrayList<RaceQueuer> queueState(){
		ArrayList<RaceQueuer> copy = new ArrayList<RaceQueuer>();
		copy.add(_racerList12);
		copy.add(_racerList34);
		return copy;
		
	}
	
	public ArrayList<RaceQueuer> queueState(int channels){
		ArrayList<RaceQueuer> copy = new ArrayList<RaceQueuer>();
		if(channels==1)
		copy.add(_racerList12);
		else
		copy.add(_racerList34);
		return copy;
		
	}
	
}
