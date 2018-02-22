import java.sql.Timestamp;
import java.util.Date;

public class Tester {

	public static void main(String[] args)
	{
		Timestamp ts = new Timestamp(new Date().getTime());
		System.out.println(ts.toString().split(" ")[1]);
		Time timestamp = new Time(ts.toString().split(" ")[1]);
		System.out.println(timestamp.convertRawTime());
	}
	
	private static void printString(String[] a)
	{
		for(String b : a)
			System.out.println(b);
	}
}
