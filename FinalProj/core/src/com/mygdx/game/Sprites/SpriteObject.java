package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;

public abstract class SpriteObject extends Sprite {
    protected World world;
    protected PlayScreen screen;
    protected Body b2body;

    private boolean markedForRemove = false;

    public SpriteObject(PlayScreen screen, float x, float y) {
        super();
        this.screen = screen;
        world = screen.getWorld();
        setPosition(x, y);
        defineBody();
    }

    protected abstract void defineBody();

    public abstract void update(float deltaT);

    public Body getBody() {
        return b2body;
    }

    public void markForRemoval(boolean mark) {
        markedForRemove = mark;
    }

    public boolean isMarkedForRemoval() {
        return markedForRemove;
    }

}