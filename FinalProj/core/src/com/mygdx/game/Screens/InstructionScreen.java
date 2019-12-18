package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Main;
import com.mygdx.game.State;



public class InstructionScreen extends Main implements Screen {
    Texture background;
    Sprite backgroundSprite;

    final Main game;



    OrthographicCamera camera;

    private Stage stage;
    private Label Instructions;

    public BitmapFont font;


    public InstructionScreen(final Main gam) {
        background = new Texture("InstructionsMenu.png");
        backgroundSprite = new Sprite(background);
        // backgroundSprite.setBounds(0f,0f,1200f,); ///or just the width and height of screen
        game = gam;




        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        backgroundSprite.draw(game.batch);
        if(Gdx.input.isTouched()){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont(Gdx.files.internal("font2.fnt"));

        Instructions = new Label("Instructions Screen\n" +
                "\n" +
                "-Movement:" +
                "   Use either the direction arrow keys or WSAD keys to \n control your movement.\n" +
                "\n" +
                "-Collect Plasma:\n" +
                "   Collect plasma throughout the map to arm your\n" +
                "\n" +
                "-Shoot:" +
                "   Press the spacebar to fire your plasma at the enemies.\n" +
                "\n" +
                "-Enemies:" +
                "   Attack or avoid the enemies. Contact with the enemies \n will result in GAME OVER." +
                " It takes 3 plasma shots to take down an \n enemy and 7 plasma shots for the boss.\n", new Label.LabelStyle(font, Color.GREEN));
        Instructions.setPosition(20, 10);


        //these are my 3 labels
        stage.addActor(Instructions);

    }


    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        Instructions.setTouchable(Touchable.disabled);

    }


}