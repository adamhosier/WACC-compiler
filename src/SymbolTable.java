import antlr.WaccParser.ParamListContext;
import antlr.WaccParser.TypeContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {

    private Stack<Map<String, Symbol>> tables = new Stack<>();

    public SymbolTable() {
        tables.add(new HashMap<>());
    }

    public void addVariable(String ident, TypeContext type) {
        tables.peek().put(ident, new VariableSymbol(type));
    }

    public void addFunction(String ident, TypeContext type, ParamListContext params) {
        tables.peek().put(ident, new FunctionSymbol(type, params));
    }

    public TypeContext getType(String ident) {
        return tables.peek().get(ident).getType();
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
