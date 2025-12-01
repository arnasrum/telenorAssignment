# Largest Consecutive Number Product

## Overview
A Java application that finds the largest product of consecutive numbers in a 2D grid. The application features alternate mode for finding  multi-threading optimization.

## Assignment Requirements
- Given a text containing 20x20 grid, parse it into a suitable format.
- Find the largest product containing 4 consecutive numbers in either horizontal, vertical, or diagonal directions and their indicies.

Although the assignment has given us some constraints, I have chosen to take some creative liberties with the coding and implemented logic fitting for all 2D grids, not only 20x20 and finding the max product of `k` consecutive numbers.

**Note:** An alternate branch `assignment-strict` implements the solution exactly as specified in the requirements. (20x20 grid and exactly 4 consecutive numbers).

## Features
- Parses grid data from `.txt` format files.
- Finds the largest product of k consecutive numbers in horizontal, vertical, and diagonal directions.
- Multi-threading support for improved performance.
- Flexible mode to find max product of any adjacent k numbers.
- Configurable number of consecutive factors and thread count. (The consecutive solution will not get speedups until about 1000x1000 grids).
- Performance timing with median calculation across multiple runs.

## Usage

### Compilation
Compile all Java files using:
```bash
javac -d . *.java
```

### Running the Application
```bash
java Main -i grid.txt
```

### Command Line Arguments

| Argument | Description | Default |
|----------|-------------|---------|
| `-i` | **REQUIRED.** Path to a `.txt` file containing the grid. | — |
| `-k` | Number of consecutive factors that form max product. | 4 |
| `-t` | Number of threads to use. | 1 |
| `-m` | Take median time of num runs. | 7 |
| `--flexible` | Enable finding max product of any adjacent k numbers. | disabled |
| `--time` | Display the median run time across specified runs. | disabled |

## Project Structure
```
.
├── Main.java                          # Entry point of the application
└── src
    ├── Factor.java                    # Represents a number in the grid
    ├── Solution.java                  # Interface for the solutions
    └── util
        ├── concurrent
        │   └── SolutionWorker.java    # Multi-threading worker for parallel processing
        ├── Direction.java             # Enum for search directions (horizontal, vertical, positive diagonal, negative diagonal)
        ├── GridParser.java            # Parses .txt files into 2D grid 
        └── largestProduct
            ├── LargestProductConsecutive.java      # Finds max product of exactly k consecutive numbers
            └── LargestProductFlexible.java         # Finds max product of any adjacent k numbers
```

