package src.util;

import java.util.Scanner;
import java.io.File;

public class GridParser {

    public static int[][] parseGrid(String filePath) {

        var file = new File(filePath);
        try {
            var fileReader = new Scanner(file);
            int rowCount = 0;
            int columnCount = 0;
            while(fileReader.hasNextLine()) {
                if(columnCount == 0) {
                    columnCount = fileReader.nextLine().split(" ").length;
                } else {
                    fileReader.nextLine();
                }
                rowCount++;
            }
            fileReader.close();
            
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
            e.printStackTrace();
            return null;
        }
    }
}