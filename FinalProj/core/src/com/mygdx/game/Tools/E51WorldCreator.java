package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.BossLevel1;
import com.mygdx.game.Sprites.BossLevel2;
import com.mygdx.game.Sprites.Plasma;
import com.mygdx.game.Sprites.PlasmaBullet;
import com.mygdx.game.Sprites.Soldier;
import com.mygdx.game.Sprites.SpriteObject;

public class E51WorldCreator {
    public Array<SpriteObject> getObjects() {
        return objects;
    }

    private PlayScreen screen;
    private Array<SpriteObject> objects = new Array<>();

    public E51WorldCreator(PlayScreen screen) {
        this.screen = screen;
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //ground fixtures
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2) / Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM, rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //boxes/crates fixtures
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2) / Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM, rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;

            fdef.filter.categoryBits = Main.OBJECT_BIT;

            body.createFixture(fdef);


        }

        //Plasma fixtures
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();


            new Plasma(screen, rect);
        }

        //Wall fixtures
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2) / Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM, rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;

            fdef.filter.categoryBits = Main.OBJECT_BIT;

            body.createFixture(fdef);

        }

        //ExitDoor
        for (MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2) / Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM, rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;

            fdef.filter.categoryBits = Main.LEVEL_SWITCH_BIT;

            body.createFixture(fdef);
        }

        //creating all soldiers
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();


            objects.add(new Soldier(screen, rect.getX() / Main.PPM, rect.getY() / Main.PPM));
        }

        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();


            objects.add(new BossLevel1(screen, rect.getX() / Main.PPM, rect.getY() / Main.PPM));
        }

        for (MapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();


            objects.add(new BossLevel2(screen, rect.getX() / Main.PPM, rect.getY() / Main.PPM));
        }
    }

    public void shootPlasmaBullet() {
        PlasmaBullet pBullet = new PlasmaBullet(screen,
                        screen.getPlayer().getX(), screen.getPlayer().getY());
        objects.add(pBullet);

        if (screen.getPlayer().isRunningRight() == true) {
            pBullet.getBody().applyLinearImpulse(new Vector2(4.5f, 1f),
                            pBullet.getBody().getWorldCenter(), true);
        } else {
            pBullet.getBody().applyLinearImpulse(new Vector2(-4.5f, 1f),
                            pBullet.getBody().getWorldCenter(), true);
        }
    }
}
