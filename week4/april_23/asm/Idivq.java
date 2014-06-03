package asm;
import java.util.*;

public class Idivq extends Instruction_a{

	public Idivq(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "idivq " + arg1;
		this.target = arg2;
		sources = new ArrayList<String>();
		sources.add(arg1);
	}

}