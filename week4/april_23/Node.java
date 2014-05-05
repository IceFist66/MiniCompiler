import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import iloc.Instruction;

public class Node {
	private int id;
	private NodeType nodeType;
	//private AST.Statement command;
	private String text;
	private ArrayList<Node> predNodes;
	private ArrayList<Node> succNodes;
	private ArrayList<Instruction> instructions;
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
		this.instructions = new ArrayList<Instruction>();
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
	   this.instructions = new ArrayList<Instruction>();
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
		this.instructions = new ArrayList<Instruction>();
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
	
	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}
	
	public void setInstructions(ArrayList<Instruction> instructions) {
		this.instructions = instructions;
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

      //System.out.println("NodeType(head) = " + nodeType);
		edges = new ArrayList<String>();
		fileName = "cfg_" + funcName + ".dot";
		f = new FileWriter(new File(fileName));
		f.write("Digraph G {\n");
		f.write(labelNode(this));
		for (Node s : succNodes) {
		   //System.out.println("NodeType(not head) = " + s.getNodeType());
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
		
		if (n.getNodeType() == NodeType.IF || n.getNodeType() == NodeType.WHILE) {
		   //System.out.println("if or while");
			Node thenNode = n.getSuccNodes().get(0);
			//System.out.println("Then node = " + thenNode.getNodeType());
			Node elseNode = null;
			if (n.getSuccNodes().size() == 2) {
			   elseNode = n.getSuccNodes().get(1);
			   //System.out.println("2");
			} else
			   //System.out.println("no else statement");	
			if (n.getSuccNodes().size() > 2)
			   System.out.println("Warning: control statement has more than two children!");			   
			f.write(labelNode(thenNode));
			String edgeTrue = n.getId() + " -> " + thenNode.getId() + " [label = \"true\"];\n";			   
			edges.add(edgeTrue);
			printNode(thenNode, f, edges);
			if (elseNode != null) {			   
			   //System.out.println("Else node = " + elseNode.getNodeType());
			   f.write(labelNode(elseNode));
			   String edgeFalse = n.getId() + " -> " + elseNode.getId() + " [label = \"false\"];\n";
			   edges.add(edgeFalse);
			   printNode(elseNode, f, edges);
			}
		} else {
			for (Node s : n.getSuccNodes()) {			
			   if (n.getNodeType() == NodeType.WHILE_BODY) {
		         //System.out.println("while-body");
			      //String backEdge = n.getId() + " -> " + n.getBackEdgeTarget().getId() + ";\n";
			      String backEdge = n.getId() + " -> " + n.getBackEdgeTarget().getId() + " [label = \"true\"];\n";
			      edges.add(backEdge);
			      //System.out.println("added backedge");
		      }			
		      //System.out.println("node type = " + s.getNodeType());
			   f.write(labelNode(s));
			   if (n.getNodeType() == NodeType.WHILE_BODY)
			      edge = n.getId() + " -> " + s.getId() + " [label = \"false\"];\n";
			   else
			      edge = n.getId() + " -> " + s.getId() + ";\n";		
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
