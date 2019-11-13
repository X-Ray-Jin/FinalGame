package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;

public abstract class Enemies extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Enemies(PlayScreen screen, float x, float y){
        this.world=screen.getWorld();
        this.screen=screen;
        setPosition(x,y);
        defineEnemies();
    }

    protected abstract void defineEnemies();
    public abstract void hitByBullet();
}