package src.util.largestProduct;

import src.Solution;
import src.util.Direction;
import src.util.concurrent.SolutionWorker;

public class LargestProductBasic implements Solution {

    int[][] grid;
    int k;
    public int[] factors;
    public int row, column;
    public long maxProduct;

    public LargestProductBasic(int[][] grid) {
        this.grid = grid;
        this.k = 4;
    }

    public LargestProductBasic(int[][] grid, int k) {
        this.grid = grid;
        this.k = k;
    }



    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getColumn() {
        return this.column;
    }

    @Override
    public int[] getFactors() {
        return this.factors;
    }

    @Override
    public void reset(int[][] grid) {
        row = -1;  column = -1;
        factors = null;
        maxProduct = Long.MIN_VALUE;
        this.grid = grid;
    }

    @Override
    public long calculate() {
        maxProduct = Long.MIN_VALUE;
        factors = new int[k];
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                for(Direction direction : Direction.values()) {
                    long localProduct = directionalProduct(i, j, direction);
                    if(localProduct > maxProduct) {
                        maxProduct = localProduct;
                        row = i; column = j;
                        for(int t = 0; t < k; t++)
                            factors[t] = grid[i + t * direction.x][j + t * direction.y];
                    }
                }
            }
        }
        return maxProduct;
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
        factors = new int[k];

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

    @Override
    public void calculate(int start, int end) {
        for(int i = start; i < end; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                for(Direction direction : Direction.values()) {
                    long localProduct = directionalProduct(i, j, direction);
                    if(localProduct > maxProduct)
                        updateMax(i, j, localProduct, direction);
                }
            }
        }
    }

    synchronized void updateMax(int i, int j, long localProduct, Direction direction) {
        maxProduct = localProduct;
        row = i; column = j;
        for(int t = 0; t < k; t++)
            factors[t] = grid[i + t * direction.x][j + t * direction.y];
    }


}