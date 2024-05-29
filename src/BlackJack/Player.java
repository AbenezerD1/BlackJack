package BlackJack;

import java.awt.*;

public class Player implements Renderable{
    private Deck playerHand;
    private int playerNum;
    private boolean IsPlaying;
    private int countAces = 0;
    private int chipBalance = 0;
    public Player(int playerNum){
        setPlayerHand(new Deck());
        setPlayerNum(playerNum);
        setTurn(false);
        this.chipBalance = 1000; //starts with 1000 chips
    }
    public Player(int playerNum, Deck deck, int chipBalance){
        setPlayerHand(deck);
        setPlayerNum(playerNum);
        setTurn(false);
        this.chipBalance = chipBalance;

        StateHandler.addTick(States.SINGLE_PLAYER,this);
        StateHandler.addTick(States.TWO_PLAYER,this);
    }
    public Deck getPlayerHand() {
        return new Deck(playerHand);
    }
    public int getChipBalance() {
        return chipBalance;
    }
    public void setPlayerHand(Deck playerHand) {
        if (playerHand == null){
            System.err.println("ERROR: Can't set player hand since deck passed was null");
            return;
        }
        this.playerHand = new Deck(playerHand);
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
    public boolean isTurn() {
        return IsPlaying;
    }
    public void setTurn(boolean turn) {
        this.IsPlaying = turn;
    }

    /**
     * adds a card to the players deck
     * PRECONDITION: players turn
     * POSTCONDITION: card is added to deck
     * @param card
     */
    public void hit(Card card){
        if(card.isAce()) countAces++;
        playerHand.AddCard(card);
    }

    public int getCountAces() {
        return countAces;
    }
    /**
     * Lets player decide the value of an ace
     * @param indexOfAce
     * @param card
     * @return
     */
    public void updateAceValue(int indexOfAce, Card card){
        if(card.getCardNum() != CardNumber.ACE){
            System.err.println("ERROR: Has to be a ace to update its value");
            return;
        }
        //checks if it doesn't have the same suit as the ace being replaced
        if(card.getSuit() != playerHand.getCard(indexOfAce).getSuit()){
            System.err.println("ERROR: Has to be a ace to update its value");
            return;
        }
        if((card.getCardPointValue() != 1) && (card.getCardPointValue() != 11)){
            System.err.println("ERROR: Can't update ace to a number that isn't either a 1 or a 11");
            return;
        }
        //swaps the ace with the provided ace
        playerHand.setCard(indexOfAce, card);
    }

    /**
     * places a ber if bet is either greater than the chips balance or if it makes player go negative it returns false
     * @param numOfChips
     * @return
     */
    public boolean placeBet(int numOfChips){
        if(chipBalance-numOfChips < 0) return false;
        if(numOfChips > chipBalance) return false;
        chipBalance -= numOfChips;
        return true;
    }

    /**
     * renders a player's hand at a location x,y where x and y are the first cards top left corner
     * @param g
     * @param x
     * @param y
     */
    public void drawPlayerHand(Graphics g, int x, int y, double cardScale){
        int imgWidth = (new Card(CardNumber.ACE,Suit.CLUB, 1)).getCardFrontImage().getWidth();
        imgWidth = (int)((double)imgWidth*cardScale);
        for(int i = 0; i < playerHand.size(); i++){
            (playerHand.getCard(i)).draw(g,x,y,cardScale);
            x+=imgWidth+10;
            //x += ((dealerHand.getCard(i)).getImage().getWidth())+spacingBetweenCards;
        }
    }

    //TODO: Adda a update method to handle if the player has finsihed his turn and the draw logic
    @Override
    public void update() {
        /**
         * POSSIBILITIES
         * 1. Player has cards that are under 21
         * 2. Player gotten card that makes over 21
         *
         */


        if(playerHand.getSum() > 21){
            StateHandler.currentState = States.PLAYER_LOST;
        } else if (playerHand.getSum() == 21) {

        }
    }

    /**
     */
    @Override
    public void render(Graphics g) {
        //TODO: Add a check if state is two player and player number to correctly place the player
        drawPlayerHand(g,100,450,0.25);
    }

    public String toString(){
        return "Player "+playerNum+ ": " + playerHand.toString();
    }


}
