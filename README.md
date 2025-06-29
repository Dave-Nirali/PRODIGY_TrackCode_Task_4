**Overview**
This is a Java-based Sudoku solver application that uses the backtracking algorithm to solve Sudoku puzzles. The application features a graphical user interface (GUI) built with Swing, allowing users to:

Input their own Sudoku puzzles
Solve puzzles instantly or visualize the solving process
Clear the board and start over
See statistics about the solving process
Features
Backtracking Algorithm: Efficiently solves Sudoku puzzles using recursive backtracking
Interactive GUI: Clean 9x9 grid with clear 3x3 block borders
Two Solving Modes:
Fast Solve: Solves the puzzle instantly
Visualization Mode: Shows the solving process step-by-step
Visual Feedback:
Original numbers displayed in black
Solved numbers displayed in blue
Current cell being processed highlighted in green
Backtracking steps shown in red
Statistics Display: Shows time taken and number of steps to solve
Requirements
Java 8 or higher
No additional libraries required
How to Use
Running the Application:

Compile: javac SudokuSolver.java
Run: java SudokuSolver
Inputting a Puzzle:

The application starts with a sample puzzle loaded
To enter your own puzzle:
Clear the board using the "Clear" button
Click on cells and enter numbers (1-9)
Use 0 or leave blank for empty cells
Solving the Puzzle:

Click "Solve Fast" to solve the puzzle instantly
Click "Visualize" to watch the solving process step-by-step
Clearing the Board:

Click "Clear" at any time to reset the puzzle
Controls
Solve Fast Button: Solves the puzzle immediately
Visualize Button: Shows the solving process with animations
Clear Button: Resets the entire board
