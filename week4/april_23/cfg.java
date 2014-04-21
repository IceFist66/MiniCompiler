import java.util.ArrayList;

public class cfg {
	
	private ArrayList<Node> listOfCFGs;
	//private AST.Program p;
	int currentIDNum;
	private boolean test;
	
	public enum NodeType {ENTRY, EXIT, WHILE_JOIN, IF_JOIN, IF, WHILE, PRINT, RETURN, ASSIGN};
	
	public class Node {
		private String id;
		private NodeType nodeType;
		//private AST.Statement command;
		private String text;
		private ArrayList<Node> predNodes;
		private ArrayList<Node> succNodes;
		private ArrayList<Node> domSet;
		private ArrayList<String> genSet;
		private ArrayList<String> killSet;
		private ArrayList<String> liveOut;
		private ArrayList<String> w; // holds id of params and locals passed into function
		private Node backEdgeTarget;
		
		public Node (NodeType nodeType, /*AST.Statement command,*/ String text, ArrayList<Node> predNodes, ArrayList<Node> succNodes) {
			this.id = generateNewNodeID();
			this.nodeType = nodeType;
			//this.command = command;
			this.text = text;
			this.predNodes = predNodes;
			this.succNodes = succNodes;
			this.domSet = new ArrayList<Node>();
			this.genSet = new ArrayList<String>();
			this.killSet = new ArrayList<String>();
			this.liveOut = new ArrayList<String>();
			this.locals = new ArrayList<String>();
			backEdgeTarget = null;
		}
		
		public Node (NodeType nodeType, /*AST.Statement command,*/ String text, Node predNode, Node succNode) {
			this.id = generateNewNodeID();
			this.nodeType = nodeType;
			//this.command = command;
			this.text = text;
			this.predNodes = new ArrayList<Node>();
			this.succNodes = new ArrayList<Node>();
			this.domSet = new ArrayList<Node>();
			this.genSet = new ArrayList<String>();
			this.killSet = new ArrayList<String>();
			this.liveOut = new ArrayList<String>();
			this.locals = new ArrayList<String>();
			predNodes.add(predNode);
			succNodes.add(succNode);
			backEdgeTarget = null;
		}
		
		public Node (NodeType nodeType, /*AST.Statement command,*/ String text, Node predNode) {
			this.id = generateNewNodeID();
			this.nodeType = nodeType;
			//this.command = command;
			this.text = text;
			this.predNodes = new ArrayList<Node>();
			this.succNodes = new ArrayList<Node>();
			this.domSet = new ArrayList<Node>();
			this.genSet = new ArrayList<String>();
			this.killSet = new ArrayList<String>();
			this.liveOut = new ArrayList<String>();
			this.locals = new ArrayList<String>();
			predNodes.add(predNode);
			backEdgeTarget = null;
		}
		
		public Node (NodeType nodeType, /*AST.Statement command,*/ String text) {
			this.id = generateNewNodeID();
			this.nodeType = nodeType;
			//this.command = command;
			this.text = text;
			this.predNodes = new ArrayList<Node>();
			this.succNodes = new ArrayList<Node>();
			this.domSet = new ArrayList<Node>();
			this.genSet = new ArrayList<String>();
			this.killSet = new ArrayList<String>();
			this.liveOut = new ArrayList<String>();
			this.locals = new ArrayList<String>();
			backEdgeTarget = null;
		}
	}
	
	public String generateNewNodeID() {
		return "s" + currentIDNum++;
	}
}