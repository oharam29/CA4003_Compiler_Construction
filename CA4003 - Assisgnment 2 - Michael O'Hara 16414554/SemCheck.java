import java.util.*;

public class SemCheck implements CCALTokeniserVisitor{

	
	static Hashtable<String, LinkedHashSet<String>> duplicates = new Hashtable<>();
	static HashSet<String> Variables = new HashSet<>();
	static HashSet<String> Types = new HashSet<>();
	static SymbolTable SymTable;

	static int TotErrorsCount = 0; 
	static String scope = "global";

	private static ArrayList<String> functionNames;
 	
	public Object visit(SimpleNode node, Object data)
	{
		throw new RuntimeException("Visit SimpleNode"); 
	}


	public Object visit(ProgramStart node, Object data) 
	{
		SymTable = (SymbolTable) data;

		functionNames = SymTable.GetFunctions();

		int num = node.jjtGetNumChildren();
		for(int i = 0; i < num; i++) 
		{
			node.jjtGetChild(i).jjtAccept(this, data);
		}

		if(functionNames.size() > 0) {
			for(String functionName : functionNames) {
      			System.out.println("Function " + functionName + " has not been called");
      			TotErrorsCount++;
      		}
      	}


		DuplicateDeclarations();
		checkInvokedFunctions();

		//checkUsedVars(); Testing something but didnt work out. 

		if(TotErrorsCount == 0)
		{
			System.out.println("No errors found");
		}
		else
		{
			System.out.println(TotErrorsCount + " errors found");
		}
		return data;
	}


	public Object visit(Var node, Object data)
	{ 	
		System.out.println("VAR"); 
		node.childrenAccept(this, data);
		return data;
	}

	public Object visit(Const node, Object data){
		System.out.println("Const");
		return data;
	}

	public Object visit(Function node, Object data)
	{
		node.childrenAccept(this, data);
		return data;
	}


	public Object visit(FunctionCall node, Object data)
	{	
		System.out.println("Function call"); 

		node.childrenAccept(this, data);
		SimpleNode firstChild = (SimpleNode) node.jjtGetChild(0);
		if(SymTable.GetFunctions().contains(((String)firstChild.value)))
		{
			if(functionNames.contains((String)firstChild.value))
			{
	    		functionNames.remove((String)firstChild.value);
			}
		}
		return node.value;
	}

	public Object visit(Return node, Object data)
	{
		node.childrenAccept(this, data);
		return node.value;
	}

	public Object visit(Type node, Object data)
	{
		node.childrenAccept(this, data);
		return node.value;
	}

	public Object visit(Param_list node, Object data)
	{
		node.childrenAccept(this, data);
		return data;
	}

	public Object visit(Parameters node, Object data)
	{
		node.childrenAccept(this, data);
		return data;
	}

	public Object visit(Main node, Object data)
	{
		System.out.println("Main");
		node.childrenAccept(this, data);
		return data;
	}

	public Object visit(Statement node, Object data)
	{
		System.out.println("Statement"); 
		node.childrenAccept(this, data);
		return data;
	}

	public Object visit(Assign node, Object data)
	{
		System.out.println("Assign"); 
		node.childrenAccept(this, data);
		return data;
		
	}

	public Object visit(ArgList_Assign node, Object data)
	{
		node.childrenAccept(this, data);
		return data;
	}

	public Object visit(Plus node, Object data)
	{

		System.out.println("Plus"); 
		SimpleNode firstChild = (SimpleNode) node.jjtGetChild(0);
		System.out.println("1: "+firstChild);

		SimpleNode secondChild = (SimpleNode) node.jjtGetChild(1);
		System.out.println("2: "+secondChild);

		DataType firstChildDataType = (DataType) firstChild.jjtAccept(this, data);
		DataType secondChildDataType = (DataType) secondChild.jjtAccept(this, data);
	  	if ((firstChildDataType != secondChildDataType) | firstChildDataType != DataType.Num  | secondChildDataType != DataType.Num ) {
	      	System.out.println("Arithmetic Operator Check Failed: Cannot add '" + firstChildDataType +"' and '" + secondChildDataType + "'");
	      	return DataType.type_unknown;
	  	}

	  return DataType.Num;
	}

