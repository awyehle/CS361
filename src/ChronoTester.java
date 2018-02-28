/**
 * @author Andrew Yehle, Michael Davis
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ChronoTester {
		
		static Chronotimer newChronotimer = new Chronotimer();
		
		@Test
		public void TestPower()
		{
			assertFalse(newChronotimer.isOn());
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			assertTrue(newChronotimer.isOn());
			
			input = "12:01:02.0 POWER";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			assertFalse(newChronotimer.isOn());
			
		}
		
		@Test
		public void TestReset()
		{
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertTrue(newChronotimer.isReset());
			
			input = "12:01:02.0 TOG 1";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertFalse(newChronotimer.isReset());
			
			input = "12:01:02.0 RESET";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			assertTrue(newChronotimer.isReset());
			
			input = "12:01:02.0 POWER";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
		}
		
		@Test
		public void TestCancel()
		{

			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			
	          input = "12:01:02.0 TOG 1";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          
	          input = "12:01:02.0 TRIG 1";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          
	          input = "12:01:02.0 TRIG 2";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          
	          assertFalse(newChronotimer.isCancled());
	          
	          input = "12:01:02.0 CANCEL";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          assertTrue(newChronotimer.isCancled());
	          
	          input = "12:01:02.0 POWER";
				command = input.split(" ");
				newChronotimer.runCommand(command);
			
		}
		
		@Test
		public void TestStartFinishEvent()
		{
			
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 TOG 1";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 TOG 2";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertFalse(newChronotimer.eventIsFinished());
			assertFalse(newChronotimer.eventIsStarted());
			
			input = "12:01:02.0 START";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertFalse(newChronotimer.eventIsFinished());
			assertTrue(newChronotimer.eventIsStarted());
			
			input = "12:01:02.0 FINISH";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertTrue(newChronotimer.eventIsFinished());
			assertTrue(newChronotimer.eventIsStarted());
			
			input = "12:01:02.0 POWER";
			command = input.split(" ");
			newChronotimer.runCommand(command);
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
