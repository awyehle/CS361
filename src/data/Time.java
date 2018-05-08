package data;
import java.sql.Timestamp;
import java.util.Date;

/**
 * A class which supports time measurements
 * @author Andrew Huelsman
 *
 */

@SuppressWarnings("rawtypes")
public class Time implements Comparable {

	private long _time;
	
	/**
	 * Create a time based off a timestamp 
	 * If 'null' is entered for the timestamp, this counts as a DNF time.
	 * @param timestamp the time in the format HH:MM:SS.nss
	 */
	public Time(String timestamp)
	{
		if(timestamp == null) _time=-1;
		else _time = convertTimestamp(timestamp);
	}
	
	/**
	 * Create a time based off a amount of time (in nanoseconds)
	 * @param time the time in nanoseconds
	 */
	public Time(long time)
	{
		if(time<0) throw new IllegalArgumentException("Time cannot be negative.");
		_time = time;
	}
	
	/**
	 * Creates a time based off the system's clock
	 */
	public Time()
	{
		this(new Timestamp(new Date().getTime()).toString().split(" ")[1]);
	}
	
	/**
	 * Gets the time of this object
	 * @return the time in nanoseconds
	 */
	public long getTime()
	{
		return _time;
	}
	
	/**
	 * Takes a readable timestamp and converts it into nanoseconds
	 * @param timestamp a string in the format mm:ss.ns where mm is minutes, ss is seconds, and nns is nanoseconds
	 * @return the timestamp's time in nanoseconds
	 */
	private long convertTimestamp(String timestamp)
	{
		if(timestamp==null) throw new IllegalArgumentException("timestamp cannot be null");
		String[] bits = timestamp.split(":");
		if(bits.length<2 || bits.length>3)
			throw new IllegalArgumentException("timestamp in invalid format: mm:ss.ms");
		String[] minMS = bits[bits.length-1].split("\\.");
		if(minMS.length!=2)
			throw new IllegalArgumentException("timestamp in invalid format: mm:ss.ms");
		
		int min=0, sec=0, ns = 0;
		try
		{
			min = Integer.parseInt(bits[bits.length-2]);
			if(bits.length==3)
				min+=Integer.parseInt(bits[0])*60;
			sec = Integer.parseInt(minMS[0]);
			ns = Integer.parseInt(minMS[1]);
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException("timestamp in invalid format: mm:ss.ms");
		}
		
		long ret = ns+(1000*sec)+(60*min*1000);
		
		return ret;
	}
	
	/**
	 * Converts the time into a readable timestamp of the format HH:MM:SS.nss where
	 * HH is hours, MM is minutes, SS is seconds, and nss is nanoseconds
	 * @return this time in a readable timestamp format
	 */
	public String convertRawTime()
	{
		if(_time<0) return "DNF";
			long ns = _time;
			long sec = _time/1000;
			ns = _time%1000;
			long min = sec/60;
			sec = sec%60;
			long hr = min/60;
			min = min%60;
			
			return String.format("%02d:%02d:%02d.%03d", hr,min,sec, ns);
	}

	/**
	 * Takes two times and finds the absolute difference between the two.
	 * @param time1 first time
	 * @param time2 second time
	 * @return difference between time1 and time2
	 */
	public static Time difference(Time time1, Time time2)
	{
		if(time1==null || time2 == null) return new Time(null);
		if(time1._time < 0 || time2._time <0) return new Time(null);
		long diff = time2._time-time1._time;
		if(diff<0) return new Time(null);
		return new Time(diff);
	}

	/**
	 * Compares this time with another time.
	 * If this time is lower, or that time is DNF, then this time comes before that time
	 * If that time is lower, or this time is DNF, then that time comes before this time
	 * If that time is not an instance of Time, then this time comes before that
	 */
	@Override
	public int compareTo(Object arg0) {
		if(arg0 == null) return -1;
		if(!(arg0 instanceof Time)) return -1;
		else
		{
			if(((Time) arg0)._time<0 || this._time < 0) return -1;
			return (int) (this._time-((Time) arg0)._time);
		}
	}
}
