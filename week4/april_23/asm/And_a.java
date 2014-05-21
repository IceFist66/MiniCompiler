package asm;

public class And_a extends Instruction_a{

	public And_a(String arg1, String arg2, String arg3){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = arg3;
		this.text = "and_a " + arg1 + ", " + arg2 + " => " + arg3;
		this.target = arg3;
	}

}