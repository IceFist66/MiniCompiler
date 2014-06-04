import iloc.*;
import asm.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

public class Assembly_Factory {

        //String[] temp = new String[]{"rdi","rsi","rdx","rcx","r8","r9"};
        private ArrayList<String> arguments;
        private String fname;
        private String stringConstants;
        private ArrayList<String> stringDirectives;
        private int stringCounter;
        private ArrayList<Node> input;
        private ArrayList<ArrayList<Node>> allNodes;
        private ArrayList<IGraph> iGraphs;
        //String rdi = "rdi";
        //arguments.add(rdi);
        //Collections.addAll(arguments, temp);

	
	
	public Assembly_Factory(ArrayList<Node> input, String fname, ArrayList<String> stringDirectives){
		this.input = input;
		this.fname = fname;
		this.stringDirectives = stringDirectives;
		stringCounter = 0;
        this.allNodes = new ArrayList<ArrayList<Node>>();
      this.arguments = new ArrayList<String>(Arrays.asList("rdi","rsi","rdx","rcx","r8","r9"));
        this.iGraphs = new ArrayList<IGraph>();
	}

	public ArrayList<Node> getInput() {
		return input;
	}

	public void setInput(ArrayList<Node> input) {
		this.input = input;
	}
	
	public void convert() throws IOException {

		ArrayList<Instruction_a> asm;
		for(Node n: input){
			ArrayList<Instruction> instructions = n.getInstructions();
                        //String prefront = "\t.text\n";// Add .text etc here
                        //String front = ".globl " + n.getFunctionName() + "\n\t.type\t" + n.getFunctionName() + ", @function\n";
                        n.getAsmInstructions().addAll(getStart());
			for(Instruction inst : instructions){
				asm = getAssembly(inst);
				n.getAsmInstructions().addAll(asm);
			}
            createKillGenSet(n);
			n.setAsmProcessed(true);
			successors(n);
         //n.getAsmInstructions().addAll(getEnd()); //This is the call to .cfi_endproc
         //n.printAsm(prefront+front); //prints the asm instructions to screen
	   }
        createListAll();
        calcLiveOutAll();
	  printAsmAll(fname, input, stringDirectives);
        generateIGraphs();
        printIGraphs();
	}

    public void successors(Node n) {
      ArrayList<Instruction> instructions;
      ArrayList<Instruction_a> asm;
      for (Node s : n.getSuccNodes()) {
            if (s.getAsmProcessed() == false) {
                instructions = s.getInstructions();
                for(Instruction inst : instructions){
        	    asm = getAssembly(inst);
        	    s.getAsmInstructions().addAll(asm);
                }
                createKillGenSet(s);
                s.setAsmProcessed(true);
                successors(s);
            }
      }    
      return;
    }
    
    public ArrayList<Instruction_a> getAssembly(Instruction i){
    	ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        String arg1 = i.getArg1(), arg2 = i.getArg2(), arg3 = i.getArg3();
    	if(i instanceof Mov){
    		list = getMovq(arg1, arg2);
    	}
        else if(i instanceof Add){
            list = getAddq(arg1, arg2, arg3);
        }
        else if(i instanceof Addi){
            list = getAddq(arg1, "$"+arg2, arg3); //$ needed for numbers
        }
        else if(i instanceof Jumpi){
            list = getJmp(arg1);
        }
        else if(i instanceof Print || i instanceof Println){
            list = getPrint(arg1);
        }
        else if(i instanceof Loadi){
            list = getMovq("$"+arg1, arg2);
        }
        else if(i instanceof Storeret){
            list = getStoreReturn(arg1);
        }
        else if(i instanceof iloc.Ret){
            list = getReturn();
        }
        else if(i instanceof Sub){
            list = getSubq(arg1, arg2, arg3);
        }
        /*else if(i instanceof Div){
            list = getDiv(arg1, arg2, arg3);
        }*///whatever else
    	else{
            list = getMovq("----", i.toString());
    	}
    	return list;
    }

