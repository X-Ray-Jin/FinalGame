package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.PlayScreen;

public class Main extends Game {

	//virtual width/height in game
    //game screen size
	public static final int V_WIDTH = 500;
	public static final int V_HEIGHT = 240;
	//scaling pixels per meter
	public static final float PPM = 100;
	public SpriteBatch batch;

	public static final short GROUND_BIT = 1;
	public static final short ALIEN_BIT = 2;
	public static final short PLASMA_BIT = 4;
	public static final short COLLECT_BIT = 8;
	public static final short ENEMY_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short BULLET_BIT = 64;
	public static final short SOLDIER_DEATH_BIT=128;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	

}
