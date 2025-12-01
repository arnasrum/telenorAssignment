# Largest Consecutive Number Product

## Overview
A Java application that finds the largest product of four consecutive numbers in a 20x20 grid. 

## Assignment Requirements

1. Parse the file into a suitable format
2. Find the largest product of 4 consecutive numbers and their
indices

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
    ├── Factor.java              # Record used for displaying factors of the max product
    ├── GridParser.java                     # Helper class for parsing provided grids
    └── LargestProductConsecutive.java      # The main solution for the assignment 
```