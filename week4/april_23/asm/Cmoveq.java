package asm;

public class Cmoveq extends Instruction_a{

	public Cmoveq(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "cmoveq " + arg1 + " , " + arg2;
	}

}