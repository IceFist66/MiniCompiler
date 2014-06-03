package asm;
import java.util.*;

public class Pushq extends Instruction_a{

	public Pushq(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "pushq " + arg1;
		this.target = null;
		sources = new ArrayList<String>();
		sources.add(arg1);
	}

}