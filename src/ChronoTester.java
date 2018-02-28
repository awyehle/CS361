/**
 * @author Andrew Yehle, Michael Davis
 */
import static org.junit.Assert.*;

import org.junit.Test;

public class ChronoTester {
		
		static Chronotimer newChronotimer = new Chronotimer();
		
		
		@Test
		public void TestPower()
		{
			
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			assertTrue(newChronotimer.isOn());
			newChronotimer.runCommand(command);
			assertFalse(newChronotimer.isOn());
			
		}
		
		@Test
		public void TestReset()
		{
			
			
			
		}
		
		@Test
		public void TestCancel()
		{
			
			
		}
		
		@Test
		public void StartEvent()
		{
			
			
		}
		
		@Test
		public void TestNewRun()
		{
			
			
		}
		
		@Test
		public void TestDNF(){
			
			
			
		}
		
		@Test
		public void TestToggleSensor()
		{
			
			
		}
		
		@Test
		public void TestEnterNewRacer()
		{
			
			
		}
		
		@Test
		public void TestTripSensorChannel1()
		{
			
			
		}
		
		@Test
		public void TestTripSensorChannel2()
		{
			
			
		}
		
		@Test
		public void TestPrint()
		{
			
			
		}
		
		@Test
		public void TestEndRun()
		{
			
			
		}
		
		@Test
		public void TestTime(){
			
			
			
		}
		
		@Test
		public void raceQueuer(){
			
			
			
		}
		
}
