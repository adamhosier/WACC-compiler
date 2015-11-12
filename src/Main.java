// import ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

// import antlr package (your code)
import antlr.*;

import java.io.FileInputStream;

public class Main {

    public static void main(String[] args) throws Exception {

        if(args.length == 0) {
            // simulate file input from stdin
            // TODO: REMOVE THIS WHEN WE WANT FILES TO BE SENT FROM THE COMMAND LINE
            //System.setIn(new FileInputStream("/homes/ah3114/work/wacc/examples/valid/advanced/hashTable.wacc"));
            System.setIn(new FileInputStream("/homes/ah3114/work/wacc/examples/valid/array/arrayPrint.wacc"));
            //System.setIn(new FileInputStream("/homes/ah3114/work/wacc/examples/invalid/semanticErr/variables/basicTypeErr01.wacc"));
        }

        // create a lexer that feeds off of input from System.in
        WaccLexer lexer = new WaccLexer(new ANTLRInputStream(System.in));

        // create a parser that feeds off the tokens buffer from the lexer
        WaccParser parser = new WaccParser(new CommonTokenStream(lexer));
        parser.setErrorHandler(new WaccParserErrorHandler());

        // begin parsing at "prog" antlr rule
        ParseTree tree = parser.prog();

        // run the visitor
        AntlrVisitor visitor = new AntlrVisitor();
        visitor.verbose = true; // TODO: REMOVE THIS WHEN NOT DEBUGGING
        visitor.visit(tree);

    }
}


