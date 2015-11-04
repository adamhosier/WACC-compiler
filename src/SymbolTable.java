import antlr.WaccParser.ParamListContext;
import antlr.WaccParser.TypeContext;

import javax.lang.model.element.Name;
import javax.naming.NameNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {

    private Stack<Map<String, Symbol>> scopetables = new Stack<>();
    private Map<String, Symbol> globaltable = new HashMap<>();


    public void addGlobalVariable(String ident, TypeContext type) {
        globaltable.put(ident, new VariableSymbol(type));
    }

    public void addScopeVariable(String ident, TypeContext type) {
        scopetables.peek().put(ident, new VariableSymbol(type));
    }

    public void addFunction(String ident, TypeContext type, ParamListContext params) {
        globaltable.put(ident, new FunctionSymbol(type, params));
    }

    public void newScope() {
        scopetables.push(new HashMap<String, Symbol>());
    }

    public void endScope() {
        scopetables.pop();
    }

    public TypeContext lookupType(String ident) {
        return getSymbol(ident).getType();
    }

    private Symbol getSymbol(String ident) {
        if(symbolExistsInCurrentScope(ident)) {
            return scopetables.peek().get(ident);
        }
        else if(symbolExistsGlobally(ident)) {
            return globaltable.get(ident);
        } else {
            throw new RuntimeException("Identifier " + ident + " not found in current or global scope");
        }
    }

    public boolean symbolExistsInCurrentScope(String ident) {
        return scopetables.peek().containsKey(ident);
    }

    public boolean symbolExistsGlobally(String ident) {
        return globaltable.containsKey(ident);
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
