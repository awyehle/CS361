/**
 * 
 * @author Andrew Yehle
 *Racer object has a bib number to identify racers.
 *
 */
public class Racer {

	int bib = -1;
	
	public Racer(int bib){
		this.bib = bib;
	}
	
	public int getBib(){
		
		return this.bib;
	}
	
}