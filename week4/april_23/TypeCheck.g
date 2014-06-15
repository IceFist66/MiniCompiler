tree grammar TypeCheck;

/* to do
XXX 1. Struct fields within struct definitions are only defined as Type.STRUCT; they
need to carry information about the StructType. This will require modifying the 
StructDef class to carry this information in another parallel array. See Type below.

XXX 2. Params needs to be modified to modify variable.
 
3. Fun needs to be modified to modify variable. NULLs reported in program are function names.

4. Once symbol table code is complete, add typechecking for UOP, BOP, and returns.
*/

options
{
   tokenVocab=Mini;
   ASTLabelType=CommonTree;
}

@header
{
   import java.util.Map;
   import java.util.HashMap;
   import java.util.Vector;
   import java.util.Iterator;
}

@members {
    private StructTypes g_stypes;
    private SymbolTable g_stable;
    private ArrayList<ArrayList<String>> ordered_params;
    
    public ArrayList<ArrayList<String>> getOrderedParams(){
        return ordered_params;
    }
}

error:
    {System.out.println("NO MATCH FOUND");}
;

types [String scope]
   : ^(TYPES (struct[scope])*) 
;

struct [String scope]: //adds a new struct definition
    ^(STRUCT id=ID 
      {StructDef sd = new StructDef(); System.out.println("Gathering fields for struct " + $id.text);} 
    (d = decl[scope] 
      {if (d != null) {
         sd.addField(d.getStructType(),d.getName(), d.getType());
            if(d.getStructType() != null)
                System.out.println("Struct type "+ d.getStructType() + " Struct field " + d.getName() + " of type " + d.getType());
            else
                System.out.println("Struct field " + d.getName() + " of type " + d.getType());
        }
      }
    )*) 
    
    {g_stypes.addStruct(scope, $id.text, sd);
      System.out.println("Added new struct definition for " + $id.text + " to StructTable with scope " + scope + ".");
    }
;

decl [String scope] returns [Variable v = null]
   : ^(DECL ^(TYPE var=type[scope]{$v = $var.v; String string = $var.s;}) (id=ID)+ ) 
   
   {
      if (var != null) {
         v.setName($id.text);
         v.setStructType(string);
      }
   }
;

type [String scope] returns [Variable v = null, String s = null]
    : ^(TYPE INT) {$v = new Variable(Type.INT, scope);}
    | ^(TYPE BOOL) {$v = new Variable(Type.BOOL, scope);}
    | ^(TYPE ^(STRUCT var=type[scope])) {$v = new Variable(scope, $var.s, scope); System.out.println("TYPE STRUCT!!!!");}
    | INT {$v = new Variable(Type.INT, scope);}                         // struct fields, params
    | BOOL {$v = new Variable(Type.BOOL, scope);}                       // struct fields, params
    | ^(STRUCT var=type[scope]) {$v = new Variable(scope, var.s, scope); $s = var.s;}   // **This is probably where structtype needs to be stored.**
    | id=ID {$s = $id.text; /*System.out.println($s);*/   }
    | VOID {$v = new Variable(Type.VOID, scope);}  
;


decls [String scope]:
   {System.out.println("decls scope = " + scope);}
    ^(DECLS (decllist[scope])*)
;

decllist[String scope]:
    ^(DECLLIST var=type[scope]{Variable v = $var.v;} (id=ID 
    
    {    
      if (v!=null) {
         if (scope.equals("global"))
            v.setVarType(Scope.GLOBAL);
         else
            v.setVarType(Scope.LOCAL);
         g_stable.addSymbol(scope, $id.text, v);
         System.out.println("Added symbol " + $id.text + " to the table with scope " + scope);
    	   Variable tt1 = g_stable.getVariable(scope, $id.text);
		   if (tt1 == null)
			   System.out.println("t1 not in table.");
		   else
			   System.out.println($id.text + " = " + tt1.getType().toString());
      }
      else {
         System.out.println("v WAS NULL");
      }
    }
    
    )*)
;

expression:
   ^(AND expression expression)
   |^(OR expression expression)
   |^(EQ expression expression)
   |^(NE expression expression)
   |^(LT expression expression)
   |^(GT expression expression)
   |^(LE expression expression)
   |^(GE expression expression)
   |^(PLUS expression expression)
   |^(MINUS expression expression)
   |^(TIMES expression expression)
   |^(DIVIDE expression expression)
   |^(NOT expression)
   |^(NEW ID)
   |^(DOT expression expression)
   |^(INVOKE ID args)
   |TRUE
   |FALSE
   |INTEGER
   |ID
   |ENDL
   |NULL
   |stmts
;

lvalue:
   ^(DOT lvalue ID)
   |ID
;

stmt:
    ^(BLOCK stmts)
    |^(PRINT expression*)
    |^(READ lvalue)
    |^(IF expression stmt stmt?)
    |^(WHILE expression stmt expression)
    |^(DELETE expression)
    |^(RETURN expression?)
    |^(INVOKE ID args)
    |^(ASSIGN expression lvalue)
;

args:
    ^(ARGS expression*)
;

stmts:
    ^(STMTS stmt*)
;

funcs [String scope]:
    ^(FUNCS (fun[scope])*)
;

fun [String scope]:
    ^(FUN id=ID (num=params[$id.text]) var=rettype[scope]
    {
        var.setIsFunc(true);
        var.setNumParam(num);
        var.setVarType(Scope.FUNCTION);
        g_stable.addSymbol(scope, $id.text, var);
    } decls[$id.text] stmts)
;

params [String scope] returns [int i = 0]:
    ^(PARAMS 
    
    {
        int j = 0;
        ArrayList<String> pa = new ArrayList<String>();
        pa.add(scope);
    }
    
    (v=decl[scope]
    
    {
        /*System.out.println("Get Param name: "+ v.getName());
        System.out.println("Get Param struct_type: "+ v.getStructType());*/
        v.setVarType(Scope.PARAMETER);
        g_stable.addSymbol(scope, v.getName(), v);
        pa.add(v.getName());
        System.out.println("Symbol " + v.getName() + " was added to " + scope);
        j++;
    }
    
    )*
    
    {
        $i = j;
        ordered_params.add(pa);
    }
    
    )
;

rettype[String scope] returns [Variable v = null]:
    ^(RETTYPE var=type[scope]{$v=var.v;})
;


verify [StructTypes stypes, SymbolTable stable] @init 
    {
        g_stypes = stypes; 
        g_stable = stable; 
        ordered_params = new ArrayList<ArrayList<String>>();

    }:
    ^(PROGRAM types["global"] decls["global"] funcs["global"])
    { System.out.println("Successfully walked Program tree."); 
      stable.gatherParams();
      stable.getAll();
    }
;
