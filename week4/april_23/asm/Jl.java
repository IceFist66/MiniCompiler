package asm;
import java.util.*;

public class Jl extends Instruction_a{

	public Jl(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "jl " + arg1;
		this.target = null;
		sources = new ArrayList<String>();
		sources.add(arg1);
	}

}