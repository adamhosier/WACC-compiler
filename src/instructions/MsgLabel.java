package instructions;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MsgLabel extends LabelInstruction {

    private String msg;
    private String decodedMsg;
    private int length;

    public MsgLabel(String msg, int index) {
        super("msg_" + index);
        this.msg = msg;
        decodedMsg = msg.replace("\\0", "\0").replace("\\b", "\b").replace("\\n", "\n").replace("\\f", "\f").replace("\\r", "\r").replace("\\\"", "\"").replace("\\'", "'").replace("\\\\", "\\");
        length = decodedMsg.length();
    }

    public String getIdent() {
        return ident;
    }

    @Override
    public String toCode() {
        return ident + ":\n"
                + "\t.word " + length + '\n'
                + "\t.ascii \"" +  msg  + "\"\n";
    }

}
