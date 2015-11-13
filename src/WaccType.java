import antlr.WaccParser;

import java.util.HashMap;
import java.util.Map;

public class WaccType {

    private static final int ALL_ID = -1;
    private static final int INVALID_ID = -2;

    public static final WaccType ALL = new WaccType(ALL_ID);
    public static final WaccType INVALID = new WaccType(INVALID_ID);

    /*
     * Get a return type of a binary operator
     */
    public static WaccType fromBinaryOperator(int op) {
        switch(op) {
            case WaccParser.MULT:
            case WaccParser.DIV:
            case WaccParser.MOD:
            case WaccParser.PLUS:
            case WaccParser.MINUS:
                return new WaccType(WaccParser.INT);
            case WaccParser.GREATER_THAN:
            case WaccParser.GREATER_THAN_EQ:
            case WaccParser.LESS_THAN:
            case WaccParser.LESS_THAN_EQ:
            case WaccParser.EQ:
            case WaccParser.NOT_EQ:
            case WaccParser.AND:
            case WaccParser.OR:
                return new WaccType(WaccParser.BOOL);
            default:
                return WaccType.INVALID;
        }
    }

    private final boolean isPair;
    private boolean isArray = false;
    private int id;
    private int id2;

    public WaccType(int id) {
        this.id = id;
        isPair = false;
    }

    public WaccType(int id1, int id2) {
        this.id = id1;
        this.id2 = id2;
        isPair = true;
    }

    public boolean isValid() {
        return id != INVALID_ID;
    }

    public int getId() {
        return id;
    }

    public int getFstId() {
        return id;
    }

    public int getSndId() {
        return id2;
    }

    public WaccType getBaseType() {
        return new WaccType(id);
    }

    public WaccType toArray() {
        isArray = true;
        return this;
    }

    public boolean isArray() {
        return isArray;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof WaccType) {
            WaccType oth = (WaccType) other;
            return oth.id == id && oth.id2 == id2 && isArray == oth.isArray;
        } else if(other instanceof Integer) {
            return id == (Integer) other;
        } else {
            return false;
        }
    }


    @Override
    public String toString() {
        if(id == ALL_ID) return "[All types]";
        if(id == INVALID_ID) return "[Invalid type]";
        String name = WaccParser.tokenNames[id];
        if(isPair) {
            return "pair(" + name + ", " + WaccParser.tokenNames[id2] + ")";
        } else if(isArray) {
            return "array(" + name + ")";
        } else {
            return name;
        }
    }
}
