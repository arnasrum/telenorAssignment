import src.interfaces.Solution;
import src.util.GridParser;
import src.util.largestProduct.LargestProductBasic;
import src.util.largestProduct.LargestProductFlexible;

import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) {
        String filePath = args[0];
        var grid = GridParser.parseGrid(filePath);
        Solution basic = new LargestProductBasic(grid, 4);
        Solution flex = new LargestProductFlexible(grid, 4);
        System.out.println(String.format("Max Product: %d | row: %d column: %d | factors: %s", basic.calculate(), basic.getRow(), basic.getColumn(), Arrays.toString(basic.getFactors())));
        System.out.println(String.format("Flex Max Product: %d | row: %d column: %d | factors: %s", flex.calculate(), flex.getRow(), flex.getColumn(), Arrays.toString(flex.getFactors())));
        System.out.println(String.format("Basic Time taken: %.2f ms", timeFunctionCall(basic, 15)));
        System.out.println(String.format("Flexible Time taken: %.2f ms", timeFunctionCall(flex, 15)));
    }

    static double timeFunctionCall(Solution obj, int numMedian) {
        long[] times = new long[numMedian];
        for(int i = 0; i < numMedian; i++) {
            var start = System.nanoTime();
            obj.calculate();
            var end = System.nanoTime();
            times[i] = (end - start) / 1000; 
        }
        Arrays.sort(times);
        var medianTime = times[times.length / 2];
        return medianTime;
    }
}
