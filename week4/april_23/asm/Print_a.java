package asm;

public class Print_a extends Instruction_a{

	public Print_a(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "print_a " + arg1;
		this.target = null;
	}

}