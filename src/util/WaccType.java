package util;

import antlr.WaccParser;

public class WaccType {

    public static final int ALL_ID = -1;
    public static final int INVALID_ID = -2;

    public static final WaccType ALL = new WaccType(ALL_ID);
    public static final WaccType INVALID = new WaccType(INVALID_ID);
    public static final WaccType PAIR = new WaccType(ALL_ID, ALL_ID);

    private boolean isPair = false;
    private boolean isArray = false;
    private boolean isFstPairArray = false;
    private boolean isSndPairArray = false;
    private int id;
    private int id2;

    private WaccType() {}

    // single types
    public WaccType(int id) {
        this.id = id;
    }

    // pair types
    public WaccType(int id1, int id2) {
        this.id = id1;
        this.id2 = id2;
        isPair = true;
    }

    public boolean isValid() {
        return id != INVALID_ID;
    }

    public boolean isAll() {
        return id == ALL_ID;
    }

    public int getId() {
        if(isPair) return WaccParser.PAIR;
        return id;
    }

    public int getFstId() {
        if(id == WaccParser.PAIR) return ALL_ID;
        return id;
    }

    public int getSndId() {
        if(id == WaccParser.PAIR) return ALL_ID;
        return id2;
    }

    public boolean isPair() {
        return isPair;
    }

    public boolean isFstArray() {
        return isFstPairArray;
    }

    public boolean isSndArray() {
        return isSndPairArray;
    }

    /*
     * Gets the base type of an array
     * e.g. getBaseType(int[]) == int
     */
    public WaccType getBaseType() {
        // strings are arrays of chars
        if(id == WaccParser.STRING && !isArray) {
            return new WaccType(WaccParser.CHAR);
        }
        else {
            WaccType t = this.copy();
            t.isArray = false;
            return t;
        }
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean b) {
        isArray = b;
    }

    public WaccType toArray() {
        if(isArray) return this;
        WaccType t = this.copy();
        t.isArray = true;
        return t;
    }

    public void setFstArray(boolean isArr) {
        isFstPairArray = isArr;
    }

    public void setSndArray(boolean isArr) {
        isSndPairArray = isArr;
    }

    private WaccType copy() {
        WaccType t = new WaccType();
        t.id = id;
        t.id2 = id2;
        t.isFstPairArray = isFstPairArray;
        t.isSndPairArray = isSndPairArray;
        t.isArray = isArray;
        t.isPair = isPair;
        return t;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof WaccType) {
            WaccType oth = (WaccType) other;
            return isArray && getId() == WaccParser.CHAR && oth.getId() == WaccParser.STRING || oth.isArray &&
                    oth.getId() == WaccParser.CHAR && getId() == WaccParser.STRING || oth.getId() == WaccParser.PAIR &&
                    isPair || oth.isPair && getId() == WaccParser.PAIR || oth.getId() == getId() &&
                    oth.getSndId() == getSndId() && isArray == oth.isArray && isFstPairArray == oth.isFstPairArray &&
                    isSndPairArray == oth.isSndPairArray;
        } else {
            // allow equals method to compare integers too
            return other instanceof Integer && id == (Integer) other;
        }
    }

    @Override
    public String toString() {
        if(id == ALL_ID) return "[All types]";
        if(id == INVALID_ID) return "[Invalid type]";

        StringBuilder sb = new StringBuilder();
        sb.append(WaccParser.tokenNames[id]);

        if(isPair) {
            if(isFstPairArray) {
                sb.insert(0, "array(");
                sb.append(")");
            }
            sb.insert(0, "pair(");
            sb.append(", ");
            if(isSndPairArray) sb.append("array(");
            sb.append(id2 == INVALID_ID ? "[Invalid type]" : WaccParser.tokenNames[id2]);
            if(isSndPairArray) sb.append(")");
            sb.append(")");
        }
        if(isArray) {
            sb.insert(0, "array(");
            sb.append(")");
        }

        return sb.toString();
    }
}
