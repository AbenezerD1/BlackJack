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
    private boolean player1Won,player2Won,dealerWon,draw;

    JPanel gamePanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw a white horizontal line in the middle
            g.setColor(Color.WHITE);
            int midY = getHeight() / 2;
            g.drawLine(0, midY, getWidth(), midY);

            if(dealerWon){
                g.setColor(Color.RED);
                g.setFont(new Font(Font.SERIF, Font.BOLD,50));
                g.drawString("BUSTED", boardWidth/2-150,boardHeight/2-10);
            }
            if(player1Won){
                g.setColor(Color.YELLOW);
                g.setFont(new Font(Font.SERIF, Font.BOLD,50));
                g.drawString("PLAYER 1 WON", boardWidth/2-150,boardHeight/2-10);
            }
            if(player2Won){
                g.setColor(Color.YELLOW);
                g.setFont(new Font(Font.SERIF, Font.BOLD,50));
                g.drawString("PLAYER 2 WON", boardWidth/2-150,boardHeight/2-10);
            }
            if(draw){
                g.setColor(Color.BLUE);
                g.setFont(new Font(Font.SERIF, Font.BOLD,50));
                g.drawString("DRAW", boardWidth/2-150,boardHeight/2);
            }


            // Draw a white vertical line in the middle of the bottom half
            int midX = getWidth() / 2;
            g.drawLine(midX, midY, midX, getHeight());

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

        betPanel1.add(betLabel1, BorderLayout.NORTH);
        betPanel1.add(betField1, BorderLayout.CENTER);

        gamePanel.add(betPanel1);

        // Create Hit and Stand buttons for Player 1
        JButton hitButton1 = new JButton("Hit");
        hitButton1.setBounds(boardWidth / 4 - 150, boardHeight - 130, 80, 30);
        hitButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card toDeal = dealer.dealcard();
                player1.hit(toDeal);
                StateHandler.update();
            }
        });
        gamePanel.add(hitButton1);

        JButton standButton1 = new JButton("Stand");
        standButton1.setBounds(boardWidth / 4 + 70, boardHeight - 130, 80, 30);
        standButton1.addActionListener(new ActionListener() {
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
                dealer.addCardToDealerHand(toDeal);
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

        betPanel2.add(betLabel2, BorderLayout.NORTH);
        betPanel2.add(betField2, BorderLayout.CENTER);

        gamePanel.add(betPanel2);

        // Create Hit and Stand buttons for Player 2
        JButton hitButton2 = new JButton("Hit");
        hitButton2.setBounds(3 * boardWidth / 4 - 150, boardHeight - 130, 80, 30);
        hitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card toDeal = dealer.dealcard();
                player2.hit(toDeal);
                StateHandler.update();
            }
        });
        gamePanel.add(hitButton2);

        JButton standButton2 = new JButton("Stand");
        standButton2.setBounds(3 * boardWidth / 4 + 70, boardHeight - 130, 80, 30);
        standButton2.addActionListener(new ActionListener() {
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
                dealer.addCardToDealerHand(toDeal);
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