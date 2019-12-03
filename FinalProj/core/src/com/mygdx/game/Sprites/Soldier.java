package com.mygdx.game.Sprites;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;

import static com.mygdx.game.Main.BULLET_BIT;
import static com.mygdx.game.Main.SOLDIER_DEATH_BIT;

public class Soldier extends Enemies {
    public enum State {SRUNNING,SDEAD, SIDLE}
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> deadAnimation;
    private Animation<TextureRegion> idleAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDeath;
    private boolean death;
    public State currentState;
    private int count = 0;

    public Soldier(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i=3;i<6; i++ )
            frames.add(new TextureRegion(screen.getAtlat().findRegion("Small_Enemy"), i *34,4,32,32));
        walkAnimation = new Animation(0.3f,frames);
        for(int i=0;i<2; i++ )
            frames.add(new TextureRegion(screen.getAtlat().findRegion("Small_Enemy"), i *34,4,32,32));
        deadAnimation = new Animation(0.3f,frames);
        for(int i=2;i<3; i++ )
            frames.add(new TextureRegion(screen.getAtlat().findRegion("Small_Enemy"), i *34,4,32,32));
        idleAnimation = new Animation(0.2f,frames);

        stateTime = 0;
        setBounds(getX(),getY(),32/Main.PPM,32/Main.PPM);

        setToDeath = false;
        death = false;
    }
    public void update(float dt){
        stateTime += dt;
            if (setToDeath && !death && count<=3) {
                world.destroyBody(b2body);
                death = true;
                setRegion(deadAnimation.getKeyFrame(stateTime));
                stateTime = 0;
                // setRegion(new TextureRegion(screen.getAtlat().findRegion("Small_Enemy"),34,0,32,32));
                //count =0;
                //adds score and plasma
                Hud.addScore(20);
                Hud.addPlasma(1);
            }

        else if(!death) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }


    }


    @Override
    protected void defineEnemies() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;


        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();


        //size of the collision body
        shape.setRadius(12.8f / Main.PPM);
        //fdef.filter.categoryBits = Main.SOLDIER_DEATH_BIT;
        fdef.filter.categoryBits = Main.ENEMY_BIT;

        fdef.filter.maskBits = Main.GROUND_BIT | Main.PLASMA_BIT|Main.ENEMY_BIT|Main.ALIEN_BIT|Main.OBJECT_BIT|BULLET_BIT;

        //setting shape above head
        fdef.shape = shape;

       // fdef.filter.categoryBits = Main.SOLDIER_DEATH_BIT;
        fdef.filter.categoryBits = Main.ENEMY_BIT;

        b2body.createFixture(fdef).setUserData(this);

       /* CircleShape body = new CircleShape();
        fdef.shape=body;
        body.setRadius(12.8f/Main.PPM);
        fdef.filter.categoryBits = SOLDIER_DEATH_BIT;
        b2body.createFixture(fdef).setUserData(this);

*/

    }
//Makes soldier dissapear when dead after .5 seconds
    public void draw(Batch batch){
        if(!death || stateTime <.5f){
            super.draw(batch);
        }
    }

    @Override
    public void hitByBullet() {
        count++;
        setToDeath = true;

        //on death add 20 points
      //  Hud.addScore(20);

    }
}
