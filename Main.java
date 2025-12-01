import java.io.IOException;

import src.Factor;
import src.GridParser;
import src.LargestProductConsecutive;

public class Main {
    
    public static void main(String[] args) {

        String filePath = args[0];
        int[][] grid;

        try {
            grid = GridParser.parseGrid(20, 20, filePath);
        } catch(IOException exception) {
            System.err.println("The provided file could not be read.");
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
