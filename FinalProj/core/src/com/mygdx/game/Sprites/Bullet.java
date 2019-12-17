package com.mygdx.game.Sprites;
import com.mygdx.game.Screens.PlayScreen;

public abstract class Bullet extends SpriteObject {

    public Bullet(PlayScreen screen, float x, float y) {
        super(screen, x, y);
    }

    public abstract void onHit();
}