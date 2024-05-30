package BlackJack;

import java.awt.*;
import java.util.ArrayList;

/**
 * Creates a dealer with a playing deck, a dealer hand, wheter they are still playing, number of aces, and
 * locationa & scale of the dealers hand   ||
 *
 * Contains methods:
 * getters: getMainDeck(), getDealerHand(), isPlaying(), getDealerHandX(), getDealerHandY(), getCardScale()
 * , getNumOfAces()||
 * setters: setMainDeck(), setDealerHand(), setIsPlaying(), setDealerHandPosition(), setDrawHandScale()
 * getNumOfAces() ||
 * member functions: size(), AddCard(Card), RemoveCard(int), equals(card), toString()
 */
public class Dealer implements Renderable{
    private PlayingDeck mainDeck; //the playing deck that dealer uses to deal cards
    private Deck dealerHand; //the dealer's hand
    private boolean isPlaying; //if the dealer has lost yet
    private int numOfAces = 0; //number of aces the dealer has
    private int dealerHandX = 0, dealerHandY = 0; //location where dealer hand is drawn
    private double cardScale = 0.25; //scale of the card

    //CONSTRUCTORS
    /**
     * Creates an ordered standard playing deck with 52 cards, sets dealer hand to empty deck, sets dealer isPlaying to true
     * and adds them to adds single player and two player renderable array list
     *
     * PRECONDITION: none
     */
    public Dealer(int handX, int handY, double cardScale){
        //initializes the drawing location of dealers hand and size of the cards
        setDrawHandPosition(handX,handY);
        setDrawHandScale(cardScale);

        this.mainDeck = new PlayingDeck();
        this.dealerHand = new Deck();
        this.isPlaying = true;
        this.mainDeck.BuildPlayingDeck();

        //adds dealer to state handlers arraylist of rendereable objects to be rendered and updated
        StateHandler.addRenderableElement(States.SINGLE_PLAYER,this);
        StateHandler.addRenderableElement(States.TWO_PLAYER,this);
    }
    /**
     * Creates a dealer with a given playing deck, dealer hand, and the number of aces the dealer has
     * adds it to elements to be rendered by state handler
     *
     * PRECONDITION: none
     */
    public Dealer(PlayingDeck mainDeck, Deck dealerHand, int numOfAces) {
        this.mainDeck = mainDeck;
        this.dealerHand = dealerHand;
        this.isPlaying = false;
        this.numOfAces = numOfAces;
        this.mainDeck.BuildPlayingDeck();

        //adds dealer to state handlers arraylist of rendereable objects to be rendered and updated
        StateHandler.addRenderableElement(States.SINGLE_PLAYER,this);
        StateHandler.addRenderableElement(States.TWO_PLAYER,this);
    }

    /**
     * deep copies all properties of other dealer into this dealer
     *
     * PRECONDITION: none
     */
    public Dealer(Dealer other){
        this.mainDeck = new PlayingDeck(other.mainDeck);
        this.dealerHand = new Deck(other.dealerHand);
        this.isPlaying = other.isPlaying;
        this.numOfAces = other.numOfAces;
        this.mainDeck.BuildPlayingDeck();

        //adds dealer to state handlers arraylist of rendereable objects to be rendered and updated
        StateHandler.addRenderableElement(States.SINGLE_PLAYER,this);
        StateHandler.addRenderableElement(States.TWO_PLAYER,this);
    }

    //GETTERS
    /**
     * returns a deep copy of this playing deck
     *
     * PRECONDITION: none
     * @return current playing deck
     */
    public PlayingDeck getMainDeck() {
        return new PlayingDeck(mainDeck);
    }
    /**
     * @return current scale of the cards
     */
    public double getCardScale() {
        return cardScale;
    }
    /**
     * number of aces the dealer has recieved
     * @return
     */
    public int getNumOfAces() {
        return numOfAces;
    }
    /**
     * @return dealers hand
     */
    public Deck getDealerHand() {
        return dealerHand.getDeck();
    }
    /**
     * gets the x value of where the dealers hand is going to be drawn
     * @return
     */
    public int getDealerHandX() {
        return dealerHandX;
    }

    /**
     * gets the y value of where the dealers hand is going to be drawn
     * @return
     */
    public int getDealerHandY() {
        return dealerHandY;
    }
    public boolean isPlaying() {
        return isPlaying;
    }

    //SETTERS
    /**
     * sets the dealer hand with a given deck of cards
     *
     * PRECONDITION: deck of cards passed isn't null
     * POSTCONDITION: sets dealer's hand to given deck of cards
     * @param dealerHand
     */
    public void setDealerHand(Deck dealerHand) {
        if(dealerHand == null){
            System.err.println("ERROR: Dealer's deck can't be set to null");
        }
        this.dealerHand = new Deck(dealerHand);
    }
    /**
     * returns a deep copy of this playing deck
     *
     * PRECONDITION: other playing deck isn't null
     * POSTCONDITION: sets the main deck to other's playing deck
     */
    public void setMainDeck(PlayingDeck other) {
        if(other == null){
            System.err.println("ERROR: The playing deck can't be set to null");
        }
        mainDeck = new PlayingDeck(other);
    }
    /**
     * if the dealer hasn't gotten a card over 17
     * @return
     */
    /**
     * sets if dealer is playing or not
     * @param playing
     */
    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
    /**
     * sets the position where the dealers hand is going to be drawn
     *
     * PRECONDITION: x and y values passed in are greater than zero
     * POSTCONDITION: updates x,y coordinates of the dealers hand
     * @param dealerHandX
     * @param dealerHandY
     */
    public void setDrawHandPosition(int dealerHandX, int dealerHandY) {
        if(dealerHandX < 0 || dealerHandY < 0){
            System.err.println("ERROR: dealer's hand can't be draw at negative x or y value");
            return;
        }
        this.dealerHandX = dealerHandX;
        this.dealerHandY = dealerHandY;
    }
    /**
     * adjusts the card scale of the dealer's hand
     *
     * PRECONDITION: card scale passed in is greater than zero
     * POSTCONDITION: sets the scale of the cards
     */
    public void setDrawHandScale(double cardScale) {
        if(cardScale < 0){
            System.err.println("ERROR: dealer's hand can't be draw with scale less than zero");
            return;
        }
        this.cardScale = cardScale;
    }

