package src.util;

import java.util.Scanner;
import java.io.File;

public class GridParser {

    public static int[][] parse20x20Grid(String filePath) {

        var file = new File(filePath);
        try {
            int rowCount = 20;
            int columnCount = 20;
            
            var reader = new Scanner(file);
            int[][] grid = new int[rowCount][columnCount];
            int currentRow = 0;
            while(reader.hasNextLine()) {
                var nums = reader.nextLine().split(" ");
                for(int i = 0; i < columnCount; i++)
                    grid[currentRow][i] = Integer.parseInt(nums[i]);
                currentRow++;
            }
            reader.close();
            return grid;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}