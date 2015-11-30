// import ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

// import antlr package (your code)
import antlr.*;

import java.io.FileInputStream;

public class Main {

    public static void main(String[] args) throws Exception {

        if(args.length == 0) {
            System.setIn(new FileInputStream("/Users/cyrusvahidi/wacc_examples/valid/array/arrayLength.wacc"));
            //System.setIn(new FileInputStream("/homes/ah3114/work/wacc/examples/valid/advanced/ticTacToe.wacc"));
        }

        // create a lexer that feeds off of input from System.in
        WaccLexer lexer = new WaccLexer(new ANTLRInputStream(System.in));

        // create a parser that feeds off the tokens buffer from the lexer
        WaccParser parser = new WaccParser(new CommonTokenStream(lexer));
        parser.setErrorHandler(new WaccParserErrorHandler());

        // begin parsing at "prog" antlr rule
        ParseTree tree = parser.prog();

        // run the visitor
        WaccSyntaxAnalyser analyser = new WaccSyntaxAnalyser();
        analyser.visit(tree);

        // run the generator
        WaccArm11Generator generator = new WaccArm11Generator();
        generator.setSymbolTable(analyser.getSymbolTable());
        generator.visit(tree);

        System.out.print(generator.generate());

    }
}


