package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;
import com.mygdx.game.Main.State;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Alien;
import com.mygdx.game.Sprites.BossLevel1;
import com.mygdx.game.Sprites.BossLevel2;
import com.mygdx.game.Sprites.SpriteObject;
import com.mygdx.game.Tools.E51WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;

public class PlayScreen implements Screen {
    private Main game;

    private TextureAtlas atlas;
    private TextureAtlas bullAtlas;
    private TextureAtlas FBossAtlas;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private MainMenuScreen mainScreen;
    private PauseScreen pauseScreen;
    private GameOverScreen gameOverScreen;

    private E51WorldCreator creator;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //box2d start
    private World world;
    private Box2DDebugRenderer b2dr;

    //sprite(s)
    private Alien player;
    //    private Soldier soldier;
    private BossLevel1 boss1;
    private BossLevel2 boss2;
    public State state = State.RUN;
    public State pause = State.PAUSE;
    private String[] levelNames = {"1-1.tmx", "1-2.tmx" };
    private Array<SpriteObject> objectsToRemove = new Array<>();
    private Sound shoot;
    private boolean switchLevel = false;

    public PlayScreen(Main game, GameOverScreen gameOverScreen){
        this.game = game;
        pauseScreen = new PauseScreen(game, this);
        this.gameOverScreen = gameOverScreen;
        init();
        loadLevel(levelNames[0]);
        shoot = Main.manager.get("Audio/laser7.ogg", Sound.class);
    }

    // initialize all the stuff we need only one time
    private void init() {
        // graphics
        atlas = new TextureAtlas("Alien_And_Enemies.atlas");
        bullAtlas = new TextureAtlas("Plasma_Ammo.atlas");
        FBossAtlas = new TextureAtlas("CHB.atlas");

        // camera, view, user interface, renderer
        gamecam = new OrthographicCamera();
        //game screen set size: FitviewPort maintains aspect ratio
        gamePort = new FitViewport(Main.Game_WIDTH / Main.PPM, Main.Game_HEIGHT/Main.PPM, gamecam);
        hud = new Hud(game.batch);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Main.PPM);

        // utility to load a tmx map
        maploader = new TmxMapLoader();

        // physics
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new WorldContactListener(this));
        b2dr = new Box2DDebugRenderer();

        // level objects creator
        creator = new E51WorldCreator(this);
    }

    public void loadLevel(String levelName) {
        map = maploader.load(levelName);
        renderer.setMap(map);

        // create world objects
        creator.createObjects(map);

        // create player-Alien
        player = new Alien(this);

        gamecam.position.set(gamePort.getWorldWidth() / 2,
                        gamePort.getWorldHeight() / 2, 0);
        switchLevel = false;
    }


    public TextureAtlas getFBossAtlas(){
        return FBossAtlas;
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public TextureAtlas getBulletAtlas(){
        return bullAtlas;
    }

    public Alien getPlayer() {
        return player;
    }

    @Override
    public void show() {
    }

    public void setState(State st){
        state = st;
    }

    /* public void handleInput(float dt) {

        if (player.currentState != Alien.State.DEAD) {
            if (hud.getPlasmaCount() > 0) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    shootTrigger();
                    Hud.minusPlasma(1);
                }
            }
          if(Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {

              if (player.jumpCount < 3) {

                  if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                      player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);

                  if (Gdx.input.isKeyJustPressed(Input.Keys.W))
                      player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);

                  //System.out.println(player.jumpCount);

                  player.alienLanded = false;
                  player.jumpCount++;

              }
          }
            if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                    || Gdx.input.isKeyPressed(Input.Keys.D)) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if ((Gdx.input.isKeyPressed(Input.Keys.LEFT)
                    || Gdx.input.isKeyPressed(Input.Keys.A)) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }*/

    //any inputs detected
    public void update(float dt){
        //handleInput(dt);

        // update player and game objects
        player.update(dt);
        for (SpriteObject object : creator.getObjects()) {
            object.update(dt);
        }

        // update the physics world and handle collisions
        // How many times to calculate per second-how 2 bodies react on collision (higher = more precise)
        world.step(1 / 60f, 6, 2);

        // check objects if they should be removed and collect them
        for (SpriteObject object : creator.getObjects()) {
            if (object.isMarkedForRemoval())
                objectsToRemove.add(object);
        }

        // actually remove the objects and destroy their physics body
        for (SpriteObject object : objectsToRemove) {
            creator.getObjects().removeValue(object, true);
            world.destroyBody(object.getBody());
        }
        // clear the temporal collection of removed objects
        objectsToRemove.clear();

        //update cam view
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y;
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gamecam.combined);
        renderer.render();

        if (switchLevel) {
            loadLevel(levelNames[1]);
        } else {
            game.batch.begin();
            player.draw(game.batch);
            for (SpriteObject object : creator.getObjects()) {
                object.draw(game.batch);
            }
            game.batch.end();
        }

        //recognize camera in game world:only render what gamecam see's
        //game.batch.setProjectionMatrix(gamecam.combined);
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver()) {
            game.setScreen(new GameOverScreen(game, mainScreen));
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if(state == State.RUN){
                setState(state = State.PAUSE);
                game.setScreen(new PauseScreen(game, this));
            }
        }
        switch(state) {
            //Player Inputs
            case RUN:
                if (player.currentState != Alien.State.DEAD) {
                    if (hud.getPlasmaCount() > 0) {
                        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                            creator.shootPlasmaBullet();
                            Hud.minusPlasma(1);
                        }
                    }
                    if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {

                        if (player.jumpCount < 3) {

                            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                                player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);

                            if (Gdx.input.isKeyJustPressed(Input.Keys.W))
                                player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);

                            //System.out.println(player.jumpCount);

                            player.alienLanded = false;
                            player.jumpCount++;

                        }
                    }
                    if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                                    || Gdx.input.isKeyPressed(Input.Keys.D)) && player.b2body.getLinearVelocity().x <= 2)
                        player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                    if ((Gdx.input.isKeyPressed(Input.Keys.LEFT)
                                    || Gdx.input.isKeyPressed(Input.Keys.A)) && player.b2body.getLinearVelocity().x >= -2)
                        player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                }
                break;
            case PAUSE:

                break;
            case END:
                break;
        }
    }

    public void switchLevel() {
        switchLevel = true;
    }


    public boolean gameOver(){
        if(player.currentState==Alien.State.DEAD && player.getStateTimer()>1){
            state = State.RUN;
            return true;
        }
        else{
            return false;
        }
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
        shoot.dispose();
    }
}