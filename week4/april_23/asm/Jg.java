package asm;
import java.util.*;

public class Jg extends Instruction_a{

	public Jg(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "jg " + arg1;
		this.target = null;
		sources = new ArrayList<String>();
		sources.add(arg1);
	}

}