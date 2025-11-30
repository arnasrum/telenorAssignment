package src;

public interface Solution {

    long calculate();
    long calculate(int numThreads);
    void calculate(int start, int end);
    int[] getFactors();
    int getRow();
    int getColumn();
    void reset(int[][] grid);
    
} 