public class Print extends Instruction{

	public Print(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "print " + arg1;
	}
}