package asm;
import java.util.*;

public class Cmovleq extends Instruction_a{

	public Cmovleq(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "cmovleq " + arg1 + " , " + arg2;
		this.target = null;
		sources = new ArrayList<String>();
		sources.add(arg1);
		sources.add(arg2);
	}

}