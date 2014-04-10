import java.util.HashMap;


public class StructTypes {
	private HashMap names;
	private ENUM type;
	private int offset;
	
	public enum ENUM{
		INTG,
		BOOLE,
		FUNC,
		STRUCT;
	}
	//UNSURE ABOUT ENUM
	public StructTypes(String path, ENUM type, int offset){
		this.names = new HashMap<String, ENUM>();
		this.type = type;
		this.offset = offset;
	}
	
	
}
