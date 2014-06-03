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
        //String rdi = "rdi";
        //arguments.add(rdi);
        //Collections.addAll(arguments, temp);

	private ArrayList<Node> input;
	
	public Assembly_Factory(ArrayList<Node> input, String fname){
		this.input = input;
		this.fname = fname;
      this.arguments = new ArrayList<String>(Arrays.asList("rdi","rsi","rdx","rcx","r8","r9"));
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
                        String prefront = "\t.text\n";// Add .text etc here
                        String front = ".globl " + n.getFunctionName() + "\n\t.type\t" + n.getFunctionName() + ", @function\n";
                        n.getAsmInstructions().addAll(getStart());
			for(Instruction inst : instructions){
				asm = getAssembly(inst);
				n.getAsmInstructions().addAll(asm);
			}
			successors(n);
                        //n.getAsmInstructions().addAll(getEnd()); //This is the call to .cfi_endproc
                        printAsmAll(fname, input);
                        //n.printAsm(prefront+front); //prints the asm instructions to screen
	        }
	}

    public void successors(Node n) {
      ArrayList<Instruction> instructions;
      ArrayList<Instruction_a> asm;
      for (Node s : n.getSuccNodes()) {
         instructions = s.getInstructions();
         for(Instruction inst : instructions){
        	 asm = getAssembly(inst);
        	 s.getAsmInstructions().addAll(asm);
         }
         successors(s);
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

    public ArrayList<Instruction_a> getStoreReturn(String arg1){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
        list.addAll(getMovq(arg1, "%rax")); //move value to return register
        return list;
    }

    public ArrayList<Instruction_a> getReturn(){
        ArrayList<Instruction_a> list = new ArrayList<Instruction_a>();
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
    
    public void printAsmAll(String fn, ArrayList<Node> funcs) throws IOException {
      FileWriter f;
		String fileName = fn.substring(0, fn.length() - 4);
		fileName = "asm_" + fileName + "s";
		f = new FileWriter(new File(fileName));
		for (Node n : funcs) {
		   String prefront = "\t.text\n";// Add .text etc here
         String front = ".globl " + n.getFunctionName() + "\n\t.type\t" + n.getFunctionName() + ", @function\n";
		   front = prefront + front;
		   f = n.printAsm(front, f);
		}
		f.close();
   }
}
