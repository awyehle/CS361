import java.util.ArrayList;

/**
 * 
 * @author Andrew Yehle
 *
 */
public class RaceQueuer {
	
	ArrayList<Racer> racerQueue = new ArrayList<Racer>();
	ArrayList<Racer> _alreadyRan = new ArrayList<Racer>();
	int size;
	
	public RaceQueuer(){
		
	}
	
	public RaceQueuer(ArrayList<Racer> racerQueue){
		this.racerQueue = racerQueue;
	}
	
	public boolean contains(Racer bib) {
		if(bib == null) return false;
		if(inQueue(bib)) return true;
		return false;
	}
	
	public boolean inQueue(Racer num){
		if(num == null) return false;
		for(int i = 0; i<racerQueue.size();i++){
			if(racerQueue.get(i).getBib() == num.getBib()) return true;
		}
		return false;
	}
	
	public boolean alreadyRan(Racer num){
		if(num == null) return false;
		for(int i = 0; i<_alreadyRan.size();i++){
			if(_alreadyRan.get(i).getBib() == num.getBib()) return true;
		}
		return false;
	}
	
	public Racer pop(){
		
		Racer firstRacer = null;
		try{
		firstRacer = racerQueue.get(0);
		_alreadyRan.add(firstRacer);
		racerQueue.remove(0);
		}catch(IndexOutOfBoundsException e){
			System.out.println("There are no racers to pop in the raceQueuer");
		}
		return firstRacer;
		
	}
	
	public boolean push(Racer racer){
		if(contains(racer))
			return false;
		racerQueue.add(racerQueue.size(), racer);
		return true;
		
	}
	
	public boolean isEmpty(){
		
		if(racerQueue.size() == 0) return true;
		
		return false;
	}
	
	public void racerCancel(){
		
		//Not sure what we want this to do
		
	}
	
	public boolean clear(){
		racerQueue.clear();
		_alreadyRan.clear();
		if(isEmpty()) return true;
		return false;
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
