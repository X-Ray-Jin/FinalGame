package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Main;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Plasma;

public class E51WorldCreator {
    public E51WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //ground fixtures
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ Main.PPM, (rect.getY() + rect.getHeight() / 2)/Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2/Main.PPM, rect.getHeight() / 2/Main.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //boxes/crates fixtures
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ Main.PPM, (rect.getY() + rect.getHeight() / 2)/Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2/Main.PPM, rect.getHeight() / 2/Main.PPM);
            fdef.shape = shape;

            fdef.filter.categoryBits = Main.OBJECT_BIT;

            body.createFixture(fdef);


        }
        //Plasma fixtures
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();


            new Plasma(screen, rect);
        }
        //Wall fixtures
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ Main.PPM, (rect.getY() + rect.getHeight() / 2)/Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2/Main.PPM, rect.getHeight() / 2/Main.PPM);
            fdef.shape = shape;

            fdef.filter.categoryBits = Main.OBJECT_BIT;

            body.createFixture(fdef);

        }
    }
}
