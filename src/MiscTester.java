import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Tests various classes to make sure usage is proper
 * @author Andrew Huelsman
 *
 */
public class MiscTester {

	@Test
	public void testResult()
	{
		Result r = new Result("12:34:52.162", "IND");;
		assertTrue(r.addResult("111", new Time().convertRawTime()));
		assertTrue(r.addResult("101", new Time().convertRawTime()));
		assertFalse(r.addResult("111", new Time().convertRawTime()));
	}
	
	@Test
	public void testTime_nanoseconds()
	{
		Time t;
		t = new Time(312540);
		assertEquals("00:05:12.540", t.convertRawTime());
		t = new Time(99999999);
		assertEquals("27:46:39.999", t.convertRawTime());
		t = new Time(1000);
		assertEquals("00:00:01.000", t.convertRawTime());
		t = new Time(60000);
		assertEquals("00:01:00.000", t.convertRawTime());
		t = new Time(600000);
		assertEquals("00:10:00.000", t.convertRawTime());
		t = new Time(3600000);
		assertEquals("01:00:00.000", t.convertRawTime());
	}
		
	@Test
	public void testTime_stamp()
	{
		Time t;
		t = new Time("00:05:12.540");
		assertEquals("00:05:12.540", t.convertRawTime());
		t = new Time("27:46:39.999");
		assertEquals("27:46:39.999", t.convertRawTime());
		t = new Time("00:00:01.000");
		assertEquals("00:00:01.000", t.convertRawTime());
		t = new Time("00:01:00.000");
		assertEquals("00:01:00.000", t.convertRawTime());
		t = new Time("00:10:00.000");
		assertEquals("00:10:00.000", t.convertRawTime());
		t = new Time("01:00:00.000");
		assertEquals("01:00:00.000", t.convertRawTime());
	}
	
	@Test
	public void testTime_dnf()
	{
		Time t;
		t = new Time(null);
		assertEquals("DNF", t.convertRawTime());;
	}
	
	@Test
	public void testRaceQueue()
	{
		RaceQueuer r;
		r = new RaceQueuer(new ArrayList<Racer>());
		Racer _101 = new Racer(101);
		Racer _102 = new Racer(102);
		Racer _103 = new Racer(103);
		Racer _104 = new Racer(104);

		assertTrue(r.push(_101));
		assertTrue(r.push(_102));
		assertTrue(r.push(_103));
		assertTrue(r.push(_104));
		
		assertFalse(r.push(_101));
		assertFalse(r.push(_102));
		assertFalse(r.push(_103));
		assertFalse(r.push(_104));
		
		/*
		 * The block below is necessary to
		 * move racers from wait queue to in progress queue.
		 */
		r.popWait();
		r.popWait();
		r.popWait();
		r.popWait();

		assertEquals(_101,r.pop());
		assertEquals(_102,r.pop());
		assertEquals(_103,r.pop());
		assertEquals(_104,r.pop());
		
		assertFalse(r.push(_101));
		assertFalse(r.push(_102));
		assertFalse(r.push(_103));
		assertFalse(r.push(_104));

	}
	
}
