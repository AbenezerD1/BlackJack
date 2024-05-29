package BlackJack;

import java.awt.*;

public class Dealer implements Renderable{
    private PlayingDeck mainDeck;
    private Deck dealerHand;
    private boolean isPlaying;
    private int numOfAces = 0;
    private int drawHandX=0, drawHandY=0;
    private double cardScale=0;

    public Dealer(int handX, int handY,double scaleCards){
        setDrawHandPosition(handX,handY);
        setDrawHandScale(scaleCards);
        this.mainDeck = new PlayingDeck();
        this.dealerHand = new Deck();
        this.isPlaying = true;
        this.mainDeck.BuildPlayingDeck();

        StateHandler.addTick(States.SINGLE_PLAYER,this);
        StateHandler.addTick(States.TWO_PLAYER,this);
    }
    public Dealer(PlayingDeck mainDeck, Deck dealerDeck, int numOfAces) {
        this.mainDeck = mainDeck;
        this.dealerHand = dealerDeck;
        this.isPlaying = true;
        this.numOfAces = numOfAces;
        this.mainDeck.BuildPlayingDeck();

        StateHandler.addTick(States.SINGLE_PLAYER,this);
        StateHandler.addTick(States.TWO_PLAYER,this);
    }

    public Dealer(Dealer other){
        this.mainDeck = other.mainDeck;
        this.dealerHand = other.dealerHand;
        this.isPlaying = other.isPlaying;
        this.numOfAces = other.numOfAces;
        this.mainDeck.BuildPlayingDeck();

        StateHandler.addTick(States.SINGLE_PLAYER,this);
        StateHandler.addTick(States.TWO_PLAYER,this);
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

    public void shufflePlayingDeck(){
        mainDeck.ShufflePlayingDeck();
    }

    public void flipMainDeck(){
        mainDeck.flipCards();
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

    public boolean isPlaying() {
        if(dealerHand.getSum() >= 17){
            isPlaying = true;
            return isPlaying;
        }
        return false;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
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
        cardToDeal.flip(); // flips card from deck then deals it
        return cardToDeal;
    }

    public void addCardToDealersDeck(Card card){
        if(card == null){
            System.err.println("ERROR: Can't add null card");
            return;
        }
        if(card.isAce()) numOfAces++;
        dealerHand.AddCard(card);
    }

    public void resetMainDeck(){
        mainDeck = new PlayingDeck();
        mainDeck.BuildPlayingDeck();
    }

    public void setDrawHandPosition(int x, int y){
        this.drawHandX = x;
        this.drawHandY = y;
    }
    public void setDrawHandScale(double scale){
        this.cardScale = scale;
    }

    public void drawDealerHand(Graphics g, int x, int y, int spacingBetweenCards, double cardScale){
        int imgWidth = (new Card(CardValues.ACE,Suit.CLUB, 1)).getImage().getWidth();
        imgWidth = (int)((double)imgWidth*cardScale);
        for(int i = 0; i < dealerHand.Size(); i++){
            (dealerHand.getCard(i)).draw(g,x,y,cardScale);
            x+=imgWidth+10;
            //x += ((dealerHand.getCard(i)).getImage().getWidth())+spacingBetweenCards;
        }
    }

    /**
     * draws the main deck as a stacked cards
     * @param g
     * @param x
     * @param y
     * @param cardScale
     */
    public void drawMainDeck(Graphics g, int x, int y, double cardScale){
        for(int i = 0; i < 5; i ++){
            mainDeck.getCard(i).draw(g,x,y,cardScale);
            x += 5;
            y -= 5;
        }
    }

    public String toString(){
        return "Dealer "+ ": " + dealerHand.toString();
    }

    //TODO: Adda a update method to handle if the dealer has finsihed his turn and the draw logic

    @Override
    public void update() {
        if(dealerHand.getSum() >= 17){
            isPlaying = false;
        }
    }
    public void revealCards() {
        dealerHand.getCard(0).flip();
     }
    /**
     *draws the dealer and the main deck
     */
    @Override
    public void render(Graphics g) {
        //hard coded the location of dealers hand and the main deck
        drawDealerHand(g,drawHandX,drawHandY,0,cardScale);
        drawMainDeck(g,800,50,0.25);
    }
}
