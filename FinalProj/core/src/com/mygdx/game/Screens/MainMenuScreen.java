package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

public class MainMenuScreen extends Main implements Screen{
    Texture backgroundImage;
    Sprite backgroundSprite;
    private Music music;
    final Main game;

    PlayScreen playScreen;
    InstructionScreen instructionScreen;
    //PauseScreen pauseScreen;
    GameOverScreen gameOverScreen;

    OrthographicCamera camera;

    private Stage stage;
    private Label startLabel;
    private Label instructionLabel;
    private Label quitLabel;
    public BitmapFont font;


    public MainMenuScreen(final Main gam) {
        backgroundImage = new Texture ("MainMenu.png");
        backgroundSprite = new Sprite(backgroundImage);
        // backgroundSprite.setBounds(0f,0f,1200f,); ///or just the width and height of screen
        game = gam;

        gameOverScreen = new GameOverScreen(game, this);
        playScreen = new PlayScreen(game,gameOverScreen);
        //pauseScreen = new PauseScreen(game, playScreen);
        instructionScreen = new InstructionScreen(game);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        music = Main.manager.get("Audio/Chiptronical.ogg", Music.class);
        music.setLooping(true);
        music.play();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        backgroundSprite.draw(game.batch);
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

        startLabel = new Label("Play", new Label.LabelStyle(font,Color.GREEN));
        startLabel.setPosition(115,230);
        startLabel.setTouchable(Touchable.enabled);
        startLabel.setBounds(115,230,startLabel.getWidth(),startLabel.getHeight());
        startLabel.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                playScreen.setState(State.RUN);
                game.setScreen(playScreen);
                dispose();
            }
        });

        instructionLabel = new Label("Instructions", new Label.LabelStyle(font,Color.GREEN));
        instructionLabel.setPosition(115,190);
        instructionLabel.setTouchable(Touchable.enabled);
        instructionLabel.setBounds(115,190,instructionLabel.getWidth(),instructionLabel.getHeight());
        instructionLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(instructionScreen);
                dispose();
            }
        });


        quitLabel = new Label("Quit", new Label.LabelStyle(font,Color.GREEN)); // ned a font
        quitLabel.setPosition(115,150);
        quitLabel.setTouchable(Touchable.enabled);
        quitLabel.setBounds(115,150,quitLabel.getWidth(),quitLabel.getHeight());
        quitLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
                dispose();
            }
        });

        //these are my 3 labels
        stage.addActor(startLabel);
        stage.addActor(quitLabel);
        stage.addActor(instructionLabel);
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
        startLabel.setTouchable(Touchable.disabled);
        quitLabel.setTouchable(Touchable.disabled);
        instructionLabel.setTouchable(Touchable.disabled);
    }
}