    public ArrayList<Instruction_a> getMovq(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Movq(arg1, arg2)); //r1 -> r2
        return list;
    }

    public ArrayList<Instruction_a> getAddq(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.addAll(getMovq(arg2, arg3)); //r3 = r2
        list.add(new Addq(arg1, arg3)); //r3 += r1
        return list;
    }

    public ArrayList<Instruction_a> getJmp(String arg1){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Jmp(arg1)); //jmp label
        return list;
    }
    
    public ArrayList<Instruction_a> getPrint(String arg1){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Movq(".LC" + stringCounter++, "%rdi"));
        list.add(new Movq(arg1, "%rsi"));
        list.add(new Movq("$0", "%rax"));
        list.add(new asm.Call("printf"));
        return list;
    }

    public ArrayList<Instruction_a> getStoreReturn(String arg1){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.addAll(getMovq(arg1, "%rax")); //move value to return register
        return list;
    }

    public ArrayList<Instruction_a> getReturn(){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.addAll(getMovq("%rbp", "%rsp"));
        list.add(new Popq("%rbp"));
        list.add(new Leave()); //leave
        list.add(new asm.Ret()); //just calls "ret"
        list.add(new Cfi("endproc"));
        return list;
    }

    public ArrayList<Instruction_a> getStart(){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Cfi("startproc"));
        list.add(new Pushq("%rbp"));
        list.addAll(getMovq("%rsp", "%rbp"));
        return list;
    }
    
    /*public ArrayList<Instruction_a> getEnd(){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Cfi("endproc"));
        return list;
    }*/

    public ArrayList<Instruction_a> getSubq(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.addAll(getMovq(arg2, arg3)); //r3 = r2
        list.add(new Subq(arg1, arg3)); //r3 -= r1
        return list;
    }
    
    /*public ArrayList<Instruction_a> getDiv(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.addAll(getMovq(arg2, arg3)); //r3 = r2
        list.add(new Idivq(arg1, arg3)); //r3 -= r1
        return list;
    }*/
    
    public void printAsmAll(String fn, ArrayList<Node> funcs, ArrayList<String> stringDirectives) throws IOException {
      FileWriter f;
      int stringDirSize;
		String fileName = fn.substring(0, fn.length() - 4);
		fileName = "asm_" + fileName + "s";
		f = new FileWriter(new File(fileName));
		stringCounter = 0;
        int closing_counter = 0;
		for (Node n : funcs) {
		   String prefront = "\t.text\n";// Add .text etc here
         String front = ".globl " + n.getFunctionName() + "\n\t.type\t" + n.getFunctionName() + ", @function\n";
		   front = prefront + front;
		   stringDirSize = stringDirectives.size();
		   if (stringDirSize > 0 && stringCounter <= (stringDirSize - 1))
		      f = n.printAsm(front, f, stringDirectives.get(stringCounter++));
		   else
		      f = n.printAsm(front, f, "");
            
            String closing = ".LFE" + (closing_counter++) + ":\n\t.size\t" + n.getFunctionName() + ", .-" + n.getFunctionName() + "\n";
            f.write(closing);
            System.out.print(closing);
		}
		f.close();
   }

   public void createKillGenSet(Node s){
        for(Instruction_a i_asm : s.getAsmInstructions()){
            ArrayList<String> kill = s.getKillSet();
            ArrayList<String> gen = s.getGenSet();
            String target = i_asm.getTarget();
            ArrayList<String> sources = i_asm.getSources();
            for(String source : sources){
                if(source.charAt(0) == 'r' || source.charAt(0) == '%'){
                    if(!kill.contains(source) && !gen.contains(source)){
                        gen.add(source);
                    }
                }
            }
            if(target != null 
                && (target.charAt(0) == 'r' || target.charAt(0) == '%') 
                && !kill.contains(target)){
                kill.add(target);
            }
        }
   }

   public void calcLiveOutAll(){
        for(ArrayList<Node> nodes: allNodes){
            calcLiveOut(nodes);
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
				if (n.getBackEdgeTarget() != null) {
					for (String g : n.getBackEdgeTarget().getGenSet()) {
						if(!n.getLiveOut().contains(g)) {
							n.getLiveOut().add(g);
							change = true;
						}
					}
					for (String l : n.getBackEdgeTarget().getLiveOut()) {
						if(!n.getLiveOut().contains(l) && !n.getBackEdgeTarget().getKillSet().contains(l)) {
							n.getLiveOut().add(l);
							change = true;
						}
					}					
				}				
				
				// add succ.genSet to n.liveOut
				for (Node s : n.getSuccNodes()) {
					for (String g : s.getGenSet()) {
						if(!n.getLiveOut().contains(g)) {
							n.getLiveOut().add(g);
							change = true;
						}
					}
				}
				
				// add succ.liveOut - succ.killSet to n.liveOut
				for (Node s : n.getSuccNodes()) {
					for (String l : s.getLiveOut()) {
						if(!n.getLiveOut().contains(l) && !s.getKillSet().contains(l)) {
							n.getLiveOut().add(l);
							change = true;
						}
					}
				}
				

				
			}
			if (!change)
				done = true;
		}
	}

    public void createListAll(){
        for(Node n: input){
            ArrayList<Node> nodes = new ArrayList<Node>();
            allNodes.add(createList(nodes, n));
        }
    }

    public ArrayList<Node> createList(ArrayList<Node> nodes, Node n){ 
        nodes.add(n);
        System.out.println("Node added: L" + n.getId());    
        for(Node s: n.getSuccNodes()){
            if(!nodes.contains(s)){
                nodes.addAll(createList(nodes, s));
            }
            else{
                System.out.println("Did not Add: L" + s.getId());
            }
        }
        return nodes;
    }

    public void generateIGraphs(){
        System.out.println("Before the generate loop" + allNodes.size());
        for(ArrayList<Node> nodes : allNodes){
            System.out.println("In Generate Loop");
            iGraphs.add(new IGraph(nodes));
        }
    }

    public void printIGraphs(){
        System.out.println("Before the loop " + iGraphs.size());
        for(IGraph graph : iGraphs){
            System.out.println("In the loop");
            graph.printBubbles();
        }
    }

    
}
