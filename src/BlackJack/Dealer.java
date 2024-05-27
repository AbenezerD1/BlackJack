package BlackJack;

import java.awt.*;
import java.util.ArrayList;

public class Dealer {
    private PlayingDeck mainDeck;
    private Deck dealerHand;
    private boolean isTurn;
    private int numOfAces = 0;

    public Dealer(){
        mainDeck = new PlayingDeck();
        dealerHand = new Deck();
        isTurn = false;
    }
    public Dealer(PlayingDeck mainDeck, Deck dealerDeck, boolean isTurn, int numOfAces) {
        this.mainDeck = mainDeck;
        this.dealerHand = dealerDeck;
        this.isTurn = isTurn;
        this.numOfAces = numOfAces;

        mainDeck.BuildPlayingDeck();
    }

    public Dealer(Dealer other){
        this.mainDeck = other.mainDeck;
        this.dealerHand = other.dealerHand;
        this.isTurn = other.isTurn;
        this.numOfAces = other.numOfAces;

        mainDeck.BuildPlayingDeck();
    }

    public Deck getMainDeck() {
        return mainDeck.getDeck();
    }

    public void setMainDeck(PlayingDeck other) {
        if(other == null){
            System.err.println("ERROR: The playing deck can't be set to null");
        }
        mainDeck = new PlayingDeck(other);
    }

    public Deck getDealerHand() {
        return dealerHand.getDeck();
    }

    public void setDealerHand(Deck dealerHand) {
        if(dealerHand == null){
            System.err.println("ERROR: Dealer's deck can't be set to null");
        }
        this.dealerHand = new Deck(dealerHand);
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public int getNumOfAces() {
        return numOfAces;
    }

    public void setNumOfAces(int numOfAces) {
        this.numOfAces = numOfAces;
    }

    /**
     * Takes a card of the playing deck and returns it
     */
    public Card dealcard(){
        Card cardToDeal = mainDeck.drawFromPlayingDeck();
        if(cardToDeal == null){
            System.err.println("ERROR: Can't draw from a playing deck");
        }
        return cardToDeal;
    }

    public void addCardToDealersDeck(Card card){
        if(card == null){
            System.err.println("ERROR: Can't add null card");
            return;
        }
        dealerHand.AddCard(card);
    }

    public void resetMainDeck(){
        mainDeck = new PlayingDeck();
        mainDeck.BuildPlayingDeck();
    }

    public void drawDealerHand(Graphics g, int x, int y, int spacingBetweenCards, double cardScale){
        ArrayList<Card> deck = dealerHand.getDeckList();
        for(Card card: deck){
            card.draw(g,x,y,cardScale);
            x += card.getImage().getWidth()+spacingBetweenCards;
        }
    }

    /**
     * draws the main deck as a stacked cards
     * @param g
     * @param x
     * @param y
     * @param spacingBetweenCards
     * @param cardScale
     */
    public void drawMainDeck(Graphics g, int x, int y, int spacingBetweenCards, double cardScale){
        ArrayList<Card> deck = mainDeck.getDeckList();
        for(Card card: deck){
            card.draw(g,x,y,cardScale);
            x += 10;
            y += 10;
        }
    }

    public String toString(){
        return "Dealer "+ ": " + dealerHand.toString();
    }
}
