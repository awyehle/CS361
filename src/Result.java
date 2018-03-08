import java.util.*;
/**
 * Simple object to hold racer times
 * @author Andrew Huelsman
 *
 */
public class Result {

	private String _runType, _time;
	
	private HashMap<String,String> _results= new HashMap<>();
	
	public Result(String time, String runType)
	{
		if(time == null || runType == null)
			throw new IllegalArgumentException("Cannot store null results");
		this._runType = runType;
		this._time = time;
	}
	
	public boolean addResult(String racer, String time)
	{
		if(time == null || racer == null)
			throw new IllegalArgumentException("Cannot store null results");
		if(_results.containsKey(racer)) return false;
		else
			_results.put(racer, time);
		return true;
	}
	
	@Override
	public String toString()
	{
		String str = _time + " " + _runType + "\n";
		for(int i = 0; i < _results.keySet().size(); ++i)
		{
			str += _results.keySet().toArray()[i] + " " + _runType + " " + _results.get(_results.keySet().toArray()[i]) + "\n";
		}
		return str;
	}
}
