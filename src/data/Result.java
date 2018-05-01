package data;
import java.util.*;
/**
 * Simple object to hold racer times.
 * @author Andrew Huelsman
 *
 */
public class Result {

	private String _runType, _time;
	private int _manyResults;
	
	private HashMap<Racer,Time> _results= new HashMap<>();
	
	/**
	 * Create a new result for a run
	 * @param time The time at which this result should be created
	 * @param runType The type of run which this result will store data for
	 */
	public Result(String time, String runType)
	{
		if(time == null || runType == null)
			throw new IllegalArgumentException("Cannot store null results");
		this._runType = runType;
		this._time = time;
	}
	
	/**
	 * Add's a racer's number and event time to this result
	 * @param racer Racer's number. Will not add a duplicate racer as the same racer should not compete 
	 * twice in one run
	 * @param time Racer's time for this run
	 * @return True if the racer was successfully added, false if not
	 */
	public boolean addResult(Racer racer, Time time)
	{
		if(racer == null)
			throw new IllegalArgumentException("Cannot store null racer");
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
	
	@Override
	public String toString()
	{
		String str = _time + " " + _runType + "\n";
		for(int i = 0; i < _results.keySet().size(); ++i)
		{
			str += _results.keySet().toArray()[i].toString() + " " + _runType 
					+ " " + _results.get(_results.keySet().toArray()[i]).convertRawTime() + "\n";
		}
		return str;
	}
	
	public Racer[] getRacers()
	{
		return _results.keySet().toArray(new Racer[0]);
	}
	
	public Time getTimeForRacer(Racer whom)
	{
		return _results.get(whom);
	}
}
