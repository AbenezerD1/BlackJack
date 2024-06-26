package BlackJack;

import javax.swing.*;
import java.awt.*;

public class testUI {
    private JFrame frame;
    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 600;
    public static void main(String[] args){
        testUI t = new testUI();
    }

    public testUI(){
        frame = new JFrame("Test");
        frame.setVisible(true);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(0,100,0));

        CardsPanel cPanel = new CardsPanel();
        frame.add(cPanel);
    }

    public class CardsPanel extends JPanel {

        //drawing a card
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Player[] players = {
                    new Player(1,new Deck(),1000),
                    new Player(2,new Deck(),1000),
            };
            Dealer dealer = new Dealer(150,50,0.25);
            Deck deck = new Deck();
            Card[] c = {
                    new Card(CardNumber.SIX, Suit.CLUB, 6),
                    new Card(CardNumber.SEVEN, Suit.HEART, 7),
                    new Card(CardNumber.FIVE, Suit.SPADE, 5),
                    new Card(CardNumber.TEN, Suit.DIAMOND, 10),
                    new Card(CardNumber.ACE, Suit.SPADE, 10),
            };

            for(int j = 0; j < players.length; j++){
                for(int i = 0; i < 2; i++){
                    players[j].hit(c[i]);
                }
            }

            c[0].flip();
            dealer.addCardToDealerHand(c[0]);
            dealer.addCardToDealerHand(c[1]);
            dealer.flipMainDeck();


            players[0].drawPlayerHand(g,10,350,0.15);
            players[1].drawPlayerHand(g,535,350,0.15);
            dealer.render(g);
        }
    }
}
