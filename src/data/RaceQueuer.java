package data;
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
			//I disabled this
			//System.out.println("There are no racers to pop in the raceQueuer");
		}
		return firstRacer;
		
	}
	
	public Racer remove()
	{
		Racer firstRacer = null;
		try{
		firstRacer = _waitQueue.get(0);
		_waitQueue.remove(0);
		}catch(IndexOutOfBoundsException e){
			//I disabled this
			//System.out.println("There are no racers to pop in the raceQueuer");
		}
		return firstRacer;
	}
	
	public Racer pop(){
		
		Racer firstRacer = null;
		try{
		firstRacer = _inProgress.get(0);
		_alreadyRan.add(0, firstRacer);
		_inProgress.remove(0);
		}catch(IndexOutOfBoundsException e){
			//I disabled this
			//System.out.println("There are no racers on course to finish");
		}
		return firstRacer;
		
	}

	public Racer peek()
	{

		Racer firstRacer = null;
		try{
		firstRacer = _inProgress.get(0);
		}catch(IndexOutOfBoundsException e){
			//I disabled this
			//System.out.println("There are no racers in the raceQueuer");
		}
		return firstRacer;
	}
	
	public Racer[] peekAll()
	{
		return _inProgress.toArray(new Racer[0]);
	}
	
	public ArrayList<Racer> peekTotal()
	{
		ArrayList<Racer> all = new ArrayList<Racer>();
		all.addAll(_waitQueue);
		all.addAll(_inProgress);
		all.addAll(_alreadyRan);
		return all;
	}
	
	public int queueSize()
	{
		return _waitQueue.size();
	}
	
	public int totalSize()
	{
		return _waitQueue.size()+_inProgress.size()+_alreadyRan.size();
	}
	
	public Racer peekRan()
	{

		Racer firstRacer = null;
		try{
		firstRacer = _alreadyRan.get(0);
		}catch(IndexOutOfBoundsException e){
			//System.out.println("There are no racers in the raceQueuer");
		}
		return firstRacer;
	}
	
	public Racer peekWaiting(int index)
	{
		Racer firstRacer = null;
		try{
		firstRacer = _waitQueue.get(index);
		}catch(IndexOutOfBoundsException e){
			//System.out.println("There are no racers in the raceQueuer");
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
	
	public void swap()
	{
		if(_waitQueue.size() < 2) return;
		Racer r = _waitQueue.get(0);
		_waitQueue.set(0, _waitQueue.get(1));
		_waitQueue.set(1, r);
	}
	
	public boolean isEmpty(){
		
		if(_waitQueue.size() == 0 && _inProgress.size() == 0 && _alreadyRan.size() == 0) return true;
		
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
	
	@Override
	public String toString(){
		String s = "Queued: ";
		for(Racer r : _waitQueue) s += r.getBib()+ "\n";
		s+="Running: ";
		for(Racer r : _inProgress) s += r.getBib()+ "\n";
		s+="Running: ";
		for(Racer r : _alreadyRan) s += r.getBib()+ "\n";
		return s;
	}
	
}
