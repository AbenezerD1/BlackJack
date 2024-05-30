package BlackJack;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Creates a card with image for the front and back of the card, a point value,
 * along with a card number and suit ||
 *
 * Contains methods:
 * getters: getCardFrontImage(), getCardBackImage(), getCardNum(), getCardPointValue(), getSuit() ||
 * setters: setCardNumber(CardNumber),setSuit(Suit), setCardPointValue(int) ||
 * member functions: draw(Graphics,int,int,double), isAce(), flip(), equals(card), toString()
 */
public class Card {
    private BufferedImage cardFront; //card facing up image
    private BufferedImage cardBack; //card facing down image
    private CardNumber cardNum; //number on card like ace,1,2,3,...
    private int cardPointValue; //how many points a card is worth
    private Suit suit; //suit of a card hears, diamonds,...

    /**
     * Creates a card using the number on the card and its suit
     * sets the point value of the card within 1-11, if out of range to 1
     *
     * PRECONDITION: card point passed in is between 1-11
     * @param cardNum
     * @param suit
     * @param cardPointValue
     */
    public Card(CardNumber cardNum, Suit suit, int cardPointValue) {
        SetCardNum(cardNum);
        SetSuit(suit);
        setCardPointValue(cardPointValue);

        //assigning card image to appropriate card from assets
        try {
            this.cardFront = ImageIO.read(new File("./BlackJack/assets/"+toString()+".png"));
            this.cardBack = ImageIO.read(new File("./BlackJack/assets/Back.png"));
        } catch (IOException e) {
            System.err.println("ERROR: Can't find " + "./assets/"+toString()+".png"+ " image file");
            e.printStackTrace();
        }
    }

    /**
     * copies another card to this card with same card number, suit, point value and image
     *
     * PRECONDITION: copies elements of other card to this card
     * @param card
     */
    public Card(Card card) {
        this.cardNum = card.cardNum;
        this.cardPointValue = card.cardPointValue;
        this.suit = card.suit;
        this.cardFront = card.cardFront;
        this.cardBack = card.cardBack;
    }

    /**
     * getters
     */
    public BufferedImage getCardFrontImage(){
        return cardFront;
    }
    public BufferedImage getCardBackImage(){
        return cardFront;
    }
    public CardNumber getCardNum(){
        return cardNum;
    }
    public int getCardPointValue(){
        return cardPointValue;
    }
    public Suit getSuit(){
        return suit;
    }

    /**
     * Setters
     */
    //PRECONDITION: card number is not null
    //POSTCONDITION: sets card number to given number
    public void SetCardNum(CardNumber number){
        this.cardNum = number;
    }

    //PRECONDITION: card suit is not null
    //POSTCONDITION: sets card suit to given suit
    public void SetSuit(Suit suit){
        this.suit = suit;
    }

    /**
     * Sets the point value of the card between 1-11 inclusive
     *
     * PRECONDITION: sets the card point to given points if within 1-11
     * POSTCONDITION: card points is set to given points
     * @param points
     */
    public void setCardPointValue(int points){
        if((points < 0) || (points > 11)) {
            System.err.println("ERROR: Card Value " + points + " is out of range");
            this.cardPointValue =  1;
            return;
        }
        this.cardPointValue = points;
    }

    /**
     * draws a card at a specified location x,y and scales the 500px by 726px card
     * image by the specified scale(Ex. 1.0 is 500x726 while 0.5 creates card 250x363)
     *
     *
     * PRECONDITION: scale is greatr than 0, x and y are greater than 0
     * POSTCONDITION: paints a card using the front of the card image  at the given location and scale
     * @param g
     * @param x
     * @param y
     * @param scale
     */
    public void draw(Graphics g,int x, int y,double scale){
        int cardWidth = (int)(scale*cardFront.getWidth());
        int cardHeight = (int)(scale*cardFront.getHeight());
        if(cardWidth == 0 || cardHeight == 0) return;
        Image tmp = cardFront.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
        cardFront = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
        g.drawImage(tmp,x,y,null);
    }

    /**
     * swaps front of card image to back of card image to look like it has been flipped
     *
     * PRECONDITION: none
     * POSTCONDITION: swaps back of card image and front
     */
    public void flip(){
        BufferedImage temp = cardFront;
        cardFront = cardBack;
        cardBack = temp;
    }

    /**
     * checks if a card is an ace
     *
     * PRECONDITION: card number is not null
     * POSTCONDITION: returns if card is an ace
     */
    public boolean isAce(){
        if(this.cardNum == null) return false;
        if(this.cardNum != CardNumber.ACE){
            return false;
        }
        return true;
    }
    /**
     * Equal if the card number, suits, and point value are the same
     *
     * PRECONDITION: object passed in is a card object
     * POSTCONDITION: true if card number, suit, and point value are the same
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Card){
            if(((Card) obj).cardNum == cardNum &&
                    ((Card) obj).suit == suit &&
                    ((Card) obj).cardPointValue == cardPointValue){
               return true;
            }
        }
        return false;
    }


    /**
     * returns a string with the card number + _of_ + the suit
     * ex. ace_of_spades
     *
     * PRECONDITION: card number and suits are not null
     * POSTCONDITIONS returns a string with card number + _of_ + the suit
     * @return
     */
    @Override
    public String toString() {
        if(cardNum.ordinal() > 0 && cardNum.ordinal() < 10){
            String result = ((cardNum.ordinal()+1) + "_of_" + suit +"S").toLowerCase();
            return result;
        }
        String result = (cardNum + "_of_" + suit +"S").toLowerCase();
        return result;
    }
}
