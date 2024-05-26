import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    Deck deck;
    Card card1;
    Card card2;
    Card card3;
    Card card4;
    @BeforeEach
    void setUp(){
        deck = new Deck();
        card1 = new Card("./assets/2_of_clubs.png", CardValues.TWO, Suit.CLUB, 2);
        card2 = new Card("./assets/7_of_hearts.png", CardValues.TWO, Suit.CLUB, 7);
        card3 = new Card("./assets/9_of_spades.png", CardValues.TWO, Suit.CLUB, 9);
        card4 = new Card("./assets/10_of_diamonds.png", CardValues.TWO, Suit.CLUB, 10);
    }

    @AfterEach
    void tearDown(){
        deck = null;
        card1 = null;
        card2 = null;
        card3 = null;
        card4 = null;
    }
    @Test
    void getSum() {
        Assertions.assertEquals(0,deck.getSum());
        deck.AddCard(card1);
        Assertions.assertEquals(2,deck.getSum());
        deck.AddCard(card2);
        Assertions.assertEquals(9,deck.getSum());
        deck.AddCard(card3);
        Assertions.assertEquals(18,deck.getSum());
        deck.AddCard(card4);
        Assertions.assertEquals(28,deck.getSum());

        deck.RemoveCard(0);
        Assertions.assertEquals(26,deck.getSum());
        deck.RemoveCard(2);
        Assertions.assertEquals(16,deck.getSum());
        deck.RemoveCard(1);
        Assertions.assertEquals(7,deck.getSum());
    }

    @Test
    void getDeck() {
        deck.AddCard(card1);
        deck.AddCard(card2);
        deck.AddCard(card3);
        deck.AddCard(card4);

        Assertions.assertEquals(card1.toString(), deck.getCard(0).toString());
        Assertions.assertEquals(card2.toString(), deck.getCard(1).toString());
        Assertions.assertEquals(card3.toString(), deck.getCard(2).toString());
        Assertions.assertEquals(card4.toString(), deck.getCard(3).toString());
    }

    @Test
    void setDeck() {
        deck.AddCard(card1);
        deck.AddCard(card2);
        deck.AddCard(card3);
        deck.AddCard(card4);

        Assertions.assertEquals(card1.toString(), deck.getCard(0).toString());
        Assertions.assertEquals(card2.toString(), deck.getCard(1).toString());
        Assertions.assertEquals(card3.toString(), deck.getCard(2).toString());
        Assertions.assertEquals(card4.toString(), deck.getCard(3).toString());
    }

}