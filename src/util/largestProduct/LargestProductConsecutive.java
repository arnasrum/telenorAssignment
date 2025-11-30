package src.util.largestProduct;

import src.Solution;
import src.Factor;
import src.util.Direction;
import src.util.concurrent.SolutionWorker;

public class LargestProductConsecutive implements Solution {

    private int[][] grid;
    private int k;
    private Factor[] factors;
    public long maxProduct;

    public LargestProductConsecutive(int[][] grid) {
        this.grid = grid;
        this.k = 4;
        this.factors = new Factor[k];
        this.maxProduct = Long.MIN_VALUE;
    }

    public LargestProductConsecutive(int[][] grid, int k) {
        this.grid = grid;
        this.k = k;
        this.factors = new Factor[k];
        this.maxProduct = Long.MIN_VALUE;
    }

    @Override
    public Factor[] getFactors() {
        return this.factors;
    }

    @Override
    public void reset(int[][] grid) {
        factors = null;
        maxProduct = Long.MIN_VALUE;
        this.grid = grid;
    }

    @Override
    public long calculate() {
        calculate(0, grid.length);
        return maxProduct;
    }

    @Override
    public void calculate(int rowStart, int rowEnd) {
        for(int i = rowStart; i < rowEnd; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                for(Direction direction : Direction.values()) {
                    long localProduct = directionalProduct(i, j, direction);
                    if(localProduct > maxProduct)
                        updateMax(i, j, localProduct, direction);
                }
            }
        }
    }

    private long directionalProduct(int row, int column, Direction direction)  {
        int lastRowElement = row + direction.x * (k - 1);
        int lastColumnElement = column + direction.y * (k - 1);
        if(lastRowElement >= grid.length || lastColumnElement >= grid[0].length || lastColumnElement < 0)
            return Long.MIN_VALUE;
        long localProduct = 1;
        for(int i = 0; i < k; i++)
            localProduct *= grid[row + i * direction.x][column + i * direction.y];
        return localProduct;
    }

    @Override
    public long calculate(int numThreads) {
        if(numThreads < 1) 
            throw new RuntimeException("Number of threads cannot be less than 1");
        if(numThreads == 1) 
            return calculate();

        maxProduct = Long.MIN_VALUE;

        var workers = new Thread[numThreads];
        int section = (grid.length / numThreads);
        for(int i = 0; i < numThreads; i++) {
            int start = i * section;
            int end = (i + 1) * section;
            if(i == numThreads - 1)
                end = grid.length;
            workers[i] = new Thread(new SolutionWorker(this, start, end));
            workers[i].start();
        }
        for(Thread worker: workers) {
            try {
                worker.join();
            } catch (Exception e) {
                e.printStackTrace();
                return Long.MIN_VALUE;
            }
        }
        return maxProduct;
    }

    synchronized void updateMax(int i, int j, long localProduct, Direction direction) {
        if(localProduct > maxProduct) {
            maxProduct = localProduct;
            factors = new Factor[k];
            for(int t = 0; t < k; t++) {
                int row = i + t * direction.x;
                int column = j + t * direction.y; 
                factors[t] = new Factor(row, column, grid[row][column]);
            }
        }
    }
}