public class Card {


    CardValues cardVal = null;
    Suit suit = null;
    public Card(CardValues cardVal, Suit suit) {
        this.cardVal = cardVal;
        this.suit = suit;
    }
    @Override
    public String toString() {
        String result = "{Value: " + cardVal + ", Suit: " + suit +"}";
        return result;
    }
}
