// import ANTLR's runtime libraries

import antlr.WaccLexer;
import antlr.WaccParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;

// import antlr package (your code)

public class Main {

    public static void main(String[] args) throws Exception {

        if(args.length == 0) {
            //System.setIn(new FileInputStream("/tmp/prog"));
            System.setIn(new FileInputStream("/home/adam/labs/wacclab/examples/valid/pairs/printNull.wacc"));
            //System.setIn(new FileInputStream("/Users/Dyl/Documents/workspace/WACC-compiler/examples/valid/if/if1.wacc"));
            //System.setIn(new FileInputStream("/Users/cyrusvahidi/WACC-compiler/examples/valid/variables/manyVariables.wacc"));
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

        Arm11Optimiser optimiser = new Arm11Optimiser();
        optimiser.optimise(generator);

        System.out.print(generator.generate());

    }
}


