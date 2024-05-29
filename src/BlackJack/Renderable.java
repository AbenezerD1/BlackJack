package BlackJack;

import java.awt.*;
public interface Renderable {
    //TODO: Add a update method to upaate the game status when dealer or player state changes
    void render(Graphics g);
    void update();
}
