package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Alien;
import com.mygdx.game.Sprites.BossLevel1;
import com.mygdx.game.Sprites.Enemies;
import com.mygdx.game.Sprites.PlasmaBullet;

import com.mygdx.game.Sprites.Soldier;
import com.mygdx.game.Tools.E51WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;
import com.mygdx.game.Sprites.Soldier;

public class PlayScreen implements Screen {
    private Main game;

    private TextureAtlas atlas;
    private TextureAtlas bullAtlas;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private E51WorldCreator creator;


    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //box2d start
    private World world;
    private Box2DDebugRenderer b2dr;

    //sprite(s)
    private Alien player;
    private Soldier soldier;
    private PlasmaBullet pBullet;
    private BossLevel1 boss1;





    public PlayScreen(Main game){
        atlas = new TextureAtlas("Alien_And_Enemies.atlas");
        bullAtlas = new TextureAtlas("Plasma_Ammo.atlas");

        this.game=game;



        gamecam= new OrthographicCamera();
        //game screen set size: FitviewPort maintains aspect ratio
        gamePort = new FitViewport(Main.V_WIDTH / Main.PPM, Main.V_HEIGHT/Main.PPM, gamecam);
        hud = new Hud(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("1-1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/Main.PPM);



        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        //gravity
        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new E51WorldCreator(this);
        //creating player-Alien
        player=new Alien(this);

        //soldier = new Soldier(this,.32f,.32f);

        pBullet = new PlasmaBullet(this, player.getX(), player.getY());

        world.setContactListener(new WorldContactListener());





    }



        public void shootTrigger() {
                pBullet = new PlasmaBullet(this, player.getX(), player.getY());

                if(player.isRunningRight()==true) {
                    pBullet.b2body.applyLinearImpulse(new Vector2(5.5f, 2), pBullet.b2body.getWorldCenter(), true);
                }
                else{
                    pBullet.b2body.applyLinearImpulse(new Vector2(-5.5f, 2), pBullet.b2body.getWorldCenter(), true);

                }

        }


    public TextureAtlas getAtlat(){
        return atlas;
    }
    public TextureAtlas getBulletAtlas(){
        return bullAtlas;
    }

    @Override
    public void show() {


    }
    public void handleInput(float dt) {

        if (player.currentState != Alien.State.DEAD) {
            if (hud.getPlasmaCount() > 0) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    shootTrigger();
                    Hud.minusPlasma(1);
                }
            }
          if(player.jumpCount !=3) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                    player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);

                if (Gdx.input.isKeyJustPressed(Input.Keys.W))
                    player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);
                player.jumpCount++;
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                    || Gdx.input.isKeyPressed(Input.Keys.D)) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if ((Gdx.input.isKeyPressed(Input.Keys.LEFT)
                    || Gdx.input.isKeyPressed(Input.Keys.A)) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }
    //any inputs detected
    public void update(float dt){
        handleInput(dt);
        //How many times to calculate per second-how 2 bodies react on collision (higher = more precise)
        world.step(1/60f,6,2);

        player.update(dt);

        pBullet.update(dt);

       // soldier.update(dt);
        for(Enemies enemies : creator.getSoldiers()) {
            enemies.update(dt);
            //delay start soldier movement + activate it
            if(enemies.getX()<player.getX()+2.8f)
                enemies.b2body.setActive(true);
        }
        for(Enemies enemies : creator.getBoss1()) {
            enemies.update(dt);
            //delay start soldier movement + activate it
            if(enemies.getX()<player.getX()+1.6f)
                enemies.b2body.setActive(true);
        }


        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y;
        //update cam view
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);


        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        //recognize camera in game world:only render what gamecam see's
        //game.batch.setProjectionMatrix(gamecam.combined);
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);

       // soldier.draw(game.batch);
        for(Enemies enemies : creator.getSoldiers())
            enemies.draw(game.batch);
        for(Enemies enemies : creator.getBoss1())
            enemies.draw(game.batch);

        if(player.isRunningRight()) {


            pBullet.draw(game.batch);
        }
        else{
            pBullet.draw(game.batch);
            pBullet.flip(true,false);


        }


        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        //resizes the game to fit screen
        gamePort.update(width,height);

    }
    public TiledMap getMap(){
        return map;

    }
    public World getWorld(){
        return world;
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
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        map.dispose();


    }
}
