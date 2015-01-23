package project1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class Agent {
   
	// Classes instances
	private RavensProblem problem;
	private SemanticNetwork semNet;
	private MeansEnds meansEnds;
	
	// Constants
	public static final boolean ENABLE_CONSOLE = true;  // enables/disables console output
	public static final String TIMESTAMP = "TIMESTAMP";
	public static final String AGENT_START = "AGENT_START";
	public static final String PROBLEM_START = "PROBLEM_START";
	
	// Hashmaps
	HashMap<String, RavensFigure> figures;
   

	/**
     * Default constructor
     */
	public Agent() {
		consoleMsg(AGENT_START, null);
    }
	
    /**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return a String representing its
     * answer to the question: "1", "2", "3", "4", "5", or "6". These Strings
     * are also the Names of the individual RavensFigures, obtained through
     * RavensFigure.getName().
     * 
     * In addition to returning your answer at the end of the method, your Agent
     * may also call problem.checkAnswer(String givenAnswer). The parameter
     * passed to checkAnswer should be your Agent's current guess for the
     * problem; checkAnswer will return the correct answer to the problem. This
     * allows your Agent to check its answer. Note, however, that after your
     * agent has called checkAnswer, it will *not* be able to change its answer.
     * checkAnswer is used to allow your Agent to learn from its incorrect
     * answers; however, your Agent cannot change the answer to a question it
     * has already answered.
     * 
     * If your Agent calls checkAnswer during execution of Solve, the answer it
     * returns will be ignored; otherwise, the answer returned at the end of
     * Solve will be taken as your Agent's answer to this problem.
     * 
     * @param problem the RavensProblem your agent should solve
     * @return your Agent's answer to this problem
     */
	public String Solve(RavensProblem problem) {
        
    	// Initialization and miscellaneous housekeeping
    	this.problem = problem;
    	consoleMsg(PROBLEM_START, this.problem.getName());
    	
    	semNet = new SemanticNetwork();
    	meansEnds = new MeansEnds();
    	long problemStartTime;
    	long problemEndTime;
    	
    	String agentAnswer = "";  // initialize the Agent's answer.

    	problemStartTime = System.nanoTime();  // THIS STATEMENT'S POSITION IS IMPORTANT!
    	try {
    		//this.figures = problem.getFigures();
    		//consoleMsg(TIMESTAMP, "figure A is " + this.problem.getFigures().get("A"));
    		//consoleMsg(TIMESTAMP, "figure B is " + figures.get("B"));
    		//consoleMsg(TIMESTAMP, "figure C is " + figures.get("C"));
    		semNet.Entry(); //TODO: replace with functioning code
		} catch (Exception e) {
    		consoleMsg(TIMESTAMP, "FAILURE - Semantic Network aborted");
			e.printStackTrace();
		}
		
		try {
			agentAnswer = meansEnds.Exit(); //TODO: replace with functioning code
		} catch (Exception e) {
    		consoleMsg(TIMESTAMP, "FAILURE - Means-Ends aborted");
			e.printStackTrace();
		}
		problemEndTime = System.nanoTime();  // THIS STATEMENT'S POSITION IS IMPORTANT!

		// List the answer and time in milliseconds it took to solve the problem
		consoleMsg(TIMESTAMP, "answer is " + agentAnswer);
		long problemElapsedTimeMs = ((problemEndTime - problemStartTime) / 1000000);
		consoleMsg(TIMESTAMP, "solution took = " + Long.toString(problemElapsedTimeMs) + " ms");
		
		// Dispose of Class instantiations created by this program. 
		semNet = null;
		meansEnds = null;
		
    	return agentAnswer;
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
    private void consoleMsg(String type, String msg) {

		if (ENABLE_CONSOLE) {
			Date sysClock = new Date();
			SimpleDateFormat df = new SimpleDateFormat();
			switch (type) {
	        	case TIMESTAMP:
	        		df.applyPattern("hh.mm.ss:S ");
	        		System.out.println(df.format(sysClock) + msg);
	        		break;
	        	case PROBLEM_START:
	        		System.out.println("\n************* " + msg);
	        		break;
	        	case AGENT_START:
	        		df.applyPattern("yyyy.MM.dd E");
	        		String asterisks = "***************************************";
	        		System.out.println("\n\n" + asterisks);
	        		System.out.println("*** Agent Started on " + df.format(sysClock));
	           		System.out.println(asterisks);
	        		break;
	        	default: 
	        		System.out.println(msg);
	        		break;
			}
		}
		
	}
	
}
