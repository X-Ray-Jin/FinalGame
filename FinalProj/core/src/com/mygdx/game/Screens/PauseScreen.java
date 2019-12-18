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



public class PauseScreen extends Main implements Screen {
    Texture background;
    Sprite backgroundSprite;

    final Main game;



    OrthographicCamera camera;

    private Stage stage;
    private Label resume;
    private Label paused;
    PlayScreen playScreen;

    public BitmapFont font;


    public PauseScreen(final Main gam, PlayScreen playScreen) {
        background = new Texture("InstructionsMenu.png");
        backgroundSprite = new Sprite(background);
        // backgroundSprite.setBounds(0f,0f,1200f,); ///or just the width and height of screen
        game = gam;
        this.playScreen = playScreen;
        // playScreen = new PlayScreen(game);




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

            playScreen.setState(State.RUN);
            game.setScreen(playScreen);
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

        resume = new Label("RESUME", new Label.LabelStyle(font,Color.GREEN));
        resume.setPosition(115,230);
        resume.setTouchable(Touchable.enabled);
        resume.setBounds(115,230,resume.getWidth(),resume.getHeight());
        resume.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                //playScreen.setState(State.PAUSE);
                game.setScreen(playScreen);
                // dispose();
            }
        });
        paused = new Label("Paused", new Label.LabelStyle(font, Color.GREEN));
        paused.setPosition(400, 300);

        stage.addActor(resume);
        stage.addActor(paused);





        //these are my 3 labels
        stage.addActor(resume);

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
        resume.setTouchable(Touchable.disabled);

    }


}
