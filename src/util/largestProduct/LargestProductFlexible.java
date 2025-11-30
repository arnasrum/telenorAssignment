package src.util.largestProduct;

import src.interfaces.Solution;

public class LargestProductFlexible implements Solution {

    public int x, y;
    public int[] factors;
    int[][] grid;
    int k;
    long maxProduct;


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
    }

    private enum Direction {
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
    
    void visitNode(int currentRow, int currentCol, long pathProduct, int depth, boolean[][] visited, int[] factors) {
        if(currentRow >= grid.length || currentCol < 0 || currentCol >= grid[0].length)
            return;
        if(visited[currentRow][currentCol]) {
            return;
        }
        if(depth >= k) {
            if(depth == k && pathProduct > maxProduct) {
                this.maxProduct = pathProduct;
                this.factors = factors.clone();
                this.x = currentRow; this.y = currentCol;
            }
            return;
        }
        visited[currentRow][currentCol] = true;
        factors[depth] = grid[currentRow][currentCol];
        for(Direction direction : Direction.values()) {
            int nextRow = currentRow + direction.x;
            int nextCol = currentCol + direction.y;
            if(nextRow >= grid.length || nextCol < 0 || nextCol >= grid[0].length)
                continue;
            visitNode(nextRow, nextCol, pathProduct * grid[currentRow][currentCol], depth + 1, visited, factors);
        }

        for(int i = 0; i < visited.length; i++) {
            for(int j = 0; j < visited[0].length; j++) {
                if(!visited[i][j]) continue;
                for(Direction direction : Direction.values()) {
                    int nextRow = i + direction.x;
                    int nextCol = j + direction.y;
                    if(nextRow >= grid.length || nextCol < 0 || nextCol >= grid[0].length)
                        continue;
                    visitNode(nextRow, nextCol, pathProduct * grid[currentRow][currentCol], depth + 1, visited, factors);
                }
            }
        }
        visited[currentRow][currentCol] = false;
    }

    @Override
    public long calculate() {
        maxProduct = Long.MIN_VALUE;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                var visited = new boolean[grid.length][grid[0].length];
                int[] nums = new int[k];
                visitNode(i, j, 1, 0, visited, nums);
            }
        }
        return this.maxProduct;
    }
}