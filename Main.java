import java.io.IOException;

import src.Factor;
import src.GridParser;
import src.LargestProductConsecutive;

public class Main {
    
    public static void main(String[] args) {

        if(args.length < 1) {
            throw new IllegalArgumentException("Please specify the input file as the first positional argument.");
        }
        String filePath = args[0];
        int[][] grid;

        try {
            grid = GridParser.parseGrid(20, 20, filePath);
        } catch(IOException exception) {
            exception.printStackTrace();
            return;
        }

        var solution = new LargestProductConsecutive(grid);

        System.out.println(String.format("Max Product: %d", solution.calculate()));
        for(Factor factor : solution.getFactors()) {
            System.out.println(factor.toString());
        }
    }


}
