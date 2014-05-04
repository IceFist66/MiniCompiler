package iloc;

public class New extends Instruction{

	public New(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "new " + arg1 + " => " + arg2;
	}
}