package instructions;

import util.Arm11Program;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class
MsgLabel extends LabelInstruction {

    private String msg;
    private int length;

    public MsgLabel(String msg, int index) {
        super("msg_" + index);
        this.msg = msg;
        length = Arm11Program.decode(msg).length();
    }

    public String getIdent() {
        return ident;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toCode() {
        return ident + ":\n"
                + "\t.word " + length + '\n'
                + "\t.ascii \"" +  msg  + "\"\n";
    }

}
