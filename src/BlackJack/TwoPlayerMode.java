package BlackJack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TwoPlayerMode {
    // Window dimensions
    int boardWidth = 1000;
    int boardHeight = 800;

    // Main frame and panel
    JFrame frame = new JFrame("Two Player Mode");
    private Player player1;
    private Player player2;
    private Dealer dealer;
    private boolean gameOver = false, player1Finsihed = false, player2Finsihed = false;
    private boolean player1Won,player2Won,dealerWon,draw, player1Busted, player2Busted;
    private int playerBet1,playerBet2;

    JPanel gamePanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw a white horizontal line in the middle
            g.setColor(Color.WHITE);
            int midY = getHeight() / 2;
            g.drawLine(0, midY, getWidth(), midY);

            // Draw a white vertical line in the middle of the bottom half
            int midX = getWidth() / 2;
            g.drawLine(midX, midY, midX, getHeight());

            int dealerStatusX = boardWidth/2 - 75,dealerStatusY = boardHeight/2 - 50;
            int playerOneX = boardWidth / 2 - 350,playerOneY = boardHeight / 2 + 260;
            int playerTwoX = boardWidth / 2 + 150,playerTwoY = boardHeight / 2 + 260;
            if(gameOver){
                if(player1Busted){
                    //player 1 got over 21
                    g.setColor(Color.RED);
                    g.setFont(new Font(Font.SERIF, Font.BOLD, 25));
                    g.drawString("PLAYER ONE BUSTED", playerOneX, playerOneY);
                }
                if(player2Busted){
                    //player 2 got over 21
                    g.setColor(Color.RED);
                    g.setFont(new Font(Font.SERIF, Font.BOLD, 25));
                    g.drawString("PLAYER TWO BUSTED", playerTwoX, playerTwoY);
                }
                if (dealerWon) {
                    //both players lost
                    g.setColor(Color.RED);
                    g.setFont(new Font(Font.SERIF, Font.BOLD, 25));
                    g.drawString("PLAYER 1 LOST", playerOneX, playerOneY);
                    g.drawString("PLAYER 2 LOST", playerTwoX, playerTwoY);
                    g.setColor(Color.BLUE);
                    g.drawString("DEALER WON", dealerStatusX, dealerStatusY);
                }
                if(!player1Busted){
                    if (player1Won) {
                        // player 2 lost
                        g.setColor(Color.GREEN);
                        g.setFont(new Font(Font.SERIF, Font.BOLD, 25));
                        g.drawString("PLAYER 1 WON", playerOneX, playerOneY);
                    } else {
                        g.setColor(Color.RED);
                        g.setFont(new Font(Font.SERIF, Font.BOLD, 25));
                        g.drawString("PLAYER 1 LOST", playerOneX, playerOneY);
                    }
                }
                if(!player2Busted){
                    if (player2Won) {
                        //player 1 lost
                        g.setColor(Color.GREEN);
                        g.setFont(new Font(Font.SERIF, Font.BOLD, 25));
                        g.drawString("PLAYER 2 WON", playerTwoX, playerTwoY);
                    } else {
                        g.setColor(Color.RED);
                        g.setFont(new Font(Font.SERIF, Font.BOLD, 25));
                        g.drawString("PLAYER 2 LOST", playerTwoX, playerTwoY);
                    }
                }
                if (draw) {
                    //all three tied
                    g.setColor(Color.ORANGE);
                    g.setFont(new Font(Font.SERIF, Font.BOLD, 25));
                    g.drawString("PUSH", boardWidth / 2 - 150, boardHeight / 2 - 50);
                }

                JButton playAgain = new JButton("Play Again");
                playAgain.setBackground(Color.WHITE);
                playAgain.setBorderPainted(true);
                playAgain.setBounds(boardWidth / 2 - 50, boardHeight/2 - 35, 100, 50);
                gamePanel.add(playAgain);

            }

            StateHandler.render(g);
            repaint();
        }
    };

    public TwoPlayerMode() {
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
        StateHandler.currentState = States.TWO_PLAYER;

        player1 = new Player(1,30,420,0.15);
        player2 = new Player(2,540,420,0.15);
        dealer = new Dealer(150,50,0.25);

        dealer.flipMainDeck();
        dealer.shufflePlayingDeck();

        Card toDeal = dealer.dealcard();
        toDeal.flip();
        dealer.addCardToDealerHand(toDeal);
        dealer.addCardToDealerHand(dealer.dealcard());

        player1.hit(dealer.dealcard());
        player1.hit(dealer.dealcard());

        player2.hit(dealer.dealcard());
        player2.hit(dealer.dealcard());
    }

    private void addComponentsToPanel() {
        //play again button
        if(gameOver){
            JButton playAgain = new JButton("Play Again");
            playAgain.setBounds(boardWidth / 2 - 70, boardHeight - 130, 80, 30);
            gamePanel.add(playAgain);
        }

        // Create the Dealer label
        JLabel dealerLabel = new JLabel("Dealer", SwingConstants.CENTER);
        dealerLabel.setForeground(Color.WHITE);
        dealerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        dealerLabel.setBounds(boardWidth / 2 - 50, 8, 100, 30);
        gamePanel.add(dealerLabel);

        // Create the title and Bet box for Player 1
        JLabel player1Label = new JLabel("Player 1", SwingConstants.CENTER);
        player1Label.setForeground(Color.WHITE);
        player1Label.setFont(new Font("Serif", Font.BOLD, 18));
        player1Label.setBounds(boardWidth / 4 - 50, boardHeight - 130, 100, 30);
        gamePanel.add(player1Label);

        JPanel betPanel1 = new JPanel();
        betPanel1.setLayout(new BorderLayout());
        betPanel1.setBackground(new Color(0, 100, 0));
        betPanel1.setBounds(boardWidth / 4 - 50, boardHeight - 100, 100, 50); // Position the bet panel

        JLabel betLabel1 = new JLabel("Bet");
        betLabel1.setForeground(Color.WHITE);
        betLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField betField1 = new JTextField();
        betField1.setHorizontalAlignment(JTextField.CENTER);
        betField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player1.getChipBalance() == 0) {
                    betField1.setEnabled(false);
                    return;
                }
                int betAmount = Integer.parseInt(betField1.getText());
                playerBet1 = betAmount;

                player1.placeBet(betAmount);
                betField1.setEnabled(false);
                StateHandler.update();
            }
        });
        betPanel1.add(betLabel1, BorderLayout.NORTH);
        betPanel1.add(betField1, BorderLayout.CENTER);

        gamePanel.add(betPanel1);

        // Create Hit and Stand buttons for Player 1
        JButton hitButton1 = new JButton("Hit");
        hitButton1.setBounds(boardWidth / 4 - 150, boardHeight - 130, 80, 30);
        hitButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if player card sum is over 21
                if(!player1.isPlaying()) {
                    hitButton1.setEnabled(false);
                    betField1.setEnabled(false);
                    return;
                }
                player1.hit(dealer.dealcard());
                StateHandler.update();
            }
        });
        gamePanel.add(hitButton1);

        JButton standButton1 = new JButton("Stand");
        standButton1.setBounds(boardWidth / 4 + 70, boardHeight - 130, 80, 30);
        standButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if player two is finished their turn
                player1Finsihed = true;
                standButton1.setEnabled(false);
                hitButton1.setEnabled(false);
                if(!player2Finsihed) {
                    return;
                }
                gameOver = true;
                //if player two lost
                dealer.flipHiddenCard();

                // Dealer's turn logic
                while (dealer.isPlaying()) {
                    Card toDeal = dealer.dealcard();
                    // Deck testDeck = dealer.getDealerHand();
                    //testDeck.AddCard(toDeal);
                    dealer.addCardToDealerHand(toDeal);
                    StateHandler.update();
                    // Check if dealer has reached the threshold (usually 17)
                }
                int player1DistanceFrom21 = Math.abs(player1.reducedPlayerAceSum() - 21);
                int player2DistanceFrom21 = Math.abs(player2.reducedPlayerAceSum() - 21);
                int dealerDistanceFrom21 = Math.abs(dealer.reducedDealerAceSum() - 21);

                if (!player1.isPlaying() && !player2.isPlaying()) {
                    //if both players have reduced card sum over 21
                    player1Busted = true;
                    player2Busted = true;
                } else if (player1.isPlaying() && player2.isPlaying() && dealer.getDealerLost()) {
                    //if both players cards are under 21 and dealer has reduced ace card sum over 21
                    if (player1DistanceFrom21 < player2DistanceFrom21) {
                        //if player one has card sum closer to 21
                        player1Won = true;
                        player1.setNumOfChips(player1.getChipBalance() + 2 * playerBet1);
                    } else if (player1DistanceFrom21 > player2DistanceFrom21) {
                        //if player two has card sum closer to 21
                        player2Won = true;
                        player2.setNumOfChips(player2.getChipBalance() + 2 * playerBet2);
                    } else {
                        draw = true;
                        player1.setNumOfChips(player1.getChipBalance() + playerBet1);
                        player2.setNumOfChips(player2.getChipBalance() + playerBet2);
                    }
                } else if (player1.isPlaying() && player2.isPlaying() && !dealer.getDealerLost()) {
                    //if both players cards are under 21 and dealer has reduced ace card sum under 21
                    if(dealerDistanceFrom21 < player2DistanceFrom21 && dealerDistanceFrom21 < player1DistanceFrom21){
                        //if dealer is closer to 21 than both players
                        dealerWon = true;
                    }
                    if (player1DistanceFrom21 < player2DistanceFrom21) {
                        //if player 1 is closer to 21
                        if(player1DistanceFrom21 < dealerDistanceFrom21){
                            //player 1 is closest
                            player1Won = true;
                            player1.setNumOfChips(player1.getChipBalance() + 2 * playerBet1);
                        }else{
                            //dealer is closest
                            dealerWon = true;
                        }
                    } else if (player1DistanceFrom21 > player2DistanceFrom21) {
                        //if player 2 is closer to 21
                        if(player2DistanceFrom21 < dealerDistanceFrom21){
                            //player to is closest to 21
                            player2Won = true;
                            player2.setNumOfChips(player2.getChipBalance() + 2 * playerBet2);
                        }else{
                            //dealer is closest
                            dealerWon = true;
                        }
                    } else {
                        //if both players are same distance from 21 than dealer
                        if(player1DistanceFrom21 < dealerDistanceFrom21){
                            player1Won = true;
                            player2Won = true;
                        }else if(player1DistanceFrom21 == dealerDistanceFrom21){
                            //dealer and players are same distance away
                            draw = true;
                        }else{
                            //dealer is closer than both players
                            dealerWon = true;
                        }
                        player1.setNumOfChips(player1.getChipBalance() + playerBet1);
                        player2.setNumOfChips(player2.getChipBalance() + playerBet2);
                    }
                }else if (!dealer.getDealerLost() && player1.isPlaying()) {
                    player2Busted = true;
                    //if dealer and player 1 have cards below 21 and player 2 lost
                    if (dealerDistanceFrom21 > player1DistanceFrom21) {
                        //if player one has card sum closer to 21
                        player1Won = true;
                        player1.setNumOfChips(player1.getChipBalance() + 2 * playerBet1);
                    } else if (player1DistanceFrom21 > dealerDistanceFrom21) {
                        //if dealer card sum is closer to 21
                        dealerWon = true;
                    } else {
                        draw = true;
                        player1.setNumOfChips(player1.getChipBalance() + 2 * playerBet1);
                    }
                } else if (!dealer.getDealerLost() && player2.isPlaying()) {
                    player1Busted = true;
                    //if dealer and player 2 have cards below 21 and player 1 lost
                    if (dealerDistanceFrom21 > player2DistanceFrom21) {
                        //if player one has card sum closer to 21
                        player2Won = true;
                        player2.setNumOfChips(player2.getChipBalance() + 2 * playerBet2);
                    } else if (player2DistanceFrom21 > dealerDistanceFrom21) {
                        //if dealer card sum is closer to 21
                        dealerWon = true;
                    } else {
                        draw = true;
                        player2.setNumOfChips(player2.getChipBalance() + 2 * playerBet2);
                    }
                }
                // Disable the stand and hit buttons after standing
                standButton1.setEnabled(false);
                hitButton1.setEnabled(false);
                betField1.setEnabled(false);
                StateHandler.update();
            }
        });
        gamePanel.add(standButton1);


        // Create the title and Bet box for Player 2
        JLabel player2Label = new JLabel("Player 2", SwingConstants.CENTER);
        player2Label.setForeground(Color.WHITE);
        player2Label.setFont(new Font("Serif", Font.BOLD, 18));
        player2Label.setBounds(3 * boardWidth / 4 - 50, boardHeight - 130, 100, 30);
        gamePanel.add(player2Label);

        JPanel betPanel2 = new JPanel();
        betPanel2.setLayout(new BorderLayout());
        betPanel2.setBackground(new Color(0, 100, 0));
        betPanel2.setBounds(3 * boardWidth / 4 - 50, boardHeight - 100, 100, 50); // Position the bet panel

        JLabel betLabel2 = new JLabel("Bet");
        betLabel2.setForeground(Color.WHITE);
        betLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField betField2 = new JTextField();
        betField2.setHorizontalAlignment(JTextField.CENTER);
        betField2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player2.getChipBalance() == 0) {
                    betField2.setEnabled(false);
                    return;
                }
                int betAmount = Integer.parseInt(betField2.getText());
                playerBet2 = betAmount;

                player2.placeBet(betAmount);
                betField2.setEnabled(false);
                StateHandler.update();
            }
        });
        betPanel2.add(betLabel2, BorderLayout.NORTH);
        betPanel2.add(betField2, BorderLayout.CENTER);

        gamePanel.add(betPanel2);

        // Create Hit and Stand buttons for Player 2
        JButton hitButton2 = new JButton("Hit");
        hitButton2.setBounds(3 * boardWidth / 4 - 150, boardHeight - 130, 80, 30);
        hitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if player card sum is over 21
                if(!player2.isPlaying()) {
                    hitButton2.setEnabled(false);
                    betField2.setEnabled(false);
                    return;
                }
                player2.hit(dealer.dealcard());
                StateHandler.update();
            }
        });
        gamePanel.add(hitButton2);

        JButton standButton2 = new JButton("Stand");
        standButton2.setBounds(3 * boardWidth / 4 + 70, boardHeight - 130, 80, 30);
        standButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player2Finsihed = true;
                standButton2.setEnabled(false);
                hitButton2.setEnabled(false);
                if(!player1Finsihed) {
                    return;
                }
                gameOver = true;
                //if player one lost
                dealer.flipHiddenCard();

                // Dealer's turn logic
                while (dealer.isPlaying()) {
                    Card toDeal = dealer.dealcard();
                    // Deck testDeck = dealer.getDealerHand();
                    //testDeck.AddCard(toDeal);
                    dealer.addCardToDealerHand(toDeal);
                    StateHandler.update();
                    // Check if dealer has reached the threshold (usually 17)
                }
                gameOver = true;

                int player1DistanceFrom21 = Math.abs(player1.reducedPlayerAceSum() - 21);
                int player2DistanceFrom21 = Math.abs(player2.reducedPlayerAceSum() - 21);
                int dealerDistanceFrom21 = Math.abs(dealer.reducedDealerAceSum() - 21);

                if(!player2.isPlaying()){
                    //player has card sum over 21
                    dealerWon = true;
                }else if(dealer.getDealerLost()){
                    //dealer has card sum over 21
                    player1Won = true;
                    player2.setNumOfChips(player2.getChipBalance()+2* playerBet2);
                    StateHandler.update();
                } else if (player2DistanceFrom21 == dealerDistanceFrom21){
                    //player and dealer has same sum
                    draw = true;
                    player2.setNumOfChips(player2.getChipBalance()+ playerBet2);
                    StateHandler.update();
                }else if(player2DistanceFrom21 < dealerDistanceFrom21){
                    //player closer to 21
                    player2Won = true;
                    player2.setNumOfChips(player2.getChipBalance()+2* playerBet2);
                    StateHandler.update();
                }else if(player2DistanceFrom21 > dealerDistanceFrom21){
                    //dealer closer to 21
                    dealerWon = true;
                }

                // Disable the stand and hit buttons after standing
                standButton2.setEnabled(false);
                hitButton2.setEnabled(false);
                betField2.setEnabled(false);
                StateHandler.update();
            }
        });

        gamePanel.add(standButton2);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TwoPlayerMode();
            }
        });
    }
}