import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

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
	
	public void printCFGtoDotFile(String funcName) throws IOException {
		FileWriter f;
		String fileName;
		ArrayList<String> edges; 
		String edge;

      System.out.println("NodeType(head) = " + nodeType);
		edges = new ArrayList<String>();
		fileName = "cfg_" + funcName + ".dot";
		f = new FileWriter(new File(fileName));
		f.write("Digraph G {\n");
		f.write(labelNode(this));
		for (Node s : succNodes) {
		   System.out.println("NodeType(not head) = " + s.getNodeType());
			f.write(labelNode(s));
			edge = id + " -> " + s.id + ";\n";
			edges.add(edge);
			printNode(s, f, edges);
		}
		ArrayList<String> nodups = removeDuplicates(edges, false);
		for (String e : nodups)
			f.write(e);
		f.write("}");
		f.close();
	}
	
	public String labelNode(Node n) {
		String shape;
		if (n.nodeType == NodeType.PRINT)
			shape = "parallelogram";
		else if (n.nodeType == NodeType.ENTRY || n.nodeType == NodeType.EXIT)
			shape = "oval";
		else if (n.nodeType == NodeType.IF || n.nodeType == NodeType.WHILE)
			shape = "diamond";
		else
			shape = "box";
		return n.id + " [label = \"" + "L" + n.id + " "//n.id.replace('s', ' ').trim() 
				//+ "\\n" + domSetToString(n) + "\\n"
				+ n.text + "\\n"
				//+ liveOutSetToString(n)
				+ "\", shape = " + shape + "];\n";
	}
	
	public void printNode(Node n, FileWriter f, ArrayList<String> edges) throws IOException {
		String edge;		
		
		if (n.nodeType == NodeType.IF || n.nodeType == NodeType.WHILE) {
		   System.out.println("if or while");
			Node thenNode = n.succNodes.get(0);
			Node elseNode = n.succNodes.get(1);
			f.write(labelNode(thenNode));
			f.write(labelNode(elseNode));
			String edgeTrue = n.id + " -> " + thenNode.id + " [label = \"true\"];\n";
			String edgeFalse = n.id + " -> " + elseNode.id + " [label = \"false\"];\n";
			edges.add(edgeTrue);
			edges.add(edgeFalse);
			printNode(thenNode, f, edges);
			printNode(elseNode, f, edges);
		}
		else if (n.nodeType == NodeType.WHILE_JOIN) {
		   System.out.println("while-join");
			String backEdge = n.id + " -> " + n.backEdgeTarget.id + ";\n";
			edges.add(backEdge);
		}		
		else {
			for (Node s : n.succNodes) {
		      System.out.println("node type = " + s.getNodeType());
			   f.write(labelNode(s));
			   edge = n.id + " -> " + s.id + ";\n";		
			   edges.add(edge);
			   printNode(s, f, edges);
			}
		}
	}
	
	public ArrayList<String> removeDuplicates(ArrayList<String>list, boolean ignoreCase){
	    Set<String> set = (ignoreCase?new TreeSet<String>(String.CASE_INSENSITIVE_ORDER):new LinkedHashSet<String>());
	    set.addAll(list);

	    ArrayList<String> res = new ArrayList<String>(set);
	    return res;
	}
}
