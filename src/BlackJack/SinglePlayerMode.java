package BlackJack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayerMode {
    // Window dimensions
    int boardWidth = 1000;
    int boardHeight = 800;

    // Main frame and panel
    JFrame frame = new JFrame("Single Player Mode");
    private boolean gameEnded = false;
    private boolean playerWon = false;
    private Player player = new Player(1,new Deck(),1000);
    private Dealer dealer = new Dealer();
    JPanel gamePanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw a white horizontal line in the middle
            if(gameEnded) {
                g.setColor(Color.RED);
                g.setFont(new Font(Font.SERIF, Font.BOLD,50));
                g.drawString("GAME OVER", boardWidth/2-150,boardHeight/2);
                return;
            }
            if(playerWon){
                g.setColor(Color.YELLOW);
                g.setFont(new Font(Font.SERIF, Font.BOLD,50));
                g.drawString("YOU WON", boardWidth/2-150,boardHeight/2);
            }
            g.setColor(Color.WHITE);
            int midY = getHeight() / 2;
            g.drawLine(0,boardHeight/2,boardWidth,boardHeight/2);

            StateHandler.render(g);

            repaint();
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
        dealer.flipMainDeck();
        dealer.shufflePlayingDeck();

        Card toDeal = dealer.dealcard();
        toDeal.flip();
        dealer.addCardToDealersDeck(toDeal);
        dealer.addCardToDealersDeck(dealer.dealcard());

        player.hit(dealer.dealcard());
        player.hit(dealer.dealcard());

        StateHandler.currentState = States.SINGLE_PLAYER;
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
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start single player game logic here

                //TODO:Fix game logic so no bugs when enforcing the rule of the game
                Card toDeal = dealer.dealcard();
                Deck testDeck = player.getPlayerHand();
                testDeck.AddCard(toDeal);
                //checks if
                if(toDeal.isAce()){
                    testDeck.AddCard(toDeal);
                    if(testDeck.getSum() > 21){
                        testDeck.setCard(testDeck.Size()-1,new Card(toDeal.getCardVal(),toDeal.getSuit(),1));
                    }else{
                        testDeck.setCard(testDeck.Size()-1,new Card(toDeal.getCardVal(),toDeal.getSuit(),11));
                    }
                }

                if((testDeck.getSum() > 21) && dealer.isPlaying() == false){
                    gameEnded = true;
                    return;
                }else if((testDeck.getSum() > 21)){
                    hitButton.setEnabled(false);
                    return;
                }else if(testDeck.getSum() == 21){
                    playerWon = true;
                    hitButton.setEnabled(false);
                    return;
                }

                player.hit(toDeal);
            }
        });
        gamePanel.add(hitButton);

        JButton standButton = new JButton("Stand");
        standButton.setBounds(boardWidth / 2 + 70, boardHeight - 80, 80, 30);
        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start single player game logic here

                //TODO:Fix game logic so no bugs when enforcing the rule of the game
                Card toDeal = dealer.dealcard();
                Deck testDeck = dealer.getDealerHand();
                testDeck.AddCard(toDeal);
                //checks if adding this card will make dealer go above 14
                if(!dealer.isPlaying()){
                    return;
                }
                dealer.addCardToDealersDeck(toDeal);
            }
        });
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