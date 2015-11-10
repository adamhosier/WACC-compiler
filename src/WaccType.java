import antlr.WaccParser;

public class WaccType {

    public static final WaccType ALL = new WaccType(-1);
    public static final WaccType INVALID = new WaccType(-2);

    public final boolean isPair;
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

    public int getId() {
        return id;
    }

    public int getFstId() {
        return id;
    }

    public int getSndId() {
        return id2;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof WaccType) {
            WaccType oth = (WaccType) other;
            return oth.id == id && oth.id2 == id2;
        } else if(other instanceof Integer) {
            return id == (Integer) other;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if(isPair) {
            return "pair(" + WaccParser.tokenNames[id] + ", " + WaccParser.tokenNames[id2] + ")";
        } else {
            return WaccParser.tokenNames[id];
        }
    }
}
