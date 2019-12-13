package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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
        Hud.addScore(10);
        //adds 2 to plasmaCount
        Hud.addPlasma(1);
    }
  /*  @Override
    public void dispose(){
        setCategoryFilter(Main.BULLET_BIT);
        getCell().setTile(null);
        //PlasmaBullet.bulletDead=true;
    }*/
 /* @Override
  public void bulletDissapear(){
      setCategoryFilter(Main.BULLET_BIT);
      getCell().setTile(null);
     // world.destroyBody(Main.BULLET_BIT);
  }*/


}

