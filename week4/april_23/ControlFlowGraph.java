
/*
 * 	Kevin Elliott - CSC 530 - Prof. Keen
 *  Assignment 3 - Fall 2013
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class ControlFlowGraph {
	
	private ArrayList<Node> listOfCFGs;
	private AST.Program p;
	int currentIDNum;
	private boolean test;
	
	public enum NodeType {ENTRY, EXIT, WHILE_JOIN, IF_JOIN, IF, WHILE, PRINT, RETURN, ASSIGN};
	
	public class Node {
		private String id;
		private NodeType nodeType;
		private AST.Statement command;
		private String text;
		private ArrayList<Node> predNodes;
		private ArrayList<Node> succNodes;
		private ArrayList<Node> domSet;
		private ArrayList<String> genSet;
		private ArrayList<String> killSet;
		private ArrayList<String> liveOut;
		private ArrayList<String> locals; // holds id of params and locals passed into function
		private Node backEdgeTarget;
		
		public Node (NodeType nodeType, AST.Statement command, String text, ArrayList<Node> predNodes, ArrayList<Node> succNodes) {
			this.id = generateNewNodeID();
			this.nodeType = nodeType;
			this.command = command;
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
		
		public Node (NodeType nodeType, AST.Statement command, String text, Node predNode, Node succNode) {
			this.id = generateNewNodeID();
			this.nodeType = nodeType;
			this.command = command;
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
		
		public Node (NodeType nodeType, AST.Statement command, String text, Node predNode) {
			this.id = generateNewNodeID();
			this.nodeType = nodeType;
			this.command = command;
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
		
		public Node (NodeType nodeType, AST.Statement command, String text) {
			this.id = generateNewNodeID();
			this.nodeType = nodeType;
			this.command = command;
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
	
	public ControlFlowGraph(AST.Program p) {
		this.p = p;
		currentIDNum = 0;
		test = false;
		listOfCFGs = new ArrayList<Node>();
	}
	
	public String generateNewNodeID() {
		return "s" + currentIDNum++;
	}
	
	public Node processWhileStatement(Node predNode, AST.Statement whSt) {
		String whText = exprToString(((AST.Statement.WhileStatement) whSt).getWhileCondition());
		if (test)
			System.out.println(whText);
		Node whNode = new Node(NodeType.WHILE, whSt, whText);
		whNode.locals = predNode.locals;
		predNode.succNodes.add(whNode);
		whNode.predNodes.add(predNode);
		for (Node n : predNode.domSet) 
			whNode.domSet.add(n);
		whNode.domSet.add(whNode);
		Node doNode = processStatement(whNode, ((AST.Statement.WhileStatement) whSt).getDoStatement());
		Node joinNode = new Node(NodeType.WHILE_JOIN, null, "while-Join");
		joinNode.locals = predNode.locals;
		joinNode.backEdgeTarget = whNode;
		doNode.succNodes.add(joinNode);
		joinNode.predNodes.add(doNode);
		for (Node n : doNode.domSet)
			joinNode.domSet.add(n);
		joinNode.domSet.add(joinNode);
		if (test)
			System.out.println("wh-Join");
		return whNode;
	}
	
	public Node processIfStatement(Node predNode, AST.Statement ifSt) {
		if (test)
			System.out.println("GOT TO ifStatement");
		String ifText = exprToString(((AST.Statement.IfStatement) ifSt).getIfCondition());
		if (test)
			System.out.println(ifText);	
		Node ifNode = new Node(NodeType.IF, ifSt, ifText);
		ifNode.locals = predNode.locals;
		predNode.succNodes.add(ifNode);
		ifNode.predNodes.add(predNode);
		for (Node n : predNode.domSet) 
			ifNode.domSet.add(n);
		ifNode.domSet.add(ifNode);
		Node thenNode = processStatement(ifNode, ((AST.Statement.IfStatement) ifSt).getThenStatement());
		Node elseNode = processStatement(ifNode, ((AST.Statement.IfStatement) ifSt).getElseStatement());
		Node joinNode = new Node(NodeType.IF_JOIN, null, "if-Join");
		joinNode.locals = predNode.locals;
		thenNode.succNodes.add(joinNode);
		elseNode.succNodes.add(joinNode);
		joinNode.predNodes.add(thenNode);
		joinNode.predNodes.add(elseNode);
		for (Node n : ifNode.domSet) 
			joinNode.domSet.add(n);
		joinNode.domSet.add(joinNode);
		return joinNode;
	}
	
	public Node processSequenceStatement(Node predNode, AST.Statement seqSt) {
		Iterator<AST.Statement> its = ((AST.Statement.SequenceStatement) seqSt).getListOfStatements().iterator();
		AST.Statement currentStatement;
		Node currentNode = predNode;
		while (its.hasNext()) {
			currentStatement = its.next();
			currentNode = processStatement(currentNode, currentStatement);
		}
		return currentNode;
	}
	
	public Node processReturnStatement(Node predNode, AST.Statement retSt) {
		if (test)
			System.out.println("GOT TO returnStatement");
		String retText = "{ return " + exprToString(((AST.Statement.ReturnStatement) retSt).getReturnExpression()) + " }";
		if (test)
			System.out.println(retText);
		Node retNode = new Node(NodeType.RETURN, retSt, retText);
		retNode.locals = predNode.locals;
		predNode.succNodes.add(retNode);
		retNode.predNodes.add(predNode);
		for (Node n : predNode.domSet) 
			retNode.domSet.add(n);
		retNode.domSet.add(retNode);
		return retNode;
	}
	
	public Node processPrintStatement(Node predNode, AST.Statement prtSt) {
		if (test)
			System.out.println("GOT TO printStatement");
		String prtText = "{ print " + exprToString(((AST.Statement.PrintStatement) prtSt).getPrintExpression()) + " }";
		if (test)
			System.out.println(prtText);
		Node prtNode = new Node(NodeType.PRINT, prtSt, prtText);
		prtNode.locals = predNode.locals;
		predNode.succNodes.add(prtNode);
		prtNode.predNodes.add(predNode);
		for (Node n : predNode.domSet) 
			prtNode.domSet.add(n);
		prtNode.domSet.add(prtNode);
		return prtNode;		
	}
	
	public Node processAssignStatement(Node predNode, AST.Statement asgnSt) {
		if (test)
			System.out.println("GOT TO assignStatement");
		String asgnText = "{ assign " + ((AST.Statement.AssignStatement) asgnSt).getID() + " " + exprToString(((AST.Statement.AssignStatement) asgnSt).getExp()) + " }";
		if (test)
			System.out.println(asgnText);
		Node asgnNode = new Node(NodeType.ASSIGN, asgnSt, asgnText);
		asgnNode.locals = predNode.locals;
		predNode.succNodes.add(asgnNode);
		asgnNode.predNodes.add(predNode);
		for (Node n : predNode.domSet) 
			asgnNode.domSet.add(n);
		asgnNode.domSet.add(asgnNode);
		return asgnNode;	
	}
	
	public Node processStatement(Node predNode, AST.Statement st) {
		if (test)
			System.out.println("GOT TO processStatement");
		Node newNode = predNode;
		if (st instanceof AST.Statement.IfStatement) {
			newNode = processIfStatement(predNode, st);
		} else if (st instanceof AST.Statement.PrintStatement) {
			newNode = processPrintStatement(predNode, st);
		} else if (st instanceof AST.Statement.SequenceStatement) {
			newNode = processSequenceStatement(predNode, st);
		} else if (st instanceof AST.Statement.AssignStatement) {
			newNode = processAssignStatement(predNode, st);
		} else if (st instanceof AST.Statement.WhileStatement) {
			newNode = processWhileStatement(predNode, st);
		} else if (st instanceof AST.Statement.ReturnStatement) {
			newNode = processReturnStatement(predNode, st);
		}
		return newNode;
	}
	
	public Node processFunction(AST.Function f) {
		if (test)
			System.out.println("GOT TO processFunction");
		AST.Statement st = f.getStatement();		
		Node head = new Node(NodeType.ENTRY, null, "Entry");
		for (String param: f.getListOfParams())
			head.locals.add(param);
		for (String local : f.getListOfLocals())
			head.locals.add(local);
		head.domSet.add(head);
		Node current = processStatement(head, st);
		Node last = new Node(NodeType.EXIT, null, "Exit");
		last.locals = head.locals;
		for (Node n : current.domSet) 
			last.domSet.add(n);
		last.domSet.add(last); // dom
		current.succNodes.add(last);
		last.predNodes.add(current);		
		return head;
	}
	
	public void processProgram() {
		Node cfg;
		for (AST.Function f : p.listOfFunctions) {
			currentIDNum = 0;
			cfg = processFunction(f);
			listOfCFGs.add(cfg);
		}
	}
	
	public ArrayList<String> removeDuplicates(ArrayList<String>list, boolean ignoreCase){
	    Set<String> set = (ignoreCase?new TreeSet<String>(String.CASE_INSENSITIVE_ORDER):new LinkedHashSet<String>());
	    set.addAll(list);

	    ArrayList<String> res = new ArrayList<String>(set);
	    return res;
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
	
	// Function takes an array containing one copy of each node in the cfg.
	public void calcLiveOut (ArrayList<Node> a) {
		boolean done = false;
		boolean change; 
		while (!done) {
			change = false;
			for (Node n : a) {
				
				// handle any backedges - will only occur in while-join nodes
				if (n.nodeType == NodeType.WHILE_JOIN) {
					for (String g : n.backEdgeTarget.genSet) {
						if(!n.liveOut.contains(g)) {
							n.liveOut.add(g);
							change = true;
						}
					}
					for (String l : n.backEdgeTarget.liveOut) {
						if(!n.liveOut.contains(l) && !n.backEdgeTarget.killSet.contains(l)) {
							n.liveOut.add(l);
							change = true;
						}
					}					
				}				
				
				// add succ.genSet to n.liveOut
				for (Node s : n.succNodes) {
					for (String g : s.genSet) {
						if(!n.liveOut.contains(g)) {
							n.liveOut.add(g);
							change = true;
						}
					}
				}
				
				// add succ.liveOut - succ.killSet to n.liveOut
				for (Node s : n.succNodes) {
					for (String l : s.liveOut) {
						if(!n.liveOut.contains(l) && !s.killSet.contains(l)) {
							n.liveOut.add(l);
							change = true;
						}
					}
				}
				

				
			}
			if (!change)
				done = true;
		}
	}
	
	public void calcGenAndKill (ArrayList<Node> a) {
		Node current;
		AST.Statement st;
		String identifier;
		for (int i = 0; i < a.size(); i++) {
			current = a.get(i);
			
			// check for assigns
			if (current.nodeType == NodeType.ASSIGN) {
				st = current.command;
				identifier = ((AST.Statement.AssignStatement) st).getID();
				for (String s : current.locals) {
					if (identifier.equals(s))
						if (!current.killSet.contains(identifier))
							current.killSet.add(((AST.Statement.AssignStatement) st).getID());
				}
			}
			
			// check for uses
			if(current.nodeType == NodeType.ASSIGN) {
				st = current.command;
				AST.Expression expr = ((AST.Statement.AssignStatement) st).getExp();
				getUses(expr, current, false);
			}
			else if (current.nodeType == NodeType.IF) {
				st = current.command;
				AST.Expression expr = ((AST.Statement.IfStatement) st).getIfCondition();
				getUses(expr, current, false);
			}
			else if (current.nodeType == NodeType.WHILE) {
				st = current.command;
				AST.Expression expr = ((AST.Statement.WhileStatement) st).getWhileCondition();
				getUses(expr, current, false);
			}
			else if (current.nodeType == NodeType.RETURN) {
				st = current.command;
				AST.Expression expr = ((AST.Statement.ReturnStatement) st).getReturnExpression();
				getUses(expr, current, false);
			}
			else if (current.nodeType == NodeType.PRINT) {
				st = current.command;
				AST.Expression expr = ((AST.Statement.PrintStatement) st).getPrintExpression();
				getUses(expr, current, false);
			}			
		}
	}
	
	// only checks expression types which could potentially hold var or call expressions
	public void getUses (AST.Expression expr, Node current, boolean isInCallExpr) {	
		
		if (expr instanceof AST.Expression.VarExpression) {
			String s = ((AST.Expression.VarExpression)expr).getID();
			boolean match = false;
			if (isInCallExpr) {
				for (String l : current.locals) {
					if (s.equals(l))
						match = true;
				}
				if (!match)
					if (!current.killSet.contains(s))
						current.killSet.add(s);
			}					
			if (!current.genSet.contains(s))
				current.genSet.add(s);
		}
		else if (expr instanceof AST.Expression.BinaryExpression) {
			AST.Expression binExpr1 = ((AST.Expression.BinaryExpression)expr).getExp1();
			AST.Expression binExpr2 = ((AST.Expression.BinaryExpression)expr).getExp2();
			getUses(binExpr1, current, isInCallExpr);
			getUses(binExpr2, current, isInCallExpr);
		}
		else if (expr instanceof AST.Expression.NotExpression) {
			AST.Expression notExpr = ((AST.Expression.NotExpression)expr).getExp();
			getUses(notExpr, current, isInCallExpr);
		}
		else if (expr instanceof AST.Expression.CallExpression) {
			ArrayList<AST.Expression> callExps = ((AST.Expression.CallExpression)expr).getExps();
			for (AST.Expression e : callExps)
				getUses(e, current, true);
		}
	}
	
	public void liveOutGraph (Node head) {
			ArrayList<Node> a = new ArrayList<Node>(); 
			a.add(head);

//			System.out.println("----New CFG----");

			for (Node s : head.succNodes)
				a = addToArray(a, s);
			calcGenAndKill(a);
			calcLiveOut(a);
			
//			// used to print gen, kill, liveout sets to console
//			for (Node n : a) {
//				System.out.print(n.id + "\t");
//				System.out.print("genSet { ");
//				for (String s : n.genSet)
//					System.out.print(s + " ");
//				System.out.print("}\t");
//				System.out.print("killSet { ");
//				for (String s : n.killSet)
//					System.out.print(s + " ");
//				System.out.print("}\t");
//				System.out.print("LiveOut { ");
//				for (String s : n.liveOut)
//					System.out.print(s + " ");
//				System.out.println("}");
//			}
//			System.out.println("Number of nodes: " + a.size());

	}
	
	public ArrayList<Node> addToArray(ArrayList<Node> arr, Node n) {
		if (!arr.contains(n)) {
			arr.add(n);
			for (Node s : n.succNodes) {
				arr = addToArray(arr, s);
			}
		}
		else
			return arr;
		return arr;
	}
	
	public void printCFGtoDotFile() throws IOException {
		FileWriter f;
		String functionName, fileName;
		int count = 0;
		AST.Function func;
		ArrayList<String> edges; 
		String edge;
		
		for (Node n : listOfCFGs) {
			liveOutGraph(n);			
			edges = new ArrayList<String>();
			func = p.listOfFunctions.get(count++);
			functionName = func.getName();
			fileName = "cfg_" + functionName + ".dot";
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
				+ "\\n" + domSetToString(n) + "\\n"
				+ n.text + "\\n"
				+ liveOutSetToString(n)
				+ "\", shape = " + shape + "];\n";
	}
	
	public String domSetToString(Node node) {
		boolean first = true;
		String d = "DOM={";
		for (Node n : node.domSet) {
			if (!first)
				d = d + (",");
			d = d + (n.id.replace('s', ' ').trim());
			first = false;
		}
		d = d + "}";
		return d;
	}
	
	public String liveOutSetToString(Node node) {
		boolean first = true;
		String d = "LiveOut={";
		for (String l : node.liveOut) {
			if (!first)
				d = d + (",");
			d = d + l;
			first = false;
		}
		d = d + "}";
		return d;
	}
	
	public String exprToString(AST.Expression exp) {
		
		if (exp instanceof AST.Expression.NumExpression) {
			return "( num "
					+ (((AST.Expression.NumExpression) exp).getNumber()) + " )";
		} else if (exp instanceof AST.Expression.VarExpression) {
			return "( var " + (((AST.Expression.VarExpression) exp).getID())
					+ " )";
		} else if (exp instanceof AST.Expression.NotExpression) {
			return "( ! " + exprToString(((AST.Expression.NotExpression) exp).getExp()) + " )";
		} else if (exp instanceof AST.Expression.TrueExpression) {
			return "( bool " + (((AST.Expression.TrueExpression) exp).getValue()) + " )";
		} else if (exp instanceof AST.Expression.FalseExpression) {
			return "( bool " + (((AST.Expression.FalseExpression) exp).getValue()) + " )";
		} else if (exp instanceof AST.Expression.BinaryExpression) {
			return "( "	+ (((AST.Expression.BinaryExpression) exp).getOp().getLabel()) + " " +
			exprToString((((AST.Expression.BinaryExpression) exp).getExp1())) +
			exprToString((((AST.Expression.BinaryExpression) exp).getExp2())) + " )";
		} else if (exp instanceof AST.Expression.CallExpression) {
			Iterator<AST.Expression> ite = ((AST.Expression.CallExpression) exp).getExps()
					.iterator();
			String text;
			AST.Expression current;
			text = "( call " + (((AST.Expression.CallExpression) exp).getID()) + " ";
			while (ite.hasNext()) {
				current = ite.next();
				text = "" + text + exprToString(current);
			}
			text = text + " )";
			return text;
		}
		return "ERROR in exprToString function! The type of expression is unrecognized.";
	}

}
