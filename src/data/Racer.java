package data;
/**
 * 
 * @author Andrew Yehle
 *Racer object has a bib number to identify racers.
 *
 */
public class Racer {

	private int bib = -1;
	
	/**
	 * Create a new racer with a bib number
	 * @param bib the number associated with this racer
	 */
	public Racer(int bib){
		if(bib < 0 || bib > 9999) throw new IllegalArgumentException("Racer bib must be between [0,9999]. Entered: " + bib);
		this.bib = bib;
	}
	
	/**
	 * Get the bib number associated with this racer
	 * @return bib number
	 */
	public int getBib(){
		
		return this.bib;
	}
	
	@Override
	public String toString()
	{
		return bib + "";
	}
	
}