package src.util.largestProduct;

import java.util.Arrays;

import src.Solution;
import src.util.Direction;
import src.util.SolutionWorker;

public class LargestProductFlexible implements Solution {

    public int x, y;
    public int[] factors;
    int[][] grid;
    int k;
    long maxProduct;
    private int maxAbsValue;


    @Override
    public void reset(int[][] grid) {
        x = 0; y = 0;
        factors = null;
        this.grid = grid;
        computeMaxAbsValue(grid);
    }

    @Override
    public int getRow() {
        return x;
    }

    @Override
    public int getColumn() {
        return y;
    }

    @Override
    public int[] getFactors() {
        return factors;
    }


    public LargestProductFlexible(int[][] grid, int k) {
        this.grid = grid;
        this.k = k;
        this.maxAbsValue = computeMaxAbsValue(grid);
    }

    private int computeMaxAbsValue(int[][] grid) {
        var maxValue = Integer.MIN_VALUE;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(Math.abs(grid[i][j]) > maxValue) {
                    maxValue = Math.abs(grid[i][j]);
                }
            }
        }
        return maxValue;
    }

    @Override
    public long calculate() {
        maxProduct = Long.MIN_VALUE;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                var visited = new int[2 * k];
                var visitedGrid = new boolean[grid.length][grid[0].length];
                Arrays.fill(visited, -1);
                int[] nums = new int[k];
                visitNode(i, j, 1, 0, visited, visitedGrid, nums);
            }
        }
        return this.maxProduct;
    }


    void visitNode(int currentRow, int currentCol, long pathProduct, int depth, int[] visited, boolean[][] visitedGrid, int[] factors) {
        if(currentRow >= grid.length || currentCol < 0 || currentCol >= grid[0].length)
            return;
        if(visitedGrid[currentRow][currentCol])
            return;
        if(pathProduct == 0 && maxProduct >= 0)
            return;
        if(depth >= k) {
            updateMax(currentRow, currentCol, depth, pathProduct, factors);
            return;
        }

        // Prune if impossible to reach maxProduct in this path
        var remainingSteps = k - depth;
        var bestCaseProduct = pathProduct;
        for(int i = 0; i < remainingSteps; i++) {
            bestCaseProduct *= maxAbsValue;
            if(bestCaseProduct > maxProduct) break;
        }
        if(bestCaseProduct <= maxProduct)
            return;

        visited[0 + 2 * depth] = currentRow;
        visited[1 + 2 * depth] = currentCol;
        visitedGrid[currentRow][currentCol] = true;

        factors[depth] = grid[currentRow][currentCol];
        for(Direction direction : Direction.values()) {
            int nextRow = currentRow + direction.x;
            int nextCol = currentCol + direction.y;
            if(nextRow >= grid.length || nextCol < 0 || nextCol >= grid[0].length)
                continue;
            visitNode(nextRow, nextCol, pathProduct * grid[currentRow][currentCol], depth + 1, visited, visitedGrid, factors);
        }

        // Visit other ends of the path to check if larger products are available there
        for(int i = 0; i < depth; i++) {
            var visitedRow = visited[0 + 2 * i];
            var visitedCol = visited[1 + 2 * i];
            for(Direction direction : Direction.values()) {
                int nextRow = visitedRow + direction.x;
                int nextCol = visitedCol + direction.y;
                if(nextRow >= grid.length || nextCol < 0 || nextCol >= grid[0].length)
                    continue;
                visitNode(nextRow, nextCol, pathProduct * grid[currentRow][currentCol], depth + 1, visited, visitedGrid, factors);
            }
        }

        visited[0 + 2 * depth] = -1;
        visited[1 + 2 * depth] = -1;
        visitedGrid[currentRow][currentCol] = false;
    }

    synchronized void updateMax(int row, int col, int depth, long pathProduct, int[] factors) {
        if(depth == k && pathProduct > maxProduct) {
            this.maxProduct = pathProduct;
            this.factors = factors.clone();
            this.x = row; this.y = col;
        }
    }

    @Override
    public long calculate(int numThreads) {
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
    public void calculate(int startRow, int endRow) {
        for(int i = startRow; i < endRow; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                var visited = new int[2 * k];
                var visitedGrid = new boolean[grid.length][grid[0].length];
                Arrays.fill(visited, -1);
                int[] nums = new int[k];
                visitNode(i, j, 1, 0, visited, visitedGrid, nums);
            }
        }
    }
}
