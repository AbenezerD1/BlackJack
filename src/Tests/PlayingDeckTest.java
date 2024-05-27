package Tests;

import BlackJack.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayingDeckTest {
    PlayingDeck deck;
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
        for(int i = 0; i < CardValues.values().length; i++){
            for(int j = 0; j < Suit.values().length; j++){
                d.AddCard(new Card(CardValues.values()[i],Suit.values()[j], deck.defineCardValue(CardValues.values()[i])));
            }
        }
        for(int i = 0; i < deck.getDeckList().size(); i++){
            assertEquals(d.getCard(i).getCardVal(), deck.getCard(i).getCardVal());
            assertEquals(d.getCard(i).getSuit(), deck.getCard(i).getSuit());
            assertEquals(d.getCard(i).getCardSumValue(), deck.getCard(i).getCardSumValue());
        }
    }

    @Test
    void shufflePlayingDeck() {
        Deck d = new Deck();
        for(int i = 0; i < CardValues.values().length; i++){
            for(int j = 0; j < Suit.values().length; j++){
                d.AddCard(new Card(CardValues.values()[i],Suit.values()[j], deck.defineCardValue(CardValues.values()[i])));
            }
        }
        deck.ShufflePlayingDeck();
        for(int i = 0; i < d.Size(); i++){
            assertEquals(true,cardFoundInDeck(d.getCard(i)));
        }
    }

    private boolean cardFoundInDeck(Card card){
        for(int i = 0; i < deck.Size(); i++){
            if(card.equals(deck.getCard(i))){
                return true;
            }
        }
        return false;
    }

    @Test
    public void drawFromPlayingDeck(){
        Card c = deck.getCard(deck.Size()-1);
        assertEquals(c,deck.drawFromPlayingDeck());
        for(Card card: deck.getDeckList()){
            c = deck.getCard(deck.Size()-1);
            assertEquals(c,deck.drawFromPlayingDeck());
        }
        assertEquals(null,deck.drawFromPlayingDeck());
    }
}