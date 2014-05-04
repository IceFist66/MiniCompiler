package iloc;

public class Movlt extends Instruction{

	public Movlt(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "movlt " + arg1 + ", " + arg2;
	}
}
