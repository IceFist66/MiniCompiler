import java.util.*;

// table_key = global or function name

public class SymbolTable {
	private HashMap<String, HashMap<String, Variable>> hashmap;
	
	public SymbolTable(){
		hashmap = new HashMap<String, HashMap<String, Variable>>();
	}
	
	public void addSymbol(String table_key, String value_key, Variable type){
		HashMap<String, Variable> value_map;

		if(!hashmap.containsKey(table_key)){
			value_map = new HashMap<String, Variable>();
		}
		else{
			value_map = hashmap.get(table_key);
		}
		
		hashmap.put(table_key, value_map);
		hashmap.get(table_key).put(value_key, type);

	}
	
	public Variable getVariable(String table_key, String value_key){
		HashMap<String, Variable> value_map;
		
		if (value_key.contains("."))
			value_key = getStructVariable(value_key);
		
		// if variable doesn't exist under local scope, set table_key to "global"
		
		// if the scope is local and the scope is in the table
		if ((!table_key.equals("global")) && (hashmap.containsKey(table_key)))
			
			// if the variable isn't found under the local scope
			if (!hashmap.get(table_key).containsKey(value_key))
				table_key = "global";
		
		// if the specified local scope doesn't exist in the table, set to global
		if ((!table_key.equals("global")) && (!hashmap.containsKey(table_key))) {
			table_key = "global";
			System.out.println("Specified local scope not in table - setting to global.");
		}
		
		if (hashmap.containsKey(table_key)) {
			value_map = hashmap.get(table_key);
			if (value_map.containsKey(value_key)) {
				return hashmap.get(table_key).get(value_key);
				//return value_map.get(value_key);
			}
			else {
				System.out.println("value_key not in hashmap.\n");
				return null;
			}
		}			
		else {
			System.out.println("table_key not in hashmap.\n");
			return null;
		}
	}
	
	public String getStructVariable(String dot_expression) {
		String[] result = dot_expression.split("\\.");
		System.out.println("result length = " + result.length);
		return result[result.length-2];
	}
	
	public void getAll(){
		Set<String> scopes = this.hashmap.keySet();
		HashMap<String, Variable> func;
		Set<String> values;
		for(String scope: scopes){
			func = this.hashmap.get(scope);
			values = func.keySet();
			for(String name: values){
				Variable v = getVariable(scope, name);
				System.out.println("VARTYPE = " + v.getVarType());
				if(v == null){
					System.out.println("In scope: " + scope + ", " + name + " has type: null");
				}
				else if(v.getType() == Type.STRUCT){
				   if(v.isFunc()){
					   System.out.println("In scope: " + scope + ", " + name + " has type: " + v.getType().toString() + " " + v.getStructType() + " is a function with " + v.getNumParam() + " parameters.");
                       if(v.getParams().size() > 0){
                            System.out.println("The Parameters are: ");
                            for(String s: v.getParams()){
                                System.out.println("\t"+s);
                            }
                       }
				   } else
					System.out.println("In scope: " + scope + ", " + name + " has type: " + v.getType().toString() + " " + v.getStructType());
				}
				else if(v.isFunc()){
					System.out.println("In scope: " + scope + ", " + name + " has type: " + v.getType().toString() + " is a function with " + v.getNumParam() + " parameters.");
                    if(v.getParams().size() > 0){
                        System.out.println("The Parameters are: ");
                        for(String s: v.getParams()){
                            System.out.println("\t"+s);
                        }
                    }
				}
				else{
					System.out.println("In scope: " + scope + ", " + name + " has type: " + v.getType().toString());
				}
			}
		}
	}

    public void gatherParams(){
      Set<String> scopes = this.hashmap.keySet();
		HashMap<String, Variable> func;
		Set<String> values;
		for(String scope: scopes){
			func = this.hashmap.get(scope);
			values = func.keySet();
			for(String name: values){
                Variable v = getVariable(scope, name);
                if(v.getVarType() == Scope.PARAMETER){
                    //get variable from global called 'scope'
                    Variable temp = this.hashmap.get("global").get(scope);
                    //save in arraylist this variable name
                    temp.addParam(name);
                    System.out.println("GATHER PARAMS: " +name+ " added to value " +scope+ " in global");
                }
            }
        }
    }
    
    // returns a list of all variable names for a given scope (e.g., "global," "foo," etc.)
    public ArrayList<String> gatherVariablesInScope(String scope) {      
		ArrayList<String> variables = new ArrayList<String>(); 
		HashMap<String, Variable> varsHashMap = hashmap.get(scope);
		if (varsHashMap != null) {
		   Set<String> gatheredVariables = varsHashMap.keySet();
		   Variable v;
		
		   // remove function names from the list (these won't be assigned registers)		
		   for (String name : gatheredVariables) {
		      v = varsHashMap.get(name);
		      if (v.isFunc() == false) {
		         variables.add(name);
		         System.out.println("Added variable " + name + " in scope " + scope);
		      }
         }      
		}
      return variables;    
    }

}
