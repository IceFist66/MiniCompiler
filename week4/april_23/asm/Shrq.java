package asm;

public class Shrq extends Instruction_a{

	public Shrq(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "shrq " + arg1 + " , " + arg2;
	}

}