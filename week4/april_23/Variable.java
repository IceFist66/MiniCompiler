import java.util.ArrayList;

public class Variable {
	private Type type;
    private boolean isFunc;
	private String name; // name of variable (needed when building struct definitions)
	private String variableScope; // name of function in which variable is declared
	private int num_param;
	private ArrayList<String> parameters;
	private String structType;
	private String structTypeScope;	// name of function in which struct is declared
	private Scope varType;

	// used for INTs and BOOLs
	public Variable(Type type, String scope) {
		this.type = type;
        this.isFunc = false;
		this.variableScope = scope;
		this.num_param = -1;
		this.parameters = null;
		this.structType = null;
		this.structTypeScope = null;	   
	}

	// used for functions
	public Variable(String scope, int num_param) {
		this.type = Type.FUNC;
        this.isFunc = true;
		this.variableScope = scope;
		this.num_param = num_param;
		this.parameters = new ArrayList<String>();//new String[num_param];
		this.structType = null;
		this.structTypeScope = null;
	}
	
	// used for Structs
	public Variable(String scope, String structType, String structTypeScope) {
		this.type = Type.STRUCT;
        this.isFunc = false;
		this.variableScope = scope;
		this.num_param = -1;
		this.parameters = null;
		this.structType = structType;
		this.structTypeScope = structTypeScope;
	}

	public void addParam(String parameter) {
	   if (this.parameters != null)
	      parameters.add(parameter);
	
	/*	if (this.parameters != null && index < this.parameters.length) {
			this.parameters[index] = parameter;
		}*/
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
	
	public int getNumParam() {
		return this.num_param;
	}
	
	public String getVariableScope() {
		return this.variableScope;
	}

	public void setStructType(String struct_type) {
		this.structType = struct_type;
	}

    public void setIsFunc(boolean newIsFunc){
        this.isFunc = newIsFunc;
        if(newIsFunc && this.parameters == null){
            this.parameters = new ArrayList<String>();
        }
    }

    public boolean isFunc() {
		return this.isFunc;
	}

    public void setNumParam(int num){
        this.num_param = num;
    }
    
    public Scope getVarType() {
        return this.varType;
    }
    
    public void setVarType(Scope varType) {
      this.varType = varType;
    }

    public ArrayList<String> getParams(){
        return this.parameters;
    }
}
