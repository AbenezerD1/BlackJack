import java.awt.*;

public class Player{
    private Deck playerHand;
    private int playerNum;
    private boolean myTurn;
    private int chipBalance = 0;
    public Player(int playerNum){
        setPlayerHand(new Deck());
        setPlayerNum(playerNum);
        setMyTurn(false);
        this.chipBalance = 1000; //starts with 1000 chips
    }
    public Player(int playerNum, Deck deck, int chipBalance){
        setPlayerHand(deck);
        setPlayerNum(playerNum);
        setMyTurn(false);
        this.chipBalance = chipBalance;
    }
    public Deck getPlayerHand() {
        return new Deck(playerHand);
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
    public boolean isMyTurn() {
        return myTurn;
    }
    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    /**
     * adds a card to the players deck
     * PRECONDITION: players turn
     * POSTCONDITION: card added to deck
     * @param card
     */
    public void hit(Card card){
        playerHand.AddCard(card);
    }

    /**
     * Lets player decide the value of an ace
     * @param indexOfAce
     * @param card
     * @return
     */
    public void updateAceValue(int indexOfAce, Card card){
        if(card.cardVal() != CardValues.ACE){
            System.err.println("ERROR: Has to be a ace to update its value");
            return;
        }
        //checks if it doesn't has the same suit as the ace being replaced
        if(card.suit() != playerHand.getCard(indexOfAce).suit()){
            System.err.println("ERROR: Has to be a ace to update its value");
            return;
        }
        if((card.cardSumValue() != 1) && (card.cardSumValue() != 10)){
            System.err.println("ERROR: Can't update ace to a number that isn't either a 1 or a 10");
            return;
        }
        //swaps the ace with the provided ace
        playerHand.setCard(indexOfAce, card);
    }

    public boolean placeBet(int numOfChips){
        if(chipBalance-numOfChips < 0) return false;
        chipBalance -= numOfChips;
        return true;
    }

    public void drawPlayerHand(Graphics g, int x, int y, int spacingBetweenCards){
        for(Card card: playerHand.getDeck()){
            card.draw(g,x,y);
            x += card.image().getWidth() + spacingBetweenCards;
        }
    }

    public String toString(){
        return "Player "+playerNum+ ": " + playerHand.toString();
    }
}