	public Object visit(Minus node, Object data)
	{
		SimpleNode firstChild = (SimpleNode) node.jjtGetChild(0);
		SimpleNode secondChild = (SimpleNode) node.jjtGetChild(1);
		DataType firstChildDataType = (DataType) firstChild.jjtAccept(this, data);
		DataType secondChildDataType = (DataType) secondChild.jjtAccept(this, data);
		if(firstChildDataType != DataType.Num | secondChildDataType != DataType.Num)
		{
			System.out.println("Arithmetic Operator Check Failed: Cannot subtract '" + firstChildDataType +"' and '" + secondChildDataType + "'");
		    return DataType.type_unknown;
		}
		return DataType.Num;
	}

	public Object visit(And node, Object data)
	{
		DataType child1DataType = (DataType) node.jjtGetChild(0).jjtAccept(this, data);
	    DataType child2DataType = (DataType) node.jjtGetChild(1).jjtAccept(this, data);
	    if(child1DataType != DataType.comp_op || child2DataType != DataType.comp_op) {
	      System.out.println( "Cannot " + node.value + " " + child1DataType + " and " + child2DataType);
	    }
		return data;
	}

	public Object visit(Or node, Object data)
	{
		node.childrenAccept(this, data);
		return node.value;
	}

	public Object visit(Comp node, Object data)
	{
		node.childrenAccept(this, data);
		return node.value;
	}

	public Object visit(EQ node, Object data)
	{
		node.childrenAccept(this, data);
		return node.value;
	}

	public Object visit(NotEQ node, Object data)
	{
		node.childrenAccept(this, data);
		return node.value;
	}

	public Object visit(LESST node, Object data)
	{
		node.childrenAccept(this, data);
		return node.value;
	}

	public Object visit(LESSTEQ node, Object data)
	{
		node.childrenAccept(this, data);
		return node.value;
	}

	public Object visit(GREATT node, Object data)
	{
		node.childrenAccept(this, data);
		return node.value;
	}

	public Object visit(GREATTEQ node, Object data)
	{
		node.childrenAccept(this, data);
		return node.value;
	} 

	public Object visit(ID node, Object data)
	{
		System.out.println("ID"); 
		node.childrenAccept(this, data);
		return node.value;
	}

	public Object visit(Number node, Object data)
	{
		System.out.println("Number"); 
		node.childrenAccept(this, data);
		return node.value;
	}

	public Object visit(Bool node, Object data)
	{
		node.childrenAccept(this, data);
		return node.value;
	}
	 


	public static void DuplicateDeclarations()
	{
		Hashtable<String, ArrayList<String>> duplicate = SymTable.getDuplicateInvoke();
		if (duplicate.size() > 0) {
			Enumeration enumm = duplicate.keys();
	   	while(enumm.hasMoreElements()) 
	   	{
	   		String scope = (String) enumm.nextElement();
	   	 	ArrayList<String> dups = new ArrayList<String>(duplicate.get(scope));
	   	 	Set<String> dupSet = new LinkedHashSet<String>(dups);
	   	 	Iterator<String> it = dupSet.iterator();
	   	 	
	   	 	
	   	 	while(it.hasNext()) 
	   	 	{
	   	 		System.out.print("Error found: Multiple declarations found for: " + it.next() );
				TotErrorsCount++;
			}
		}
		}

	} 


	public static void checkUsedVars()
	{
		ArrayList<String> varsFound = SymTable.getVars();
		for(int i=0; i<varsFound.size(); i++)
		
		{
			if(Types.contains(varsFound.get(i)))
			{
				System.out.println("Error found: " + varsFound.get(i) + " is never used!");
				TotErrorsCount++;
			}
		}
	}

	public static void checkInvokedFunctions()
	{
		ArrayList<String> returnedFunc = SymTable.GetFunctions();
		for(int i = 0; i<returnedFunc.size(); i++)
		{
			if(!Types.contains(returnedFunc.get(i)))
			{
				System.out.println("Error found: " + returnedFunc.get(i) + " is declared but never used");
				TotErrorsCount ++;
			}
		}
	}
 

}