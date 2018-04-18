import java.util.Random;

import data.Time;
import pcmr.Chronotimer;

public class Tester {

	public static void main(String[] args) throws InterruptedException
	{
		Chronotimer c = new Chronotimer();
		System.out.println(1/2);
		c.runCommand(new Time().convertRawTime(), "POWER");
		Thread.sleep(2854);
		c.runCommand(new Time().convertRawTime(), "CONN", "EYE", "1");
		Thread.sleep(1567);
		c.runCommand(new Time().convertRawTime(), "CONN", "EYE", "2");
		Thread.sleep(1567);
		c.runCommand(new Time().convertRawTime(), "TOG", "1");
		Thread.sleep(1567);
		c.runCommand(new Time().convertRawTime(), "TOG", "2");
		Thread.sleep(1567);
		c.runCommand(new Time().convertRawTime(), "TOG", "3");
		Thread.sleep(1567);
		c.runCommand(new Time().convertRawTime(), "EVENT", "PARIND");
		Thread.sleep(1567);
		c.runCommand(new Time().convertRawTime(), "NUM", "133");
		Thread.sleep(1567);
		c.runCommand(new Time().convertRawTime(), "NUM", "163");
		Thread.sleep(1567);
		c.runCommand(new Time().convertRawTime(), "NEWRUN");
		Thread.sleep(1567);
		c.runCommand(new Time().convertRawTime(), "START");
		Thread.sleep(12);
		c.runCommand(new Time().convertRawTime(), "TRIG", "3");
		for(int i = 0; i < 10; ++i)
		{
			Thread.sleep(new Random().nextInt(1000) + 1000);
			System.out.println(c.getRunningDisplay());
		}
	}
		
	@SuppressWarnings("unused")
	private static void printString(String[] a)
	{
		for(String b : a)
			System.out.println(b);
	}
}
