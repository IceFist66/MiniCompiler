package iloc;

public class Call extends Instruction{

	public Call(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "call " + arg1 + ", " + arg2;
		this.target = arg1;
	}

}