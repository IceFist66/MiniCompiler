package asm;

public class Cmovneq extends Instruction_a{

	public Cmovneq(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "cmovneq " + arg1 + " , " + arg2;
	}

}