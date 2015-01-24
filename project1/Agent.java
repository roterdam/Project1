package project1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class Agent {

	// Switches for monitoring agent execution and debugging  
	public static final boolean SOLVE_SINGLE_PROBLEM = false;				//* results in only a single problem
																			//*   to be solved by name
	public static final String debugThisProblem = "2x1 Basic Problem 01";	//* name of single problem
																			//*   to be solved
	
	public static final boolean ENABLE_CONSOLE = true;  // enables/disables console output
	
	// Constants
	public static final String TIMESTAMP = "TIMESTAMP";
	public static final String AGENT_START = "AGENT_START";
	public static final String PROBLEM_START = "PROBLEM_START";
	public static final String DEFAULT_ANSWER = "1";
	
	// Classes instances
	private RavensProblem problem;
	private RavensGizmos gizmos;
	private SemanticNetwork semNet;
	private MeansEnds meansEnds;
		
	// Hashmaps
	HashMap<String, RavensFigure> figures;
   

	/**
     * Default constructor
     */
	public Agent() {
		
    	this.gizmos = new RavensGizmos();
    								 
		gizmos.consoleMsg(AGENT_START, null);
		
    }
	
	/**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return a String representing its
     * answer to the question: "1", "2", "3", "4", "5", or "6". These Strings
     * are also the Names of the individual RavensFigures, obtained through
     * RavensFigure.getName().
     * 
     * @param problem the RavensProblem the agent given
     * @return Agent's answer to the problem
     */
	public String Solve(RavensProblem problem) {
       
		// Initialization and miscellaneous housekeeping
    	this.problem = problem;
    	String agentAnswer = DEFAULT_ANSWER;	// initialize the Agent's answer.
		
		if (!SOLVE_SINGLE_PROBLEM){
			agentAnswer = solveThisProblem();
		} else if (SOLVE_SINGLE_PROBLEM && (this.problem.getName().equals(debugThisProblem))) {	
	    	agentAnswer = solveThisProblem();
		}
    	return agentAnswer;
    }

    private String solveThisProblem() {
    	
    	gizmos.consoleMsg(PROBLEM_START, this.problem.getName());
    	
    	this.semNet = new SemanticNetwork();
    	this.meansEnds = new MeansEnds();
    	String answer = DEFAULT_ANSWER;
    	
    	long problemStartTime = System.nanoTime();  // THIS STATEMENT'S POSITION IS IMPORTANT!
    	try {
    		semNet.Entry(); //TODO: replace with functioning code
		} catch (Exception e) {
			gizmos.consoleMsg(TIMESTAMP, "FAILURE: Semantic Network - problem aborted");
    		gizmos.reportElapsedTime(problemStartTime);
			e.printStackTrace();
			return DEFAULT_ANSWER;
		}
		
		try {
			answer = meansEnds.Exit(); //TODO: replace with functioning code
		} catch (Exception e) {
			gizmos.consoleMsg(TIMESTAMP, "FAILURE: Means-Ends - problem aborted");
    		gizmos.reportElapsedTime(problemStartTime);
			e.printStackTrace();
			return DEFAULT_ANSWER;
		}

		// Dispose of Class instantiations created by this program. 
		this.semNet = null;
		this.meansEnds = null;
		
		gizmos.reportElapsedTime(problemStartTime);
		gizmos.consoleMsg(TIMESTAMP, "answer is " + answer);
		return answer;
	}

}
