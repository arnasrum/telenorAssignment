package src.util.largestProduct;

import src.interfaces.Solution;

import java.util.Arrays;

public class LargestProductFlexible implements Solution {

    public int x, y;
    public int[] factors;
    int[][] grid;
    int k;
    long maxProduct;
    private int maxAbsValue;


    @Override
    public void reset() {
        x = 0; y = 0;
        factors = null;
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

    static private enum Direction {
        HORIZONTAL(0, 1),
        VERTICAL(1, 0),
        POSITIVE_DIAGONAL(1, 1),
        NEGATIVE_DIAGONAL(1, -1);

        final int x, y;
        Direction(int x, int y) {
            this.x = x; 
            this.y = y;
        }
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
    
    void visitNode(int currentRow, int currentCol, long pathProduct, int depth, int[] visited, boolean[][] visitedGrid, int[] factors) {
        if(currentRow >= grid.length || currentCol < 0 || currentCol >= grid[0].length)
            return;
        if(visitedGrid[currentRow][currentCol])
            return;
        if(pathProduct == 0 && maxProduct >= 0)
            return;
        if(depth >= k) {
            if(depth == k && pathProduct > maxProduct) {
                this.maxProduct = pathProduct;
                this.factors = factors.clone();
                this.x = currentRow; this.y = currentCol;
            }
            return;
        }

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
}