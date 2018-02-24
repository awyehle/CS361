import java.sql.Timestamp;
import java.util.Date;

/**
 * A class which will support time measurements
 * @author Andrew Huelsman
 *
 */

public class Time {

	private long _time;
	
	public Time(String timestamp)
	{
		if(timestamp == null) _time=-1;
		else _time = convertTimestamp(timestamp);
	}
	
	public Time(long time)
	{
		if(time<0) throw new IllegalArgumentException("Time cannot be negative.");
		_time = time;
	}
	
	public Time()
	{
		this(new Timestamp(new Date().getTime()).toString().split(" ")[1]);
	}
	
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
	 * Converts the time (in nanoseconds) into a readable timestamp of the format mm:ss.nns where
	 * mm is minutes, ss is seconds, and nns is nanoseconds
	 * @return this time in a readable timestamp format
	 */
	public String convertRawTime(boolean HMS)
	{
		if(_time<0) return "DNF";
		if(!HMS)
		{
			long ns = _time;
			long sec = _time/1000;
			ns = _time%1000;
			long min = sec/60;
			sec = sec%60;
			
			return String.format("%02d:%02d.%03d", min,sec,ns);
		}
		else
		{
			long ns = _time;
			long sec = _time/1000;
			ns = _time%1000;
			long min = sec/60;
			sec = sec%60;
			long hr = min/60;
			min = min%60;
			
			return String.format("%02d:%02d:%02d", hr,min,sec);
		}
	}

	/**
	 * Takes two times and finds the absolute difference between the two.
	 * @param time1 first time
	 * @param time2 second time
	 * @return absolute difference between time1 and time2
	 */
	public static Time difference(Time time1, Time time2)
	{
		return new Time(Math.abs(time1.getTime()-time2.getTime()));
	}
}
