package src;

public class LargestProductConsecutive {

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

    public Factor[] getFactors() {
        return this.factors;
    }

    public void reset(int[][] grid) {
        factors = null;
        maxProduct = Long.MIN_VALUE;
        this.grid = grid;
    }

    private enum Direction {
        HORIZONTAL(0, 1),
        VERTICAL(1, 0),
        POSITIVE_DIAGONAL(1, 1),
        NEGATIVE_DIAGONAL(1, -1);

        public final int x, y;
        Direction(int x, int y) {
            this.x = x; 
            this.y = y;
        }
    }

    public long calculate() {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                for(Direction direction : Direction.values()) {
                    long localProduct = directionalProduct(i, j, direction);
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
}