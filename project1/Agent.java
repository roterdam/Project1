package project1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class Agent {

	//*****************************************************************************************************
	//*****************************************************************************************************
	//  SWITCHES FOR MONITORING AGENT EXECUTION AND FOR DEBUGGING PURPOSES 
	
	protected static final boolean ENABLE_CONSOLE = true;  		// enables/disables console output
	
	static final boolean solveSingleProblem = true;					// Results in only a single problem
																	//   to be solved by name.
	
	private static final String debugThisProblem = "2x1 Basic Problem 12";	// Name of the single problem
																			//   to be solved.
	
	private static final boolean listProblemDetails = true;		// When true, the contents of the
																//   RavensProblem object are listed
																//   on the system console.

	//*****************************************************************************************************
	//*****************************************************************************************************
	
	// Constants
	protected static final String TIMESTAMP = "TIMESTAMP";
	protected static final String AGENT_START = "AGENT_START";
	protected static final String PROBLEM_START = "PROBLEM_START";
	protected static final String NO_TIMESTAMP = "NO_TIMESTAMP";
	protected static final String DEFAULT_ANSWER = "1";
	
	// Classes instances
	private RavensProblem problem;
	private ConsoleLog conLog; 
	private SemanticNetwork semNet;
	private MeansEnds meansEnds;
		
	// Hashmaps
	HashMap<String, RavensFigure> figures;
   

	/**
     * Default constructor
     */
	public Agent() {
		
    	this.conLog = new ConsoleLog();							 
    	conLog.writeMsg(AGENT_START, null);
		
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
		
		if (!solveSingleProblem){
			// Process all RavensProblem objects
			agentAnswer = solveThisProblem();
		} else if (solveSingleProblem && (this.problem.getName().equals(debugThisProblem))) {	
			// Process only a single RavensProblem object by name
	    	agentAnswer = solveThisProblem();
		}
			
    	return agentAnswer;
    }

    private String solveThisProblem() {
    	
    	conLog.writeMsg(PROBLEM_START, this.problem.getName());
    	
    	String answer = DEFAULT_ANSWER;
    	this.semNet = new SemanticNetwork(problem, this.conLog, this.listProblemDetails);
    	this.meansEnds = new MeansEnds();

    	long problemStartTime = System.nanoTime();  // THIS STATEMENT'S POSITION IS IMPORTANT!
    	try {
    		semNet.build();
		} catch (Exception e) {
			conLog.writeMsg(TIMESTAMP, "FAILURE: Semantic Network - problem aborted");
    		conLog.reportElapsedTime(problemStartTime);
			e.printStackTrace();
			return DEFAULT_ANSWER;
		}
		
		try {
			//TODO: MeansEnds class invocation; replace with functioning code
		} catch (Exception e) {
			conLog.writeMsg(TIMESTAMP, "FAILURE: Means-Ends - problem aborted");
    		conLog.reportElapsedTime(problemStartTime);
			e.printStackTrace();
			return DEFAULT_ANSWER;
		}

		// Dispose of Class instantiations created by this program. 
		this.semNet = null;
		this.meansEnds = null;
		
		// Calculate and report the time elapsed to solve this problem.
		conLog.reportElapsedTime(problemStartTime);
		
		// *** MUST ONLY EXECUTE checkAnswer() WHEN MONITORING AND DEBUGGING!!  ***
		if (ENABLE_CONSOLE){
			String correctAnswer = problem.checkAnswer(answer);
			if (correctAnswer.equals(answer)){
				conLog.writeMsg(NO_TIMESTAMP, "Correct!  Agent's answer(" + answer + ")");
			}else{
				conLog.writeMsg(NO_TIMESTAMP, "Agent's answer(" + answer + 
						") - correct answer (" + correctAnswer + ")");	
			}	
		}


		return answer;
	}

}
