package asm;
import java.util.*;

public class Type extends Instruction_a{

	public Type(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "type " + arg1 + ", " + arg2;
		this.target = null;
		sources = new ArrayList<String>();
	}

}