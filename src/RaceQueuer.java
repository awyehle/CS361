import java.util.ArrayList;

/**
 * 
 * @author Andrew Yehle
 *
 */
public class RaceQueuer {
	
	ArrayList<Racer> _waitQueue = new ArrayList<Racer>();
	ArrayList<Racer> _inProgress = new ArrayList<Racer>();
	ArrayList<Racer> _alreadyRan = new ArrayList<Racer>();
	
	public RaceQueuer(){
		
	}
	
	public RaceQueuer(ArrayList<Racer> racerQueue){
		this._waitQueue = racerQueue;
	}
	
	public boolean contains(Racer bib) {
		if(bib == null) return false;
		if(inWaitQueue(bib) || inProgressQueue(bib) || alreadyRan(bib)) return true;
		return false;
	}
	
	public boolean contains(int bib) {
		boolean has = false;
		for(Racer r : _waitQueue)
		{
			if(r.getBib()==bib) {has = true; break;}
		}
		if(has) return true;
		for(Racer r : _inProgress)
		{
			if(r.getBib()==bib) {has = true; break;}
		}
		if(has) return true;
		for(Racer r : _alreadyRan)
		{
			if(r.getBib()==bib) {has = true; break;}
		}
		return has;
	}
	
	public boolean inWaitQueue(Racer num){
		if(num == null) return false;
		for(int i = 0; i<_waitQueue.size();i++){
			if(_waitQueue.get(i).getBib() == num.getBib()) return true;
		}
		return false;
	}
	
	public boolean inProgressQueue(Racer num){
		if(num == null) return false;
		for(int i = 0; i<_inProgress.size();i++){
			if(_inProgress.get(i).getBib() == num.getBib()) return true;
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
	
	public Racer popWait(){
		
		Racer firstRacer = null;
		try{
		firstRacer = _waitQueue.get(0);
		_inProgress.add(firstRacer);
		_waitQueue.remove(0);
		}catch(IndexOutOfBoundsException e){
			System.out.println("There are no racers to pop in the raceQueuer");
		}
		return firstRacer;
		
	}
	
	public Racer pop(){
		
		Racer firstRacer = null;
		try{
		firstRacer = _inProgress.get(0);
		_alreadyRan.add(firstRacer);
		_inProgress.remove(0);
		}catch(IndexOutOfBoundsException e){
			System.out.println("There are no racers on course to finish");
		}
		return firstRacer;
		
	}
	
	public Racer peek()
	{

		Racer firstRacer = null;
		try{
		firstRacer = _inProgress.get(0);
		}catch(IndexOutOfBoundsException e){
			System.out.println("There are no racers in the raceQueuer");
		}
		return firstRacer;
	}
	
	public boolean push(Racer racer){
		if(contains(racer))
			return false;
		try
		{
			_waitQueue.add(_waitQueue.size(), racer);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
		
	}
	
	public boolean isEmpty(){
		
		if(_waitQueue.size() == 0 && _inProgress.size() == 0) return true;
		
		return false;
	}
	
	public void racerCancel(){
		
		//Not sure what we want this to do
		
	}
	
	public boolean clear(){
		_waitQueue.clear();
		_inProgress.clear();
		_alreadyRan.clear();
		if(isEmpty()) return true;
		return false;
	}
	
	public String toString(){
		
		return _inProgress.toString();
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
