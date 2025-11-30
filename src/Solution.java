package src;

public interface Solution {

    long calculate();
    long calculate(int numThreads);
    void calculate(int rowStart, int rowEnd);
    Factor[] getFactors();
    void reset(int[][] grid);
    
} 