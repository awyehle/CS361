import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class BowlingTest {
	
	Bowling a;
	
	@Before
	public void setUp() {
		a = new Bowling();
	}
	
	@Test
	public void testOneThrow() {
		assertTrue(a.bowl(7));
		assertEquals(7, a.getScore(a.getFrame()));
	}
	
	@Test
	public void testTwoThrow() {
		assertTrue(a.bowl(7));
		assertTrue(a.bowl(2));
		assertEquals(9, a.getScore(0));
		assertEquals(9, a.getTotalScore());
		assertEquals(1, a.getFrame());
	}
	
	@Test
	public void testThreeThrow() {
		assertTrue(a.bowl(3));
		assertTrue(a.bowl(3));
		assertTrue(a.bowl(3));
		assertEquals(9, a.getTotalScore());
		assertEquals(1, a.getFrame());
		assertEquals(6, a.getScore(0));
		assertEquals(3, a.getScore(1));
	}
	
	@Test
	public void testSpareCountsNextFrameScore() {
		assertTrue(a.bowl(3));
		assertTrue(a.bowl(7));
		assertTrue(a.bowl(3));
		assertTrue(a.bowl(6));
		assertEquals(28, a.getTotalScore());
		assertEquals(2, a.getFrame());
		assertEquals(19, a.getScore(0));
		assertEquals(9, a.getScore(1));
	}
	
	@Test
	public void testStrikeMovesToNextFrame() {
		assertTrue(a.bowl(10));
		assertEquals(10, a.getTotalScore());
		assertEquals(1, a.getFrame());
	}
	
	@Test
	public void testStrikeCountsNextFrameScores() {
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertEquals(30, a.getScore(0));
		assertEquals(20, a.getScore(1));
		assertEquals(10, a.getScore(2));
		assertEquals(3, a.getFrame());
		assertEquals(60, a.getTotalScore());
	}
	
	@Test
	public void testSpareOnLastFrame() {
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(3));
		assertTrue(a.bowl(7));
		assertEquals(270, a.getTotalScore());
	}
	
	@Test
	public void testStrikeOnLastFrames() {
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertTrue(a.bowl(10));
		assertEquals(270, a.getTotalScore());
	}
	

	@Test
	public void testThrowOn11thFrame() {
		assertTrue(a.bowl(10)); // 1
		assertTrue(a.bowl(10)); // 2
		assertTrue(a.bowl(10)); // 3
		assertTrue(a.bowl(10)); // 4
		assertTrue(a.bowl(10)); // 5
		assertTrue(a.bowl(10)); // 6
		assertTrue(a.bowl(10)); // 7
		assertTrue(a.bowl(10)); // 8
		assertTrue(a.bowl(10)); // 9
		assertTrue(a.bowl(10)); // 10
		assertFalse(a.bowl(10)); // fail to throw the 11th frame
		assertEquals(270, a.getTotalScore());
	}

}
