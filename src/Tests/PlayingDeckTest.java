package Tests;

import BlackJack.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayingDeckTest {
    PlayingDeck deck;

    public int defineCardValue(CardNumber val){
        if((val.ordinal()+1) < 11){
            return val.ordinal() + 1;
        }
        return 10;
    }

    @BeforeEach
    void setUp() {
        deck = new PlayingDeck();
        deck.BuildPlayingDeck();
    }

    @AfterEach
    void tearDown() {
        deck = null;
    }

    @Test
    void defineCardValue() {
    }

    @Test
    void buildPlayingDeck() {
        Deck d = new Deck();
        for(int i = 0; i < CardNumber.values().length; i++){
            for(int j = 0; j < Suit.values().length; j++){
                d.AddCard(new Card(CardNumber.values()[i],Suit.values()[j], defineCardValue(CardNumber.values()[i])));
            }
        }
        for(int i = 0; i < deck.getDeckList().size(); i++){
            assertEquals(d.getCard(i).getCardNum(), deck.getCard(i).getCardNum());
            assertEquals(d.getCard(i).getSuit(), deck.getCard(i).getSuit());
            assertEquals(d.getCard(i).getCardPointValue(), deck.getCard(i).getCardPointValue());
        }
    }

    @Test
    void shufflePlayingDeck() {
        Deck d = new Deck();
        for(int i = 0; i < CardNumber.values().length; i++){
            for(int j = 0; j < Suit.values().length; j++){
                d.AddCard(new Card(CardNumber.values()[i],Suit.values()[j], defineCardValue(CardNumber.values()[i])));
            }
        }
        deck.ShufflePlayingDeck();
        for(int i = 0; i < d.size(); i++){
            assertEquals(true,cardFoundInDeck(d.getCard(i)));
        }
    }

    private boolean cardFoundInDeck(Card card){
        for(int i = 0; i < deck.size(); i++){
            if(card.equals(deck.getCard(i))){
                return true;
            }
        }
        return false;
    }

    @Test
    public void drawFromPlayingDeck(){
        Card c = deck.getCard(deck.size()-1);
        assertEquals(c,deck.drawFromPlayingDeck());
        for(Card card: deck.getDeckList()){
            c = deck.getCard(deck.size()-1);
            assertEquals(c,deck.drawFromPlayingDeck());
        }
        assertEquals(null,deck.drawFromPlayingDeck());
    }
}