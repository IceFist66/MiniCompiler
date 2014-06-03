package asm;
import java.util.*;

public class Leave extends Instruction_a{

	public Leave(){
		this.arg1 = null;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "leave " + "";
		this.target = null;
		sources = new ArrayList<String>();
	}

}