
public class Tester {

	public static void main(String[] args) throws InterruptedException
	{
		//sample run
		Chronotimer c = new Chronotimer();
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
		c.runCommand(new Time().convertRawTime(), "START");
		Thread.sleep(16462);
		c.runCommand(new Time().convertRawTime(), "FINISH");
		c.printRun(0);
	}
	
	private static void printString(String[] a)
	{
		for(String b : a)
			System.out.println(b);
	}
}
