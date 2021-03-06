import asm.*;

import java.util.Collections;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import iloc.Instruction;
import asm.Instruction_a;

public class Node {
	private int id;
	private NodeType nodeType;
	private String text;
	private String functionName;
	private ArrayList<Node> predNodes;
	private ArrayList<Node> succNodes;
	private ArrayList<Instruction> instructions;
	private ArrayList<Instruction_a> asm_instructions;
	//private ArrayList<Node> domSet;
	private ArrayList<String> genSet;
	private ArrayList<String> killSet;
	private ArrayList<String> liveOut;
	private ArrayList<String> locals; // holds id of params and locals passed into function
	private HashMap<String, String> registerMap;
	private Node backEdgeTarget;
    private boolean isMainHead;
	private boolean iloc_printed;
	private boolean asm_printed;
	private boolean asm_printed_1;
	private boolean asm_processed;
	
	public Node (NodeType nodeType, int id, String text) {
		this.id = id;
		this.nodeType = nodeType;
		this.text = text;
		this.predNodes = new ArrayList<Node>();
		this.succNodes = new ArrayList<Node>();
		this.instructions = new ArrayList<Instruction>();
		this.asm_instructions = new ArrayList<Instruction_a>();
		this.genSet = new ArrayList<String>();
		this.killSet = new ArrayList<String>();
		this.liveOut = new ArrayList<String>();
		this.locals = new ArrayList<String>();
		this.registerMap = new HashMap<String, String>();
		functionName = "";
		backEdgeTarget = null;
		iloc_printed = false;
		asm_printed = false;
		asm_printed_1 = false;
		asm_processed = false;
        isMainHead = false;
	}
	
	public boolean getAsmProcessed() {
	   return asm_processed;
	}
	
	public void setAsmProcessed(boolean b) {
	   asm_processed = b;
	}
	
	public boolean getAsmPrinted1() {
	   return asm_printed_1;
	}
	
	public void setAsmPrinted1(boolean b) {
	   asm_printed_1 = b;
	}
	
	public String getFunctionName() {
	   return this.functionName;
	}
	
