package asm;

public class Jle extends Instruction_a{

	public Jle(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "jle " + arg1;
	}

}