import src.Solution;
import src.util.GridParser;
import src.util.largestProduct.LargestProductBasic;
import src.util.largestProduct.LargestProductFlexible;

import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) {

        System.out.println(Arrays.toString(args));
        Solution solution;
        boolean flexibleMode = false;
        boolean timingEnabled = false;
        String filePath = "";
        int k = 4;
        int numThreads = 1;
        int median = 30;

        for(int i = 0; i < args.length; i++) {
            switch(args[i]) {
                case "-i" -> {
                    filePath = args[++i];
                }
                case "-k" -> {
                    k = Integer.parseInt(args[++i]);
                    if(k < 1) 
                        throw new RuntimeException("the argument k cannot be less than 1");
                    break;
                }
                case "-m" -> {
                    median = Integer.parseInt(args[++i]);
                    if(median < 1) 
                        throw new RuntimeException("the argument m cannot be less than 1");
                    break;
                }
                case "-t" -> {
                    numThreads = Integer.parseInt(args[++i]);
                    if(numThreads < 1) 
                        throw new RuntimeException("the argument \"-t\" cannot be less than 1");
                    break;
                }
                case "--flexible" -> {
                    flexibleMode = true;
                }
                case "--time" -> {
                    timingEnabled = true;
                } 
                default -> {
                    throw new RuntimeException(String.format("The flag \"%s\" is not recognized", args[i]));
                }


            }
        }
        if(filePath.isBlank())
            throw new RuntimeException("please specify the input grid text file path using the \"-i\" flag.");

        var grid = GridParser.parseGrid(filePath);

        if(flexibleMode) {
            solution = new LargestProductFlexible(grid, k);
        } else {
            solution = new LargestProductBasic(grid, k);
        }
        System.out.println(String.format("Max Product: %d | row: %d column: %d | factors: %s", solution.calculate(numThreads), solution.getRow(), solution.getColumn(), Arrays.toString(solution.getFactors())));
        if(timingEnabled)
            System.out.println(String.format("Time taken of %d median runs: %.2f ms", timeFunctionCall(solution, median), median));
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
