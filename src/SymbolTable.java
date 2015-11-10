import antlr.WaccParser;
import antlr.WaccParser.ParamListContext;
import antlr.WaccParser.TypeContext;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.lang.model.element.Name;
import javax.naming.NameNotFoundException;
import java.util.*;

public class SymbolTable {

    private Map<String, Symbol> globaltable;
    private WaccVisitorErrorHandler errorHandler;

    private LinkedList<Map<String, Symbol>> tables = new LinkedList<>();

    public SymbolTable(WaccVisitorErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        globaltable = new HashMap<>();
        tables.add(globaltable);
    }

    public void addFunction(String ident, int type, ParamListContext params) {
        addVar(globaltable, ident, new FunctionSymbol(new WaccType(type), params));
    }

    public void addVariable(String ident, int type) {
        addVar(tables.getFirst(), ident, new VariableSymbol(new WaccType(type)));
    }

    public void addPair(String ident, int t1, int t2) {
        addVar(tables.getFirst(), ident, new VariableSymbol(new WaccType(t1, t2)));
    }

    public void addArray(String ident, WaccType type, int length) {
        addVar(tables.getFirst(), ident, new ArraySymbol(type, length));
    }

    private void addVar(Map<String, Symbol> table, String ident, Symbol sym) {
        if(table.containsKey(ident)) {
            throw new RuntimeException("TODO: IMPROVE THIS ERROR (redefinition)");
        }
        table.put(ident, sym);
    }

    public void newScope() {
        tables.addFirst(new HashMap<>());
    }

    public void endScope() {
        if(tables.size() > 1) {
            tables.removeFirst();
        } else {
            throw new RuntimeException("TODO: IMPROVE THIS ERROR (too many scopes popped)");
        }
    }

    public WaccType lookupType(String ident) {
        return getSymbol(ident).getType();
    }

    public WaccType lookupType(ParseTree child) {
        return lookupType(child.getText());
    }

    public int getArrayLength(String ident) {
        Symbol sym = getSymbol(ident);
        if(!(sym instanceof ArraySymbol)) {
            return -1; //TODO: MAYBE ERROR HERE INSTEAD OF THIS
        } else {
            return ((ArraySymbol) sym).getLength();
        }
    }

    private Symbol getSymbol(String ident) {
        for(Map<String, Symbol> table : tables) {
            if(table.containsKey(ident)) {
                return table.get(ident);
            }
        }
        throw new RuntimeException("TODO: IMPROVE THIS ERROR (symbol not found)");
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

        private final int length;

        public int getLength() {
            return length;
        }

        public ArraySymbol(WaccType type, int length) {
            super(type);
            this.length = length;
        }
    }
}
