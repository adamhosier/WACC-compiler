package util;

import antlr.WaccParser;
import org.antlr.v4.runtime.misc.Pair;

import java.util.*;

public class SymbolTable {

    public boolean verbose = false;

    // points to the lowest table in the hash map list
    private Map<String, Symbol> globaltable;

    // holds functions
    private Map<String, FunctionSymbol> functions = new HashMap<>();

    // holds tables in different scopes
    private LinkedList<Map<String, Symbol>> tables = new LinkedList<>();


    public SymbolTable() {
        globaltable = new HashMap<>();
        tables.add(globaltable);
    }

    /*
     * Outputs debugging strings to stdout if verbose option is true
     */
    public void output(String s) {
        if(verbose) System.out.print(s);
    }

    public void outputln(String s) {
        if(verbose) System.out.println(s);
    }

    /*
     * Adds a function to the functions and global table
     * Returns false on failure
     */
    public boolean addFunction(String ident, WaccType type) {
        output("> ADDING THE FUNCTION " + ident + " WITH TYPE " + type.toString());
        FunctionSymbol sym = new FunctionSymbol(type);
        return addVar(functions, ident, sym) && addVar(globaltable, ident, sym);
    }

    /*
     * Adds a variable to the symbol table in the current scope
     * Returns false on failure
     */
    public boolean addVariable(String ident, WaccType type) {
        output("> ADDING THE VARIABLE " + ident + " WITH TYPE " + type.toString() + " AT SCOPE " + tables.size());
        return addVar(tables.getFirst(), ident, new VariableSymbol(type));
    }

    /*
     * Takes some generic table, an identifier and a symbol and adds that symbol to the given table under its ident
     */
    private <T extends Symbol> boolean addVar(Map<String, T> table, String ident, T sym) {
        if(table.containsKey(ident) && !(table.get(ident) instanceof FunctionSymbol && sym instanceof VariableSymbol)) {
            outputln("... FAILED");
            return false;
        }
        outputln("... DONE");
        table.put(ident, sym);
        return true;
    }

    /*
     * Given some function name, parameter name and parameter type, add it to the function symbol
     */
    public void addParamToFunction(String funcIdent, String paramIdent, WaccType type) {
        FunctionSymbol sym = getFunctionSymbol(funcIdent);
        if(sym != null) {
            sym.addParam(paramIdent, type);
        }
    }

    /*
     * Create new scope
     */
    public void newScope() {
        outputln("> NEW SCOPE CREATED: amount " + (tables.size() + 1));
        tables.addFirst(new HashMap<String, Symbol>());
    }

    /*
     * Destroy top most scope
     */
    public boolean endScope() {
        outputln("> CURRENT SCOPE ENDED: amount " + (tables.size() - 1));
        if(tables.size() > 1) {
            tables.removeFirst();
            return true;
        } else {
            return false;
        }
    }

    /*
     * Gets the type of the variable [ident], or null if it doesn't exist
     */
    public WaccType lookupType(String ident) {
        Symbol sym = getSymbol(ident);
        if(sym == null) return null;
        else return sym.getType();
    }

    /*
     * Gets a list of types and strings of all parameters of functions
     */
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


    /*
     * Given the identifier of a function, return it's result type, or null if it doesn't exist
     */
    public WaccType lookupFunctionType(String ident) {
        FunctionSymbol sym = getFunctionSymbol(ident);
        if(sym == null) {
            return null;
        }
        return sym.getType();
    }

    /*
     * Given the identifier of a function, and one of its parameters,
     *  return the parameters result type, or null if it doesn't exist
     */
    public WaccType getFunctionParamType(String ident, int param) {
        FunctionSymbol sym = getFunctionSymbol(ident);
        if(sym == null) return null;
        return sym.getParamType(param);
    }

    /*
     * Enters the function called [ident] pushing all of its parameters to the current scope
     * NOTE: doest not create a new scope
     */
    public void enterFunction(String ident) {
        FunctionSymbol sym = getFunctionSymbol(ident);

        if(sym == null) return;

        //sym.getParams().forEach((Pair<util.WaccType, String> param) -> addVariable(param.b, param.a));

        for(Pair<WaccType, String> param : sym.getParams()) {
            addVariable(param.b, param.a);
        }
    }

    public int getNumParams(String ident) {
        FunctionSymbol sym = getFunctionSymbol(ident);
        if(sym == null) return 0;
        return sym.getNumerParams();
    }

    /*
     * Checks if a variable given by [ident] is declared
     */
    public boolean isDeclared(String ident) {
        return getSymbol(ident) != null;
    }

    public boolean isFunction(String ident) {
        return getSymbol(ident) instanceof FunctionSymbol;
    }

    /*
     * For locating where variable are stored in code generation
     */
    public void setRegister(String ident, Register register) {
        Symbol symbol = getSymbol(ident);
        if (symbol != null) {
            symbol.setRegister(register);
        }
    }

    public Register getRegister(String ident) {
        Symbol symbol = getSymbol(ident);
        if (symbol != null) {
            return symbol.getRegister();
        }
        return null;
    }

    public void setAddress(String ident, int address) {
        Symbol symbol = getSymbol(ident);
        if (symbol != null) {
            symbol.setAddress(address);
        }
    }

    public int getAddress(String ident) {
        Symbol symbol = getSymbol(ident);
        if (symbol != null) {
            return symbol.getAddress();
        }
        return -1;
    }

    public void setStackSize(String ident, int size) {
        Symbol symbol = getSymbol(ident);
        if(symbol != null) {
            symbol.setSize(size);
        }
    }

    public int getStackSize(String ident) {
        Symbol symbol = getSymbol(ident);
        if(symbol != null) {
            return symbol.getSize();
        }
        return -1;
    }

    public int getParamAddress(String funcName, String paramName) {
        FunctionSymbol sym = getFunctionSymbol(funcName);
        if(sym == null) return -1;
        return sym.getParamAddress(paramName);
    }

    ///////////// INNER CLASSES /////////////

    private abstract class Symbol {
        private WaccType type;

        // for code gen - location of variables
        private Register register;
        private int address; //
        private boolean isStoredInReg;

        private int size;

        public WaccType getType() {
            return type;
        }

        public Register getRegister() {
            return register;
        }

        public int getAddress() {
            return address;
        }

        public void setRegister(Register register) {
            this.register = register;
        }

        public void setAddress(int address) {
            this.address = address;
        }

        public boolean isStoredInReg() {
            return isStoredInReg;
        }

        public Symbol(WaccType type) {
            this.type = type;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
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

        public int getParamAddress(String paramName) {
            return 0; //TODO
        }
    }

    private class VariableSymbol extends Symbol {
        public VariableSymbol(WaccType type) {
            super(type);
        }
    }

}
