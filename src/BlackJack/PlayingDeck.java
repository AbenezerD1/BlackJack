package BlackJack;

import java.util.ArrayList;
import java.util.Random;

public class PlayingDeck extends Deck {

    public PlayingDeck() {
        super();
    }

    public PlayingDeck(ArrayList<Card> deck) {
        super(deck);
    }

    public PlayingDeck(PlayingDeck other) {
        super(other.getDeckList());
    }

    /**
     * returns 1-10 for cards values up to 10 and 10 for the rest
     * Note: ace is set to 1, use only for building a deck
     * @param val
     * @return
     */
    public int defineCardValue(CardValues val){
         if((val.ordinal()+1) < 11){
             return val.ordinal() + 1;
         }
         return 10;
    }

    /**
     * adds a full 52 card deck to the deck
     * PRECONDITION: deck is empty
     */
    public void BuildPlayingDeck(){
        if(getDeckList().size() != 0) {
            System.err.println("ERROR: Can only build playing deck from an empty deck");
            return;
        }
        for(int i =0; i < CardValues.values().length; i++){
            for(int j = 0; j < Suit.values().length; j++){
                AddCard(new Card(CardValues.values()[i],Suit.values()[j],defineCardValue(CardValues.values()[i])));
            }
        }
    }

    public void flipCards() {
        for(int i = 0; i < Size(); i++){
            Card temp = getCard(i);
            temp.flip();
            setCard(i,temp);
        }
    }
    /**
     * shuffles cards in a random order
     */
    public void ShufflePlayingDeck(){
        Random r = new Random();
        int randomIndexWithinDeck = r.nextInt(getDeckList().size());
        for(int i = 0; i < getDeckList().size(); i++){
            swapCardInDeck(i,randomIndexWithinDeck);
            randomIndexWithinDeck = r.nextInt(getDeckList().size());
        }
    }

    /**
     * swaps any card in the deck
     * @param thisIndex
     * @param otherIndex
     */
    private void swapCardInDeck(int thisIndex, int otherIndex) {
        if(thisIndex < 0 || thisIndex > Size()) {
            System.err.println("ERROR: this index is out of bounds, couldn't swap cards");
            return;
        }
        if(otherIndex < 0 || otherIndex > Size()) {
            System.err.println("ERROR: other index is out of bounds, couldn't swap cards");
            return;
        }
        Card temp = getCard(thisIndex);
        setCard(thisIndex, getCard(otherIndex));
        setCard(otherIndex,temp);
    }

    /**
     * Removes a card from the end of the playing deck and returns it
     * @return
     */
    public Card drawFromPlayingDeck(){
        if(Size() != 0) return RemoveCard(Size()-1);
        return null;
    }
}
