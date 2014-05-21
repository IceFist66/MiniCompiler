package asm;

public class Del_a extends Instruction_a{

	public Del_a(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "del_a " + arg1;
		this.target = null;
	}

}