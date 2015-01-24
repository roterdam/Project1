package project1;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RavensGizmos {

	/**
     * Default constructor
     */
	public RavensGizmos(){ 
	
	}
	
	/*****
     * Prints a message to the console, as a debugging aid. It can also be used
     * to monitor the progress of the agent as it works on the problem.  It is
     * particularly helpful when watching for endless loops.
     * 
     * Requires that ENABLE_CONSOLE is true to print messages to system console.
     * The default setting is ENABLE_CONSOLE = true.
     * 
     * Parameters: 
     * 	String type
     * 		TIMESTAMP prefixes the message with a time stamp (hh.mm.ss:SSS)
     * 		PROBLEM_START prints the name of the problem
     * 		AGENT_START prints the date Agent() was instantiated (yyyy.MM.dd E)
     * 	String msg - the message that will be printed 
     */
    public void consoleMsg(String messageType, String message) {

		if (Agent.ENABLE_CONSOLE) {
			Date sysClock = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat();
			switch (messageType) {
	        	case Agent.TIMESTAMP:
	        		sdf.applyPattern("hh.mm.ss:S ");
	        		System.out.println(sdf.format(sysClock) + message);
	        		break;
	        	case Agent.PROBLEM_START:
	        		System.out.println("\n************* " + message);
	        		break;
	        	case Agent.AGENT_START:
	        		sdf.applyPattern("yyyy.MM.dd E");
	        		String asterisks = "***************************************";
	        		System.out.println("\n\n" + asterisks);
	        		System.out.println("*** Agent Started on " + sdf.format(sysClock));
	        		if (Agent.SOLVE_SINGLE_PROBLEM)
	        			System.out.println("*** Set to solve only a single problem");
	        		System.out.println(asterisks);	
	        		break;
	        	default: 
	        		System.out.println(message);
	        		break;
			}
		}
		
    }
	
    /**
     * List the time in milliseconds it took to solve the problem
     * 
     * @param problemStartTime
     */
    public void reportElapsedTime(long problemStartTime) {
    	
		long problemEndTime = System.nanoTime();
		long problemElapsedTimeMs = ((problemEndTime - problemStartTime) / 1000000);
		consoleMsg(Agent.TIMESTAMP, "solution took = " + Long.toString(problemElapsedTimeMs) + " ms");
	}
}
