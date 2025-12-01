import src.Factor;
import src.util.GridParser;
import src.util.LargestProductConsecutive;

import java.io.File;

public class Main {
    
    public static void main(String[] args) {

        String filePath = args[0];

        File file = new File(filePath);
        if(!file.exists() || !file.canRead()) {
            throw new RuntimeException("Please provide a valid grid file.");
        }

        var grid = GridParser.parse20x20Grid(filePath);

        var solution = new LargestProductConsecutive(grid);

        System.out.println(String.format("Max Product: %d", solution.calculate()));
        for(Factor factor : solution.getFactors()) {
            System.out.println(factor.toString());
        }
    }


}
