/**
 * System for timer...
 * @author Andrew Huelsman, whomever else
 *
 */
public class Chronotimer {

	private boolean _isOn;
	private Event POWER, TOG;
	private final int _CHANNELS = 8;
	private boolean[] _channelOn = new boolean[_CHANNELS];
	
	private abstract class Event {

		private String _name;
		
		public Event(String name)
		{
			this._name=name;
		}

	}

	private void initializeEvents()
	{
		POWER = new Event("POWER") {
			public void action()
			{
				_isOn = !_isOn;
			}
		};
		TOG = new Event("TOG") {
			public void action(int channel)
			{
				if(channel < 0 || channel >=_CHANNELS) throw new IllegalArgumentException("Channel does not exist");
				_channelOn[channel]=!_channelOn[channel];
			}
		};
	}
}
