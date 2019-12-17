package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.MainMenuScreen;

public class Main extends Game {

    public enum State {
        RUN, PAUSE, END
    }

    //virtual width/height in game
    //game screen size
    public static final int Game_WIDTH = 500;
    public static final int Game_HEIGHT = 240;

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
    public static final short SOLDIER_DEATH_BIT = 128;
    public static final short DISSAPEAR_BIT = 256;
    public static final short BOSS1_BIT = 512;
    public static final short LEVEL_SWITCH_BIT = 1024;
    public static final short BOSS2_BIT = 2048;
	public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("Audio/Chiptronical.ogg", Music.class);

		manager.load("Audio/laser7.ogg", Sound.class);
		manager.load("Audio/death.ogg", Sound.class);
		//setScreen(new PlayScreen(this));
		manager.finishLoading();
		this.setScreen(new MainMenuScreen(this));
	}

    @Override
    public void render() {
        super.render();
    }
}