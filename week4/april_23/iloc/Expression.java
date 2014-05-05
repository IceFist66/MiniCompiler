package iloc;
import java.util.*;

public class Expression{
	private ArrayList<Instruction> ex_instructions;
	private int int_value;
	private boolean bool_value;
	
	public Expression(){
		ex_instructions = new ArrayList<Instruction>();
		int_value = 0;
		bool_value = false;
	}

	public ArrayList<Instruction> getEx_instructions() {
		return ex_instructions;
	}

	public void setEx_instructions(ArrayList<Instruction> ex_instructions) {
		this.ex_instructions = ex_instructions;
	}

	public int getInt_value() {
		return int_value;
	}

	public void setInt_value(int int_value) {
		this.int_value = int_value;
	}

	public boolean isBool_value() {
		return bool_value;
	}

	public void setBool_value(boolean bool_value) {
		this.bool_value = bool_value;
	}
	
	
}