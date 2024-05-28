import java.awt.*;
import javax.swing.*;

public class SinglePlayerMode {
    // Window dimensions
    int boardWidth = 1000;
    int boardHeight = 800;

    // Main frame and panel
    JFrame frame = new JFrame("Single Player Mode");
    JPanel gamePanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw a white horizontal line in the middle
            g.setColor(Color.WHITE);
            int midY = getHeight() / 2;
            g.drawLine(0, midY, getWidth(), midY);
        }
    };

    public SinglePlayerMode() {
        initializeUI();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        gamePanel.setBackground(new Color(0, 100, 0));
        gamePanel.setLayout(null); // Use absolute positioning
        frame.add(gamePanel);

        addComponentsToPanel();
    }

    private void initializeUI() {
        // Initialize UI components if needed
    }

    private void addComponentsToPanel() {
        // Create the Dealer label
        JLabel dealerLabel = new JLabel("Dealer", SwingConstants.CENTER);
        dealerLabel.setForeground(Color.WHITE);
        dealerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        dealerLabel.setBounds(boardWidth / 2 - 50, 8, 100, 30);
        gamePanel.add(dealerLabel);

        // Placeholder for game logic
            //add game logic classes here





        // Player 1 Section
        JLabel playerLabel = new JLabel("Player", SwingConstants.CENTER);
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(new Font("Serif", Font.BOLD, 18));
        playerLabel.setBounds(boardWidth / 2 - 50, boardHeight - 80, 100, 30);
        gamePanel.add(playerLabel);

        JPanel betPanel = new JPanel();
        betPanel.setLayout(new BorderLayout());
        betPanel.setBackground(new Color(0, 100, 0));
        betPanel.setBounds(boardWidth / 2 + 375, boardHeight - 100, 100, 50); // Position the bet panel

        JLabel betLabel = new JLabel("Bet");
        betLabel.setForeground(Color.WHITE);
        betLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField betField = new JTextField();
        betField.setHorizontalAlignment(JTextField.CENTER);

        betPanel.add(betLabel, BorderLayout.NORTH);
        betPanel.add(betField, BorderLayout.CENTER);

        gamePanel.add(betPanel);

        // Create Hit and Stand buttons for Player
        JButton hitButton = new JButton("Hit");
        hitButton.setBounds(boardWidth / 2 - 150, boardHeight - 80, 80, 30);
        hitButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Hit action triggered! (Placeholder)"));
        gamePanel.add(hitButton);

        JButton standButton = new JButton("Stand");
        standButton.setBounds(boardWidth / 2 + 70, boardHeight - 80, 80, 30);
        standButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Stand action triggered! (Placeholder)"));
        gamePanel.add(standButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SinglePlayerMode();
            }
        });
    }
}
