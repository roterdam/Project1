package project1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SemanticNetwork {

	// Instance variables
	private RavensProblem problem;	// Problem object passed to this agent
	private ConsoleLog conLog;		// Console log; used for agent monitoring and debugging
	
	private boolean listDetails;	// When true, the details of the RavensProblem
									//   object will be listed on the system console.
	
	// Collections
	private ArrayList<String> avowed 	= new ArrayList<>(20);	// Avowed objects in Figs A-C
	private ArrayList<String> disavowed = new ArrayList<>(20);	// Disavowed objects in Figs A-C
	
	// Stuff in Figures (i.e. objects, shapes, other attributes)
	private ArrayList<String> inFigA = new ArrayList<>(20);		// All objects in Figure A
	private ArrayList<String> inFigB = new ArrayList<>(20);		// All objects in Figure B
	private ArrayList<String> inFigC = new ArrayList<>(20);		// All objects in Figure C
	
	/*
	 * Default Constructor
	 * 
	 * @param problem the RavensProblem the agent was given.
	 * @param console the class used to output messages to the console.
	 * @param listDetails is a flag used to list the contents of the RavensProblem object.
	 */
	public SemanticNetwork( RavensProblem problem, 
							ConsoleLog console, 
							boolean listDetails){
		
		this.problem = problem; 
		this.conLog = console;
		if (listDetails)
			this.listDetails = true;
	}
	
	/*
	 *  Builds not so much a semantic network of objects and relationships, but two lists:
	 *    one list whose elements (object names: z, y, x...) are determined by logic/rule 
	 *    to be given greater weighting when choosing a correct answer, i.e. the avowed list;
	 *    a disavowed list whose elements(also object names) that by logic/rule will not  
	 *    exist in the correct answer, and are accordingly given a reduced weighting.
	 *    
	 */
	public void buildSemNet(){
		
		// Identifies objects (z, y, x ...) that can later be pruned by giving answer
		//   figures 1-6  a negative or positive weighting.  This pruning will be done here
		//   by logic/rules.
		setupObjectTokens();
		makeObjectRules(this.inFigA, this.inFigB);
		setupObjectShapes();
		makeShapeRules();
	}


	public void setupObjectTokens() {
		
		// For monitoring or debugging purposes, list all the figures, objects and properties 
		//  in the RavensProblem object.
		if (listDetails)
			dumpRavensProblemDetails();
		
		// Populate figure A-C lists with RavensProblem data.  Each arraylist contains the
		//   names that are found within them.
		Collection<RavensFigure> values = problem.getFigures().values();
		for (Object figure : values) {
			RavensFigure ravensFigure = (RavensFigure) figure;
			String figureName = ravensFigure.getName();
			switch (figureName){
        		case "A":
        			this.inFigA = extractObjectTokens(ravensFigure);
        			break;
        		case "B":
        			this.inFigB = extractObjectTokens(ravensFigure);
        			break;
        		case "C":
        			this.inFigC = extractObjectTokens(ravensFigure);
        			break;
            	default: 
            		// Not interested in Figures D and 1-6
            		break;
			}
        }
		
		// For monitoring or debugging purposes, list all the figures, objects and properties 
		//  in the RavensProblem object.
		if (listDetails){
			conLog.writeMsg(Agent.TIMESTAMP, "Leaving setupObjectTokens()");
			conLog.writeMsg(Agent.NO_TIMESTAMP, "  objects in Figure A = " + this.inFigA.toString());
			conLog.writeMsg(Agent.NO_TIMESTAMP, "  objects in Figure B = " + this.inFigB.toString());
			conLog.writeMsg(Agent.NO_TIMESTAMP, "  objects in Figure C = " + this.inFigC.toString());
		}
	}
	
	
	private void setupObjectShapes() {
		
		// Erase contents of figure arraylists
		this.inFigA.clear();
		this.inFigA.clear();
		this.inFigA.clear();
		
		// Populate figure A-C lists with RavensProblem data.  Each arraylist contains the
		//   object names and shape names and associated that are found within them.
		Collection<RavensFigure> values = problem.getFigures().values();
		for (Object figure : values) {
			RavensFigure ravensFigure = (RavensFigure) figure;
			String figureName = ravensFigure.getName();
			switch (figureName){
        		case "A":
        			this.inFigA = extractShapeTokens(ravensFigure);
        			break;
        		case "B":
        			this.inFigB = extractShapeTokens(ravensFigure);
        			break;
        		case "C":
        			this.inFigC = extractShapeTokens(ravensFigure);
        			break;
            	default: 
            		// Not interested in Figures D and 1-6
            		break;
			}
		}
		// For monitoring or debugging purposes, list all the figures, objects and properties 
		//  in the RavensProblem object.
		if (listDetails){
			conLog.writeMsg(Agent.TIMESTAMP, "Leaving setupShapeTokens()");
			conLog.writeMsg(Agent.NO_TIMESTAMP, "  shapes in Figure A = " + this.inFigA.toString());
			conLog.writeMsg(Agent.NO_TIMESTAMP, "  shapes in Figure B = " + this.inFigB.toString());
			conLog.writeMsg(Agent.NO_TIMESTAMP, "  shapes in Figure C = " + this.inFigC.toString());
		}
	}

	
	private ArrayList<String> extractObjectTokens(RavensFigure ravensFigure) {
		
		ArrayList<RavensObject> objects;
		ArrayList<String> tempFig = new ArrayList<>(20);
		String objectName;
		
		// extract object names: z, y, x ...
		objects = ravensFigure.getObjects();
		for (Object object : objects) {
			RavensObject ravensObject = (RavensObject) object;
			objectName = ravensObject.getName();
			tempFig.add(objectName);
		}
		return tempFig;
	}
	
	
	private ArrayList<String> extractShapeTokens(RavensFigure ravensFigure) { 
		
		ArrayList<RavensObject> objects;
		ArrayList<String> tempFig = new ArrayList<>(20);
		String objectName;
		
		// extract object names: z, y, x ...
		objects = ravensFigure.getObjects();
		for (Object object : objects) {
			RavensObject ravensObject = (RavensObject) object;
			objectName = ravensObject.getName();
			
			// extract shape names: square, circle, triangle ...
			ArrayList<RavensAttribute> attributes = ravensObject.getAttributes();
			for (Object attrib : attributes) {
				RavensAttribute ravensAttribute = (RavensAttribute) attrib;
				String attribName = ravensAttribute.getName();
				String attribValue = ravensAttribute.getValue();
				if (attribName.equals("shape")){
					String tokenStr = String.format("obj=%s:shape=%s", objectName, attribValue);  
        			tempFig.add(tokenStr);
				}
			}
		}
		return tempFig;
	}

	
	public void dumpRavensProblemDetails(){
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
				conLog.writeMsg(Agent.NO_TIMESTAMP, "   object name = " + objectName);
				ArrayList<RavensAttribute> attributes = ravensObject.getAttributes();
				for (Object attrib : attributes) {
					RavensAttribute ravensAttribute = (RavensAttribute) attrib;
					String attribName = ravensAttribute.getName();
					String attribValue = ravensAttribute.getValue();
					conLog.writeMsg(Agent.NO_TIMESTAMP, "      Attribute name = " + 
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
	
	
	public ArrayList<String> getObjectsAvowed(){
		return this.avowed; 
	}
	
	
	public ArrayList<String> getObjectsDisavowed(){
		return this.disavowed; 
	}
	
	
	private void makeObjectRules(ArrayList<String> figA, ArrayList<String> figB) {
	
		// Using Figure A as a reference ...
		if ( !(figB.isEmpty())){
			for (String objectName : figA){
				// If an object name is present in FigA but NOT in FigB, then add it to the  
				//   disavowed list because by inference the same object name in FigC should
				//   not be in the answer figure
				if (!(figB.contains(objectName))) {
					String strToken = String.format("Obj=%s:NotInAnswer", objectName); 
					this.disavowed.add(strToken);
					break;
				} else {
					// If an object name is present in FigA and also in FigB, then add it to the  
					//   avowed list because by inference an object name in FigC will also be
					//   in the answer figure.
					if ((figB.contains(objectName))) {
						String strToken = String.format("Obj=%s:WillBeInAnswer", objectName); 
						this.avowed.add(strToken);
					}
				}
			}
		}
		
		// Using Figure B as a reference ...
		if ( !(figA.isEmpty())){
			for (String objectName : figB){
				// If an object name is present in FigB but not in FigA, it was added in FigB.
				//   Thus add the object name to the avowed list because by inference any object not
				//   found in C will be in the answer figure.
				if (!(figA.contains(objectName))) {
					String strToken = String.format("Obj=%s:NotInFigC", objectName); 
					this.avowed.add(strToken);
					break;
				} 
			}
		}
		
		// For monitoring or debugging purposes, list all the figures, objects and properties 
		//  in the RavensProblem object.
		if (listDetails){
			conLog.writeMsg(Agent.TIMESTAMP, "Leaving makeObjectRules()");
			conLog.writeMsg(Agent.NO_TIMESTAMP, "  avowed list = " + this.avowed.toString());
			conLog.writeMsg(Agent.NO_TIMESTAMP, "  disavowed list = " + this.disavowed.toString());
		}
	}
	
	
	private void makeShapeRules() {
		// TODO Auto-generated method stub
		
	}

	
	//TODO 
	/* **** USE THIS TO EXTRACT INFORMATION FROM RavensProblem object
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
*/
	
	
}
