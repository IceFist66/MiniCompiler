package asm;

public class Movlt_a extends Instruction_a{

	public Movlt_a(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "movlt_a " + arg1 + ", " + arg2;
		this.target = null;
	}

}