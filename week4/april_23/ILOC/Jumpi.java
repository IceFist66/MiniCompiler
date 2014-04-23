public class Jumpi extends Instruction{

	public Jumpi(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "jumpi " + arg1;
	}
}