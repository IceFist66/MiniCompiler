
public enum Type {
		INT,
		BOOL,
		FUNC,
		STRUCT;
		
	private String string;
	
	static {
		INT.string = "INT";
		BOOL.string = "BOOL";
		FUNC.string = "FUNC";
		STRUCT.string = "STRUCT";		
	}
	
	public String toString() {
		return string;
	}
}
