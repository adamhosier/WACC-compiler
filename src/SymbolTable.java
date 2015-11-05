import antlr.WaccParser.ParamListContext;
import antlr.WaccParser.TypeContext;

import javax.lang.model.element.Name;
import javax.naming.NameNotFoundException;
import java.util.*;

public class SymbolTable {

    private LinkedList<Map<String, Symbol>> tables = new LinkedList<>();
    private Map<String, Symbol> globaltable;

    public SymbolTable() {
        globaltable = new HashMap<>();
        tables.add(globaltable);
    }

    public void addGlobalVariable(String ident, TypeContext type) {
        addVar(globaltable, ident, new VariableSymbol(type));
    }

    public void addScopeVariable(String ident, TypeContext type) {
        addVar(tables.getFirst(), ident, new VariableSymbol(type));
    }

    private void addVar(Map<String, Symbol> table, String ident, Symbol sym) {
        if(table.containsKey(ident)) {
            throw new RuntimeException("TODO: IMPROVE THIS ERROR (redefinition)");
        }
        table.put(ident, sym);
    }

    public void addFunction(String ident, TypeContext type, ParamListContext params) {
        addVar(globaltable, ident, new FunctionSymbol(type, params));
    }

    public void newScope() {
        tables.addFirst(new HashMap<String, Symbol>());
    }

    public void endScope() {
        if(tables.size() > 1) {
            tables.removeFirst();
        } else {
            throw new RuntimeException("TODO: IMPROVE THIS ERROR (too many scopes popped)");
        }
    }

    public TypeContext lookupType(String ident) {
        return getSymbol(ident).getType();
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
        private TypeContext type;

        public TypeContext getType() {
            return type;
        }

        public Symbol(TypeContext type) {
            this.type = type;
        }
    }

    private class VariableSymbol extends Symbol {
        VariableSymbol(TypeContext type) {
            super(type);
        }
    }

    private class FunctionSymbol extends Symbol {
        private ParamListContext params;

        public ParamListContext getParams() {
            return params;
        }

        FunctionSymbol(TypeContext type, ParamListContext params) {
            super(type);
            this.params = params;
        }

    }
}
