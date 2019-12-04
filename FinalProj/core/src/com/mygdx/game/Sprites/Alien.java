package com.mygdx.game.Sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main;
import com.mygdx.game.Screens.PlayScreen;

public class Alien extends Sprite {

    public World world;

    public enum State {FALLING, JUMPING, STANDING, RUNNING, SHOOTING, DEAD}
    public State currentState;
    public State previousState;
    public Body b2body;
    private TextureRegion alienIdle;
    private boolean alienIsDead;

    private Animation <TextureRegion> alienRun;
    private Animation <TextureRegion> alienJump;
    private Animation <TextureRegion> alienShoot;
    private Animation <TextureRegion> alienDead;
    private float stateTimer;
    private boolean runningRight;
    public int jumpCount=0;

    public Alien(PlayScreen screen) {
        //Take in the alien sprite sheet
        super(screen.getAtlat().findRegion("Small_Alien"));

        this.world = screen.getWorld();
        defineAlien();

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 6; i < 9; i++)
            frames.add(new TextureRegion(getTexture(), i * 34, 4, 32, 32));
        alienRun = new Animation(0.11f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 34, 4, 32, 32));
        alienJump = new Animation(0.11f, frames);
       // frames.clear();

        for (int i =9; i < 11; i++)
            frames.add(new TextureRegion(getTexture(), i * 34, 4, 32, 32));
        alienShoot = new Animation(0.06f, frames);
        for (int i =0; i < 2; i++)
            frames.add(new TextureRegion(getTexture(), i * 34, 4, 32, 32));
        alienDead = new Animation(0.06f, frames);

        //total width is 374px (1,34,68,102,136,170,204,...etc)
        alienIdle = new TextureRegion(getTexture(), 68, 4, 32, 32);
        //set bounds of sprite
        setBounds(0, 0, 32 / Main.PPM, 32 / Main.PPM);
        setRegion(alienIdle);
        alienIsDead=false;
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }
    public boolean isRunningRight(){
        return runningRight;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case DEAD:
                region=alienDead.getKeyFrame(stateTimer);
                break;
            case JUMPING:
                region = alienJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = alienRun.getKeyFrame(stateTimer, true);
                break;
            case SHOOTING:
                region = alienShoot.getKeyFrame(stateTimer);
                break;
            case FALLING:
            case STANDING:
            default:
                region = alienIdle;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState=currentState;

        return region;

    }




    public State getState(){
        if(alienIsDead){
            return State.DEAD;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
            return State.SHOOTING;
        if(b2body.getLinearVelocity().y>0 || (b2body.getLinearVelocity().y <0 && previousState == State.JUMPING))
            return State.JUMPING;
            jumpCount++;
        if(b2body.getLinearVelocity().y<0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x !=0)
            return State.RUNNING;
        else
            return State.STANDING;

        }

    public void hit(){
            alienIsDead=true;
            System.out.println("HIT");

    }
    public void hitGround(){
        jumpCount=0;

    }


    public void defineAlien() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / Main.PPM, 32 / Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;


        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();


        //size of the collision body
        shape.setRadius(13.4f / Main.PPM);

        fdef.filter.categoryBits = Main.ALIEN_BIT;
        fdef.filter.maskBits = Main.GROUND_BIT | Main.PLASMA_BIT|Main.ENEMY_BIT|Main.OBJECT_BIT;

        //setting shape above head
        fdef.shape = shape;



        b2body.createFixture(fdef).setUserData(this);


    }

}