	public void setFunctionName(String functionName) {
	   this.functionName = functionName;
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
	
   public ArrayList<Instruction_a> getAsmInstructions() {
		return asm_instructions;
	}
	
	public void setInstructions(ArrayList<Instruction> instructions) {
		this.instructions = instructions;
	}
	
	public void setAsmInstructions(ArrayList<Instruction_a> asm_instructions) {
		this.asm_instructions = asm_instructions;
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
	
	public HashMap<String, String> getRegisterMap() {
	   return this.registerMap;
	}
	
	public void setRegisterMap(HashMap<String, String> registerMap) {
	   this.registerMap = registerMap;
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
	   String body = "\\n";
	   for (Instruction i : n.getInstructions()) {
	      body = body + i.toString() + "\\l";     
	   }
	   shape = "box"; // set all shapes to box
		return n.id + " [label = \"" + "L" + n.id + " "//n.id.replace('s', ' ').trim() 
				//+ "\\n" + domSetToString(n) + "\\n"
				//+ n.text 
				+ body//+ "\\n" + n.instructions.get(0).toString()
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
			   System.out.println("Warning: control statement has more than two children! Type = " + n.getNodeType());			   
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
			  // if (n.getNodeType() == NodeType.WHILE_BODY) {
			   if (n.getBackEdgeTarget() != null) {
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

    public void printCFGtoFile(String funcName) throws IOException {
        FileWriter f;
		String fileName;

		fileName = "il_" + funcName + ".il";
		f = new FileWriter(new File(fileName));
      //System.out.println(fileName);
      System.out.println();
      //String line = "L" + this.id + ":\n";
      String line = this.getFunctionName() + ":\n";
		f.write(line);
      System.out.print(line);
      ArrayList<Instruction> instructions = this.getInstructions();
      for(Instruction inst : instructions){
         line = "\t" + inst.toString() + "\n";
         f.write(line);
         System.out.print(line);
      }
      this.iloc_printed = true;
      nodeToString(this, f);
		f.close();
    }

    public void nodeToString(Node n, FileWriter f) throws IOException {
      ArrayList<Instruction> instructions;
      for (Node s : n.succNodes) {
         if (s.iloc_printed == false) {
            String line = "L" + s.id + ":\n";
	      	f.write(line);
            System.out.print(line);
            instructions = s.getInstructions();
            for(Instruction inst : instructions){
               line = "\t" + inst.toString() + "\n";
               f.write(line);
               System.out.print(line);
            }
            s.iloc_printed = true;
            nodeToString(s, f);            
         }
		}		   
      return;
    }
    
   
   public FileWriter printAsm(String front, FileWriter f, String stringConstants, boolean isMain, ArrayList<String> callee) throws IOException {
      System.out.println();
      //String line = "L" + this.id + ":\n";
      // Add .text etc here
      f.write(stringConstants);
      System.out.print(stringConstants);
      f.write(front); //.globl etc
      System.out.print(front); 
      String line = this.getFunctionName() + ":\n";
		f.write(line);
      System.out.print(line);
      ArrayList<Instruction_a> asm_instructions = this.getAsmInstructions();
      for(Instruction_a ainst : asm_instructions){
          line = "";
          if(ainst instanceof Leave){
              //System.out.println("FOUND A Leave");
              if(!isMain){
              line += addCalleePops(callee);
              }
          }
         line += "\t" + ainst.toString() + "\n";
         f.write(line);
         System.out.print(line);
      }
      //printGenKillSet(this, f); //comment out
      //printLiveOutSet(this, f); //comment out
      this.asm_printed = true;
      f = asmSucc(this, f, isMain, callee);
      return f;
    }
    
    public FileWriter asmSucc(Node n, FileWriter f, boolean isMain, ArrayList<String> callee) throws IOException {
      ArrayList<Instruction_a> asm_instructions;
      for (Node s : n.succNodes) {
      if (s.asm_printed == false) {
            String line = "L" + s.id + ":\n";
	      	f.write(line);

            System.out.print(line);
            asm_instructions = s.getAsmInstructions();
            for(Instruction_a ainst : asm_instructions){
                line = "";
                if(ainst instanceof Leave){
                    //System.out.println("FOUND A Leave");
                    if(!isMain){
                        line += addCalleePops(callee);
                    }
                }
               line += "\t" + ainst.toString() + "\n";
               f.write(line);
               System.out.print(line);
            }
            //printGenKillSet(s, f); //comment out
            //printLiveOutSet(s, f); //comment out
            s.asm_printed = true;
            asmSucc(s, f, isMain, callee);
         }
		}    
      return f;
    }
    
    public void printGenKillSet(Node s, FileWriter f) throws IOException{
        String finalprint = "";
        finalprint+="gen{";        
        for(String gen: s.getGenSet()){        
            finalprint+= gen + ", ";
        }
        finalprint+= "}\nkill{";
        for(String kill: s.getKillSet()){
            finalprint += kill + ", ";
        }
        finalprint += "}\n";
        System.out.print(finalprint);
        f.write(finalprint);
    }

    public void printLiveOutSet(Node s, FileWriter f) throws IOException{
        String finalprint = "";
        finalprint+="LiveOut{";        
        for(String l: s.getLiveOut()){        
            finalprint+= l + ", ";
        }
        finalprint += "}\n";
        System.out.print(finalprint);
        f.write(finalprint);
    }

    /*public boolean calcLiveOut(){
        boolean changed = false;
        for(Node s: succNodes){
            changed = calcLiveOut();
        }
        for(Node s: succNodes){
            for(String g : s.getGenSet()){
                if(!liveOut.contains(g)){
                    liveOut.add(g);
                    changed = true;
                }
            }
        }
        for (Node s: succNodes){
            for(String l : liveOut){
                if(!liveOut.contains(l) && !s.killSet.contains(l)){
                    liveOut.add(l);
                    changed = true;
                }
            }
        }
        return changed;
    }*/
    public boolean getIsMainHead(){
        return this.isMainHead;
    }
    
    public void setIsMainHead(boolean isMainHead){
        this.isMainHead = isMainHead;
    }
    
    public String addCalleePops(ArrayList<String> callee){
        String line = "";
        ArrayList<String> reverse = new ArrayList<String>(callee);
        Collections.reverse(reverse);
        for(String s: reverse){
            line += "\t" + (new Popq(s)).toString() + "\n";
        }
        return line;
    }
    
     public FileWriter printAsmFirst(FileWriter f, boolean isMain) throws IOException {
      System.out.println();

      String line = this.getFunctionName() + ":\n";
		//f.write(line);
      System.out.print(line);
      ArrayList<Instruction_a> asm_instructions = this.getAsmInstructions();
      for(Instruction_a ainst : asm_instructions){
          line = "";
          if(ainst instanceof Leave){
              //System.out.println("FOUND A Leave");
              if(!isMain){
              }
          }
         line += "\t" + ainst.toString() + "\n";
         //f.write(line);
         System.out.print(line);
      }
      printGenKillSet(this, f); //comment out
      printLiveOutSet(this, f); //comment out
      this.asm_printed_1 = true;
      f = asmSuccFirst(this, f, isMain);
      return f;
    }
    
    public FileWriter asmSuccFirst(Node n, FileWriter f, boolean isMain) throws IOException {
      ArrayList<Instruction_a> asm_instructions;
      for (Node s : n.succNodes) {
      if (s.asm_printed_1 == false) {
            String line = "\nL" + s.id + ":\n";
	      	//f.write(line);

            System.out.print(line);
            asm_instructions = s.getAsmInstructions();
            for(Instruction_a ainst : asm_instructions){
                line = "";
                if(ainst instanceof Leave){
                    //System.out.println("FOUND A Leave");
                    if(!isMain){
                       
                    }
                }
               line += "\t" + ainst.toString() + "\n";
               //f.write(line);
               System.out.print(line);
            }
            printGenKillSet(s, f); //comment out
            printLiveOutSet(s, f); //comment out
            s.asm_printed_1 = true;
            asmSuccFirst(s, f, isMain);
         }
		}    
      return f;
    }
}
