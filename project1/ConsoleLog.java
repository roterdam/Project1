package project1;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLog {
	
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
     * 	String message - the message that will be written to the system console 
     */
    public void writeMsg(String messageType, String message) {

		if (Agent.ENABLE_CONSOLE) {
			Date sysClock = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat();
			switch (messageType) {
	        	case Agent.TIMESTAMP:
	        		sdf.applyPattern("hh.mm.ss:S ");
	        		System.out.println(sdf.format(sysClock) + message);
	        		break;
	        	case Agent.NO_TIMESTAMP:
	        		String lineHeader = ("--.--.--:--- ");
	        		System.out.println(lineHeader + message);
	        		break;
	        	case Agent.PROBLEM_START:
	        		System.out.println("\n************* " + message);
	        		break;
	        	case Agent.AGENT_START:
	        		sdf.applyPattern("yyyy.MM.dd E");
	        		String asterisks = "***************************************";
	        		System.out.println("\n\n" + asterisks);
	        		System.out.println("*** Agent Started on " + sdf.format(sysClock));
	        		if (Agent.solveSingleProblem)
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
		writeMsg(Agent.TIMESTAMP, "solution took = " + Long.toString(problemElapsedTimeMs) + " ms");
	}
}

