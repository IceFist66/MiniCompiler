package asm;

public class Read_a extends Instruction_a{

	public Read_a(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "read_a " + arg1;
		this.target = null;
	}

}