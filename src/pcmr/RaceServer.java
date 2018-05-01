package pcmr;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import data.Racer;
import data.Result;
import data.Time;


public class RaceServer {

	static ArrayList<Result> dir = new ArrayList<Result>();
	static String[] _names = new String[9999];
	static final String _CSSfile = "style.css";
	private static Chronotimer _chrono;
	
	private static Thread _updater;
	
    // a shared area where we get the POST data and then use it in the other handler
    static String sharedResponse = "";
    static boolean gotMessageFlag = false;
    
    private static class Data
    {
    	Racer _whom; 
    	Time _time;
    	public Data(Racer whom, Time time)
    	{
    		_whom=whom;
    		_time=time;
    	}
    }
    
    public RaceServer(Chronotimer who) throws Exception
    {
    	if(who == null) throw new IllegalArgumentException("Chronotimer was not established");
    	_chrono = who;
    	getNames();
    	
    	_updater = new Thread() {
    		@Override
    		public void run()
    		{
				while(true) {
					dir=_chrono.getResults();
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					}
				}
    		}
    	};
    	_updater.start();
    	startup();
    }
    
    static void getNames()
    {
    	try(Scanner nameGetter = new Scanner(new File("racers.txt")))
    	{
    		for(int i = 0; i < _names.length; ++i)
    			_names[i] = nameGetter.nextLine();
    	}
    	catch(FileNotFoundException e) {e.printStackTrace();}
    }

    private static void startup() throws Exception {

        // set up a simple HTTP server on our local host
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // create a context to get the request to display the results
        server.createContext("/race-status/raw", new RawTextDisplayer());
        server.createContext("/race-status", new TableDisplayer());
        server.createContext("/race-status/style.css", new CSSDisplayer());

        // create a context to get the request for the POST
        server.createContext("/sendresults",new PostHandler());
        server.setExecutor(null); // creates a default executor

        // get it going
        System.out.println("Starting Server...");
        server.start();
    }
    static class CSSDisplayer implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
        	t.sendResponseHeaders(200, 0);
            OutputStream output = t.getResponseBody();
            FileInputStream fs = new FileInputStream(_CSSfile);
            final byte[] buffer = new byte[0x10000];
            int count = 0;
            while ((count = fs.read(buffer)) >= 0) {
                output.write(buffer, 0, count);
            }
            output.flush();
            output.close();
            fs.close();
        }
    }

    static class RawTextDisplayer implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {

            String response = "Begin of response\n";
			// set up the header
			String echo = "";
            System.out.println(response);
			
            response += "End of response\n";
            System.out.println(response);
            // write out the response
            //t.sendResponseHeaders(200, response.length());
            t.sendResponseHeaders(200, echo.length());
            OutputStream os = t.getResponseBody();
            //os.write(response.getBytes());
            os.write(echo.getBytes());
            os.close();
        }
    }
    
    static class TableDisplayer implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {

            String response = "Begin of response\n";
			// set up the header
			String echo = "";
            System.out.println(response);
			
            ArrayList<Data> results = new ArrayList<Data>();
            if(dir.size()>0)
            {
            	for(int i = 0; i < dir.get(0).results(); ++i)
            		{
                    results.add(
            				new Data(
            						dir.get(0).getRacers()[i], 
            						dir.get(0).getTimeForRacer(dir.get(0).getRacers()[i])));
            		}
            }
            results.sort(new TimeCompare());
			echo = toTable(results);
            response += "End of response\n";
            System.out.println(response);
            // write out the response
            //t.sendResponseHeaders(200, response.length());
            t.sendResponseHeaders(200, echo.length());
            OutputStream os = t.getResponseBody();
            //os.write(response.getBytes());
            os.write(echo.getBytes());
            os.close();
        }
        
        private String toTable(ArrayList<Data> results)
        {
        	String html = "<html>"
        			+ "<head>"
        			+ "<link rel=\"stylesheet\" href=\"race-status/style.css\">"
        			+ "</head>"
        			+ "<body>"
        			+ "<h2> Race: " + _chrono.getRun() + ": " + _chrono.getEvent() + "</h2>"
        			+ "<table>"
        			+ "<tr><th>Place</th>"
        			+"<th>Number</th>"
        			+ "<th>Name</th>"
        			+ "<th>Time</th></tr>";
        	for(int i = 0; i < results.size(); ++i)
        	{
        		html+= "<tr>"
        				+ "<td>" + (i+1) + "</td>"
                		+ "<td>" + results.get(i)._whom + "</td>"
                        + "<td>" + RaceServer.getName(results.get(i)._whom.getBib()) + "</td>"
                        + "<td>" + results.get(i)._time.convertRawTime() + "</td>"
        				+ "</tr>";
        	}
        	html+="</table></body></html>";
        	return html;
        }
        
        public class TimeCompare implements Comparator<Data>
    	{

    		@Override
    		public int compare(Data arg0, Data arg1) {
    			int ret = arg0._time.compareTo(arg1._time);
    			return ret;
    		}

    		
    	}
    }
    

    static class PostHandler implements HttpHandler {
        public void handle(HttpExchange transmission) throws IOException {

            //  shared data that is used with other handlers
            sharedResponse = "";

            // set up a stream to read the body of the request
            InputStream inputStr = transmission.getRequestBody();

            // set up a stream to write out the body of the response
            OutputStream outputStream = transmission.getResponseBody();

            // string to hold the result of reading in the request
            StringBuilder sb = new StringBuilder();

            // read the characters from the request byte by byte and build up the sharedResponse
            int nextChar = inputStr.read();
            while (nextChar > -1) {
                sb=sb.append((char)nextChar);
                nextChar=inputStr.read();
            }

            // create our response String to use in other handler
            sharedResponse = sharedResponse+sb.toString();

            // respond to the POST with ROGER
            String postResponse = "ROGER JSON RECEIVED";

            System.out.println("response: " + sharedResponse);
            String echo = "";
			Gson g = new Gson();
            try {
				if (!sharedResponse.isEmpty()) {
					switch(sharedResponse.split(" ")[0])
					{
					case "ADD":
						{ArrayList<Result> fromJson = g.fromJson(sharedResponse.split(" ")[1],
								new TypeToken<Collection<Racer>>() {
								}.getType());
						dir.clear(); //TODO Is it necessary to remove previous results?
						dir.addAll(fromJson);
						echo = "Result added:\n";
						for(Result e : fromJson)
							echo+= e.toString() + "\n";
						System.out.println(echo);
						break;}
					default:
						System.out.println("Invalid command received...");
					}
				}
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
            //runCommand(sharedResponse);
            //Desktop dt = Desktop.getDesktop();
            //dt.open(new File("raceresults.html"));

            // assume that stuff works all the time
            transmission.sendResponseHeaders(300, postResponse.length());

            // write it and return it
            outputStream.write(postResponse.getBytes());

            outputStream.close();
        }
    }
    
	@SuppressWarnings("unchecked")
	public void add(String json){
		
		Object[] list = new Gson().fromJson(json, new TypeToken<Collection<Object>>(){}.getType());
		dir.addAll((ArrayList<Result>) list[1]);
		
	}

	
	private void runCommand(String cmd)
	{
		String[] command = cmd.split(" ");
		if(cmd.equals("ADD"))
			add(command[1]);
	}
	
	
	public static String getName(int bib)
	{
		return _names[bib];
	}
	
}
