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
		public void TestInvalidCommand()
		{
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 TIDEPOD";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertTrue(newChronotimer.isOn());
			assertFalse(newChronotimer.isNewRunTriggered());
			assertFalse(newChronotimer.isConnected());
			assertFalse(newChronotimer.isToggled());
			
			input = "12:01:02.0 POWER";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
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
	          
	          //assertFalse(newChronotimer.isCancled()); NOT PROPER IMPLEMENTATION ANYMORE. MAY COME IN HANDY IN FUTURE
	          
	          //Channel 1 should have 1 time within it
	          assertEquals(1,newChronotimer.getTimeSize(1));
	          
	          //Since Channel 2 was never toggled on, it should not have a time in it
	          assertEquals(0,newChronotimer.getTimeSize(2));
	          
	          input = "12:01:02.0 CANCEL";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          assertEquals(0,newChronotimer.getTimeSize(1));
	          assertEquals(0,newChronotimer.getTimeSize(2));

	          input = "12:01:02.0 TOG 2";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          
	          input = "12:01:02.0 TRIG 1";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          
	          input = "12:01:02.0 TRIG 2";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          
	          //Channels 1 and 2 should have 1 time within them
	          assertEquals(1,newChronotimer.getTimeSize(1));
	          assertEquals(1,newChronotimer.getTimeSize(2));
	          
	          input = "12:01:02.0 CANCEL";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          assertEquals(0,newChronotimer.getTimeSize(1));
	          assertEquals(0,newChronotimer.getTimeSize(2));
	          
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
		public void TestNewEndRun()
		{
			
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 NEWRUN";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertTrue(newChronotimer.isNewRunTriggered());
			
			input = "12:01:02.0 ENDRUN";
			command = input.split(" ");
			newChronotimer.runCommand(command);

			assertFalse(newChronotimer.isNewRunTriggered());
			
			input = "12:01:02.0 POWER";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
		}
		
		@Test
		public void TestDNF(){
			
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
		      
		      input = "12:01:02.0 DNF";
		      command = input.split(" ");
		      newChronotimer.runCommand(command);
		      
		      assertFalse(newChronotimer.eventIsFinished());
		      assertTrue(newChronotimer.eventIsStarted());
		      
		      input = "12:01:02.0 POWER";
		      command = input.split(" ");
		      newChronotimer.runCommand(command);
			
		}
		
		@Test
		public void TestToggleSensor()
		{
			
			String input = "12:01:02.0 POWER";
		      String[] command = input.split(" ");
		      newChronotimer.runCommand(command);
		      
		      assertFalse(newChronotimer.isToggled());
		      
		      input = "12:01:02.0 TOG 1";
		      command = input.split(" ");
		      newChronotimer.runCommand(command);
		      
		      input = "12:01:02.0 TOG 2";
		      command = input.split(" ");
		      newChronotimer.runCommand(command);
		      
		      assertTrue(newChronotimer.isToggled());
		      
		      
		      input = "12:01:02.0 START";
		      command = input.split(" ");
		      newChronotimer.runCommand(command);
		      
		      
		      input = "12:01:02.0 POWER";
		      command = input.split(" ");
		      newChronotimer.runCommand(command);
			
		}
		
		@Test
		public void TestTripSensorChannel1()
		{
			
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 TOG 1";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertFalse(newChronotimer.eventIsStarted());
			
			input = "12:01:02.0 START";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertTrue(newChronotimer.eventIsStarted());
			
			input = "12:01:02.0 POWER";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
		}
		
		@Test
		public void TestTripSensorChannel2()
		{
			
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 TOG 2";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertFalse(newChronotimer.eventIsFinished());
			
			input = "12:01:02.0 FINISH";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertTrue(newChronotimer.eventIsFinished());
			
			input = "12:01:02.0 POWER";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
		}
		
		@Test
		public void Testevent(){
			
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 EVENT IND";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertEquals(newChronotimer.getEvent(), "IND");
			
			input = "12:01:02.0 POWER";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
		}
		
		@Test
		public void TestCONN(){
			
			String input = "12:01:02.0 POWER";
		      String[] command = input.split(" ");
		      newChronotimer.runCommand(command);
		      
		      assertFalse(newChronotimer.isConnected());
		      
		      input = "12:01:02.0 CONN EYE 1";
		      command = input.split(" ");
		      newChronotimer.runCommand(command);
		      
		      input = "12:01:02.0 CONN EYE 2";
		      command = input.split(" ");
		      newChronotimer.runCommand(command);
		      
		      input = "12:01:02.0 CONN GATE 3";
		      command = input.split(" ");
		      newChronotimer.runCommand(command);
		      
		      input = "12:01:02.0 CONN GATE 4";
		      command = input.split(" ");
		      newChronotimer.runCommand(command);
		      
		      assertTrue(newChronotimer.isConnected());
		      
		      input = "12:01:02.0 POWER";
		      command = input.split(" ");
		      newChronotimer.runCommand(command);
			
		}
		
}
