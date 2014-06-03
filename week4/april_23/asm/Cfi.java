package asm;
import java.util.*;

public class Cfi extends Instruction_a{

	public Cfi(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = ".cfi_" + arg1;
		this.target = null;
		sources = new ArrayList<String>();
	}

}