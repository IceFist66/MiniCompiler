public class Restoreformal extends Instruction{

	public Restoreformal(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "restoreformal " + arg1 + ", " + arg2;
	}
}