import java.util.HashMap;

public class StructTypes {

	private HashMap<String, HashMap<String, StructDef>> structmap;
	
	public StructTypes(){
		structmap = new HashMap<String, HashMap<String, StructDef>>();
	}
	
	public void addStruct(String scope, String variable, StructDef def){
		HashMap<String, StructDef> value_map;

		if(!structmap.containsKey(scope)){
			value_map = new HashMap<String, StructDef>();
		}
		else{
			value_map = structmap.get(scope);
		}
		
		structmap.put(scope, value_map);
		structmap.get(scope).put(variable, def);
		//System.out.println(structmap.get(scope).get(variable).getName());
	}
	
	// have to determine value_key from path
	public Type getType(String table_key, String value_key, String dot_expression){
		
		HashMap<String, StructDef> value_map;
		String[] result = dot_expression.split("\\.");
		String field = result[result.length-1];
		
		// if variable doesn't exist under local scope, set table_key to "global"
		
		// if the scope is local and the scope is in the table
		if ((!table_key.equals("global")) && (structmap.containsKey(table_key))) {
			
			// if the variable isn't found under the local scope
			if (!structmap.get(table_key).containsKey(value_key))
				table_key = "global"; 
		}
		
		// if the scope is local and the scope is not in the table, try global
		if ((!table_key.equals("global")) && (!structmap.containsKey(table_key))) {
			table_key = "global";
		}
		
		
		
		if (structmap.containsKey(table_key)) {
			value_map = structmap.get(table_key);
			if (value_map.containsKey(value_key)) {
				return structmap.get(table_key).get(value_key).getFieldType(field);
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
	
}
