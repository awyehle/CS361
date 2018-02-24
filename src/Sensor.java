
public class Sensor {

	private Chronotimer _connection;
	
	public void setConnection(Chronotimer connection)
	{
		this._connection=connection;
	}
	
	public void trip()
	{
		if(_connection==null)
			return;
		_connection.runCommand(new Time().convertRawTime(), "TRIG", ""+_connection.getConnection(this));
	}
}
