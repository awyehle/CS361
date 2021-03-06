package data;
import java.util.*;
/**
 * Simple object to hold racer times.
 * @author Andrew Huelsman
 *
 */
public class Result {

	private String _runType, _time;
	private int _manyResults, _run;
	
	private HashMap<Integer,Time> _results= new HashMap<>();
	
	/**
	 * Create a new result for a run
	 * @param time The time at which this result should be created
	 * @param runType The type of run which this result will store data for
	 */
	public Result(int run, String time, String runType)
	{
		if(time == null || runType == null)
			throw new IllegalArgumentException("Cannot store null results");
		this._runType = runType;
		this._time = time;
		this._run = run;
	}
	
	/**
	 * Add's a racer's number and event time to this result
	 * @param racer Racer's number. Will not add a duplicate racer as the same racer should not compete 
	 * twice in one run
	 * @param time Racer's time for this run
	 * @return True if the racer was successfully added, false if not
	 */
	public boolean addResult(int racer, Time time)
	{
		if(_results.containsKey(racer) && _results.get(racer)!=null) return false;
		else if (_results.containsKey(racer)) 
		{
			_results.put(racer, time);
			return true;
		}
		
		++_manyResults;
		_results.put(racer, time);
		
		return true;
	}
	
	/**
	 * Utilized for testing purposes; returns how many racers are stored in this result
	 * @return number of racers used to create this result
	 */
	public int results()
	{
		return _manyResults;
	}
	
	/**
	 * Set the time at which this event was started
	 * @param timestamp the time this event started
	 * @return true
	 */
	public boolean setTime(String timestamp)
	{
		_time = timestamp;
		return true;
	}
	
	/**
	 * Set the type of event this result was for
	 * @param event the race type
	 */
	public void setEvent(String event)
	{
		if(event==null) throw new IllegalArgumentException();
		_runType=event;
	}
	
	@Override
	public String toString()
	{
		String str = _time + " " + _runType + ": Run " + _run + "\n";
		for(int i = 0; i < _results.keySet().size(); ++i)
		{
			str += _results.keySet().toArray()[i].toString() + " " + _runType 
					+ " " + _results.get(_results.keySet().toArray()[i]).convertRawTime() + "\n";
		}
		return str;
	}
	
	/**
	 * Get an array of all racer bib numbers that participated in this race
	 * @return an Integer array of bib numbers
	 */
	public Integer[] getRacers()
	{
		return _results.keySet().toArray(new Integer[0]);
	}
	
	/**
	 * Get the racer's time from this race
	 * @param whom the bib number of the racer
	 * @return the time of the racer, or null if that racer was not in this race
	 */
	public Time getTimeForRacer(int whom)
	{
		return _results.get(whom);
	}
	
	/**
	 * Gets the run number associated with this result
	 * @return the run number
	 */
	public int getRun()
	{
		return _run;
	}
	
	/**
	 * Gets the event associated with this result
	 * @return the race type
	 */
	public String getEvent()
	{
		return _runType;
	}
}
