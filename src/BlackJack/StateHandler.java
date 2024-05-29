package BlackJack;

import java.awt.*;
import java.util.ArrayList;
public class StateHandler {
    public static States currentState;
    private static  ArrayList<Renderable> singlePlayer = new ArrayList<>();
    private static  ArrayList<Renderable> twoPlayer = new ArrayList<>();

    public static void addTick(States state, Renderable r){
        if(r == null ) return;
        switch (state){
            case SINGLE_PLAYER:
                //single player elements
                singlePlayer.add(r);
                break;
            case TWO_PLAYER:
                //two player elements
                twoPlayer.add(r);
                break;
            default:
                System.err.println("ERROR: Undefined state");
                break;
        }
    }

    public static void update(){
        switch (currentState){
            case SINGLE_PLAYER:
                //single player elements
                for(Renderable r: singlePlayer){
                    r.update();
                }
                break;
            case TWO_PLAYER:
                //two player elements
                for(Renderable r: twoPlayer){
                    r.update();
                }
                break;
            default:
                System.err.println("ERROR: Undefined state");
                break;
        }
    }

    public static void render(Graphics g){
        switch (currentState){
            case SINGLE_PLAYER:
                //single player elements
                for(Renderable r: singlePlayer){
                    r.render(g);
                }
                break;
            case TWO_PLAYER:
                //two player elements
                for(Renderable r: twoPlayer){
                    r.render(g);
                }
                break;
            default:
                System.err.println("ERROR: Undefined state");
                break;
        }
    }
}
