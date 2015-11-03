import java.util.*;
import antlr.*;
import org.antlr.v4.runtime.misc.NotNull;

public class AntlrVisitor extends WaccParserBaseVisitor<Void>{

    public boolean verbose = false;


    /////////// VISITOR METHODS ////////////

    public Void visitProg(WaccParser.ProgContext ctx) {
        outputln("Visited main program entry");
        return visitChildren(ctx);
    }

    public Void visitFunc(WaccParser.FuncContext ctx) {
        output("Function " + ctx.ident().getText());
        visit(ctx.paramList());
        // need to visit function args in a loop

        //System.out.print(" => ");
        //vist funtion return type (note this is out of normal tree order)
        //visitChildren(ctx.param);
        //System.out.println("");
        outputln("");
        return null;
    }

    public Void visitParamList(WaccParser.ParamListContext ctx) {
        output("(");
        for(int i = 0; i < ctx.getChildCount(); i++){
            if(i != 0) output(", ");
            visit(ctx.children.get(i));
        }
        output(")");
        return null;
    }

    public Void visitParam(WaccParser.ParamContext ctx) {
        output(ctx.type().getText() + " " + ctx.ident().getText());
        return null;
    }



    ////////// OUTPUT FUNCTIONS ////////////

    public void output(String s) {
        if(verbose) System.out.print(s);
    }

    public void outputln(String s) {
        if(verbose) System.out.println(s);
    }
}