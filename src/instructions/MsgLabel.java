package instructions;

/**
 * Created by cyrusvahidi on 27/11/15.
 */
public class MsgLabel extends LabelInstruction {

    private String msg;

    public MsgLabel(String msg, int index) {
        super("msg_" + index);
        this.msg = msg;
    }

    public String getIdent() {
        return ident;
    }

    @Override
    public String toCode() {
        return ident + ":\n"
                + '\t' + ".word " + msg.length() + '\n'
                + '\t' + ".ascii " + msg + '\n';
    }

}
