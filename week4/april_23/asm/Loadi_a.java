package asm;

public class Loadi_a extends Instruction_a{

	public Loadi_a(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "loadi_a " + arg1 + " => " + arg2;
		this.target = arg2;
	}

}