package BlackJack;

import java.util.ArrayList;

public class Deck{
    private ArrayList<Card> deck;
    private int sum;

    /**
     * Creates a default 52 card deck that are null in value and suit
     */
    public Deck(){
        setDeck(new ArrayList<>());
        updateSum();
    }

    /**
     * copies a given deck as arraylist of cards to the current deck
     */
    public Deck(ArrayList<Card> deck) {
        setDeck(deck);
        updateSum();
    }

    /**
     * copies other deck into current deck
     */
    public Deck(Deck other){
        setDeck(other.getDeckList());
        updateSum();
    }

    public int getSum(){
        return sum;
    }

    public ArrayList<Card> getDeckList() {
        return copyDeck(deck);
    }

    public Deck getDeck() {
        return new Deck(deck);
    }
    /**
     * copies the deck and updates the sum in this deck
     * @param deck
     */
    public void setDeck(ArrayList<Card> deck) {
        this.deck = copyDeck(deck);
        updateSum();
    }

    /**
     * updates the sum of the deck
     */
    private void updateSum() {
        sum = 0;
        for(int i = 0; i < deck.size(); i++){
            Card card = deck.get(i);
            sum += card.getCardSumValue();
        }
    }



    /**
     * gets a card to the deck
     * @param indexOfCard
     * @return Card
     */
    public Card getCard(int indexOfCard){
        if(indexOfCard < 0 || indexOfCard >= deck.size() ) {
            System.err.println("ERROR: Card at index " + indexOfCard+ " is out of range");
            return null;
        }
        if(deck.size() == 0) {
            System.err.println("ERROR: Can't get card from empty deck");
            return null;
        }
        return new Card(deck.get(indexOfCard));
    }

    public void setCard(int indexOfCard, Card card){
        if(indexOfCard < 0 || indexOfCard >= deck.size() ) {
            System.err.println("ERROR: Card at index " + indexOfCard+ " is out of range");
        }
        if(deck.size() == 0) {
            System.err.println("ERROR: Can't set card from empty deck");
            return;
        }
        deck.set(indexOfCard, new Card(card));
        updateSum();
    }

    public int Size(){
        return deck.size();
    }

    public void AddCard(Card card){
        Card copy = new Card(card);
        deck.add(copy);
        updateSum();
    }

    public Card RemoveCard(int indexOfCard){
        Card toReturn = deck.remove(indexOfCard);
        updateSum();
        return toReturn;
    }
    private ArrayList<Card> copyDeck(ArrayList<Card> other){
        ArrayList<Card> temp = new ArrayList<>(other.size());
        for(int i = 0; i < other.size(); i++){
            temp.add(other.get(i));
        }
        return temp;
    }

    /**
     * Equal if every card is the same
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Deck){
            if(((Deck) obj).Size() != Size()) return false;
            for(int i=0; i < deck.size(); i++){
                if(!((deck.get(i)).equals(((Deck) obj).getCard(i)))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * prints each card along with the sum value in paranthesis
     * @return
     */
    @Override
    public String toString() {
        if(deck.size() <= 0 ) return "[]";

        String toReturn = "[" + deck.get(0)+"("+deck.get(0).getCardSumValue()+")\n";
        for(int i = 1; i < deck.size(); i++){
            toReturn += ", " + deck.get(i) + "("+deck.get(i).getCardSumValue()+")\n" ;
        }
        return toReturn + "]";
    }
}
