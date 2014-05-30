import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class CFG {
	
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
		private ArrayList<String> locals; // holds id of params and locals passed into function
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
	
   public CFG() {
		currentIDNum = 0;
		test = false;
		listOfCFGs = new ArrayList<Node>();
	}
	
	public String generateNewNodeID() {
		return "s" + currentIDNum++;
	}

   public void printCFGtoDotFile(ArrayList<Node> listOfCFGs, ArrayList<String> funcNames) throws IOException {
		FileWriter f;
		String funcName, fileName;
		int count = 0;
		//AST.Function func;
		ArrayList<String> edges; 
		String edge;
		
		for (Node n : listOfCFGs) {
			//liveOutGraph(n);			
			edges = new ArrayList<String>();
			funcName = funcNames.get(count++);
			fileName = "cfg_" + funcName + ".dot";
			f = new FileWriter(new File(fileName));
			f.write("Digraph G {\n");
			f.write(labelNode(n));
			for (Node s : n.succNodes) {
				f.write(labelNode(s));
				edge = n.id + " -> " + s.id + ";\n";
				edges.add(edge);
				printNode(s, f, edges);
			}
			ArrayList<String> nodups = removeDuplicates(edges, false);
			for (String e : nodups)
				f.write(e);
			f.write("}");
			f.close();
		}
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
		return n.id + " [label = \"" + n.id.replace('s', ' ').trim() 
				//+ "\\n" + domSetToString(n) + "\\n"
				+ n.text + "\\n"
				//+ liveOutSetToString(n)
				+ "\", shape = " + shape + "];\n";
	}
	
	public void printNode(Node n, FileWriter f, ArrayList<String> edges) throws IOException {
		String edge;		
		
		if (n.nodeType == NodeType.IF || n.nodeType == NodeType.WHILE) {
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
			String backEdge = n.id + " -> " + n.backEdgeTarget.id + ";\n";
			edges.add(backEdge);
		}		
		else {
			for (Node s : n.succNodes) {
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
