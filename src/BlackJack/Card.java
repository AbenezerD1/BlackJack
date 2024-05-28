package BlackJack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class Card {
    private BufferedImage cardFront;
    private BufferedImage cardBack;
    private CardValues cardVal;
    private int cardSumValue;
    private Suit suit;

    /**
     * Every card that is created gets to choose its value within 1-10
     * BufferedImage is created using a file name with the extension in it
     *
     * @param cardVal
     * @param suit
     * @param cardSumValue
     */
    public Card(CardValues cardVal, Suit suit, int cardSumValue) {
        this.cardVal = cardVal;
        if(cardSumValue > 0 && cardSumValue < 12) {
            this.cardSumValue = cardSumValue;
        }else{
            System.err.println("ERROR: Card Value " + cardSumValue + " is out of range");
            this.cardSumValue =  1;
        }
        this.suit = suit;
        try {
            this.cardFront = ImageIO.read(new File("./assets/"+toString()+".png"));
            this.cardBack = ImageIO.read(new File("./assets/Back.png"));
        } catch (IOException e) {
            System.err.println("ERROR: Can't find " + "./assets/"+toString()+".png"+ " image file");
            e.printStackTrace();
        }
    }
    public Card(Card card) {
        this.cardVal = card.cardVal;
        this.cardSumValue = card.cardSumValue;
        this.suit = card.suit;
        this.cardFront = card.cardFront;
        this.cardBack = card.cardBack;
    }
    /**
     * getters
     */
    public BufferedImage getImage(){
        return cardFront;
    }

    public CardValues getCardVal(){
        return cardVal;
    }
    public int getCardSumValue(){
        return  cardSumValue;
    }
    public Suit getSuit(){
        return suit;
    }

    /**
     * Setters
     */
    public void SetCardVal(CardValues val){
        this.cardVal = val;
    }
    public void SetSuit(Suit suit){
        this.suit = suit;
    }
    /**
     * Sets the sum value between 1-10 inclusive
     * @param sumVal
     */
    public void SetCardSumValue(int sumVal){
        if(sumVal < 0 && sumVal > 11) {
            System.err.println("ERROR: Card Value " + sumVal + " is out of range");
            this.cardSumValue =  1;
            return;
        }
        this.cardSumValue = sumVal;
    }

    public void draw(Graphics g,int x, int y,double scale){
        int cardWidth = (int)(scale*cardFront.getWidth());
        int cardHeight = (int)(scale*cardFront.getHeight());
        if(cardWidth == 0 || cardHeight == 0) return;
        Image tmp = cardFront.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
        cardFront = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
        g.drawImage(tmp,x,y,null);
    }
    public void flip(){
        BufferedImage temp = cardFront;
        cardFront = cardBack;
        cardBack = temp;
    }

    /**
     * checks if a card is an ace
     * @param
     * @return
     */
    public boolean isAce(){
        if(this == null) return false;
        if(this.cardVal != CardValues.ACE){
            return false;
        }
        return true;
    }
    /**
     * Equal if the card value, suits, and sum value are the same
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Card){
            if(((Card) obj).cardVal == cardVal &&
                    ((Card) obj).suit == suit &&
                    ((Card) obj).cardSumValue == cardSumValue){
               return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if(cardVal.ordinal() > 0 && cardVal.ordinal() < 10){
            String result = ((cardVal.ordinal()+1) + "_of_" + suit +"S").toLowerCase();
            return result;
        }
        String result = (cardVal + "_of_" + suit +"S").toLowerCase();
        return result;
    }
}
