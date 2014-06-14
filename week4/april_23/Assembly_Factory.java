import iloc.*;
import asm.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Set;
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
        
        private int maxParam;
        private ArrayList<Node> input;
        private ArrayList<ArrayList<Node>> allNodes;
        private ArrayList<ArrayList<String>> allCalleeRegisters;
        private ArrayList<IGraph> iGraphs;
        private ArrayList<String> caller;
        private ArrayList<String> globals;
        private boolean store;
    
    private final int offset = 8;
    private final int default_stack = 48;
    private final int max_arguments = 6;
    private final int argument_location = 16;
    private final int subq_location = 3;
        //String rdi = "rdi";
        //arguments.add(rdi);
        //Collections.addAll(arguments, temp);

	
	
	public Assembly_Factory(ArrayList<Node> input, String fname, ArrayList<String> stringDirectives, ArrayList<String> globals, int maxParam){
        this.input = input;
        this.fname = fname;
        this.stringDirectives = stringDirectives;
        this.globals = globals;
        stringCounter = 0;
        this.allNodes = new ArrayList<ArrayList<Node>>();
        this.allCalleeRegisters = new ArrayList<ArrayList<String>>();
        this.arguments = new ArrayList<String>(Arrays.asList("%rdi","%rsi","%rdx","%rcx","%r8","%r9"));
        this.caller = new ArrayList<String>(Arrays.asList("%rax", "%rcx", "%rdi", "%rsi", "%r8", "%r9", "%r10", "%r11"));
        this.iGraphs = new ArrayList<IGraph>();
        this.store = false;
        this.maxParam = maxParam;
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
                        //n.getAsmInstructions().addAll(getStart()); //Static getStart
            n.getAsmInstructions().add(new Cfi("startproc"));
            n.getAsmInstructions().add(new Pushq("%rbp"));
            n.getAsmInstructions().addAll(getMovq("%rsp", "%rbp"));
            n.getAsmInstructions().add(new Subq("$"+this.default_stack, "%rsp"));
			for(Instruction inst : instructions){
				asm = getAssembly(inst);
				n.getAsmInstructions().addAll(asm);
			}
            createKillGenSet(n);
			n.setAsmProcessed(true);
			successors(n);
			stringCounter++;
         //n.getAsmInstructions().addAll(getEnd()); //This is the call to .cfi_endproc
         //n.printAsm(prefront+front); //prints the asm instructions to screen
	   }
        createListAll();
        calcLiveOutAll();
        //printAsmAllFirst(fname, input); //comment out
        generateIGraphs();
        printIGraphs();
        colorIGraphs();
        printIGraphColorings();
        applyColor();
        int association = 0;
        for(Node n : input){//settting up the callee saved registers
            IGraph graph = iGraphs.get(association); //grab associated Igraph
            getCalleeRegisters(n, graph);
            association++;
        }
        int i = 0;
        for(Node n: input){ //inserting getStartproperly
            IGraph graph = iGraphs.get(i);
            ArrayList<Instruction_a> insts = getStart(graph.getSpillSpace()); //Dynamic getStart
            if(!n.getIsMainHead()){
                for(String call : allCalleeRegisters.get(i)){
                    insts.add(new Pushq(call));
                }
            }
            for(int j = insts.size() - 1; j >= 0; j--){
                n.getAsmInstructions().set(this.subq_location, insts.get(j));
            }
            i++;
        }
        printAsmAll(fname, input, stringDirectives);
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
        else if(i instanceof Print){
            list = getPrint(arg1);
        }
        else if(i instanceof Println){
            list = getPrintln(arg1);
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
        else if(i instanceof Mult){
            list = getMult(arg1, arg2, arg3);
        }
        else if(i instanceof Div){
            list = getDiv(arg1, arg2, arg3);
        }
        else if(i instanceof And){
            list = getAndq(arg1, arg2, arg3);
        }
        else if(i instanceof Or){
            list = getOrq(arg1, arg2, arg3);
        }
        else if(i instanceof Comp){
            list = getCmpq(arg1, arg2);
        }
        else if(i instanceof Cbreq){
            list = getJe(arg2, arg3);
        }
        else if(i instanceof Cbrge){
            list = getJge(arg2, arg3);
        }
        else if(i instanceof Cbrgt){
            list = getJg(arg2, arg3);
        }
        else if(i instanceof Cbrle){
            list = getJle(arg2, arg3);
        }
        else if(i instanceof Cbrlt){
            list = getJl(arg2, arg3);
        }
        else if(i instanceof Cbrne){
            list = getJne(arg2, arg3);
        }
        else if(i instanceof Jumpi){
            list = getJmp(arg2, arg3);
        }
        else if(i instanceof Moveq){
            list = getCmoveq(arg1, arg2);
        }
        else if(i instanceof Movge){
            list = getCmovgeq(arg1, arg2);
        }
        else if(i instanceof Movgt){
            list = getCmovgq(arg1, arg2);
        }
        else if(i instanceof Movle){
            list = getCmovleq(arg1, arg2);
        }
        else if(i instanceof Movlt){
            list = getCmovlq(arg1, arg2);
        }
        else if(i instanceof Movne){
            list = getCmovneq(arg1, arg2);
        }
        else if(i instanceof Loadai){
            list = getLoadai(arg1, arg2, arg3);
        }
        else if(i instanceof Loadret){
            list = getLoadRet(arg1);
        }
        else if(i instanceof Storeai){
            list = getStoreai(arg1, arg2, arg3);
        }
        else if(i instanceof Storeret){
            list = getStoreRet(arg1);
        }
        else if(i instanceof iloc.Call){
            list = getCall(arg1, arg2);
        }
        else if(i instanceof Storeoutargument){
            list = getStoreOutArgument(arg1, arg2);
        }
        else if(i instanceof New){
            list = getNew(arg1, arg2, arg3);
        }
        else if(i instanceof Loadinargument){
            list = getLoadInArgument(arg1, arg2, arg3);
        }
        else if(i instanceof Del){
            list = getDel(arg1);
        }
        else if(i instanceof Read){
            list = getRead(arg1);
        }
        else if(i instanceof Brz){
            list = getBrz(arg1, arg2, arg3);
        }//whatever else
    	else{
            list = getMovq("----", "i_" + i.toString());
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
        list.addAll(getMovq(arg1, arg3)); //r3 = r1
        list.add(new Addq(arg2, arg3)); //r3 += r2
        return list;
    }

    public ArrayList<Instruction_a> getJmp(String arg1){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Jmp(arg1)); //jmp label
        return list;
    }
    
    public ArrayList<Instruction_a> getPrint(String arg1){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        for(int i = 0; i < this.caller.size(); i++){
            list.add(new Pushq(caller.get(i)));
        }
        //list.add(new Pushq("%rdi"));
        //list.add(new Pushq("%rsi"));
        //list.add(new Pushq("%rax"));
        list.add(new Movq("$.LC" + stringCounter, "%rdi"));
        list.add(new Movq(arg1, "%rsi"));
        list.add(new Movq("$0", "%rax"));
        list.add(new asm.Call("printf"));
        //list.add(new Popq("%rax"));
        //list.add(new Popq("%rsi"));
        //list.add(new Popq("%rdi"));
        for(int i = this.caller.size() - 1; i >= 0; i--){
            list.add(new Popq(caller.get(i)));
        }
        return list;
    }
    
    public ArrayList<Instruction_a> getPrintln(String arg1){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        for(int i = 0; i < this.caller.size(); i++){
            list.add(new Pushq(caller.get(i)));
        }
        //list.add(new Pushq("%rdi"));
        //list.add(new Pushq("%rsi"));
        //list.add(new Pushq("%rax"));
        list.add(new Movq("$.LL" + stringCounter, "%rdi"));
        list.add(new Movq(arg1, "%rsi"));
        list.add(new Movq("$0", "%rax"));
        list.add(new asm.Call("printf"));
        //list.add(new Popq("%rax"));
        //list.add(new Popq("%rsi"));
        //list.add(new Popq("%rdi"));
        for(int i = this.caller.size() - 1; i >= 0; i--){
            list.add(new Popq(caller.get(i)));
        }
        return list;
    }

    public ArrayList<Instruction_a> getAndq(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.addAll(getMovq(arg1, arg3)); //r3 = r1
        list.add(new Andq(arg2, arg3)); //r3 &&= r2
        return list;
    }

    public ArrayList<Instruction_a> getOrq(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.addAll(getMovq(arg1, arg3)); //r3 = r1
        list.add(new Orq(arg2, arg3)); //r3 ||= r2
        return list;
    }

    public ArrayList<Instruction_a> getStoreReturn(String arg1){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.addAll(getMovq(arg1, "%rax")); //move value to return register
        return list;
    }

    public ArrayList<Instruction_a> getReturn(){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Addq("$"+this.default_stack, "%rsp")); //*********MAKE SURE TO DYNAMICLY CHANGE THIS LATER
        list.addAll(getMovq("%rbp", "%rsp"));
        list.add(new Popq("%rbp"));
        //list.add(new Leave()); //leave
        list.add(new asm.Ret()); //just calls "ret"
        list.add(new Cfi("endproc"));
        return list;
    }

    public ArrayList<Instruction_a> getStart(int spillSpace){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        /*list.add(new Cfi("startproc"));
        list.add(new Pushq("%rbp"));
        list.addAll(getMovq("%rsp", "%rbp"));*/
        System.out.println("Max Param: " + maxParam + " SpillSpace: " + spillSpace);
        if(maxParam<this.max_arguments){
            list.add(new Subq("$"+(this.default_stack+((spillSpace)*this.offset)), "%rsp"));        }
        else{
            list.add(new Subq("$"+(this.default_stack+((spillSpace+(maxParam-this.max_arguments))*this.offset)), "%rsp"));
        }
        return list;
    }
    
    /*public ArrayList<Instruction_a> getEnd(){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Cfi("endproc"));
        return list;
    }*/

    public ArrayList<Instruction_a> getSubq(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.addAll(getMovq(arg1, arg3)); //r3 = r1
        list.add(new Subq(arg2, arg3)); //r3 -= r2
        return list;
    }

    public ArrayList<Instruction_a> getMult(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        if(arg3 != null){
            list.add(new Movq(arg1, arg3));
            list.add(new Imulq(arg2, arg3)); //r3 *= r2
        }
        else{
            list.add(new Imulq(arg1, arg2)); //r3 *= r1 * r2
        }
        return list;
    }
    
    public ArrayList<Instruction_a> getDiv(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Pushq("%rdx")); //push %rdx
        list.add(new Pushq("%rax")); //push %rax
        list.addAll(getMovq(arg1, "%rax")); //r1 -> rax
        list.add(new Cqto()); //cqto- sign extend rax to rdx:rax
        list.add(new Idivq(arg2)); //rax /= r2
        list.addAll(getMovq("%rax", arg3)); //rax -> r3
        list.add(new Popq("%rax")); //pop rax
        list.add(new Popq("%rdx")); //pop rdx
        return list;
    }

    public ArrayList<Instruction_a> getCmpq(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Cmpq(arg1, arg2)); //original code
        //list.add(new Cmpq(arg2, arg1));
        return list;
    }

    public ArrayList<Instruction_a> getJe(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Je(arg1));
        list.add(new Jmp(arg2));
        return list;
    }

    public ArrayList<Instruction_a> getJge(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Jge(arg1));
        list.add(new Jmp(arg2));
        return list;
    }

    public ArrayList<Instruction_a> getJg(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Jg(arg1));
        list.add(new Jmp(arg2));
        return list;
    }

    public ArrayList<Instruction_a> getJle(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Jle(arg1));
        list.add(new Jmp(arg2));
        return list;
    }

    public ArrayList<Instruction_a> getJl(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Jl(arg1));
        list.add(new Jmp(arg2));
        return list;
    }

    public ArrayList<Instruction_a> getJne(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Jne(arg1));
        list.add(new Jmp(arg2));
        return list;
    }

    public ArrayList<Instruction_a> getJmp(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Jmp(arg2));
        return list;
    }

    public ArrayList<Instruction_a> getCmoveq(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Pushq("%r15"));
        list.add(new Movq("$1", "%r15"));
        list.add(new Cmoveq("%r15", arg2));
        list.add(new Popq("%r15"));
        return list;
    }

    public ArrayList<Instruction_a> getCmovgq(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Pushq("%r15"));
        list.add(new Movq("$1", "%r15"));
        list.add(new Cmovlq("%r15", arg2));
        list.add(new Popq("%r15"));
        return list;
    }

    public ArrayList<Instruction_a> getCmovgeq(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Pushq("%r15"));
        list.add(new Movq("$1", "%r15"));
        list.add(new Cmovleq("%r15", arg2));
        list.add(new Popq("%r15"));
        return list;
    }

    public ArrayList<Instruction_a> getCmovlq(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Pushq("%r15"));
        list.add(new Movq("$1", "%r15"));
        list.add(new Cmovgq("%r15", arg2));
        list.add(new Popq("%r15"));
        return list;
    }

    public ArrayList<Instruction_a> getCmovleq(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Pushq("%r15"));
        list.add(new Movq("$1", "%r15"));
        list.add(new Cmovgeq("%r15", arg2));
        list.add(new Popq("%r15"));
        return list;
    }

    public ArrayList<Instruction_a> getCmovneq(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Pushq("%r15"));
        list.add(new Movq("$1", "%r15"));
        list.add(new Cmovneq("%r15", arg2));
        list.add(new Popq("%r15"));
        return list;
    }
    
    public ArrayList<Instruction_a> getBrz(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Pushq("%r15"));
        list.add(new Movq("$1", "%r15"));
        list.add(new Cmpq("%r15", arg1)); //original code
        //list.add(new Cmpq(arg1, "%r15"));
        list.add(new Popq("%r15"));
        //list.addAll(getJne(arg2, arg3)); //original code NOT SURE due to Jeff
        list.addAll(getJe(arg2, arg3));
        return list;
    }

    public ArrayList<Instruction_a> getLoadai(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        //int offset = 8; //64 bits
        //offset *= Integer.parseInt(arg2); //create an offset from arg2
        list.addAll(getMovq((this.offset+"("+arg1+")"), arg3)); //movq offset(arg1), arg3
        return list;
    }

    public ArrayList<Instruction_a> getLoadRet(String arg1){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.addAll(getMovq("%rax", arg1)); //rax => r1
        //pop all the callers
        for(int i = this.caller.size() - 1; i >= 0; i--){
            list.add(new Popq(caller.get(i)));
        }
        this.store = false;
        return list;
    }

    public ArrayList<Instruction_a> getStoreai(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        //int offset = 8; //64 bits
        //offset *= Integer.parseInt(arg3); //create an offset from arg2
        list.addAll(getMovq(arg1, this.offset+"("+arg2+")")); //movq offset(arg1), arg3
        return list;
    }

    public ArrayList<Instruction_a> getStoreRet(String arg1){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.addAll(getMovq(arg1, "%rax")); //rax => r1
        return list;
    }

    public ArrayList<Instruction_a> getCall(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        int argument_count = Integer.parseInt(arg2);
        //int offset = 8;
        //fix for more than 6 arguments
        list.add(new asm.Call(arg1));
        /*for(int i = argument_count - 1; i > 0; i++){//pop Enum Register
            list.add(new Popq(arguments.get(i)));
        }*/
        return list;
    }

    public ArrayList<Instruction_a> getNew(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        StringTokenizer st = new StringTokenizer(arg2, " ,");
        int i = 1;
        while(st.hasMoreTokens()){
            i++;
            st.nextToken();
        }
        for(int j = 0; j < this.caller.size(); j++){
            list.add(new Pushq(caller.get(j)));
        }
        //list.add(new Pushq("%rdi"));
        //list.add(new Pushq("%rax"));
        list.add(new Movq("$"+(this.offset*i), "%rdi"));
        list.add(new asm.Call("malloc"));
        list.add(new Movq("%rax", arg3));
        //list.add(new Popq("%rax"));
        //list.add(new Popq("%rdi"));
        for(int j = this.caller.size() - 1; j >= 0; j--){
            list.add(new Popq(caller.get(j)));
        }
        return list;
    }

    public ArrayList<Instruction_a> getDel(String arg1){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        for(int i = 0; i < this.caller.size(); i++){
            list.add(new Pushq(caller.get(i)));
        }
        //list.add(new Pushq("%rdi"));
        list.add(new Movq(arg1, "%rdi"));
        list.add(new asm.Call("free"));
        //list.add(new Popq("%rdi"));
        for(int i = this.caller.size() - 1; i >= 0; i--){
            list.add(new Popq(caller.get(i)));
        }
        return list;
    }

    public ArrayList<Instruction_a> getStoreOutArgument(String arg1, String arg2){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        int argument = Integer.parseInt(arg2);
        if(this.store == false){ //Only do this for the first StoreOut
            for(int i = 0; i<caller.size(); i++){
                list.add(new Pushq(caller.get(i)));
            }
            this.store = true;
        }
        //list.add(new Pushq(arguments.get(argument)));
        if(argument < this.max_arguments){
            list.add(new Movq(arg1, arguments.get(argument)));
        }
        else{
            //list.add(new Movq(arg1, ((argument-this.max_arguments)*this.offset)+"(%rsp)")); //original code
            list.add(new Movq(arg1, ((argument-this.max_arguments)*this.offset)+"(%rsp)"));
            
        }
        return list;
    }

    public ArrayList<Instruction_a> getRead(String arg1){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.add(new Pushq("%rdi"));
        list.add(new Pushq("%rsi"));
        list.add(new Pushq("%rax"));
        list.add(new Movq("$.LS" + stringCounter, "%rdi"));
        list.add(new Movq("$.scan", "%rsi"));
        list.add(new Movq("$0", "%rax"));
        list.add(new asm.Call("scanf"));
        list.add(new Movq(".scan", "%r15"));
        list.add(new Popq("%rax"));
        list.add(new Popq("%rsi"));
        list.add(new Popq("%rdi"));
        //list.add(new Movq("%r15", "%rax"));
        list.add(new Movq("%r15", arg1));
        return list;
    }

    public ArrayList<Instruction_a> getLoadInArgument(String arg1, String arg2, String arg3){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        int argument = Integer.parseInt(arg2);
        if(argument < this.max_arguments){
            list.add(new Movq(arguments.get(argument), arg3));
        }
        else{
            list.add(new Movq(((this.argument_location + ((argument-this.max_arguments)*this.offset))+"(%rbp)"), arg3));
        }
        return list;
    }
    
    public void printAsmAll(String fn, ArrayList<Node> funcs, ArrayList<String> stringDirectives) throws IOException {
      FileWriter f;
        int prefix = 4;
      int stringDirSize;
		String fileName = fn.substring(0, fn.length() - prefix);
		fileName = "asm_" + fileName + "s";
		f = new FileWriter(new File(fileName));
		stringCounter = 0;
      int closing_counter = 0;
      String global_prefront = "\n";
      for(String global: globals){
         global_prefront += "\t.comm\t" + global + " , 8 , 8\n";
      }
      global_prefront += "\t.comm\t.scan , 8 , 8\n";
      System.out.print(global_prefront);
      f.write(global_prefront);
		for (Node n : funcs) {
         String prefront = "";
         /* //add .comm globals based on globals private variable (find example on web)
          for(String global: globals){
               prefront += ".comm\n\t" + global + " , 8 , 8\n";
           }
           prefront += ".comm\n\t.scan , 8 , 8\n";*/
		   prefront += "\t.text\n";// Add .text etc here
         String front = ".globl " + n.getFunctionName() + "\n\t.type\t" + n.getFunctionName() + ", @function\n";
		   front = prefront + front;
		   stringDirSize = stringDirectives.size();
		   if (stringDirSize > 0 && stringCounter <= (stringDirSize - 1))
		      f = n.printAsm(front, f, stringDirectives.get(stringCounter++), n.getIsMainHead(), allCalleeRegisters.get(input.indexOf(n)));
		   else
		      f = n.printAsm(front, f, "", n.getIsMainHead(), allCalleeRegisters.get(input.indexOf(n)));
            
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
            ArrayList<String> targets = i_asm.getTargets();
            ArrayList<String> sources = i_asm.getSources();
            for(String source : sources){
                if(source.charAt(0) == 'r' || source.charAt(0) == '%'){
                    if(!kill.contains(source) && !gen.contains(source)){
                        gen.add(source);
                    }
                }
            }
            for(String target : targets){
                if((target.charAt(0) == 'r' || target.charAt(0) == '%') 
                    && !kill.contains(target)){
                    kill.add(target);
                }
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
            System.out.println("Size of nodes after creatList: " + nodes.size());
        }
    }

    public ArrayList<Node> createList(ArrayList<Node> nodes, Node n){ 
        nodes.add(n);
        System.out.println("Node added: L" + n.getId());    
        for(Node s: n.getSuccNodes()){
            if(!nodes.contains(s)){
                createList(nodes, s);
            }
            else{
                System.out.println("Did not Add: L" + s.getId());
            }
        }
        return nodes;
    }

    public void generateIGraphs(){
        System.out.println("Before the generate loop " + allNodes.size());
        for(ArrayList<Node> nodes : allNodes){
            System.out.println("In Generate Loop " + nodes.size());
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
    
    public void colorIGraphs(){
        for(IGraph graph : iGraphs){
            graph.colorGraph();
        }
    }
    
    public void printIGraphColorings(){
        for(IGraph graph : iGraphs){
            graph.printBubbleColors();
        }
    }

    public void applyColor(){
        int association = 0;
        for(ArrayList<Node> nodes : allNodes){ //for each list of nodes
            IGraph graph = iGraphs.get(association); //grab associated Igraph
            HashMap<String, Integer> unc = graph.getUncoloredRegisters(); //get all the uncolored registers
            //ArrayList<Bubble> bubbles = graph.getBubbles();//getBubbles
            for(Node n : nodes){//for each Node
                ArrayList<Instruction_a> copy = new ArrayList<Instruction_a>();
                for(Instruction_a inst : n.getAsmInstructions()){
                    ArrayList<Integer> st = getTargetSource(inst);
                    String arg1 = inst.getArg1();
                    String arg2 = inst.getArg2();
                    String arg3 = inst.getArg3();
                    String address1 = "";
                    String address2 = "";
                    boolean a1c = false;
                    boolean a2c = false;
                    if(arg1 != null && arg1.charAt(0) == 'r'){
                        //System.out.println("Before: " + inst.getArg1() + " text " + inst.toString());
                        Bubble sb = graph.getBubble(arg1);
                        /*if(sb == null){
                            System.out.println("This is null from arg1: " + arg1 + " in Node: " + n.getId());
                        }*/
                        String color = sb.getColor().text();
                        if(color.equals("uncolorable")){
                            int spill = this.offset * -1 * unc.get(sb.getId());
                            address1 = spill+"(%rbp)";
                            inst.setArg1("%r14");
                            a1c = true;
                        }
                        else{
                            inst.setArg1(color);
                        }
                        /*if(inst instanceof Imulq && arg3 != null){
                            inst.resetText2();
                        }
                        else{
                            inst.resetText();
                        }*/
                        //System.out.println("After: " + inst.getArg1() + " text " + inst.toString());
                    }
                    if(arg2 != null && arg2.charAt(0) == 'r'){
                        //System.out.println("Before: " + inst.getArg2() + " text " + inst.toString());
                        Bubble sb = graph.getBubble(arg2);
                        String color = sb.getColor().text();
                        if(color.equals("uncolorable")){
                            int spill = this.offset * -1 * unc.get(sb.getId());
                            address2 = spill+"(%rbp)";
                            inst.setArg2("%r15");
                            a2c = true;
                        }
                        else{
                            inst.setArg2(color);
                        }
                        /*if(inst instanceof Imulq && arg3 != null){
                            inst.resetText2();
                        }
                        else{
                            inst.resetText();
                        }*/
                        //System.out.println("After: " + inst.getArg2() + " text " + inst.toString());
                    }
                    /*(if(arg3 != null && arg3.charAt(0) == 'r'){
                        //System.out.println("Before: " + inst.getArg3() + " text " + inst.toString());
                        Bubble sb = graph.getBubble(arg3);
                        String color = sb.getColor().text();
                        if(color.equals("uncolorable")){
                            int spill = this.offset * -1 * unc.get(sb.getId());
                            String address = spill+"(%rbp)";
                            inst.setArg3(address);
                        }
                        else{
                            inst.setArg3(color);
                        }
                        /*if(inst instanceof Imulq && arg3 != null){
                            inst.resetText2();
                        }
                        else{
                            inst.resetText();
                        }
                        //System.out.println("After: " + inst.getArg3() + " text " + inst.toString());
                    }*/
                    if(inst instanceof Imulq && arg3 != null){
                        inst.resetText2();
                    }
                    else{
                        inst.resetText();
                    }
                    if(a1c){
                        if(st.get(0) == 1 || st.get(0) == 3){
                            copy.add(new Movq(address1, "%r14"));
                        }
                    }
                    if(a2c){
                        if(st.get(1) == 1 || st.get(1) == 3){
                            copy.add(new Movq(address2, "%r15"));
                        }
                    }
                    copy.add(inst);
                    if(a1c){
                        if(st.get(0) == 2 || st.get(0) == 3){
                            copy.add(new Movq("%r14", address1));
                        }
                    }
                    if(a2c){
                        if(st.get(1) == 2 || st.get(1) == 3){
                            copy.add(new Movq("%r15", address2));
                        }
                    }
                }
                n.setAsmInstructions(copy);
            }
            association++;//increment association
        }
    }

    public void removeRedundantMovq(Node n, Instruction_a inst){
        if(inst instanceof Movq && inst.getArg1().equals(inst.getArg2())){
            n.getAsmInstructions().remove(inst);
        }
    }
   public void getCalleeRegisters(Node n, IGraph graph){
       ArrayList<String> callee = new ArrayList<String>();
       HashMap<String, String> hashmap = n.getRegisterMap();
       Set<String> keys = hashmap.keySet();
       ArrayList<String> reg = new ArrayList<String>();
       for(String k: keys){
           reg.add(hashmap.get(k));
           System.out.println("Key found: " + k + ", " + hashmap.get(k));
       }
       for(String r: reg){
           if(r != null && r.charAt(0) == 'r'){
               Bubble sb = graph.getBubble(r);
               if(sb == null){
                   System.out.println("This is null from reg: " + r + " in Node: " + n.getId());
               }
               else{
                   if(caller!= null && !caller.contains(sb.getColor().text())){
                       callee.add(sb.getColor().text());
                       System.out.println("Added register: " + r + ", " + sb.getColor().text());
                   }
                   else{
                       System.out.println("DID NOT ADD register: " + r + ", " + sb.getColor().text());
                   }
               }
           }
       }
       allCalleeRegisters.add(callee);
   }
   
    public void printAsmAllFirst(String fn, ArrayList<Node> funcs) throws IOException {
      FileWriter f;
        int prefix = 4;
		String fileName = fn.substring(0, fn.length() - prefix);
		fileName = "asm_" + fileName + "s";
		f = new FileWriter(new File(fileName));


		for (Node n : funcs) {

		      f = n.printAsmFirst(f, n.getIsMainHead());

		}
		f.close();
   }
    public ArrayList<Integer> getTargetSource(Instruction_a i){
        //0 not source or target
        //1 source
        //2 target
        //3 both
        ArrayList<Integer> st = new ArrayList<Integer>();
        
        if(i instanceof Addq){
            st.add(1);
            st.add(3);
            return st;
        }
        
        if(i instanceof Subq){
            st.add(1);
            st.add(3);
            return st;
        }
        
        if(i instanceof Imulq){
            st.add(1);
            st.add(3);
            return st;
        }
        
        if(i instanceof Idivq){
            st.add(1);
            st.add(0);
            return st;
        }
        
        if(i instanceof Andq){
            st.add(1);
            st.add(3);
            return st;
        }
        
        if(i instanceof Orq){
            st.add(1);
            st.add(3);
            return st;
        }

        
        if(i instanceof Xorq){
            st.add(1);
            st.add(3);
            return st;
        }

        
        if(i instanceof Cmpq){
            st.add(1);
            st.add(1);
            return st;
        }
        
        if(i instanceof Jmp){
            st.add(0);
            st.add(0);
            return st;
        }
        
        if(i instanceof Je){
            st.add(0);
            st.add(0);
            return st;
        }
        
        if(i instanceof Jg){
            st.add(0);
            st.add(0);
            return st;
        }
        
        if(i instanceof Jge){
            st.add(0);
            st.add(0);
            return st;
        }
        
        if(i instanceof Jl){
            st.add(0);
            st.add(0);
            return st;
        }
        
        if(i instanceof Jle){
            st.add(0);
            st.add(0);
            return st;
        }
        if(i instanceof Jne){
            st.add(0);
            st.add(0);
            return st;
        }
        
        if(i instanceof Movq){
            st.add(1);
            st.add(3);
            return st;
        }

        
        if(i instanceof asm.Call){
            st.add(0);
            st.add(0);
            return st;
        }
        
        if(i instanceof asm.Ret){
            st.add(0);
            st.add(0);
            return st;
        }

        
        if(i instanceof Cmoveq){
            st.add(1);
            st.add(3);
            return st;
        }

        
        if(i instanceof Cmovgq){
            st.add(1);
            st.add(3);
            return st;
        }

        
        if(i instanceof Cmovgeq){
            st.add(1);
            st.add(3);
            return st;
        }

        
        if(i instanceof Cmovlq){
            st.add(1);
            st.add(3);
            return st;
        }

        
        if(i instanceof Cmovleq){
            st.add(1);
            st.add(3);
            return st;
        }

        
        if(i instanceof Cmovneq){
            st.add(1);
            st.add(3);
            return st;
        }

        
        if(i instanceof Shrq){
            st.add(1);
            st.add(2);
            return st;
        }
        
        if(i instanceof Leave){
            st.add(0);
            st.add(0);
            return st;
        }
        
        if(i instanceof Pushq){
            st.add(1);
            st.add(0);
            return st;
        }
        
        if(i instanceof Popq){
            st.add(2);
            st.add(0);
            return st;
        }
        
        //if not one of the above
        st.add(0);
        st.add(0);
        return st;
    }
}
