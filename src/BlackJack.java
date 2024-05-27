import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;

public class BlackJack {
    //window
    int boardWidth = 600;
    int boardHeight = boardWidth;

    JFrame frame = new JFrame("BlackJack");
    JPanel gamePanel = new JPanel();



    public BlackJack() {
        startGame();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new GridBagLayout());
        gamePanel.setBackground(new Color(0,100,0));
        frame.add(gamePanel);

        addComponentsToPanel();
    }
    public void startGame() {

    }
    private void addComponentsToPanel() {
        // Use a built-in font that resembles Island Moments
        Font titleFont = new Font("Serif", Font.ITALIC, 48);
        Font descriptionFont = new Font("Serif", Font.PLAIN, 24);
        Font buttonFont = new Font("SansSerif", Font.PLAIN, 18);

        // Create a title label
        JLabel titleLabel = new JLabel("Black Jack Deluxe", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.YELLOW);

        // Create a description label
        JLabel descriptionLabel = new JLabel("Choose Your Game Mode", SwingConstants.CENTER);
        descriptionLabel.setFont(descriptionFont);
        descriptionLabel.setForeground(Color.WHITE);

        // Create buttons
        JButton singlePlayerButton = new JButton("Single Player");
        JButton twoPlayersButton = new JButton("Two Players");
        JButton exitButton = new JButton("EXIT");

        // Set button fonts and sizes
        singlePlayerButton.setFont(buttonFont);
        twoPlayersButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);
        singlePlayerButton.setPreferredSize(new Dimension(250, 60));
        twoPlayersButton.setPreferredSize(new Dimension(250, 60));
        exitButton.setPreferredSize(new Dimension(250, 60));

        // Add action listeners to buttons
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start single player game logic here
                JOptionPane.showMessageDialog(frame, "Single Player mode selected!");
            }
        });

        twoPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start two players game logic here
                JOptionPane.showMessageDialog(frame, "Two Players mode selected!");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                System.exit(0);
            }
        });

        // Set up GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 20, 0);

        // Add the title label with padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);  // Adjust the top inset to move the title down
        gamePanel.add(titleLabel, gbc);

        // Add the description label
        gbc.gridy = 1;
        gbc.insets = new Insets(40, 0, 10, 0);  // Add some space above and below
        gamePanel.add(descriptionLabel, gbc);

        // Create a panel for buttons using GridBagLayout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // To keep the background color of the parent panel
        gbc.insets = new Insets(10, 0, 10, 0);  // Add some vertical spacing between buttons

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        gbcButton.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(singlePlayerButton, gbcButton);
        gbcButton.gridy = 1;
        buttonPanel.add(twoPlayersButton, gbcButton);
        gbcButton.gridy = 2;
        buttonPanel.add(exitButton, gbcButton);

        // Add the button panel
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        gamePanel.add(buttonPanel, gbc);
    }
}