public class Player{
    private Deck playerHand;
    private int playerNum;
    public Player(int playerNum){
        setPlayerHand(new Deck());
        setPlayerNum(playerNum);
    }

    public Player(int playerNum, Deck deck){
        setPlayerHand(deck);
        setPlayerNum(playerNum);
    }
    public Deck getPlayerHand() {
        return new Deck(playerHand);
    }

    public void setPlayerHand(Deck playerHand) {
        this.playerHand = new Deck(playerHand);
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
}
