import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuSolver extends JFrame {
    private static final int SIZE = 9;
    private static final int CELL_SIZE = 60;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private JButton solveButton, clearButton, visualizeButton;
    private boolean solving = false;
    private int steps = 0;
    private long startTime;

    public SudokuSolver() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SIZE * CELL_SIZE + 50, SIZE * CELL_SIZE + 120);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gridPanel.setBackground(Color.BLACK); // Set background to black for grid lines

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Create the Sudoku grid with proper 3x3 block borders
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                gbc.insets = new Insets(
                    row % 3 == 0 ? 2 : 1, 
                    col % 3 == 0 ? 2 : 1, 
                    row == 8 ? 2 : 1, 
                    col == 8 ? 2 : 1
                );

                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 20));
                cells[row][col].setOpaque(true);
                cells[row][col].setBackground(Color.WHITE);
                
                // Add to a wrapper panel for proper borders
                JPanel cellWrapper = new JPanel(new BorderLayout());
                cellWrapper.setBackground(Color.BLACK);
                cellWrapper.add(cells[row][col]);
                gridPanel.add(cellWrapper, gbc);
            }
        }

        // Create button panel
        JPanel buttonPanel = new JPanel();
        solveButton = new JButton("Solve Fast");
        clearButton = new JButton("Clear");
        visualizeButton = new JButton("Visualize");

        solveButton.addActionListener(e -> solveSudoku(false));
        clearButton.addActionListener(e -> clearGrid());
        visualizeButton.addActionListener(e -> solveSudoku(true));

        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(visualizeButton);

        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Add sample puzzle
        setSamplePuzzle();
    }

    private void setSamplePuzzle() {
        int[][] sample = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (sample[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(sample[row][col]));
                    cells[row][col].setForeground(Color.BLACK);
                } else {
                    cells[row][col].setText("");
                }
            }
        }
    }

    private void clearGrid() {
        if (solving) return;
        
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].setText("");
                cells[row][col].setForeground(Color.BLUE);
                cells[row][col].setBackground(Color.WHITE);
            }
        }
    }

    private void solveSudoku(boolean visualize) {
        if (solving) return;
        
        solving = true;
        steps = 0;
        startTime = System.currentTimeMillis();

        int[][] board = new int[SIZE][SIZE];
        
        // Read the current board
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                try {
                    board[row][col] = cells[row][col].getText().isEmpty() ? 
                            0 : Integer.parseInt(cells[row][col].getText());
                } catch (NumberFormatException e) {
                    board[row][col] = 0;
                }
            }
        }

        // Solve in a separate thread to keep the UI responsive
        new Thread(() -> {
            boolean solved = solve(board, 0, 0, visualize);
            
            SwingUtilities.invokeLater(() -> {
                solving = false;
                if (solved) {
                    long elapsed = System.currentTimeMillis() - startTime;
                    JOptionPane.showMessageDialog(this, 
                            "Solved in " + elapsed + "ms with " + steps + " steps",
                            "Solution Found", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "No solution exists for this puzzle",
                            "No Solution", JOptionPane.ERROR_MESSAGE);
                }
            });
        }).start();
    }

    private boolean solve(int[][] board, int row, int col, boolean visualize) {
        if (row == SIZE) {
            return true; // Reached the end
        }
        
        if (col == SIZE) {
            return solve(board, row + 1, 0, visualize);
        }
        
        if (board[row][col] != 0) {
            return solve(board, row, col + 1, visualize);
        }
        
        for (int num = 1; num <= SIZE; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;
                steps++;
                
                if (visualize) {
                    final int r = row, c = col, n = num;
                    SwingUtilities.invokeLater(() -> {
                        cells[r][c].setText(String.valueOf(n));
                        cells[r][c].setForeground(Color.BLUE);
                        cells[r][c].setBackground(new Color(200, 255, 200)); // Light green
                    });
                    
                    try {
                        Thread.sleep(50); // Slow down for visualization
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                
                if (solve(board, row, col + 1, visualize)) {
                    return true;
                }
                
                board[row][col] = 0;
                
                if (visualize) {
                    final int r = row, c = col;
                    SwingUtilities.invokeLater(() -> {
                        cells[r][c].setText("");
                        cells[r][c].setBackground(new Color(255, 200, 200)); // Light red
                    });
                    
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    
                    SwingUtilities.invokeLater(() -> {
                        cells[r][c].setBackground(Color.WHITE);
                    });
                }
            }
        }
        
        return false;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        // Check row
        for (int c = 0; c < SIZE; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }
        
        // Check column
        for (int r = 0; r < SIZE; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }
        
        // Check 3x3 box
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                if (board[r][c] == num) {
                    return false;
                }
            }
        }
        
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuSolver solver = new SudokuSolver();
            solver.setVisible(true);
        });
    }
}