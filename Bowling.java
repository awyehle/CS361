
public class Bowling {
	
	enum FrameStatus{
		STRIKE, SPARE, REGULAR
	}
	
	private int frameCount;
	private int[] score;
	private FrameStatus[] fs;
	private boolean firstThrow;
	private int numPinsLeft;
	
	public Bowling() {
		frameCount = 0;
		score = new int[10];
		fs = new FrameStatus[2];
		firstThrow = true;
		numPinsLeft = 10;
	}
	
	public boolean bowl(int pinsKnocked) {
		if (frameCount >= 10)
			return false;
		if (numPinsLeft < pinsKnocked)
			return false;
		numPinsLeft -= pinsKnocked;
		score[frameCount] += pinsKnocked;
		if (numPinsLeft == 0) {
			beginNewFrame();
			return true;
		}
		if (!firstThrow) {
			beginNewFrame();
			return true;
		}
		firstThrow = false;
		return true;
	}
	
	public int getFrame() {
		return frameCount;
	}
	
	public int getScore(int frame) {
		return score[frame];
	}
	
	public int getTotalScore() {
		int sum = 0;
		for (int i=0; i<10; i++) {
			sum += score[i];
		}
		return sum;
	}
	
	private void beginNewFrame() {
		if (frameCount >= 2 && fs[0] == FrameStatus.STRIKE)
			score[frameCount-2] += score[frameCount];
		if (frameCount >= 1 && (fs[1] == FrameStatus.SPARE || fs[1] == FrameStatus.STRIKE))
			score[frameCount-1] += score[frameCount];
		fs[0] = fs[1];
		if (score[frameCount] == 10)
			if (firstThrow) 
				fs[1] = FrameStatus.STRIKE;
			else
				fs[1] = FrameStatus.SPARE;
		else
			fs[1] = FrameStatus.REGULAR;
		firstThrow = true;
		numPinsLeft = 10;
		frameCount++;
	}

}
