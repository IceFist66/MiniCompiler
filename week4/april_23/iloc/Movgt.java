package iloc;

public class Movgt extends Instruction{

	public Movgt(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "movgt " + arg1 + ", " + arg2;
	}

}