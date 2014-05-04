package iloc;

public class Or extends Instruction{

	public Or(String arg1, String arg2, String arg3){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = arg3;
		this.text = "or " + arg1 + ", " + arg2 + " => " + arg3;
	}
}