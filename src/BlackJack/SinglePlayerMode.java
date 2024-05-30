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
    private boolean dealerWon = false, draw = false;
    private boolean playerWon = false;
    private Player player;
    private Dealer dealer;
    private boolean playerTurnOver = false;
    private int playerBet = 0;
    JPanel gamePanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if(dealerWon){
                g.setColor(Color.RED);
                g.setFont(new Font(Font.SERIF, Font.BOLD,50));
                g.drawString("BUSTED", boardWidth/2-150,boardHeight/2-10);
            }
            if(playerWon){
                g.setColor(Color.YELLOW);
                g.setFont(new Font(Font.SERIF, Font.BOLD,50));
                g.drawString("YOU WON", boardWidth/2-150,boardHeight/2-10);
            }

            if(draw){
                g.setColor(Color.BLUE);
                g.setFont(new Font(Font.SERIF, Font.BOLD,50));
                g.drawString("DRAW", boardWidth/2-150,boardHeight/2-10);
            }

            //Draw a white horizontal line in the middle
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
        StateHandler.currentState = States.SINGLE_PLAYER;

        player = new Player(1,100,450,0.25);
        dealer = new Dealer(50,50,0.25);

        dealer.flipMainDeck();
        dealer.shufflePlayingDeck();

        Card toDeal = dealer.dealcard();
        toDeal.flip();
        dealer.addCardToDealerHand(toDeal);
        dealer.addCardToDealerHand(dealer.dealcard());

        player.hit(dealer.dealcard());
        player.hit(dealer.dealcard());

        StateHandler.update();
    }

    private void addComponentsToPanel() {
        // Create the Dealer label
        JLabel dealerLabel = new JLabel("Dealer", SwingConstants.CENTER);
        dealerLabel.setForeground(Color.WHITE);
        dealerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        dealerLabel.setBounds(boardWidth / 2 - 50, 8, 100, 30);
        gamePanel.add(dealerLabel);

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
        betField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player.getChipBalance() == 0) {
                    betField.setEnabled(false);
                    return;
                }
                int betAmount = Integer.parseInt(betField.getText());
                playerBet = betAmount;

                StateHandler.update();
                player.placeBet(betAmount);
                betField.setEnabled(false);
                StateHandler.update();
            }
        });

        betPanel.add(betLabel, BorderLayout.NORTH);
        betPanel.add(betField, BorderLayout.CENTER);

        gamePanel.add(betPanel);

        // Create Hit and Stand buttons for Player
        JButton hitButton = new JButton("Hit");
        hitButton.setBounds(boardWidth / 2 - 150, boardHeight - 80, 80, 30);
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if player card sum is over 21
                if(!player.isPlaying()) {
                    hitButton.setEnabled(false);
                    betField.setEnabled(false);
                    return;
                }
                player.hit(dealer.dealcard());
                StateHandler.update();
            }
        });
        gamePanel.add(hitButton);

        JButton standButton = new JButton("Stand");
        standButton.setBounds(boardWidth / 2 + 70, boardHeight - 80, 80, 30);
        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dealer.flipHiddenCard();
                // Mark the player's turn as over
                playerTurnOver = true;

                // Dealer's turn logic
                while (dealer.isPlaying()) {
                    Card toDeal = dealer.dealcard();
                    // Deck testDeck = dealer.getDealerHand();
                    //testDeck.AddCard(toDeal);
                    dealer.addCardToDealerHand(toDeal);
                    StateHandler.update();
                    // Check if dealer has reached the threshold (usually 17)
                }
                int playerDistanceFrom21 = Math.abs(player.reducedPlayerAceSum() - 21);
                int dealerDistanceFrom21 = Math.abs(dealer.reducedDealerAceSum() - 21);

                if(!player.isPlaying()){
                    //player has card sum over 21
                    dealerWon = true;
                }else if(dealer.getDealerLost()){
                    //dealer has card sum over 21
                    playerWon = true;
                    player.setNumOfChips(player.getChipBalance()+2* playerBet);
                    StateHandler.update();
                } else if (playerDistanceFrom21 == dealerDistanceFrom21){
                    //player and dealer has same sum
                    draw = true;
                    player.setNumOfChips(player.getChipBalance()+ playerBet);
                    StateHandler.update();
                }else if(playerDistanceFrom21 < dealerDistanceFrom21){
                    //player closer to 21
                    playerWon = true;
                    player.setNumOfChips(player.getChipBalance()+2* playerBet);
                    StateHandler.update();
                }else if(playerDistanceFrom21 > dealerDistanceFrom21){
                    //dealer closer to 21
                    dealerWon = true;
                }

                // Disable the stand and hit buttons after standing
                standButton.setEnabled(false);
                hitButton.setEnabled(false);
                betField.setEnabled(false);
                StateHandler.update();
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