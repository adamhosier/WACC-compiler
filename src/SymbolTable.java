import antlr.WaccParser.ParamListContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;

public class SymbolTable {

    private Map<String, Symbol> globaltable;

    private LinkedList<Map<String, Symbol>> tables = new LinkedList<>();

    public SymbolTable() {
        globaltable = new HashMap<>();
        tables.add(globaltable);
    }

    public boolean addFunction(String ident, int type, ParamListContext params) {
        return addVar(globaltable, ident, new FunctionSymbol(new WaccType(type), params));
    }

    public boolean addVariable(String ident, WaccType type) {
        return addVar(tables.getFirst(), ident, new VariableSymbol(type));
    }

    public boolean addArray(String ident, WaccType type, int[] length) {
        return addVar(tables.getFirst(), ident, new ArraySymbol(type, length));
    }

    private boolean addVar(Map<String, Symbol> table, String ident, Symbol sym) {
        if(table.containsKey(ident)) {
            return false;
        }
        table.put(ident, sym);
        return true;
    }

    public void newScope() {
        tables.addFirst(new HashMap<String, Symbol>());
    }

    public boolean endScope() {
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

    public ParamListContext getParamList(String ident) {
        return ((FunctionSymbol) getSymbol(ident)).getParams();
    }

    private Symbol getSymbol(String ident) {
        for(Map<String, Symbol> table : tables) {
            if(table.containsKey(ident)) {
                return table.get(ident);
            }
        }
        return null;
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

    private class VariableSymbol extends Symbol {
        VariableSymbol(WaccType type) {
            super(type);
        }
    }

    private class FunctionSymbol extends Symbol {
        private ParamListContext params;

        public ParamListContext getParams() {
            return params;
        }

        FunctionSymbol(WaccType type, ParamListContext params) {
            super(type);
            this.params = params;
        }

    }

    private class ArraySymbol extends Symbol {

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
