package com.mygdx.game.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Main;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;

public class Plasma extends InteractiveObject {

    public Plasma(PlayScreen screen, Rectangle bounds){
        super(screen,bounds);
        fixture.setUserData(this);
        setCategoryFilter(Main.PLASMA_BIT);
    }

    @Override
    public void onBodyHit() {
        setCategoryFilter(Main.COLLECT_BIT);
        getCell().setTile(null);
        //adds 10 to score
        Hud.addScore(20);
        //adds 2 to plasmaCount
        Hud.addPlasma(3);
    }
}