package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;

import javax.swing.ViewportLayout;

public class PauseScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    PlayScreen playScreen;

    public PauseScreen(final Game game){
        this.game = game;
        viewport = new FitViewport(Main.Game_WIDTH, Main.Game_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((Main)game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.GREEN);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("PAUSED", font);
        Label playAgainLabel = new Label("Play Again", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);
      //  playScreen = new PlayScreen(this);


    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            game.setScreen(playScreen);
            //dispose();
        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
       stage.dispose();

    }


}

