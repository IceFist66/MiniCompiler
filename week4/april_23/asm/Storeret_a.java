package asm;

public class Storeret_a extends Instruction_a{

	public Storeret_a(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "storeret_a " + arg1;
		this.target = null;
	}

}