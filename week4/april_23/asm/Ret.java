package asm;
import java.util.*;

public class Ret extends Instruction_a{

	public Ret(){
		this.arg1 = null;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "ret " + "";
		this.target = null;
		sources = new ArrayList<String>();
	}

}