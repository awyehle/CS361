/**
 * 
 * @author Andrew Yehle
 *Racer object has a bib number to identify racers.
 *
 */
public class Racer {

	private int bib = -1;
	
	public Racer(int bib){
		if(bib < 0 || bib > 9999) throw new IllegalArgumentException("Racer bib must be between [0,9999]. Entered: " + bib);
		this.bib = bib;
	}
	
	public int getBib(){
		
		return this.bib;
	}
	
}