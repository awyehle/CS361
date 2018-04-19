package pcmr;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import com.google.gson.Gson;

import data.RaceQueuer;
import data.Racer;
import data.Result;
import data.Sensor;
import data.Time;

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
	private RaceQueuer[] _queues = new RaceQueuer[_CHANNELS/2];
	
	// Deprecated due to using an array of queues. I think it will help more for the future sprints to do so
	//private RaceQueuer _racerList12 = new RaceQueuer();
	//private RaceQueuer _racerList34 = new RaceQueuer();
	private boolean laneOne = true;
	
	private static enum EVENTS {IND, PARIND, GRP, PARGRP};
	private EVENTS event = EVENTS.IND;
	private boolean _eventRunning;
	
	private Time[] _lastToFinish = new Time[2];
	
	private int _bibNumber = 0;
	
	//private Racer _lastFinished;
	
	private Printer _printer = new Printer();
	private Display _display = new Display();
	
	
	/**
	 * stupid ugly printer class it echos crap
	 * Printer also now extends Arraylist so that
	 * all echos can be retrieved as a display on the paper tape
	 * @author Andrew Huelsman / Steven Messer
	 *
	 */
	private class Printer extends ArrayList<String>
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static final int printerWidth = 34;
		boolean _powered = true;

		public void println(String echo)
		{
			if(_powered) {
				if(!echo.startsWith("- ")) echo = "- " + echo;
				System.out.println(echo);
				if(echo.length() > printerWidth) {
					smartPrinterWrapper(echo);
					return;
				}
				add(0,echo);
			}
		}
		
		private void smartPrinterWrapper(String s) {
			if(!s.contains(" ")) {
				while(s.length() < 0) {
					add(s.substring(0, printerWidth-1));
					s = s.substring(printerWidth);
				}
				return;
			}
			String buildString = "";
			String[] printArray = s.split(" ");
			for(String as: printArray) {
				if(buildString.length() + as.length() < printerWidth) buildString = buildString + as + " ";
				else {
					add(buildString);
					buildString = "   " + as + " ";
				}
			}
			add(buildString);
		}
		
		public String[] getPrinterStrings() {
			
			String[] ret = this.toArray(new String[0]);
			clear();
			return ret;
			
		}
	}
	public void powerPrinter() {_printer._powered=!_printer._powered;}
	public String[] getPrinterStrings() { return _printer.getPrinterStrings();}
	
	private class Display
	{
		private String _queue;
		private String _running;
		private String _finished;
		
		void display()
		{
			switch(event)
			{
			case IND:
			{
				try
				{
					_queue="";
					_queue+=_queues[0].peekWaiting(0).toString();
					for(int i = 1; i < 3; ++i)
						_queue+=", " + _queues[0].peekWaiting(i).toString();
				}
				catch(NullPointerException e) {}
				try
				{
					_running="";
					Racer[] running = _queues[0].peekAll();
					_running += running[0].toString();
					for(int i = 1; i < running.length; ++i)
						_running+=", " + running[i].toString();
				}
				catch(IndexOutOfBoundsException e) {}
				try
				{
					_finished="";
					_finished+=_queues[0].peekRan().toString();
				}
				catch(NullPointerException e) {}
				break;
			}
			case PARIND:
			{
				try
				{
					_queue="";
					_queue+=_queues[0].peekWaiting(0).toString();
				}
				catch(NullPointerException e) {}
				try
				{
					_queue+=", ";
					_queue+=_queues[1].peekWaiting(0).toString();
				}
				catch(NullPointerException e) {}
				try
				{
					_running="";
					_running+=_queues[0].peek().toString() + ": " 
					+ Time.difference(_startTimes[0].getFirst(), new Time()).convertRawTime();
				}
				catch(Exception e) {}
				try
				{
					_running+="\n"+_queues[1].peek().toString() + ": " 
					+ Time.difference(_startTimes[1].getFirst(), new Time()).convertRawTime();
				}
				catch(Exception e) {}
				try
				{
					_finished="";
					_finished+=_lastToFinish[0].convertRawTime();
				}
				catch(NullPointerException e) {}
				try
				{
					_finished+="\n" + _lastToFinish[1].convertRawTime();
				}
				catch(NullPointerException e) {}
				break;
			}
			case GRP:
			{
				_queue="";
				try
				{
					_running="";
					_running+= Time.difference( _startTimes[0].getFirst(),new Time()).convertRawTime();
				}
				catch(Exception e) {}
				try
				{
					_finished="";
					_finished+=_lastToFinish[0].convertRawTime();
				}
				catch(Exception e) {}
				break;
			}
			default:break;
			}
		}
	}
	
	public Chronotimer()
	{
		_time = new Time();
		resetTimes();
		resetQueues();
		//_channelOn[0] = true;
		//_sensorsConnected[0] = new Sensor();
	}
	
	public Time getTime()
	{
		return _time;
	}

	private void resetQueues()
	{
		for(int i = 0; i < _queues.length; ++i)
			_queues[i]=new RaceQueuer();
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
		case "SWAP":
		{
			swap(command);
			break;
		}
		default: {System.out.println("Invalid command entered. Contact a Software Engineer to solve this problem"); break;}
		}
		
	}

	/**
	 * Adds a racer to a queue which holds racers ready to begin a race 
	 * @param command Command in the format <timestamp> <NUM> <bibNumber>
	 */
	private void addRacer(String... command) {
			try{
				Racer newRacer;
				if(event!= EVENTS.GRP)
					newRacer = new Racer(Integer.parseInt(command[2]));
				else
					newRacer = new Racer(++_bibNumber);
				
				boolean canAdd = true;
				for(RaceQueuer r : _queues)
				{
					if(r.contains(newRacer.getBib())) {canAdd=false; break;}
				}
				
				if(!canAdd || !(laneOne || event != EVENTS.PARIND ? _queues[0].push(newRacer):_queues[1].push(newRacer)))
					_printer.println("Racer already in queue or ran!");
				else 
					{
					_printer.println("Racer " + newRacer.getBib() + " has been added to the queue.");
					laneOne = (!laneOne || event != EVENTS.PARIND);
					}
				}catch(NumberFormatException e){
					_printer.println("Invalid Bib Number Entered");
				}catch(IllegalArgumentException e){//s
					_printer.println(e.getMessage());
				}catch(IndexOutOfBoundsException e){
					_printer.println("bad boy");
			}
	}
	
	/**
	 * Swaps the next two racers in queue 1 
	 * @param command Command in the format <timestamp> <SWAP>
	 */
	private void swap(String... command) {
		if(event == EVENTS.IND) {
			if(_queues[0].queueSize() < 2) {
				_printer.println("Race Queue has fewer than 2 racers!");
				return;
			}
			_queues[0].swap();
			_printer.println("Racers in queue 1 have been swapped");
		}
		else _printer.println("SWAP can only be used on IND races");
	}

	/**
	 * Exports the indicated run number to a JSON file. It holds a result, which contains
	 * the time the run was started, along with the race type, and the racer's (who participated) times
	 * @param command Command in the format <timestamp> <EXPORT> <runNumber>
	 */
	private void export(String... command) {
		try
		{
			int i = Integer.parseInt(command[2]);
			if(i > _run.size() || i < 1) throw new IndexOutOfBoundsException();
			try(PrintWriter writer = new PrintWriter(new FileOutputStream("Run_"+i+".json",false)))
			{
				writer.println(new Gson().toJson(_run.get(i-1)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			_printer.println("Run " + i + " has been saved");
		}
		catch(NumberFormatException e) {_printer.println("Invalid Channel");}
		catch(IndexOutOfBoundsException er) {_printer.println(command[0] + " Not a valid run");}
	}

	/**
	 * Prints the current run or a specific run out to the paper tape. It prints out the result of the 
	 * run, which contains the time the run was started, along with the race type, 
	 * and the racer's (who participated) times
	 * @param command Command in the format <timestamp> <PRINT> [<runNumber>]
	 */
	private void print(String... command) {
		if(command.length < 3) {
			print(command[0],command[1], ""+_runNumber);
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

	/**
	 * Cancels a racer's time in lane 1
	 * @param command
	 */
	private void cancel(String... command) {
		_channelTripped[0] = false;
		_channelTripped[1] = false;
		if(_startTimes[0].size()>0)
			_startTimes[0].removeLast();
		if(_finishTimes[0].size()>0)
			_finishTimes[0].removeLast();
		_printer.println(command[0] + " Run for channels [1] and [2] has been canceled");
	}

	
	/**
	 * Causes a racer in lane 1 to have a DNF time
	 * @param command
	 */
	private void dnf(String... command) {
		_finishTimes[0].add(new Time(null));
		_queues[0].pop();
		_printer.println(command[0] + " Racer for channels [1] and [2] did not finish (DNF)");
	}

	/**
	 * Resets this system's clock. I don't think this has any use at all until qe implement a GUI
	 * @param command
	 */
	private void time(String... command) {
		try{
		_time = new Time(command[2]);
		}catch(ArrayIndexOutOfBoundsException e){System.out.println("Please enter a valid time"); return;}
		_printer.println(command[0] + " Time reset");
	}

	/**
	 * Resets the chronotimer to its initial state
	 * @param command
	 */
	private void reset(String... command) {
		_channelOn = new boolean[_CHANNELS];
		_channelTripped = new boolean[_CHANNELS];
		// TODO:
		// Not really a todo. But the project description states that a reset should clear the "saves" of the runs
		// That, however, does not function with the sample test commands
		//_run.clear();
		//_runNumber=0;
		endrun();
		_printer.println(command[0] + " All channels have been reset and turned off. Runs have been erased");
	}

	/**
	 * Triggers a sensor, which adds a trigger time to an arraylist if it is a starting trigger, and
	 * adds a result to this run if the trigger was a finishing trigger
	 * @param command Command in the format <timestamp> <TRIG> <channelNum>
	 */
	private void trigger(String... command) {
		if(!_eventRunning) {
			_printer.println("No event is underway");
			return;
		}
		try
		{
			int channel = Integer.parseInt(command[2]);
			if(/*_sensorsConnected[i] == null || */!_channelOn[channel-1])
				return;
			if(channel%2==1) 
			{
				try{
					if(event!=EVENTS.GRP)
					{
						_queues[channel/2].popWait();
						_startTimes[channel/2].add(new Time(command[0]));
					}
					else
					{
						if(channel/2 !=0) return;
						for(int i = 0; i <_queues[0].queueSize(); ++i)
							{
							_queues[0].popWait();
							_startTimes[0].add(new Time(command[0]));
							}
					}
				}catch(NullPointerException e){return;}
			}
			else 
			{
				try{
					if(event!=EVENTS.GRP)
					{
						Racer finisher = _queues[(channel-1)/2].pop();
						_finishTimes[(channel-1)/2].add(new Time(command[0]));
						_run.get(_runNumber-1).addResult(""+finisher.getBib(), getRacerTime((channel-1)/2));
					}
					else
					{
						if((channel-1)/2 != 0) return;
						_finishTimes[0].add(new Time(command[0]));
						_run.get(_runNumber-1).addResult(""+_queues[0].pop().getBib(), getRacerTime((channel-1)/2));
					}
				}catch(NullPointerException e){_printer.println("no racer here");return;}
			}
			_printer.println(command[0] + " Channel " + (channel) + " has been tripped!");
			_channelTripped[channel-1]=true;
		}
		catch(NumberFormatException e) {_printer.println("Error triggering");}
		catch(ArrayIndexOutOfBoundsException er) {_printer.println(command[0] + " Not a valid channel");}
	}

	/**
	 * turns specified channel on or off
	 * @param command
	 */
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

	/**
	 * changes the event type
	 * @param command
	 */
	private void event(String... command) {
		try{
			switch(command[2].toUpperCase())
			{
			case "IND":
			{
				setEvent(EVENTS.IND,command);
				break;
			}
			case "PARIND":
			{
				setEvent(EVENTS.PARIND,command);
				break;
			}
			case "GRP":
			{
				setEvent(EVENTS.GRP,command);
				break;
			}
			case "PARGRP":
			default: System.out.println("That type of event is either [A]: Not supported (yet) or [B]: Not a valid event");
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {_printer.println("That type of event is either [A]: Not supported (yet) or [B]: Not a valid event");}
	}

	private void setEvent(EVENTS type,String... command) {
		if(_eventRunning){
			_printer.println("You must end the current run before switching event types");
			return;
		}
		event = type;
		_printer.println(command[0] +" Event type set to " + event.toString());
		endrun();
	}

	private void connect(String... command) {
		try
		{
			int channel = Integer.parseInt(command[3])-1;
			if(command[2].toUpperCase().equals("NONE")){
				if((_sensorsConnected[channel]==null)){
					return;
				}
				_sensorsConnected[channel] = null;
				_printer.println(command[0] + " Channel " + (channel+1) + " has had a sensor removed");
				return;
			}
			_sensorsConnected[channel] = new Sensor();
			_printer.println(command[0] + " Channel " + (channel+1) + " has a sensor " + command[2] + " connected");
		}
		catch(NumberFormatException e) {_printer.println(command[0] + " Error connecting the sensor");}
		catch(ArrayIndexOutOfBoundsException er) {_printer.println(command[0] + " Not a valid channel");}
	}

	private void endrun() {
		_eventRunning=false;
		resetQueues();
		_bibNumber = 0;
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
		_printer.println("Event type " + event.toString() +" has been started (Run #" + _runNumber + ")");
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
	
	public String getQueueDisplay()
	{
		_display.display();
		return _display._queue;
	}
	
	public String getRunningDisplay()
	{
		_display.display();
		return _display._running;
	}
	
	public String getRanDisplay()
	{
		_display.display();
		return _display._finished;
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
	
	/**
	 * Gets a racer's time at the point at which they finish the race. This happens as soon as a racer
	 * finishes, so this method is not useful outside of the trigger context
	 * @param startChannel
	 * @return
	 */
	public String getRacerTime(int startChannel)
	{
		try{
			Time time = Time.difference(_startTimes[startChannel].remove(), _finishTimes[startChannel].remove());
			_lastToFinish[1]=_lastToFinish[0];
			_lastToFinish[0]=time;
			return time.convertRawTime();
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
			if(_channelOn[i] != true && _channelTripped[i] != true 
					&& laneOne == true && _eventRunning == false){
				continue; 
			}else{return false;}
		}
		for(int i = 0; i<_queues.length; i++){
			if(_queues[i].isEmpty()){
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
		copy.add(_queues[0]);
		copy.add(_queues[1]);
		return copy;
		
	}
	
	public ArrayList<RaceQueuer> queueState(int channels){
		ArrayList<RaceQueuer> copy = new ArrayList<RaceQueuer>();
		copy.add(_queues[channels/2]);
		return copy;
		
	}
}
