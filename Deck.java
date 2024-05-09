import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck;
    private int sum;

    /**
     * Creates a default 52 card deck that are null in value and suit
     */
    public Deck(){
        this.deck = new ArrayList<>(52);
        int sum = 0;
    }

    /**
     * copies a given deck as arraylist of cards to the current deck
     * @param deck
     */
    public Deck(ArrayList<Card> deck) {
        this.deck = copyDeck(deck);
        sum = getSum();
    }

    /**
     * copies other deck into current deck
     * @param other
     */
    public Deck(Deck other){
        this.deck = copyDeck(other.deck);
        sum = getSum();
    }

    /**
     * Adds values of cards in the deck using their index + 1
     * @return sum
     */
    public int getSum(){
        sum = 0;
        for(Card card:deck){
            sum += card.cardVal.ordinal()+1;
        }
        return sum;
    }

    public ArrayList<Card> getDeck() {
        return copyDeck(deck);
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = copyDeck(deck);
    }

    /**
     * sets sum value with a given num
     * @param num
     */
    public void setSum(int num) {
        if(num < 1) {
            System.err.println("Sum of deck can't be negative");
            this.sum = 0;
        }else {
            this.sum = num;
        }
    }

    private ArrayList<Card> copyDeck(ArrayList<Card> other){
        ArrayList<Card> temp = new ArrayList<>(other.size());
        for(int i = 0; i < other.size(); i++){
            temp.add(other.get(i));
        }
        return temp;
    }

    @Override
    public String toString() {
        if(deck.size() <= 0 ) return "[]";

        String toReturn = "[" + deck.get(0);
        for(int i = 1; i < deck.size(); i++){
            toReturn += ", " + deck.get(i) ;
        }
        return toReturn + "]";
    }
}
