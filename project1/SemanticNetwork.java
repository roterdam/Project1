package project1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SemanticNetwork {

	// Instance variables
	RavensProblem problem;	// Problem object passed to this agent
	ConsoleLog conLog;		// Console log; used for agent monitoring and debugging
	
	boolean listDetails;	// When true, the details of the RavensProblem
							//   object will be listed on the system console.
	
	Map<String, Integer> viableAnswersMap; 
	
	/*
	 * Default Constructor
	 * 
	 * @param problem the RavensProblem the agent was given.
	 * @param console the class used to output messages to the console.
	 * @param listDetails is a flag used to list the contents of the RavensProblem object.
	 */
	public SemanticNetwork(RavensProblem problem, ConsoleLog console, boolean listDetails){
		
		this.problem = problem; 
		this.conLog = console;
		if (listDetails)
			this.listDetails = true;
	}
	
	
	public void build() {
		
		// List all the Figures, Objects and properties in RavensProblem object.
		if (listDetails)
			listProblemDetails();
		
		this.viableAnswersMap = makeViableAnswersMap();

	}

	private Map<String, Integer> makeViableAnswersMap() {

		Map<String, Integer> answerFigures = new HashMap<>();
		
		// initialize all 6 figure answer candidates to have a zero weighting
		for (int i = 1; i <= 6; i++){
			String figure = Integer.toString(i);
			answerFigures.put(figure, 0);  // AnswerFigure i, weight = 0
		}
		
		
		// Dump contents of answerFigure hash map; for Monitor and Debug purposes
		if (Agent.ENABLE_CONSOLE){
			conLog.writeMsg(Agent.NO_TIMESTAMP, "SemanticNetwork() - Contents dump of viableAnswerMap HashMap ");
			for (int i = 1; i <= 6; i++){
				String figure = Integer.toString(i);
				conLog.writeMsg(Agent.NO_TIMESTAMP, "   figure = " + figure + " : weight = " + 
						answerFigures.get(figure));
			}
		}

		
		return answerFigures; 
	}

	private void dumpViableAnswersMap() {

		// Dump contents of answerFigure hash map; for Monitor and Debug purposes
		if (Agent.ENABLE_CONSOLE){
			conLog.writeMsg(Agent.NO_TIMESTAMP, "SemanticNetwork() - Contents dump of viableAnswerMap HashMap ");
			//TODO: Change for into iterator
			for (int i = 1; i <= 6; i++){
				String figure = Integer.toString(i);
				conLog.writeMsg(Agent.NO_TIMESTAMP, "   figure = " + figure + " : weight = " + 
						this.viableAnswersMap.get(figure));
			}
		}

		
		return; 
	}


	// attrInfo
	protected void listProblemDetails() {
		HashMap<String, Set> attribute = new HashMap<String, Set>();
		Collection<RavensFigure> values = problem.getFigures().values();
	
		for (Object figure : values) {
			RavensFigure ravensFigure = (RavensFigure) figure;
			String figureName = ravensFigure.getName();
			conLog.writeMsg(Agent.TIMESTAMP, "figure name = " + figureName);
			ArrayList<RavensObject> objects = ravensFigure.getObjects();
			for (Object object : objects) {
				RavensObject ravensObject = (RavensObject) object;
				String objectName = ravensObject.getName();
				conLog.writeMsg(Agent.TIMESTAMP, "   object name = " + objectName);
				ArrayList<RavensAttribute> attributes = ravensObject.getAttributes();
				for (Object attrib : attributes) {
					RavensAttribute ravensAttribute = (RavensAttribute) attrib;
					String attribName = ravensAttribute.getName();
					String attribValue = ravensAttribute.getValue();
					conLog.writeMsg(Agent.TIMESTAMP, "      Attribute name = " + 
                		attribName + " :  value = " + attribValue);
					Set set = attribute.get(attribName);
					if (set == null) {
						set = new HashSet<String>();
						attribute.put(attribName, set);
					}
					set.add(attribValue);
				}
			}
		}
	}	

}
