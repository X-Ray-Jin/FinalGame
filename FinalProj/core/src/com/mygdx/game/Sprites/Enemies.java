package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;

public abstract class Enemies extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Vector2 velocity;
    public Body b2body;
    public Enemies(PlayScreen screen, float x, float y){
        this.world=screen.getWorld();
        this.screen=screen;
        setPosition(x,y);
        defineEnemies();
        velocity = new Vector2(1.5f,0);
        b2body.setActive(false);
    }

    protected abstract void defineEnemies();
    public abstract void hitByBullet();

    public abstract void update(float dt);

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
}