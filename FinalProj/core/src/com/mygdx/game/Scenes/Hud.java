package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;

import com.badlogic.gdx.graphics.Color;





public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;

    private static Integer score;
    private static Integer plasmaCount;

    // Label plasmaLabel
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;
    private static Label plasmaLabel;
    private static Label plasmaIncrease;
    public Hud(SpriteBatch sb){
        worldTimer = 300;
        plasmaCount = 1000;
        score = 0;

        viewport = new FitViewport(Main.Game_WIDTH, Main.Game_HEIGHT, new OrthographicCamera());
        stage = new Stage (viewport, sb);

        Table table = new Table();
        //puts table at top of screen
        table.top();
        //table is size of stage
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        scoreLabel = new Label(String.format("%05d", score), new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        plasmaIncrease = new Label(String.format("%03d", plasmaCount),new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        worldLabel = new Label("Level", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        marioLabel = new Label("Escaping 51:", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        plasmaLabel = new Label("Plasma:", new Label.LabelStyle(new BitmapFont(),Color.GREEN));


        //evenly spaces out labels on top of screen

        table.add(marioLabel).expandX().padTop(0);
        table.add(worldLabel).expandX().padTop(0);
       // table.add(timeLabel).expandX().padTop(10);
        table.add(plasmaLabel).expandX().padTop(0);
        table.row();

        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(plasmaIncrease).expandX();
        //table.add(countdownLabel).expandX();

        stage.addActor(table);
    }
    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%05d", score));


    }
    public static void addPlasma(int pValue){
        plasmaCount +=pValue;
        plasmaIncrease.setText(String.format("%03d",plasmaCount));
    }
    public static void minusPlasma(int pValue){
        plasmaCount -=pValue;
        plasmaIncrease.setText(String.format("%03d",plasmaCount));
    }
    public int getPlasmaCount(){
        return plasmaCount;
    }
    public static void setPlasmaCount(int pCount){
        plasmaCount = pCount;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

