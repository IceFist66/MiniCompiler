package asm;

public class Moveq_a extends Instruction_a{

	public Moveq_a(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "moveq_a " + arg1 + ", " + arg2;
		this.target = null;
	}

}