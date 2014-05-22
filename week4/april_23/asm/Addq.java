package asm;

public class Addq extends Instruction_a{

	public Addq(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "addq " + arg2 + " , " + arg1;
	}

}