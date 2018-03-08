/**
 * Simple object to hold racer times
 * @author Andrew Huelsman
 *
 */
public class Result {

	@SuppressWarnings("unused")
	private String _racer, _time;
	
	public Result(String _racer, String _time)
	{
		if(_racer == null || _time == null)
			throw new IllegalArgumentException("Cannot store null results");
		this._racer = _racer;
		this._time = _time;
	}
	
}
