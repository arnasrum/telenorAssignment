# Largest Consecutive Number Product

## Overview
A Java application that finds the largest product of four consecutive numbers in a 20x20 grid. 

## Alternate Implementation

An alternate branch was implemented that has features that go beyond the requirements of this assignment.

**General Adjacency:** The soluiton is capable of finding the largest product of `k` adjacent numbers, not only consecutive numbers. 

**Multi-threading:** To support efficient computing of large grids, the solution has an option for multi-threading.

**Timing:** The solution has built-in option for timing the solutions.

## Assignment Requirements

1. Parse the file into a suitable format
2. Find 4 consecutive numbers that make up the largest product and their indicies.

## Usage

### Compilation
Compile all Java files using:
```bash
javac *.java
```

### Running the Application
```bash
java Main grid.txt
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