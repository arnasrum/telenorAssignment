import src.Factor;
import src.LargestProductConsecutive;
import src.LargestProductFlexible;
import src.Solution;
import src.util.GridParser;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) {

        Solution solution;
        boolean flexibleMode = false;
        boolean timingEnabled = false;
        String filePath = "";
        int k = 4;
        int numThreads = 1;
        int median = 7;

        for(int i = 0; i < args.length; i++) {
            switch(args[i]) {
                case "-i" -> {
                    filePath = args[++i];
                }
                case "-k" -> {
                    k = Integer.parseInt(args[++i]);
                    if(k < 1) 
                        throw new IllegalArgumentException("The argument \"-k\" cannot be less than 1");
                    break;
                }
                case "-m" -> {
                    median = Integer.parseInt(args[++i]);
                    if(median < 1) 
                        throw new IllegalArgumentException("The argument \"-m\" cannot be less than 1");
                    break;
                }
                case "-t" -> {
                    numThreads = Integer.parseInt(args[++i]);
                    if(numThreads < 1) 
                        throw new IllegalArgumentException("The argument \"-t\" cannot be less than 1");
                    break;
                }
                case "--flexible" -> {
                    flexibleMode = true;
                }
                case "--time" -> {
                    timingEnabled = true;
                } 
                default -> {
                    throw new IllegalArgumentException(String.format("The flag \"%s\" is not recognized", args[i]));
                }
            }
        }
        if(filePath.isBlank())
            throw new IllegalArgumentException("Please specify the input grid text file path using the \"-i\" flag.");

        int[][] grid;
        try {
            grid = GridParser.parseGrid(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if(flexibleMode) {
            solution = new LargestProductFlexible(grid, k);
        } else {
            solution = new LargestProductConsecutive(grid, k);
        }

        System.out.println(String.format("Max Product: %d", solution.calculate(numThreads)));
        for(Factor factor : solution.getFactors()) {
            System.out.println(factor.toString());
        }

        if(timingEnabled)
            System.out.println(String.format("Median time taken of %d runs: %.0f ms", median, timeFunctionCall(solution, median, numThreads)));
    }

    static double timeFunctionCall(Solution obj, int numMedian, int threads) {
        long[] times = new long[numMedian];
        for(int i = 0; i < numMedian; i++) {
            var start = System.nanoTime();
            obj.calculate(threads);
            var end = System.nanoTime();
            times[i] = (end - start) / 1000; 
        }
        Arrays.sort(times);
        var medianTime = times[times.length / 2];
        return medianTime;
    }
}
