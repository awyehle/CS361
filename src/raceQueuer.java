import java.util.ArrayList;

/**
 * 
 * @author Andrew Yehle
 *
 */
public class raceQueuer {
	
	ArrayList<Racer> racerQueue = new ArrayList<Racer>();
	int size;
	
	public raceQueuer(ArrayList<Racer> racerQueue){
		racerQueue = this.racerQueue;
	}
	
	private Racer pop(){
		
		Racer firstRacer = racerQueue.get(0);
		racerQueue.remove(0);
		return firstRacer;
		
	}
	
	private void push(Racer racer){
		
		racerQueue.add(0, racer);
		
	}
	
	public boolean isEmpty(){
		
		if(racerQueue.size() == 0) return true;
		
		return false;
	}
	
	public void racerCancel(){
		
		
		
	}
	
	public String toString(){
		
		return racerQueue.toString();
	}
	
	public static void main(String[] args)
	{
		ArrayList<Racer> newQueue = new ArrayList<Racer>();
		Racer racer1 = new Racer(0);
		Racer racer2 = new Racer(1);
		Racer racer3 = new Racer(1);
		Racer racer4 = new Racer(2);
		newQueue.add(racer1);
		newQueue.add(racer2);
		newQueue.add(racer3);
		newQueue.add(racer4);
		raceQueuer firstQueue = new raceQueuer(newQueue);
		firstQueue.toString();
		
	}
}
