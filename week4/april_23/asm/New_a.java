package asm;

public class New_a extends Instruction_a{

	public New_a(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "new_a " + arg1 + " => " + arg2;
		this.target = arg2;
	}

}