import java.util.*;


public class SymbolTable extends Object {
    

    Hashtable<String, LinkedList<String>> symbolTable;
    Hashtable<String, String> types;
    Hashtable<String, String> values;
    
    
    SymbolTable() {
        this.symbolTable = new Hashtable<>();
        this.types = new Hashtable<>();
        this.values = new Hashtable<>();
        
        symbolTable.put("global", new LinkedList<>());
    }

    public void addToTable(String id, String value, String type, String scope ){
        
        LinkedList<String> CScope = symbolTable.get(scope);
        
        if(CScope == null){
            CScope = new LinkedList<>();
            CScope.add(id);
            symbolTable.put(scope,CScope);
        } 
        else{
            CScope.addFirst(id);
        }
        values.put(id+scope, value);
        types.put(id+scope, type);
    }   

    public void PrintTable(){
        String scope;
        Enumeration tableenumm = symbolTable.keys();
        
        while (tableenumm.hasMoreElements()){
            scope = (String) tableenumm.nextElement();
            System.out.println("\nScope: "+scope);
            LinkedList<String> ID_List = symbolTable.get(scope);
            int numoftableelem = 0;
            for (String id : ID_List) {
                numoftableelem++;
                String val = values.get(id + scope);
                String type = types.get(id + scope);
                System.out.print(numoftableelem + ". " + "type: " +  val + " id: " + id + " desc: " + type + "\n");
            }
        }
    }

    public ArrayList<String> GetFunctions(){
        ArrayList<String> functionList = new ArrayList<String>();
        LinkedList<String> scope = symbolTable.get("global");
        for(int i =0; i < scope.size(); i++)
        {
                String val = values.get(scope.get(i)+"global");
                if(val.equals("Function"))
                {
                    System.out.println(scope.get(i));
                    functionList.add(scope.get(i));
                }
        }
        System.out.print(functionList);
        return functionList;
    }


    public LinkedList<String> getScopeTable(String scope) 
    {
        return symbolTable.get(scope);
    }



    public Hashtable<String,  ArrayList<String>> getDuplicateInvoke()
    {
        Set<String>keys = symbolTable.keySet();
        Hashtable<String, ArrayList<String>> dupsTable = new Hashtable<String, ArrayList<String>>();
        
        for(String key : keys) 
        {
            LinkedList<String> tempL = symbolTable.get(key);
            
            ArrayList<String> duplicates = new ArrayList<String>();
            while(0 < tempL.size() -1){
                Collections.sort(tempL);
                if (tempL.size() > 0) {
                    String dupCheck = tempL.pop();
                    if(tempL.contains(dupCheck)){
                        duplicates.add(dupCheck);
                    }
                }
            }
            dupsTable.put(key, duplicates);
        }
        
        return dupsTable;
    }


    public ArrayList<String> getVars()
    {
        ArrayList<String> varsList = new ArrayList<String>();
        Enumeration enumm =symbolTable.keys();
        while(enumm.hasMoreElements())
        {
            String scope= (String) enumm.nextElement();
            LinkedList<String> scopeL = symbolTable.get(scope);
            for(int i=0; i< scopeL.size(); i++)
            {
                String desc = values.get(scopeL.get(i)+scope);
                if(desc.equals("var"))
                {
                    varsList.add(scopeL.get(i));
                }
            }
        }
        return varsList;
    }
    
       
}

