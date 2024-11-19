package c4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connect4Panel extends JPanel {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private JButton[][] buttons;
    private int[][] owner;
    private int currentPlayer;

    public Connect4Panel() {
        setLayout(new GridLayout(ROWS + 1, COLUMNS));
        buttons = new JButton[ROWS][COLUMNS];
        owner = new int[ROWS][COLUMNS];
        currentPlayer = 1;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setBackground(Color.BLACK);
                buttons[row][col].setOpaque(true);
                buttons[row][col].setBorderPainted(true);
                buttons[row][col].addActionListener(new ButtonClickListener(col));
                add(buttons[row][col]);
            }
        }

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame());
        add(resetButton);
    }

    private void resetGame() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                buttons[row][col].setBackground(Color.BLACK);
                owner[row][col] = 0;
            }
        }
        currentPlayer = 1;
    }

    private class ButtonClickListener implements ActionListener {
        private int column;

        public ButtonClickListener(int column) {
            this.column = column;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = dropDisc(column);
            if (row != -1) {
                if (currentPlayer == 1) {
                    buttons[row][column].setBackground(Color.CYAN);
                } else {
                    buttons[row][column].setBackground(Color.RED);
                }

                if (checkWin(row, column)) {
                    JOptionPane.showMessageDialog(Connect4Panel.this, "Player " + currentPlayer + " wins!");
                    resetGame();
                } else {
                    currentPlayer = (currentPlayer == 1) ? 2 : 1;
                }
            }
        }
    }

    private int dropDisc(int column) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (owner[row][column] == 0) {
                owner[row][column] = currentPlayer;
                return row;
            }
        }
        return -1;
    }

    private boolean checkWin(int row, int col) {
        return (checkDirection(row, col, 0, 1) ||
                checkDirection(row, col, 1, 0) ||
                checkDirection(row, col, 1, 1) ||
                checkDirection(row, col, -1, 1));
    }

    private boolean checkDirection(int row, int col, int rowIncrement, int colIncrement) {
        int count = 1;

        for (int i = 1; i < 4; i++) {
            int newRow = row + i * rowIncrement;
            int newCol = col + i * colIncrement;

            if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLUMNS
                    && owner[newRow][newCol] == currentPlayer) {
                count++;
            } else {
                break;
            }
        }

        for (int i = 1; i < 4; i++) {
            int newRow = row - i * rowIncrement;
            int newCol = col - i * colIncrement;

            if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLUMNS
                    && owner[newRow][newCol] == currentPlayer) {
                count++;
            } else {
                break;
            }
        }

        return count >= 4;
    }
}
