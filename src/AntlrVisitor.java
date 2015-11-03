import java.util.*;
import antlr.*;

public class AntlrVisitor extends WaccParserBaseVisitor<Void>{

    public boolean verbose = false;

    public Void visitProg(WaccParser.ProgContext ctx){
        output("Visited main program entry");
        return visitChildren(ctx);
    }

    public Void visitFunc(WaccParser.FuncContext ctx){
        output("Visited a function called " + ctx.ident());
        //need to visit function args in a loop
        //for (int i = 0; i < ctx.params.size(); i++){
        //    visit(ctx.params.get(i));
        //}
        //System.out.print(" => ");
        //vist funtion return type (note this is out of normal tree order)
        //visitChildren(ctx.param);
        //System.out.println("");
        return null;
    }

    public void output(String s) {
        if(verbose) System.out.println(s);
    }
}