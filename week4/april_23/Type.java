
public enum Type {
		INT,
		BOOL,
		FUNC,
        VOID,
		STRUCT;
		
	private String string;
	
	static {
		INT.string = "INT";
		BOOL.string = "BOOL";
		FUNC.string = "FUNC";
        VOID.string = "VOID";
		STRUCT.string = "STRUCT";		
	}
	
	public String toString() {
		return string;
	}
}
