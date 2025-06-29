# Sudoku Solver - Java Application

## Overview

This is a Java-based Sudoku solver application that uses the backtracking algorithm to solve Sudoku puzzles. The application features a graphical user interface (GUI) built with Swing, allowing users to:

- Input their own Sudoku puzzles
- Solve puzzles instantly or visualize the solving process
- Clear the board and start over
- See statistics about the solving process

## Features

- **Backtracking Algorithm**: Efficiently solves Sudoku puzzles using recursive backtracking
- **Interactive GUI**: Clean 9x9 grid with clear 3x3 block borders
- **Two Solving Modes**:
  - **Fast Solve**: Solves the puzzle instantly
  - **Visualization Mode**: Shows the solving process step-by-step
- **Visual Feedback**:
  - Original numbers displayed in black
  - Solved numbers displayed in blue
  - Current cell being processed highlighted in green
  - Backtracking steps shown in red
- **Statistics Display**: Shows time taken and number of steps to solve

## Requirements

- Java 8 or higher
- No additional libraries required

## How to Use

1. **Running the Application**:
   - Compile: `javac SudokuSolver.java`
   - Run: `java SudokuSolver`

2. **Inputting a Puzzle**:
   - The application starts with a sample puzzle loaded
   - To enter your own puzzle:
     - Clear the board using the "Clear" button
     - Click on cells and enter numbers (1-9)
     - Use 0 or leave blank for empty cells

3. **Solving the Puzzle**:
   - Click "Solve Fast" to solve the puzzle instantly
   - Click "Visualize" to watch the solving process step-by-step

4. **Clearing the Board**:
   - Click "Clear" at any time to reset the puzzle

## Controls

- **Solve Fast Button**: Solves the puzzle immediately
- **Visualize Button**: Shows the solving process with animations
- **Clear Button**: Resets the entire board

## Implementation Details

- The solver uses a recursive backtracking algorithm
- The GUI is built using Java Swing
- Visualization runs in a separate thread to keep the UI responsive
- Proper grid borders clearly show the 3x3 Sudoku blocks

## License

This project is open-source and available for free use.
