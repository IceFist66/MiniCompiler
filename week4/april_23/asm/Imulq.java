package asm;
import java.util.*;

public class Imulq extends Instruction_a{

	public Imulq(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "imulq " + arg2 + " , " + arg1;
		this.target = arg2;
		sources = new ArrayList<String>();
		sources.add(arg1);
	}

	public Imulq(String arg1, String arg2, String arg3){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = arg3;
		this.text = "imulq " + arg3 + " , " + arg2  + " , " + arg1;
		this.target = arg2;
		sources = new ArrayList<String>();
		sources.add(arg1);
	}

}