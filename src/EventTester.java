/**
 * @author Andrew Yehle, Michael Davis
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.Racer;
import pcmr.Chronotimer;

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
			
			rc(newChronotimer, "event grp");
			
			assertEquals(newChronotimer.getEvent(), "GRP");
			
			rc(newChronotimer, "event pargrp");
			
			assertEquals(newChronotimer.getEvent(), "PARGRP");
			
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
			
			assertTrue(newChronotimer.queueState().get(0).isEmpty());
			assertTrue(newChronotimer.queueState().get(1).isEmpty());
			
			rc(newChronotimer, "tog 1");
			rc(newChronotimer, "tog 2");

			assertTrue(newChronotimer.isToggled(1));
			assertTrue(newChronotimer.isToggled(2));
			
			rc(newChronotimer, "num 1");
			rc(newChronotimer, "num 2");
			rc(newChronotimer, "num 3");
			assertTrue(newChronotimer.queueState().get(0).contains(1));
			assertTrue(newChronotimer.queueState().get(0).contains(2));
			assertTrue(newChronotimer.queueState().get(0).contains(3));
			
			rc(newChronotimer, "newrun");
			assertTrue(newChronotimer.isNewRunTriggered());
			
			rc(newChronotimer, "start");
			assertTrue(newChronotimer.eventIsStarted());
			
			assertEquals(1, newChronotimer.queueState(1).size());
			assertTrue(newChronotimer.queueState().get(0).contains(1));
			assertTrue(newChronotimer.queueState().get(0).contains(2));
			assertTrue(newChronotimer.queueState().get(0).contains(3));
			
			rc(newChronotimer, "trig 2");
			assertEquals(1, newChronotimer.queueState(2).size());
			
			rc(newChronotimer, "trig 2");
			
			assertTrue(newChronotimer.queueState().get(0).contains(1));
			assertTrue(newChronotimer.queueState().get(0).contains(2));
			assertTrue(newChronotimer.queueState().get(0).contains(3));
			
			rc(newChronotimer, "trig 1");
			
			rc(newChronotimer, "trig 1");
			
			rc(newChronotimer, "trig 1");
			
			assertTrue(newChronotimer.queueState().get(0).contains(1));
			assertTrue(newChronotimer.queueState().get(0).contains(2));
			assertTrue(newChronotimer.queueState().get(0).contains(3));
			
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

			rc(ct, "newrun");
			assertEquals(2,ct.getRun());
			assertEquals(0,ct.getResultSize(2));
			
			rc(ct, "POWER");				
		}
		
		@Test
		public void testGRP(){
		
			powerUpAndToggleAllChannels(newChronotimer);
			
			assertTrue(newChronotimer.queueState().get(0).isEmpty());
			assertTrue(newChronotimer.queueState().get(1).isEmpty());
			
			rc(newChronotimer, "EVENT GRP");
			
			rc(newChronotimer, "num 1");
			
			rc(newChronotimer, "num 2");
			
			rc(newChronotimer, "num 3");
			
			rc(newChronotimer, "num 4");
			

			assertTrue(newChronotimer.queueState().get(0).contains(1));
			assertTrue(newChronotimer.queueState().get(0).contains(2));
			assertTrue(newChronotimer.queueState().get(0).contains(3));
			assertTrue(newChronotimer.queueState().get(0).contains(4));
			
			assertFalse(newChronotimer.eventIsStarted());
			
			rc(newChronotimer, "newrun");

			rc(newChronotimer, "trig 1");
			
			assertTrue(newChronotimer.eventIsStarted());
			
			assertTrue(newChronotimer.queueState().get(0).inProgressQueue(new Racer(1)));
			assertTrue(newChronotimer.queueState().get(0).inProgressQueue(new Racer(2)));
			assertTrue(newChronotimer.queueState().get(0).inProgressQueue(new Racer(3)));
			assertTrue(newChronotimer.queueState().get(0).inProgressQueue(new Racer(4)));
			
			rc(newChronotimer, "trig 2");
			
			assertTrue(newChronotimer.queueState().get(0).alreadyRan(new Racer(1)));
			rc(newChronotimer, "trig 2");
			assertTrue(newChronotimer.queueState().get(0).alreadyRan(new Racer(2)));
			rc(newChronotimer, "trig 2");
			assertTrue(newChronotimer.queueState().get(0).alreadyRan(new Racer(3)));
			rc(newChronotimer, "trig 2");
			assertTrue(newChronotimer.queueState().get(0).alreadyRan(new Racer(4)));
			
			rc(newChronotimer, "endrun");
			
			assertTrue(newChronotimer.queueState().get(0).isEmpty());
			assertTrue(newChronotimer.queueState().get(1).isEmpty());
			
			rc(newChronotimer, "power");
			
		}
		
		@Test
		public void testPARGRP(){
		
			powerUpAndToggleAllChannels(newChronotimer);
			
			assertTrue(newChronotimer.queueState().get(0).isEmpty());
			assertTrue(newChronotimer.queueState().get(1).isEmpty());
			
			rc(newChronotimer, "EVENT PARGRP");
			
			rc(newChronotimer, "num 1");
			
			rc(newChronotimer, "num 2");
			
			rc(newChronotimer, "num 3");
			
			rc(newChronotimer, "num 4");
			

			assertTrue(newChronotimer.queueState().get(0).contains(1));
			assertTrue(newChronotimer.queueState().get(1).contains(2));
			
			
			rc(newChronotimer, "newrun");

			rc(newChronotimer, "trig 1");
			
			assertTrue(newChronotimer.eventIsStarted());
			
			assertTrue(newChronotimer.queueState().get(0).inProgressQueue(new Racer(1)));
			assertTrue(newChronotimer.queueState().get(1).inProgressQueue(new Racer(2)));
			
			rc(newChronotimer, "trig 3");
			
			assertFalse(newChronotimer.queueState().get(0).alreadyRan(new Racer(1)));
			rc(newChronotimer, "trig 5");
			assertFalse(newChronotimer.queueState().get(0).alreadyRan(new Racer(2)));
			rc(newChronotimer, "trig 2");
			assertFalse(newChronotimer.queueState().get(0).alreadyRan(new Racer(3)));
			rc(newChronotimer, "trig 1");
			assertFalse(newChronotimer.queueState().get(0).alreadyRan(new Racer(4)));
			rc(newChronotimer, "trig 4");
			rc(newChronotimer, "trig 6");
			
			rc(newChronotimer, "endrun");
			
			assertTrue(newChronotimer.queueState().get(0).isEmpty());
			assertTrue(newChronotimer.queueState().get(1).isEmpty());
			
			rc(newChronotimer, "power");
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
