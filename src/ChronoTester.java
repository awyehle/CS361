/**
 * @author Andrew Yehle, Michael Davis
 */
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ChronoTester {
		
		static Chronotimer newChronotimer = new Chronotimer();
		
		@Before
		public void fijfesgsdfgd()
		{
		newChronotimer= new Chronotimer();
		}
		/**
		 * Tests the POWER command turns on and off Chronotimer
		 */
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
		
		/**
		 * Tests to make sure an invalid command is not accepted and
		 * does not mutate anything
		 */
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
		
		/**
		 * Tests the reset command which calls Chronotimer's isReset method
		 */
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
		
		/**
		 * Tests to make sure an event is properly cancelled using
		 * the cancel method
		 */
		@Test
		public void TestCancel()
		{

			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			
	          input = "12:01:02.0 NEWRUN";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
			
	          input = "12:01:02.0 TOG 1";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);

	          input = "12:01:02.0 NUM 1";
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

	          input = "12:01:02.0 ENDRUN";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          
	          input = "12:01:02.0 NEWRUN";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);

	          input = "12:01:02.0 NUM 1";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          
	          input = "12:01:02.0 TOG 2";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          
	          input = "12:01:02.0 TRIG 1";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          
	          input = "12:01:02.0 TRIG 2";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          
	          //Channels 1 and 2 should have not have time within them, as the times are removed and stored into a result
	          assertEquals(0,newChronotimer.getTimeSize(1));
	          assertEquals(0,newChronotimer.getTimeSize(2));
	          
	          assertEquals(1,newChronotimer.getResultSize(2));
	          
	          input = "12:01:02.0 CANCEL";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          assertEquals(0,newChronotimer.getTimeSize(1));
	          assertEquals(0,newChronotimer.getTimeSize(2));
	          
	          input = "12:01:02.0 POWER";
				command = input.split(" ");
				newChronotimer.runCommand(command);
			
		}
		
		/**
		 * Tests the commands START and FINISH for an event
		 */
		@Test
		public void TestStartFinishEvent()
		{
			
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 EVENT IND";
			command = input.split(" ");
			newChronotimer.runCommand(command);

	          input = "12:01:02.0 NEWRUN";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);

	          input = "12:01:02.0 NUM 1";
	          command = input.split(" ");
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
		
		/**
		 * Tests creation of a new run and ending of a run
		 */
		@Test
		public void TestNewEndRun()
		{
			newChronotimer = new Chronotimer();
			
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
		
		/**
		 * Tests the did not finish command functionality
		 */
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
		      
		      rc(newChronotimer, "newrun");
		      
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
		
		/**
		 * Tests to ensure sensors are properly toggled
		 */
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
		
		/**
		 * Tests to ensure sensors are correctly tripped on channel 1
		 */
		@Test
		public void TestTripSensorChannel1()
		{
			
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);

			rc(newChronotimer, "newrun");
			
	          input = "12:01:02.0 NUM 1";
	          command = input.split(" ");
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
		
		/**
		 * Tests to ensure sensors are correctly tripped on channel 2
		 */
		@Test
		public void TestTripSensorChannel2()
		{
			
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);

			rc(newChronotimer, "newrun");

	          input = "12:01:02.0 NUM 1";
	          command = input.split(" ");
	          newChronotimer.runCommand(command);
	          
			input = "12:01:02.0 TOG 1";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertFalse(newChronotimer.eventIsStarted());
			
			input = "12:01:02.0 START";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertTrue(newChronotimer.eventIsStarted());
			
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
		
		/**
		 * Tests to ensure that the individual event can be created
		 * in working order
		 */
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
		
		/**
		 * Validate NUM command enters a racer into the queue
		 * and never duplicates in the same race
		 */
		@Test
		public void TestNUM(){
			newChronotimer = new Chronotimer();
			
			ArrayList<RaceQueuer> queueState = newChronotimer.queueState();
			queueState.get(0).isEmpty();
			
			String input = "12:01:02.0 POWER";
			String[] command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 EVENT IND";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 NUM 1";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 NUM 2";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			queueState = newChronotimer.queueState();
			
			assertFalse(queueState.get(0).isEmpty());
			assertTrue(queueState.get(1).isEmpty());
			
			input = "12:01:02.0 TOG 1";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 TOG 2";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 TOG 3";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 TOG 4";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 TRIG 1";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 TRIG 1";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertFalse(queueState.get(0).isEmpty());
			assertTrue(queueState.get(1).isEmpty());
			
			input = "12:01:02.0 TRIG 2";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertFalse(queueState.get(0).isEmpty());
			assertTrue(queueState.get(1).isEmpty());
			
			input = "12:01:02.0 TRIG 2";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			queueState = newChronotimer.queueState();
			
			assertTrue(queueState.get(0).isEmpty());
			assertTrue(queueState.get(1).isEmpty());
			
			input = "12:01:02.0 ENDRUN";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 EVENT PARIND";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 NEWRUN";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertTrue(queueState.get(0).isEmpty());
			assertTrue(queueState.get(1).isEmpty());
			
			input = "12:01:02.0 NUM 1";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 NUM 2";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertFalse(queueState.get(0).isEmpty());
			assertFalse(queueState.get(1).isEmpty());
			
			input = "12:01:02.0 TRIG 1";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 TRIG 3";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertFalse(queueState.get(0).isEmpty());
			assertFalse(queueState.get(1).isEmpty());
			
			input = "12:01:02.0 TRIG 2";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			input = "12:01:02.0 TRIG 4";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertTrue(queueState.get(0).isEmpty());
			assertTrue(queueState.get(1).isEmpty());
			
			input = "12:01:02.0 ENDRUN";
			command = input.split(" ");
			newChronotimer.runCommand(command);
			
			assertTrue(queueState.get(0).isEmpty());
			assertTrue(queueState.get(1).isEmpty());
			
			input = "12:01:02.0 POWER";
			command = input.split(" ");
			newChronotimer.runCommand(command);
		}
		
		/**
		 * Tests the connect command to ensure it connects a sensor
		 * to a channel properly
		 */
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

		@Test
		public void testParInd()
		{
			Chronotimer ct = new Chronotimer();
			powerUpAndToggleAllChannels(ct);
			assertEquals("IND", ct.getEvent());
			
			rc(ct, "EVENT PARIND");
			assertEquals("PARIND", ct.getEvent());
			rc(ct, "NUM 4123");
			rc(ct, "NUM 2545");

			assertEquals(2, ct.queueState().size());
			assertEquals(1, ct.queueState(1).size());
			assertEquals(1, ct.queueState(3).size());

			assertEquals(0,ct.getRun());
			rc(ct, "newrun");
			
			rc(ct, "trig 1");
			assertEquals(1, ct.getTimeSize(1));
			rc(ct, "trig 3");
			assertEquals(1, ct.getTimeSize(3));
			
			rc(ct, "trig 2");
			assertEquals(0, ct.getTimeSize(1));
			rc(ct, "trig 4");
			assertEquals(0, ct.getTimeSize(3));
			
			assertEquals(2,ct.getResultSize(1));
			
			rc(ct, "print 1");
			assertEquals(1,ct.getRun());
			
			rc(ct, "endrun");
			assertEquals(1,ct.getRun());

			rc(ct, "newrun");
			assertEquals(2,ct.getRun());
			assertEquals(0,ct.getResultSize(2));
			
			//rc(ct, "reset");
			//assertEquals(0,ct.getRun());
		}
		
		private void rc(Chronotimer c, String cmd)
		{
			runCommandWithoutTime(c,cmd);
		}
		
		private void runCommandWithoutTime(Chronotimer c, String cmd)
		{
			String diddle = "12:12:12.122 "+ cmd;
			c.runCommand(diddle.split(" "));
		}
		
		private void powerUpAndToggleAllChannels(Chronotimer c)
		{
			String[] todo = 
				{
						"12:12:12.122 POWER",
						"12:12:12.122 TOG 1",
						"12:12:12.122 TOG 2",
						"12:12:12.122 TOG 3",
						"12:12:12.122 TOG 4"
				};
			runCommandSeries(c, todo);
		}
		
		private void runCommandSeries(Chronotimer c, String... commands)
		{
			for(int i = 0; i< commands.length; ++i)
			{
				String input = commands[i];
				c.runCommand(input.split(" "));
			}
		}
		
}
