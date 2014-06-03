package asm;
import java.util.*;

public class Subq extends Instruction_a{

	public Subq(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "subq " + arg2 + " , " + arg1;
		this.target = arg2;
		sources = new ArrayList<String>();
		sources.add(arg1);
		sources.add(arg2);
	}

}