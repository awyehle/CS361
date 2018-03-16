/**
 * @author Andrew Yehle, Michael Davis
 */
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class EventTester {
		
		static Chronotimer newChronotimer = new Chronotimer();
		
		@Before
		public void initiate()
		{
		newChronotimer= new Chronotimer();
		}
		
		/**
		 * Tests to make sure all events can be properly switches
		 * to and the state changes
		 */
		@Test
		public void TestEvent(){
			
			rc(newChronotimer, "power");
			
			rc(newChronotimer, "event parind");
			
			assertEquals(newChronotimer.getEvent(), "PARIND");
			
			rc(newChronotimer, "event ind");
			
			assertEquals(newChronotimer.getEvent(), "IND");
			
			rc(newChronotimer, "power");
			
		}
		
		
		/**
		 * Tests to ensure that the individual event can be created
		 * in working order
		 */
		@Test
		public void TestInd(){
			
			rc(newChronotimer, "power");
			
			rc(newChronotimer, "event ind");
			
			assertEquals(newChronotimer.getEvent(), "IND");
			
			assertEquals(0, newChronotimer.queueState(2).size());
			assertEquals(0, newChronotimer.queueState(1).size());
			rc(newChronotimer, "tog 1");
			rc(newChronotimer, "tog 2");
			rc(newChronotimer, "num 1");
			rc(newChronotimer, "num 2");
			rc(newChronotimer, "num 3");
			assertEquals(3, newChronotimer.queueState(1).size());
			
			rc(newChronotimer, "newrun");
			assertTrue(newChronotimer.isNewRunTriggered());
			
			rc(newChronotimer, "start");
			assertTrue(newChronotimer.eventIsStarted());
			
			rc(newChronotimer, "trig 2");
			assertEquals(1, newChronotimer.queueState(2).size());
			
			rc(newChronotimer, "trig 2");
			assertEquals(1, newChronotimer.queueState(2).size());
			assertEquals(2, newChronotimer.queueState(1).size());
			
			rc(newChronotimer, "trig 1");
			assertEquals(2, newChronotimer.queueState(2).size());
			assertEquals(1, newChronotimer.queueState(1).size());
			
			rc(newChronotimer, "trig 1");
			assertEquals(3, newChronotimer.queueState(2).size());
			assertEquals(0, newChronotimer.queueState(1).size());
			
			rc(newChronotimer, "trig 1");
			assertEquals(4, newChronotimer.queueState(2).size());
			assertEquals(1, newChronotimer.queueState(1).size());
			
			rc(newChronotimer, "trig 1");
			assertEquals(5, newChronotimer.queueState(2).size());
			assertEquals(0, newChronotimer.queueState(1).size());
			
			rc(newChronotimer, "power");
			
		}

		/**
		 * Tests to make sure ParInd can be
		 *  called and works properly
		 */
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
			
			rc(ct, "POWER");				
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
