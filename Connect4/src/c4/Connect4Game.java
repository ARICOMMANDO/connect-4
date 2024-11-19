package c4;

import javax.swing.*;

public class Connect4Game {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Connect 4 Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 600);
            frame.setContentPane(new Connect4Panel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
