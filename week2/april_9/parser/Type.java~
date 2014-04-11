public class Type {
	private MiniType type;
	private Scope scope;
	private int num_param;
	private String[] parameters;
	private String structType;
	private String structTypeScope;

	// used for INTs and BOOLs
	public Type(MiniType type, Scope scope) {
		this.type = type;
		this.scope = scope;
		this.num_param = -1;
		this.parameters = null;
		this.structType = null;
		this.structTypeScope = null;
	}

	// used for functions
	public Type(Scope scope, int num_param) {
		this.type = MiniType.FUNC;
		this.scope = scope;
		this.num_param = num_param;
		this.parameters = new String[num_param];
		this.structType = null;
		this.structTypeScope = null;
	}
	
	// used for Structs
	public Type(Scope scope, String structType, String structTypeScope) {
		this.type = MiniType.STRUCT;
		this.scope = scope;
		this.num_param = -1;
		this.parameters = null;
		this.structType = structType;
		this.structTypeScope = structTypeScope;
	}

	public void addParam(int index, String parameter) {
		if (this.parameters != null && index < this.parameters.length) {
			this.parameters[index] = parameter;
		}
	}

	public MiniType getMiniType() {
		return this.type;
	}
	
	public String getStructType() {
		return this.structType;
	}
	
	public String getStructTypeScope() {
		return this.structTypeScope;
	}
}
