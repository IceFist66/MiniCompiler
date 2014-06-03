package asm;
import java.util.*;

public class Jge extends Instruction_a{

	public Jge(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "jge " + arg1;
		this.target = null;
		sources = new ArrayList<String>();
		sources.add(arg1);
	}

}