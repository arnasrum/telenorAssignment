# Largest Consecutive Number Product

## Overview
A Java application that finds the largest product of four consecutive numbers in a 20x20 grid. 

## Assignment Requirements

1. Parse the file into a suitable format
2. Find 4 consecutive numbers that make up the largest product and their indices.

### Alternate Implementation

An alternate branch `assignment-extended` was implemented that has features that go beyond the requirements of this assignment.



## Usage

### Input Format
The input file should contain 20x20 grid of space-seperated integers:
```
14 94 80 21 62 10 ...
57 24 31 68 90 82 ...
...
```

### Running
```bash
javac Main.java
java Main grid.txt
```

### Example Output
```
Max Product: 93168306
row: 15 | column: 18 | value: 98
row: 16 | column: 17 | value: 99
row: 17 | column: 16 | value: 97
row: 18 | column: 15 | value: 99
```

## Project Structure

```
.
├── Main.java           # Entry point for the application
└── src
    ├── Factor.java                         # Record used for displaying factors of the max product
    ├── GridParser.java                     # Helper class for parsing provided grids
    └── LargestProductConsecutive.java      # The main solution for the assignment 
```