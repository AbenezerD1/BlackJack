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
            Player jhon = new Player(1,new Deck(),1000);
            Player jhon2 = new Player(2,new Deck(),1000);
            Deck deck = new Deck();
            Card card1= new Card(CardValues.SIX, Suit.CLUB, 6);
            Card card2 = new Card(CardValues.SEVEN, Suit.HEART, 7);
            Card card3 = new Card(CardValues.FIVE, Suit.SPADE, 5);
            Card card4 = new Card(CardValues.TEN, Suit.DIAMOND, 10);
            Card card5 = new Card(CardValues.ACE, Suit.SPADE, 10);
            card1.flip();
            jhon.hit(card1);
            jhon.hit(card2);
            jhon.hit(card3);
            jhon.hit(card4);
            jhon.hit(card5);
            jhon2.hit(card1);
            jhon2.hit(card2);
            jhon2.hit(card3);
            jhon2.hit(card4);
            jhon2.hit(card5);
            jhon.drawPlayerHand(g,10,350,15,0.15);
            jhon2.drawPlayerHand(g,535,350,15,0.15);
        }
    }
}
