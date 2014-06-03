package asm;
import java.util.*;

public class File extends Instruction_a{

	public File(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "file " + "\"" + arg1 + "\"";
		this.target = null;
		sources = new ArrayList<String>();
	}

}