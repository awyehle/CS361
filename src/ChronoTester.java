/**
 * @author Andrew Yehle, Michael Davis
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pcmr.Chronotimer;

public class ChronoTester {
		
		static Chronotimer newChronotimer = new Chronotimer();
		
		@Before
		public void initiate()
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
			rc(newChronotimer, "POWER");
			assertTrue(newChronotimer.isOn());
			
			rc(newChronotimer, "power");
			assertFalse(newChronotimer.isOn());
			
		}
		
		/**
		 * Tests to make sure an invalid command is not accepted and
		 * does not mutate anything
		 */
		@Test
		public void TestInvalidCommand()
		{
			rc(newChronotimer, "power");
			
			rc(newChronotimer, "tidepod");
			
			assertTrue(newChronotimer.isOn());
			assertFalse(newChronotimer.isNewRunTriggered());
			assertFalse(newChronotimer.isConnected());
			assertFalse(newChronotimer.isToggled());
			
			rc(newChronotimer, "power");
			
		}
		
		/**
		 * Tests the reset command which calls Chronotimer's isReset method
		 */
		@Test
		public void TestReset()
		{
			powerUpAndToggleAllChannels(newChronotimer);
			String[] todo = 
				{
						
						"12:12:12.122 NUM 1",
						"12:12:12.122 NUM 2",
						"12:12:12.122 NUM 3",
						"12:12:12.122 NUM 4",
						"12:12:12.122 NEWRUN",
						"12:12:12.122 TRIG 1",
						"12:12:12.122 TRIG 2",
						"12:12:12.122 TRIG 3",
						"12:12:12.122 TRIG 4"
				};
			runCommandSeries(newChronotimer, todo);
			
			assertFalse(newChronotimer.isReset());
			
			rc(newChronotimer, "reset");
			assertTrue(newChronotimer.isReset());
			
			rc(newChronotimer, "event parind");
			
			runCommandSeries(newChronotimer, todo);
			assertFalse(newChronotimer.isReset());
			
			rc(newChronotimer, "reset");
			assertTrue(newChronotimer.isReset());
			
			assertTrue(newChronotimer.isReset());
			
			rc(newChronotimer, "power");
			
		}
		
		/**
		 * Tests to make sure an event is properly cancelled using
		 * the cancel method
		 */
		@Test
		public void TestCancel()
		{

			rc(newChronotimer, "power");
			
			rc(newChronotimer, "newrun");
			
			rc(newChronotimer, "tog 1");

			rc(newChronotimer, "num 1");
	          
			rc(newChronotimer, "trig 1");
	          
			rc(newChronotimer, "trig 2");
	          
			//assertFalse(newChronotimer.isCancled()); NOT PROPER IMPLEMENTATION ANYMORE. MAY COME IN HANDY IN FUTURE
	          
			//Channel 1 should have 1 time within it
			assertEquals(1,newChronotimer.getTimeSize(1));
	          
			//Since Channel 2 was never toggled on, it should not have a time in it
			assertEquals(0,newChronotimer.getTimeSize(2));
	          
			rc(newChronotimer, "cancel");
			assertEquals(0,newChronotimer.getTimeSize(1));
			assertEquals(0,newChronotimer.getTimeSize(2));

			rc(newChronotimer, "endrun");
	          
			rc(newChronotimer, "newrun");

			rc(newChronotimer, "num 1");
	          
	     	rc(newChronotimer, "tog 2");
	          
	    	rc(newChronotimer, "trig 1");
	          
	    	rc(newChronotimer, "trig 2");
	          
	    	//Channels 1 and 2 should have not have time within them, as the times are removed and stored into a result
	    	assertEquals(0,newChronotimer.getTimeSize(1));
	    	assertEquals(0,newChronotimer.getTimeSize(2));
	          
	    	assertEquals(1,newChronotimer.getResultSize(2));
	          
	    	rc(newChronotimer, "cancel");
	    	assertEquals(0,newChronotimer.getTimeSize(1));
	    	assertEquals(0,newChronotimer.getTimeSize(2));
	          
	    	rc(newChronotimer, "power");
			
		}
		
		/**
		 * Tests the commands START and FINISH for an event
		 */
		@Test
		public void TestStartFinishEvent()
		{
			
			powerUpAndToggleAllChannels(newChronotimer);
			
			String[] todo = 
				{
						"12:01:02.0 NEWRUN",
						"12:01:02.0 NUM 1"
				};
			runCommandSeries(newChronotimer, todo);
			
			rc(newChronotimer, "event parind");

			rc(newChronotimer, "newrun");

			rc(newChronotimer, "num 1");
			
			assertFalse(newChronotimer.eventIsFinished());
			assertFalse(newChronotimer.eventIsStarted());
			
			rc(newChronotimer, "start");
			
			assertFalse(newChronotimer.eventIsFinished());
			assertTrue(newChronotimer.eventIsStarted());
			
			rc(newChronotimer, "finish");
			
			assertTrue(newChronotimer.eventIsFinished());
			assertTrue(newChronotimer.eventIsStarted());
			
			rc(newChronotimer, "power");
		}
		
		/**
		 * Tests creation of a new run and ending of a run
		 */
		@Test
		public void TestNewEndRun()
		{
			
			powerUpAndToggleAllChannels(newChronotimer);
			
			rc(newChronotimer, "newrun");
			
			assertTrue(newChronotimer.isNewRunTriggered());
			
			rc(newChronotimer, "endrun");

			assertFalse(newChronotimer.isNewRunTriggered());
			
			rc(newChronotimer, "power");
			
		}
		
		/**
		 * Tests the did not finish command functionality
		 */
		@Test
		public void TestDNF(){
			
			rc(newChronotimer, "power");
		      
			rc(newChronotimer, "tog 1");
		      
			rc(newChronotimer, "tog 2");
		      
		      rc(newChronotimer, "newrun");
		      
		      assertFalse(newChronotimer.eventIsFinished());
		      assertFalse(newChronotimer.eventIsStarted());
		      
		      rc(newChronotimer, "start");
		      
		      assertFalse(newChronotimer.eventIsFinished());
		      assertTrue(newChronotimer.eventIsStarted());
		      
		      rc(newChronotimer, "dnf");
		      
		      assertFalse(newChronotimer.eventIsFinished());
		      assertTrue(newChronotimer.eventIsStarted());
		      
		      rc(newChronotimer, "power");
			
		}
		
		/**
		 * Tests to ensure sensors are properly toggled
		 */
		@Test
		public void TestToggleSensor()
		{
			
			rc(newChronotimer, "power");
		      
		      assertFalse(newChronotimer.isToggled());
		      
		      rc(newChronotimer, "tog 1");
		      
		      rc(newChronotimer, "tog 2");
		      
		      assertTrue(newChronotimer.isToggled());
		      
		      rc(newChronotimer, "start");
		      
		      rc(newChronotimer, "power");
			
		}
		
		/**
		 * Tests to ensure sensors are correctly tripped on channel 1
		 */
		@Test
		public void TestTripSensorChannel1()
		{
			
			rc(newChronotimer, "power");

			rc(newChronotimer, "newrun");
			
			rc(newChronotimer, "num 1");
	          
			rc(newChronotimer, "tog 1");
			
			assertFalse(newChronotimer.eventIsStarted());
			
			rc(newChronotimer, "start");
			
			assertTrue(newChronotimer.eventIsStarted());
			
			rc(newChronotimer, "power");
			
		}
		
		/**
		 * Tests to ensure sensors are correctly tripped on channel 2
		 */
		@Test
		public void TestTripSensorChannel2()
		{
			
			rc(newChronotimer, "power");

			rc(newChronotimer, "newrun");

			rc(newChronotimer, "num 1");
	          
			rc(newChronotimer, "tog 1");
			
			assertFalse(newChronotimer.eventIsStarted());
			
			rc(newChronotimer, "start");
			
			assertTrue(newChronotimer.eventIsStarted());
			
			rc(newChronotimer, "tog 2");
			
			assertFalse(newChronotimer.eventIsFinished());
			
			rc(newChronotimer, "finish");
			
			
			
			assertTrue(newChronotimer.eventIsFinished());
			
			rc(newChronotimer, "power");
			
		}
		
		/**
		 * Validate NUM command enters a racer into the queue
		 * and never duplicates in the same race
		 */
		@Test
		public void TestNUM(){
			
			
			powerUpAndToggleAllChannels(newChronotimer);
			
			assertTrue(newChronotimer.queueState().get(0).isEmpty());
			assertTrue(newChronotimer.queueState().get(1).isEmpty());
			
			rc(newChronotimer, "event ind");
			
			rc(newChronotimer, "num 1");
			
			rc(newChronotimer, "num 2");
			
			
			assertTrue(newChronotimer.queueState().get(0).contains(1));
			assertTrue(newChronotimer.queueState().get(0).contains(2));
			
			rc(newChronotimer, "trig 1");
			
			rc(newChronotimer, "trig 1");
			
			assertTrue(newChronotimer.queueState().get(0).contains(1));
			assertTrue(newChronotimer.queueState().get(0).contains(2));
			
			rc(newChronotimer, "trig 2");
			
			rc(newChronotimer, "trig 2");
			
			assertTrue(newChronotimer.queueState().get(0).contains(1));
			assertTrue(newChronotimer.queueState().get(0).contains(2));
			
			rc(newChronotimer, "endrun");
			
			assertTrue(newChronotimer.queueState().get(0).isEmpty());
			assertTrue(newChronotimer.queueState().get(1).isEmpty());
			
			rc(newChronotimer, "event parind");
			
			rc(newChronotimer, "newrun");
			
			rc(newChronotimer, "num 1");
			
			rc(newChronotimer, "num 2");
			
			assertTrue(newChronotimer.queueState().get(0).contains(1));
			assertTrue(newChronotimer.queueState().get(1).contains(2));
			
			rc(newChronotimer, "trig 1");
			
			rc(newChronotimer, "trig 3");
			
			assertTrue(newChronotimer.queueState().get(0).contains(1));
			assertTrue(newChronotimer.queueState().get(1).contains(2));
			
			rc(newChronotimer, "trig 2");
			
			rc(newChronotimer, "trig 4");
			
			assertTrue(newChronotimer.queueState().get(0).contains(1));
			assertTrue(newChronotimer.queueState().get(1).contains(2));
			
			rc(newChronotimer, "endrun");
			
			assertTrue(newChronotimer.queueState().get(0).isEmpty());
			assertTrue(newChronotimer.queueState().get(1).isEmpty());
			
			rc(newChronotimer, "power");
		}
		
		/**
		 * Tests the connect command to ensure it connects a sensor
		 * to a channel properly
		 */
		@Test
		public void TestCONN(){
			
			rc(newChronotimer, "power");
		      
		      assertFalse(newChronotimer.isConnected());
		      
		      rc(newChronotimer, "conn eye 1");
		      
		      rc(newChronotimer, "conn eye 2");
		      
		      rc(newChronotimer, "gate 3");
		      
		      rc(newChronotimer, "gate 4");
		      
		      assertTrue(newChronotimer.isConnected());
		      
		      rc(newChronotimer, "power");
			
		}
		
		@Test
		public void TestSwap(){
			
			
			
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
