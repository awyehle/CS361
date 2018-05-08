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
	
	/**
	 * Returns if the racer(by racer object reference) is in the queue, is running, or has finished
	 * @param bib the racer to find
	 * @return true if the racer has, is, or will participate
	 */
	public boolean contains(Racer bib) {
		if(bib == null) return false;
		if(inWaitQueue(bib) || inProgressQueue(bib) || alreadyRan(bib)) return true;
		return false;
	}
	
	/**
	 * Takes the first running racer and put them back in the waiting queue
	 * @return true if there was a racer running, false if there wasn't
	 */
	public boolean cancel()
	{
		try {
			Racer r = _inProgress.remove(0);
			return _waitQueue.add(r);
		}
		catch (NullPointerException e) {return false;}
	}
	
	/**
	 * Returns if the racer(by bib number) is in the queue, is running, or has finished
	 * @param bib the racer number to find
	 * @return true if the racer has, is, or will participate
	 */
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
	
	/**
	 * Returns if a racer is waiting to go
	 * @param num the racer
	 * @return whether or not the racer is in the queue to run
	 */
	public boolean inWaitQueue(Racer num){
		if(num == null) return false;
		for(int i = 0; i<_waitQueue.size();i++){
			if(_waitQueue.get(i).getBib() == num.getBib()) return true;
		}
		return false;
	}
	
	/**
	 * Returns if a racer is racing
	 * @param num the racer
	 * @return whether or not the racer is racing
	 */
	public boolean inProgressQueue(Racer num){
		if(num == null) return false;
		for(int i = 0; i<_inProgress.size();i++){
			if(_inProgress.get(i).getBib() == num.getBib()) return true;
		}
		return false;
	}
	
	/**
	 * Returns if a racer has finished
	 * @param num the racer
	 * @return whether or not the racer has finished
	 */
	public boolean alreadyRan(Racer num){
		if(num == null) return false;
		for(int i = 0; i<_alreadyRan.size();i++){
			if(_alreadyRan.get(i).getBib() == num.getBib()) return true;
		}
		return false;
	}
	
	/**
	 * Takes the next racer in the waiting queue and sends them off on the race
	 * @return the racer that came out of the queue, or null, if the queue was empty
	 */
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
	
	/**
	 * Removes the first racer in the waiting queue
	 * @return the racer removed
	 */
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
	
	/**
	 * Finishes the next racer in the race
	 * @return the racer that finished, or null if no racers were racing
	 */
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

	/**
	 * Returns the next racer to finish
	 * @return the next racer to finish, or null, if no racers are racing
	 */
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
	
	/**
	 * Returns all racers currently running in the race
	 * @return all racers(maybe none!) racing
	 */
	public Racer[] peekAll()
	{
		return _inProgress.toArray(new Racer[0]);
	}
	
	/**
	 * Returns all racers, whether they are in queue, running, or finished
	 * @return all racers (maybe none!)
	 */
	public ArrayList<Racer> peekTotal()
	{
		ArrayList<Racer> all = new ArrayList<Racer>();
		all.addAll(_waitQueue);
		all.addAll(_inProgress);
		all.addAll(_alreadyRan);
		return all;
	}
	
	/**
	 * Returns how many racers are waiting to go
	 * @return the amount of racers in the wait queue
	 */
	public int queueSize()
	{
		return _waitQueue.size();
	}
	
	/**
	 * Returns how many racers are in the queue, running, or finished
	 * @return the amount of all the racers in the race
	 */
	public int totalSize()
	{
		return _waitQueue.size()+_inProgress.size()+_alreadyRan.size();
	}
	
	/**
	 * Returns the first racer to finish 
	 * @return the racer
	 */
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
	
	/**
	 * Returns the racer to start a race, by their position in the queue
	 * @param index the position in the queue
	 * @return the racer waiting in the queue
	 */
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
	
	/**
	 * Adds a racer to the end of the waiting queue, provided that the racer
	 * being added is not already part of this race
	 * @param racer the racer to add
	 * @return true if the racer was added to the queue
	 */
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
	
	/**
	 * Swaps the first two racers in the waiting queue, so that
	 * the original second racer in the queue will now go next,
	 * and the original first racer in the queue will now go second.
	 * 
	 * Only works if there are at least two racers in the queue
	 */
	public void swap()
	{
		if(_waitQueue.size() < 2) return;
		Racer r = _waitQueue.get(0);
		_waitQueue.set(0, _waitQueue.get(1));
		_waitQueue.set(1, r);
	}
	
	/**
	 * Returns whether or not this race has any racers
	 * @return true if there are no racers in this race
	 */
	public boolean isEmpty(){
		
		if(_waitQueue.size() == 0 && _inProgress.size() == 0 && _alreadyRan.size() == 0) return true;
		
		return false;
	}
	
	/**
	 * Clears the race of all racers
	 * @return true, unless somehow some error occurred
	 */
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
