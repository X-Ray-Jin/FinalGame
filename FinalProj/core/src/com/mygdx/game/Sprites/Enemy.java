package com.mygdx.game.Sprites;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Screens.PlayScreen;

public abstract class Enemy extends SpriteObject {

    public Vector2 velocity;

    public Enemy(PlayScreen screen, float x, float y){
        super(screen, x, y);
        velocity = new Vector2(1.25f,0);
        b2body.setActive(false);
    }

    public abstract void hitByBullet();

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
}