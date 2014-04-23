import java.util.ArrayList;

public class Node {
	private int id;
	private NodeType nodeType;
	//private AST.Statement command;
	private String text;
	private ArrayList<Node> predNodes;
	private ArrayList<Node> succNodes;
	//private ArrayList<Node> domSet;
	private ArrayList<String> genSet;
	private ArrayList<String> killSet;
	private ArrayList<String> liveOut;
	private ArrayList<String> locals; // holds id of params and locals passed into function
	private Node backEdgeTarget;
	
	public Node (NodeType nodeType, int id, /*AST.Statement command,*/ String text, ArrayList<Node> predNodes, ArrayList<Node> succNodes) {
		this.id = id;
		this.nodeType = nodeType;
		//this.command = command;
		this.text = text;
		this.predNodes = predNodes;
		this.succNodes = succNodes;
		//this.domSet = new ArrayList<Node>();
		this.genSet = new ArrayList<String>();
		this.killSet = new ArrayList<String>();
		this.liveOut = new ArrayList<String>();
		this.locals = new ArrayList<String>();
		backEdgeTarget = null;
	}
	
	public Node (NodeType nodeType, int id, /*AST.Statement command,*/ String text, Node predNode, Node succNode) {
		this.id = id;
		this.nodeType = nodeType;
		//this.command = command;
		this.text = text;
		this.predNodes = new ArrayList<Node>();
		this.succNodes = new ArrayList<Node>();
		//this.domSet = new ArrayList<Node>();
		this.genSet = new ArrayList<String>();
		this.killSet = new ArrayList<String>();
		this.liveOut = new ArrayList<String>();
		this.locals = new ArrayList<String>();
		predNodes.add(predNode);
		succNodes.add(succNode);
		backEdgeTarget = null;
	}
	
	public Node (NodeType nodeType, int id,/*AST.Statement command,*/ String text, Node predNode) {
		this.id = id;
		this.nodeType = nodeType;
		//this.command = command;
		this.text = text;
		this.predNodes = new ArrayList<Node>();
		this.succNodes = new ArrayList<Node>();
		//this.domSet = new ArrayList<Node>();
		this.genSet = new ArrayList<String>();
		this.killSet = new ArrayList<String>();
		this.liveOut = new ArrayList<String>();
		this.locals = new ArrayList<String>();
		predNodes.add(predNode);
		backEdgeTarget = null;
	}
	
	public Node (NodeType nodeType, int id,/*AST.Statement command,*/ String text) {
		this.id = id;
		this.nodeType = nodeType;
		//this.command = command;
		this.text = text;
		this.predNodes = new ArrayList<Node>();
		this.succNodes = new ArrayList<Node>();
		//this.domSet = new ArrayList<Node>();
		this.genSet = new ArrayList<String>();
		this.killSet = new ArrayList<String>();
		this.liveOut = new ArrayList<String>();
		this.locals = new ArrayList<String>();
		backEdgeTarget = null;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList<Node> getPredNodes() {
		return predNodes;
	}

	public void setPredNodes(ArrayList<Node> predNodes) {
		this.predNodes = predNodes;
	}

	public ArrayList<Node> getSuccNodes() {
		return succNodes;
	}

	public void setSuccNodes(ArrayList<Node> succNodes) {
		this.succNodes = succNodes;
	}

	public ArrayList<String> getGenSet() {
		return genSet;
	}

	public void setGenSet(ArrayList<String> genSet) {
		this.genSet = genSet;
	}

	public ArrayList<String> getKillSet() {
		return killSet;
	}

	public void setKillSet(ArrayList<String> killSet) {
		this.killSet = killSet;
	}

	public ArrayList<String> getLiveOut() {
		return liveOut;
	}

	public void setLiveOut(ArrayList<String> liveOut) {
		this.liveOut = liveOut;
	}

	public ArrayList<String> getLocals() {
		return locals;
	}

	public void setLocals(ArrayList<String> locals) {
		this.locals = locals;
	}

	public Node getBackEdgeTarget() {
		return backEdgeTarget;
	}

	public void setBackEdgeTarget(Node backEdgeTarget) {
		this.backEdgeTarget = backEdgeTarget;
	}
}
