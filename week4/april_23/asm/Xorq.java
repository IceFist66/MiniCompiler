package asm;

public class Xorq extends Instruction_a{

	public Xorq(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "xorq " + arg2 + " , " + arg1;
	}

}