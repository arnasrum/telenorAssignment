import src.interfaces.Solution;
import src.util.GridParser;
import src.util.largestProduct.LargestProductBasic;

import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) {
        String filePath = args[0];
        var grid = GridParser.parseGrid(filePath);
        Solution lpb = new LargestProductBasic(grid, 4);
        System.out.println(String.format("Max Product: %d | row: %d column: %d | factors: %s", lpb.calculate(), lpb.getRow(), lpb.getColumn(), Arrays.toString(lpb.getFactors())));
        System.out.println(String.format("Time taken: %.2f ms", timeFunctionCall(lpb, 15)));
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
