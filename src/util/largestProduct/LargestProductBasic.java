package src.util.largestProduct;

import src.interfaces.Solution;

public class LargestProductBasic implements Solution {

    int[][] grid;
    int k;
    public int[] factors;
    public int x, y;
    public long product;

    public LargestProductBasic(int[][] grid) {
        this.grid = grid;
        this.k = 4;
    }

    public LargestProductBasic(int[][] grid, int k) {
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

    @Override
    public int getRow() {
        return this.x;
    }

    @Override
    public int getColumn() {
        return this.y;
    }

    @Override
    public int[] getFactors() {
        return this.factors;
    }

    @Override
    public void reset() {
        x = -1; y = -1;
        factors = null;
        product = Long.MIN_VALUE;
    }

    public long calculateOld() {
        product = Long.MIN_VALUE;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                var horizontalValue = horizontalProduct(i, j, k);
                var verticalValue = verticalProduct(i, j, k);
                var positiveDiagonalValue = positiveDiagonalProduct(i, j, k);
                var negativeDiagonalValue = negativeDiagonalProduct(i, j, k);

                updateMax(horizontalValue, i, j, "horizontal");
                updateMax(verticalValue, i, j, "vertical");
                updateMax(positiveDiagonalValue, i, j, "positiveDiagonal");
                updateMax(negativeDiagonalValue, i, j, "negativeDiagonal");
            }
        }
        return product;
    }

    @Override
    public long calculate() {
        product = Long.MIN_VALUE;
        factors = new int[k];
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                for(Direction direction : Direction.values()) {
                    long localProduct = directionalProduct(i, j, direction);
                    if(localProduct > product) {
                        product = localProduct;
                        x = i; y = j;
                        for(int t = 0; t < k; t++)
                            factors[t] = grid[i + t * direction.x][j + t * direction.y];
                    }
                }
            }
        }
        return product;
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

    private long horizontalProduct(int row, int column, int k) {
        if(column + k > grid[0].length)
            return Long.MIN_VALUE;
        long product = 1;
        for(int i = 0; i < k; i++)
            product *= grid[row][column + i];
        return product;
    }

    private long verticalProduct(int row, int column, int k) {
        if(row + k > grid.length)
            return Long.MIN_VALUE;
        long product = 1;
        for(int i = 0; i < k; i++)
            product *= grid[row + i][column];
        return product;
    }

    private long positiveDiagonalProduct(int row, int column, int k) {
        if(column + k > grid[0].length || row + k > grid.length)
            return Long.MIN_VALUE;
        long product = 1;
        for(int i = 0; i < k; i++)
            product *= grid[row + i][column + i];
        return product;
    }

    private long negativeDiagonalProduct(int row, int column, int k) {
        if(column < k - 1 || row + k > grid.length) 
            return Long.MIN_VALUE;
        long product = 1;
        for(int i = 0; i < k; i++)
            product *= grid[row + i][column - i];
        return product;
    }

    private void updateMax(long value, int row, int column, String direction) {
        if(value > product) {
            product = value;
            getFactors(row, column, k, direction);
            x = row;
            y = column;
        }
    }

    private void getFactors(int row, int column, int k, String direction) {
        factors = new int[k];
        for(int i = 0; i < k; i++) {
            switch(direction) {
                case "horizontal" -> factors[i] = grid[row][column + i];
                case "vertical" -> factors[i] = grid[row + i][column];
                case "positiveDiagonal" -> factors[i] = grid[row + i][column + i];
                case "negativeDiagonal" -> factors[i] = grid[row + i][column - i];
            }
        }
    }
    
}