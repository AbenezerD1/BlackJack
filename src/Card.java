import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Card {
    private BufferedImage image;
    private CardValues cardVal = null;
    private int cardSumValue;
    private Suit suit = null;

    /**
     * Every card that is created gets to choose its value within 1-10
     * BufferedImage is created using a file name with the extension in it
     *
     * @param cardFileName
     * @param cardVal
     * @param suit
     * @param cardSumValue
     */
    public Card(String cardFileName, CardValues cardVal, Suit suit, int cardSumValue) {
        this.cardVal = cardVal;
        if(cardSumValue > 0 && cardSumValue < 11) {
            this.cardSumValue = cardSumValue;
        }else{
            System.err.println("ERROR: Card Value " + cardSumValue + " is out of range");
            this.cardSumValue =  1;
        }
        this.suit = suit;
        try {
            this.image = ImageIO.read(new File(cardFileName));
        } catch (IOException e) {
            System.err.println("ERROR: Can't find " + cardFileName+ " image file");
            e.printStackTrace();
        }
    }
    public Card(Card card) {
        this.cardVal = card.cardVal;
        this.cardSumValue = card.cardSumValue;
        this.suit = card.suit;
        this.image = card.image;
    }
    /**
     * getters
     */
    public BufferedImage image(){
        return image;
    }
    public CardValues cardVal(){
        return cardVal;
    }
    public int cardSumValue(){
        return  cardSumValue;
    }
    public Suit suit(){
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

    @Override
    public String toString() {
        String result = "{Value: " + cardVal + ", Suit: " + suit +"}";
        return result;
    }
}
