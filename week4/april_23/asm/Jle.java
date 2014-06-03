package asm;
import java.util.*;

public class Jle extends Instruction_a{

	public Jle(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "jle " + arg1;
		this.target = null;
		sources = new ArrayList<String>();
		sources.add(arg1);
	}

}