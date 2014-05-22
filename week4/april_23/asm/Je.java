package asm;

public class Je extends Instruction_a{

	public Je(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "je " + arg1;
	}

}