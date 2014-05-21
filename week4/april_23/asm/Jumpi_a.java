package asm;

public class Jumpi_a extends Instruction_a{

	public Jumpi_a(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "jumpi_a " + arg1;
		this.target = null;
	}

}