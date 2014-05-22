package asm;

public class Jne extends Instruction_a{

	public Jne(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "jne " + arg1;
	}

}