    //ACTIONS/HELPERS
    /**
     * shuffles playing deck
     */
    public void shufflePlayingDeck(){
        mainDeck.ShufflePlayingDeck();
    }
    /**
     * flip all the cards in the main playing deck
     */
    public void flipMainDeck(){
        mainDeck.flipCards();
    }
    /**
     * flips the hidden card from the dealer(first card in dealers hand) to reveal the value
     */
    public void flipHiddenCard(){
        ArrayList<Card> copyHand = getDealerHand().getDeckList();
        Card hiddenCard = copyHand.get(0);
        hiddenCard.flip();
        copyHand.set(0,hiddenCard);
        Deck deck = new Deck(copyHand);
        setDealerHand(deck);
    }
    /**
     * Lets dealer decide the value of an ace
     * @param indexOfAce
     * @param card a new ace with the value you want to replace it with
     * @return
     */
    public void updateAceValue(int indexOfAce, Card card){
        if(card.getCardNum() != CardNumber.ACE){
            System.err.println("ERROR: Has to be a ace to update its value");
            return;
        }
        //checks if it doesn't have the same suit as the ace being replaced
        if(card.getSuit() != dealerHand.getCard(indexOfAce).getSuit()){
            System.err.println("ERROR: Has to be a ace to update its value");
            return;
        }
        if((card.getCardPointValue() != 1) && (card.getCardPointValue() != 11)){
            System.err.println("ERROR: Can't update ace to a number that isn't either a 1 or a 11");
            return;
        }
        //swaps the ace with the provided ace
        dealerHand.setCard(indexOfAce, card);
    }
    /**
     * gives a card off the top(at the end) of the playing deck
     *
     * PRECONDITION: playing deck has at least one card
     * @return a card off the playing deck
     */
    public Card dealcard(){
        Card cardToDeal = mainDeck.drawFromPlayingDeck();
        if(cardToDeal == null){
            System.err.println("ERROR: Can't draw from a empty playing deck");
        }
        cardToDeal.flip(); // flips card from deck then deals it
        return cardToDeal;
    }
    /**
     * adds a card to dealers hand
     *
     * PRECONDITION: card passed in isn't null
     * POSTCONDITION: adds card to dealer's hand
     * @param card
     */
    public void addCardToDealerHand(Card card){
        if(card == null){
            System.err.println("ERROR: Can't add null card");
            return;
        }
        if(card.isAce()) numOfAces++;
        dealerHand.AddCard(card);
    }
    /**
     * clears previous playing deck and creates a new ordered standard 52 card playing deck
     */
    public void resetMainDeck(){
        mainDeck = new PlayingDeck();
        mainDeck.BuildPlayingDeck();
    }

    //RENDERING METHODS
    /**
     * draws the curent hand of the dealer
     * @param g
     * @param x
     * @param y
     * @param spacingBetweenCards
     * @param cardScale
     */
    private void drawDealerHand(Graphics g, int x, int y, int spacingBetweenCards, double cardScale){
        int imgWidth = (new Card(CardNumber.ACE,Suit.CLUB, 1)).getCardFrontImage().getWidth();
        imgWidth = (int)((double)imgWidth*cardScale);
        for(int i = 0; i < dealerHand.size(); i++){
            (dealerHand.getCard(i)).draw(g,x,y,cardScale);
            x+=imgWidth+10;
        }
    }
    /**
     * draws the main deck as stacked cards
     * @param g
     * @param x
     * @param y
     * @param cardScale
     */
    private void drawMainDeck(Graphics g, int x, int y, double cardScale){
        for(int i = 0; i < 10; i ++){
            mainDeck.getCard(i).draw(g,x,y,cardScale);
            x -= 1;
            y -= 1;
        }
    }

    //TODO: Adda a update method to handle if the dealer has finsihed his turn and the draw logic
    /**
     * updates if the dealer is still playing, if their hand is greater than 17 and less than 21 they stand
     */
    @Override
    public void update() {
        if(dealerHand.getSum() >= 17){
            isPlaying = false;
        }
    }

    /**
     *draws the dealer and the main deck
     */
    @Override
    public void render(Graphics g) {
        //hard coded the location of dealers hand and the main deck
        drawDealerHand(g,dealerHandX,dealerHandY,0,cardScale);
        drawMainDeck(g,800,50,0.25);
    }

    /**
     * @return a string label with dealer and toStinrg() of deck
     */
    public String toString(){
        return "Dealer "+ ": " + dealerHand.toString();
    }
}
