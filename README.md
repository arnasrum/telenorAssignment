# Largest Consecutive Number Product

## Overview
A Java application that finds the largest product of k consecutive numbers in a 2D grid, with support for multi-threading and flexible adjacency search.

## Assignment Requirements
- Given a text containing 20x20 grid, parse it into a suitable format.
- Find the largest product containing 4 consecutive numbers in either horizontal, vertical, or diagonal directions and their indices.

Although the assignment has given us some constraints, I have chosen to take some creative liberties with the coding and implemented logic fitting for all 2D grids, not only 20x20 and finding the max product of `k` consecutive numbers.

**Note:** An alternate branch `assignment-strict` implements the solution exactly as specified in the requirements. (20x20 grid and exactly 4 consecutive numbers).

## Features
- Parses grid data from `.txt` format files.
- Finds the largest product of k consecutive numbers in horizontal, vertical, and diagonal directions.
- Multi-threading support for improved performance.
- Flexible mode to find max product of any adjacent k numbers.
- Configurable number of consecutive factors and thread count. 
- Performance timing with median calculation across multiple runs.

## Modes

### Consecutive Mode (default)
Searches for `k` numbers in straight lines: horizontal, vertical, or diagonal.

### Flexible Mode (`--flexible`)
Searches for any `k` adjacent numbers sharing an edge or corner in the grid, including non-linear patterns. 

## Usage

Developed with Java 21 (earlier versions might work).

### Input Format
The input file should contain any complete (n x m) grid of space-separated integers:
```
14 94 80 21 62 10 ...
57 24 31 68 90 82 ...
...
```

### Running
```bash
javac Main.java
java Main -i grid.txt
```

### Example Output
```
Max Product: 93168306
row: 15 | column: 18 | value: 98
row: 16 | column: 17 | value: 99
row: 17 | column: 16 | value: 97
row: 18 | column: 15 | value: 99
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
├── Main.java                               # Entry point of the application
└── src
    ├── Factor.java                         # Represents a number in the grid 
    ├── LargestProductConsecutive.java      # Finds max product of k consecutive numbers
    ├── LargestProductFlexible.java         # Finds max product of any adjacent k numbers
    ├── Solution.java                       # Interface for the solutions
    └── util
        ├── Direction.java          # Enum for search directions (horizontal, vertical, positive diagonal, negative diagonal)
        ├── GridParser.java         # Parses .txt files into 2D grid
        └── SolutionWorker.java     # Multi-threading worker for parallel computation
```