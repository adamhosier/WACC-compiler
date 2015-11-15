import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;

public class SymbolTable {
  
    public final boolean verbose = true; 
    
    public void output(String s) {
      if(verbose) System.out.print(s);
  }

  public void outputln(String s) {
      if(verbose) System.out.println(s);
  }

    // points to the lowest table in the hash map list
    private Map<String, Symbol> globaltable;

    // holds functions
    private Map<String, FunctionSymbol> functions = new HashMap<>();
    private LinkedList<Map<String, Symbol>> tables = new LinkedList<>();

    public SymbolTable() {
        globaltable = new HashMap<>();
        tables.add(globaltable);
    }

    public boolean addFunction(String ident, WaccType type) {
        outputln("> ADDING THE FUNCTION " + ident + " WITH TYPE "
                 + type.toString());
        FunctionSymbol sym = new FunctionSymbol(type);
        return addVar(functions, ident, sym) && addVar(globaltable, ident, sym);
    }

    public boolean addVariable(String ident, WaccType type) {
        outputln("> ADDING THE VARIABLE " + ident + " WITH TYPE " 
                 + type.toString() + " AT SCOPE " + tables.size());
        return addVar(tables.getFirst(), ident, new VariableSymbol(type));
    }

    public boolean addArray(String ident, WaccType type, int[] length) {
        outputln("> ADDING ARRAY " + ident + " WITH TYPE " + type.toString() 
                 + " AT SCOPE " + tables.size());
        return addVar(tables.getFirst(), ident, new ArraySymbol(type, length));
    }

    private <T extends Symbol> boolean addVar(Map<String, T> table, String ident, T sym) {
        if(table.containsKey(ident)) {
            return false;
        }
        table.put(ident, sym);
        return true;
    }

    public void addParamToFunction(String funcIdent, String paramIdent, WaccType type) {
        FunctionSymbol sym = getFunctionSymbol(funcIdent);
        if(sym != null) {
            sym.addParam(paramIdent, type);
        }
    }

    public void newScope() {
        outputln("> NEW SCOPE CREATED: amount " + (tables.size() + 1));
        tables.addFirst(new HashMap<String, Symbol>());
    }

    public boolean endScope() {
        outputln("> CURRENT SCOPE DESTROYED: amount " + (tables.size() - 1));
        if(tables.size() > 1) {
            tables.removeFirst();
            return true;
        } else {
            return false;
        }
    }

    public WaccType lookupType(String ident) {
        Symbol sym = getSymbol(ident);
        if(sym == null) return null;
        else return sym.getType();
    }

    public WaccType lookupType(ParseTree child) {
        return lookupType(child.getText());
    }

    public int[] getArrayLength(String ident) {
        Symbol sym = getSymbol(ident);
        if(!(sym instanceof ArraySymbol)) {
            return null;
        } else {
            return ((ArraySymbol) sym).getLengths();
        }
    }

    public List<Pair<WaccType, String>> getParamList(String ident) {
        FunctionSymbol sym = getFunctionSymbol(ident);
        if(sym == null) return null;
        else return sym.getParams();
    }

    private Symbol getSymbol(String ident) {
        for(Map<String, ? extends Symbol> table : tables) {
            if(table.containsKey(ident)) {
                return table.get(ident);
            }
        }
        return null;
    }

    private FunctionSymbol getFunctionSymbol(String funcIdent) {
        if(!functions.containsKey(funcIdent)) {
            return null;
        }
        return functions.get(funcIdent);
    }

    public WaccType lookupFunctionType(String ident) {
        FunctionSymbol sym = getFunctionSymbol(ident);
        if(sym == null) {
            return null;
        }
        return sym.getType();
    }

    public WaccType getFunctionParamType(String ident, int param) {
        FunctionSymbol sym = getFunctionSymbol(ident);
        if(sym == null) return null;
        return sym.getParamType(param);
    }

    public void enterFunction(String ident) {
        FunctionSymbol sym = getFunctionSymbol(ident);

        if(sym == null) return;

        for(int i = 0; i < sym.getNumerParams(); i++) {
            addVariable(ident, sym.getParamType(i));
        }
    }

    public int getNumParams(String ident) {
        FunctionSymbol sym = getFunctionSymbol(ident);
        if(sym == null) return 0;
        return sym.getNumerParams();

    }

    public boolean isDeclared(String ident) {
        return getSymbol(ident) != null;
    }

    private abstract class Symbol {
        private WaccType type;

        public WaccType getType() {
            return type;
        }

        public Symbol(WaccType type) {
            this.type = type;
        }
    }

    private class FunctionSymbol extends Symbol {

        private List<Pair<WaccType, String>> params;

        FunctionSymbol(WaccType type) {
            super(type);
            params = new ArrayList<>();
        }

        public List<Pair<WaccType, String>> getParams() {
            return params;
        }

        public Pair<WaccType, String> getParam(int param) {
            return params.get(param);
        }

        public WaccType getParamType(int param) {
            return getParam(param).a;
        }

        public void addParam(String ident, WaccType type) {
            params.add(new Pair<>(type, ident));
        }

        public int getNumerParams() {
            return params.size();
        }
    }

    private class VariableSymbol extends Symbol {
        public VariableSymbol(WaccType type) {
            super(type);
        }
    }

    private class PrimativeSymbol extends VariableSymbol {
        PrimativeSymbol(WaccType type) {
            super(type);
        }
    }

    private class ArraySymbol extends VariableSymbol {

        private final int[] lengths;

        public int[] getLengths() {
            return lengths;
        }

        public ArraySymbol(WaccType type, int[] lengths) {
            super(type);
            this.lengths = lengths;
        }
    }
}
