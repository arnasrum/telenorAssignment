package src.util;

import java.util.List;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;


public class GridParser {

    public static int[][] parseGrid(String filePath) throws IOException {


        List<String> lines = Files.readAllLines(Path.of(filePath));
        if(lines.size() < 1) {
            throw new IllegalArgumentException(String.format("The input file is empty"));
        }
        int n = lines.size();
        int m = lines.get(0).split(" ").length;

        int[][] grid = new int[n][m];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                var nums = lines.get(i).split(" ");
                if(nums.length != m)
                    throw new IllegalArgumentException("The provided input file has an inconsistent number of columns");
                grid[i][j] = Integer.parseInt(nums[j]);
            }
        }
        return grid;
    }
}