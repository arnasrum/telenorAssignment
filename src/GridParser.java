package src;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GridParser {

    public static int[][] parseGrid(int n, int m, String filePath) throws IOException, IllegalArgumentException {


        List<String> lines = Files.readAllLines(Path.of(filePath));
        if(lines.size() != n) {
            throw new IllegalArgumentException(String.format("The amount of rows in the input file is not %d.", n));
        }
        if(lines.get(0).split(" ").length != m) {
            throw new IllegalArgumentException(String.format("The amount of columns in the input file is not %d.", m));
        }

        int[][] grid = new int[n][m];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                var nums = lines.get(i).split(" ");
                grid[i][j] = Integer.parseInt(nums[j]);
            }
        }
        return grid;

    }
}