import java.util.ArrayList;

/**
 * 
 * @author Andrew Yehle
 *
 */
public class RaceQueuer {
	
	ArrayList<Racer> racerQueue;
	int size;
	
	public RaceQueuer(ArrayList<Racer> racerQueue){
		this.racerQueue = racerQueue;
	}
	
	public boolean contains(Racer bib) {
		if(bib == null) return false;
		if(racerQueue.contains(bib)) return true;
		return false;
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
		
		//Not sure what we want this to do
		
	}
	
	public String toString(){
		
		return racerQueue.toString();
	}
	
	public static void main(String[] args)
	{
		//Test this class down here
		ArrayList<Racer> newQueue = new ArrayList<Racer>();
		Racer racer1 = new Racer(0);
		Racer racer2 = new Racer(1);
		Racer racer3 = new Racer(1);
		Racer racer4 = new Racer(2);
		newQueue.add(racer1);
		newQueue.add(racer2);
		newQueue.add(racer3);
		newQueue.add(racer4);
		RaceQueuer firstQueue = new RaceQueuer(newQueue);
		System.out.println(firstQueue.toString());
		System.out.print(firstQueue.isEmpty());
		firstQueue.pop();
		firstQueue.pop();
		System.out.println(firstQueue.toString());
		System.out.print(firstQueue.isEmpty());
		firstQueue.pop();
		firstQueue.pop();
		System.out.print(firstQueue.isEmpty());
		System.out.println(firstQueue.toString());
		firstQueue.push(racer1);
		System.out.println(firstQueue.toString());
		firstQueue.push(racer2);
		System.out.println(firstQueue.toString());
		
		
	}
}
