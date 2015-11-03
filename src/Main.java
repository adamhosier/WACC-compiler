// import ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

// import antlr package (your code)
import antlr.*;

import java.io.FileInputStream;

public class Main {

    public static void main(String[] args) throws Exception {

        // simulate file input from stdin
        // TODO: REMOVE THIS WHEN WE WANT FILES TO BE SENT FROM THE COMMAND LINE
        System.setIn(new FileInputStream("~work/wacc_examples/valid/advanced/hashTable.wacc"));

        // create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(System.in);

        // create a lexer that feeds off of input CharStream
        WaccLexer lexer = new WaccLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        WaccParser parser = new WaccParser(tokens);

        ParseTree tree = parser.prog(); // begin parsing at program rule

        // build and run my custom visitor
        System.out.println("====");
        AntlrVisitor visitor = new AntlrVisitor();
        visitor.visit(tree);
        System.out.println("====");

    }
}


