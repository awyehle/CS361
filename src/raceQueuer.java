import java.util.ArrayList;

/**
 * 
 * @author Andrew Yehle
 *
 */
public class raceQueuer {
	
	ArrayList<Racer> racerQueue;
	int size;
	
	public raceQueuer(ArrayList<Racer> racerQueue){
		this.racerQueue = racerQueue;
	}
	
	private Racer pop(){
		
		Racer firstRacer = null;
		try{
		firstRacer = racerQueue.get(0);
		}catch(IndexOutOfBoundsException e){
			System.out.println("There are no racers to pop in the raceQueuer");
		}
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
		System.out.println(firstQueue.toString());
		System.out.print(firstQueue.isEmpty());
		firstQueue.pop();
		firstQueue.pop();
		System.out.println(firstQueue.toString());
		System.out.print(firstQueue.isEmpty());
		firstQueue.pop();
		firstQueue.pop();
		System.out.print(firstQueue.isEmpty());
		firstQueue.pop();
		
		
	}
}
