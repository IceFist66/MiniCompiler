package iloc;

public class Cbrgt extends Instruction{

	public Cbrgt(String arg1, String arg2, String arg3){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = arg3;
		this.text = "cbrgt " + arg1 + ", " + arg2 + ", " + arg3;
		this.target = null;
	}

}