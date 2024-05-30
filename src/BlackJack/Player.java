package BlackJack;

import java.awt.*;

public class Player implements Renderable{
    private Deck playerHand;
    private int playerNum; //player number if using multiplayer
    private boolean isPlaying; //if player is able to draw cards
    private int countAces = 0; // number of aces a player has
    private int chipBalance = 0;
    private int drawHandX=0, drawHandY=0; //position of where the players hand is being drawn
    private double cardScale=0.25; //size of the players cards

    //CONSTRUCTORS

    /**
     * PRECONDITION: player number is above 0, x and y of player hand is greater than zero, and card scale is greater than zero
     * @param playerNum player number
     * @param drawHandX x location of the player hand
     * @param drawHandY y location of the player hand
     * @param cardScale scale of the card
     */
    public Player(int playerNum, int drawHandX, int drawHandY, double cardScale){
        setDrawHandPosition(drawHandX,drawHandY);
        setDrawHandScale(cardScale);
        setPlayerHand(new Deck());
        setPlayerNum(playerNum);
        setTurn(true);
        this.chipBalance = 1000; //starts with 1000 chips

        //adds player to correct state's renederable elements so it can be rendered by state handler
        if(StateHandler.currentState == States.SINGLE_PLAYER){
            StateHandler.addRenderableElement(States.SINGLE_PLAYER,this);
        }else if (StateHandler.currentState == States.TWO_PLAYER){
            StateHandler.addRenderableElement(States.TWO_PLAYER,this);
        }
    }

    /**
     * uses a given player number, deck, and chip balance to create a player
     * @param playerNum player number that is greater than zero
     * @param deck deck that is not null
     * @param chipBalance
     */
    public Player(int playerNum, Deck deck, int chipBalance){
        setPlayerHand(deck);
        setPlayerNum(playerNum);
        setTurn(true);
        this.chipBalance = chipBalance;

        //adds player to correct state's renederable elements so it can be rendered by state handler
        if(StateHandler.currentState == States.SINGLE_PLAYER){
            StateHandler.addRenderableElement(States.SINGLE_PLAYER,this);
        }else if (StateHandler.currentState == States.TWO_PLAYER){
            StateHandler.addRenderableElement(States.TWO_PLAYER,this);
        }
    }

    //GETTERS

    /**
     * @return current players hand as a deck of cards
     */
    public Deck getPlayerHand() {
        return new Deck(playerHand);
    }
    /**
     * @return current players chip balance
     */
    public int getChipBalance() {
        return chipBalance;
    }
    /**
     * @return current players number
     */
    public int getPlayerNum() {
        return playerNum;
    }
    /**
     * @return current players number of aces in  players hand
     */
    public int getCountAces() {
        return countAces;
    }
    /**
     * @return if player is still able to draw cards
     */
    public boolean isPlaying() {
        if(reducedPlayerAceSum() > 21){
            isPlaying = false;
            return isPlaying;
        }
        return isPlaying;
    }

    //SETTERS

    /**
     * sets player hand to a given deck of cards
     * @param playerHand a deck of cards that isn't null
     */
    public void setPlayerHand(Deck playerHand) {
        if (playerHand == null){
            System.err.println("ERROR: Can't set player hand since deck passed was null");
            return;
        }
        this.playerHand = new Deck(playerHand);
    }

    /**
     * sets player number using a given player number
     * @param playerNum
     */
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    /**
     * @param turn change whether the player can draw cards
     */
    public void setTurn(boolean turn) {
        this.isPlaying = turn;
    }

    /**
     * adjusts the location of the players hand using the given x,y coordinates
     * @param x value of the location of the players hand
     * @param y value of the location of the players hand
     */
    public void setDrawHandPosition(int x, int y){
        this.drawHandX = x;
        this.drawHandY = y;
    }

    /**
     * sets the scale of players cards
     * @param scale size of players cards
     */
    public void setDrawHandScale(double scale){
        this.cardScale = scale;
    }

    //ACTIONS/HELPERS
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
    public void setNumOfChips(int numOfChips){
        if(numOfChips < 0) return;
        chipBalance = numOfChips;
    }
    /**
     * Lets player decide the value of an ace
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

    public int reducedPlayerAceSum(){
        int playerSum = playerHand.getSum();
        int aces = getCountAces();
        while(playerSum > 21 && aces > 0){
            playerSum -= 10;
            aces -= 1;
        }
        return playerSum;
    }

    //RENDERING
    /**
     * renders a player's hand at a location x,y where x and y are the first cards top left corner
     * @param g Graphics object
     * @param x coordinate of player hand
     * @param y coordinate of player hand
     */
    public void drawPlayerHand(Graphics g, int x, int y, double cardScale){
        int imgWidth = (new Card(CardNumber.ACE,Suit.CLUB, 1)).getCardFrontImage().getWidth();
        imgWidth = (int)((double)imgWidth*cardScale);
        for(int i = 0; i < playerHand.size(); i++){
            (playerHand.getCard(i)).draw(g,x,y,cardScale);
            x+=imgWidth+10;
            if(StateHandler.currentState == States.TWO_PLAYER){
                if(playerNum == 1){
                    if(x > 400){
                        x = 30;
                        y += 125;
                    }
                } else{
                    if(x > 950){
                        x = 540;
                        y += 125;
                    }
                }
            }
            //x += ((dealerHand.getCard(i)).getImage().getWidth())+spacingBetweenCards;
        }
    }

    public void drawCurrentBet(Graphics g, int x, int y, int fontSize){
        g.setColor(Color.YELLOW);
        g.setFont(new Font(Font.SERIF,Font.BOLD, fontSize));
        g.drawString("Chip Balance: "+chipBalance,x,y);
    }
    //TODO: Adda a update method to handle if the player has finsihed his turn and the draw logic
    @Override
    public void update() {
        if(reducedPlayerAceSum() > 21){
            isPlaying = false;
        }
    }

    /**
     */
    @Override
    public void render(Graphics g) {
        //TODO: Add a check if state is two player and player number to correctly place the player
        if(StateHandler.currentState == States.SINGLE_PLAYER){
            drawCurrentBet(g,50,750,25);
            drawPlayerHand(g, drawHandX, drawHandY, cardScale);
        }else{
            drawPlayerHand(g, drawHandX, drawHandY, cardScale);
            if(playerNum == 1){
                drawCurrentBet(g,10,750,15);
            }else{
                drawCurrentBet(g,520,750,15);
            }
        }

    }

    public String toString(){
        return "Player "+playerNum+ ": " + playerHand.toString();
    }


}
