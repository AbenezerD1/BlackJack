import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player jhon;
    Deck deck;
    Card card1;
    Card card2;
    Card card3;
    Card card4;
    Card card5;
    Card card6;
    @BeforeEach
    void setUp() {
        jhon = new Player(1,new Deck(),1000);
        deck = new Deck();
        card1 = new Card(CardValues.SIX, Suit.CLUB, 6);
        card2 = new Card(CardValues.SEVEN, Suit.HEART, 7);
        card3 = new Card(CardValues.FIVE, Suit.SPADE, 5);
        card4 = new Card(CardValues.TEN, Suit.DIAMOND, 10);
        card5 = new Card(CardValues.ACE, Suit.SPADE, 10);
        card6 = new Card(CardValues.ACE, Suit.SPADE, 1);
        deck.AddCard(card1);
        deck.AddCard(card2);
        deck.AddCard(card3);
        deck.AddCard(card4);
        deck.AddCard(card5);
    }

    @AfterEach
    void tearDown() {
        deck = null;
        card1 = null;
        card2 = null;
        card3 = null;
        card4 = null;
    }

    @Test
    void setPlayerHand() {
        //Sets a full deck
        jhon.setPlayerHand(deck);
        Assertions.assertEquals(deck.toString(),jhon.getPlayerHand().toString());

        //Sets empty deck
        jhon.setPlayerHand(new Deck());
        Assertions.assertEquals((new Deck()).toString(), jhon.getPlayerHand().toString());
    }

    @Test
    void updatedAceValue() {
        jhon.setPlayerHand(deck);
        jhon.updateAceValue(4,card6);
        Card originalCard = jhon.getPlayerHand().getCard(4);
        assertEquals(1,originalCard.getCardSumValue());
    }

    @Test
    void placeBet() {
        jhon.placeBet(5000);
        assertEquals(1000,jhon.getChipBalance());
        jhon.placeBet(1000);
        jhon.placeBet(1);
        assertEquals(0,jhon.getChipBalance());
    }
}