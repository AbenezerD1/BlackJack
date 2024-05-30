package BlackJack;

import java.util.ArrayList;

/**
 * Creates a Deck with a list of cards and the sum of their point values ||
 *
 * Contains methods:
 * getters: getCard(), getDeck(), getDeckList(), getSum() ||
 * setters: setDeck(ArrayList<Card>),setCard(Card) ||
 * member functions: size(), AddCard(Card), RemoveCard(int), equals(card), toString()
 */
public class Deck{
    private ArrayList<Card> deck;
    private int sum;

    //CONSTRUCTORS
    /**
     * Creates an empty deck
     */
    public Deck(){
        setDeck(new ArrayList<>());
        updateSum();
    }

    /**
     * copies a given deck as arraylist of cards to the current deck
     * automatically updates the sum
     *
     * PRECONDITION: arraylist of cards is not null
     */
    public Deck(ArrayList<Card> deck) {
        setDeck(deck);
        updateSum();
    }

    /**
     * copies other deck into current deck
     * automatically updates sum
     *
     * PRECONDITION: other deck is not null
     */
    public Deck(Deck other){
        setDeck(other.getDeckList());
        updateSum();
    }

    //GETTERS
    public int getSum(){
        return sum;
    }

    /**
     * gets a card from the deck
     *
     * PRECONDITION: index of card is within size of deck - 1 and greater than 0
     * POSTCONDITION: returns the card at the index
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

    /**
     * returns a copy of cards as a ArrayList<Card>
     * @return
     */
    public ArrayList<Card> getDeckList() {
        return copyDeck(deck);
    }

    /**
     * returns a copy of the current deck object
     * @return
     */
    public Deck getDeck() {
        return new Deck(deck);
    }

    //SETTERS
    /**
     * set a card to the deck
     *
     * PRECONDITION: index of card is within size of deck - 1 and greater than zero
     * POSTCONDITION: changes card at given index
     * @param indexOfCard
     * @param card
     */
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
    /**
     * copies the deck and updates the current deck with the copy
     * @param deck
     */
    public void setDeck(ArrayList<Card> deck) {
        this.deck = copyDeck(deck);
        updateSum();
    }

    //ACTIONS/HELPERS
    /**
     * updates the sum of the deck using the cards point value
     */
    private void updateSum() {
        sum = 0;
        for(int i = 0; i < deck.size(); i++){
            Card card = deck.get(i);
            sum += card.getCardPointValue();
        }
    }
    /**
     * returns the size of the deck
     * @return
     */
    public int size(){
        return deck.size();
    }

    /**
     * Addds a card the end of the deck
     *
     * PRECONDITION: none
     * POSTCONDITION: adds card to deck
     * @param card
     */
    public void AddCard(Card card){
        Card copy = new Card(card);
        deck.add(copy);
        updateSum();
    }

    /**
     * Removes a card at given index
     *
     * PRECONDITION: index is within size of deck - 1 and grete than zero
     * POSTCONDITION: returns the removed card
     * @param indexOfCard
     * @return
     */
    public Card RemoveCard(int indexOfCard){
        Card toReturn = deck.remove(indexOfCard);
        updateSum();
        return toReturn;
    }

    /**
     * copies an arraylist of cards and returnsa copy of the array list of cards
     * @param other
     * @return
     */
    private ArrayList<Card> copyDeck(ArrayList<Card> other){
        ArrayList<Card> temp = new ArrayList<>(other.size());
        for(int i = 0; i < other.size(); i++){
            temp.add(other.get(i));
        }
        return temp;
    }

    /**
     * Equal if every card is the same
     *
     * PRECONDITION: deck is not empty and deck passed in is not null
     * POSTCONDITION: returns if every card in the deck is the same card number, suit, and point values as current deck
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Deck){
            if(((Deck) obj).size() != size()) return false;
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
     *
     * PRECONDITION: none
     * POSTCONDITION: returns a string formatted as [card1(point of card1),card2(point of card2),card3(point of card3),...]
     * @return
     */
    @Override
    public String toString() {
        if(deck.size() <= 0 ) return "[]";

        String toReturn = "[" + deck.get(0)+"("+deck.get(0).getCardPointValue()+")\n";
        for(int i = 1; i < deck.size(); i++){
            toReturn += ", " + deck.get(i) + "("+deck.get(i).getCardPointValue()+")\n" ;
        }
        return toReturn + "]";
    }
}
