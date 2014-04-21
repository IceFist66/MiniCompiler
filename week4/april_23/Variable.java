public class Variable {
	private Type type;
	private String name; // name of variable (needed when building struct definitions)
	private String variableScope; // name of function in which variable is declared
	private int num_param;
	private String[] parameters;
	private String structType;
	private String structTypeScope;	// name of function in which struct is declared

	// used for INTs and BOOLs
	public Variable(Type type, String scope) {
		this.type = type;
		this.variableScope = scope;
		this.num_param = -1;
		this.parameters = null;
		this.structType = null;
		this.structTypeScope = null;
	}

	// used for functions
	public Variable(String scope, int num_param) {
		this.type = Type.FUNC;
		this.variableScope = scope;
		this.num_param = num_param;
		this.parameters = new String[num_param];
		this.structType = null;
		this.structTypeScope = null;
	}
	
	// used for Structs
	public Variable(String scope, String structType, String structTypeScope) {
		this.type = Type.STRUCT;
		this.variableScope = scope;
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

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public String getStructType() {
		return this.structType;
	}
	
	public String getStructTypeScope() {
		return this.structTypeScope;
	}
	
	public int getNumParameters() {
		return this.num_param;
	}
	
	public String getVariableScope() {
		return this.variableScope;
	}

	public void setStructType(String struct_type) {
		this.structType = struct_type;
	}
}
