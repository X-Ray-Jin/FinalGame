package com.mygdx.game.Sprites;

import static com.mygdx.game.Main.BULLET_BIT;
import static com.mygdx.game.Main.ENEMY_BIT;

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

public class BossLevel2 extends Enemy {
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> deadAnimation;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> shootAnimation;
    private Array<TextureRegion> frames;
    private int count=0;
    private float stateTime;
    private boolean setToDeath;
    private boolean death;
    private boolean runningRight;



    public BossLevel2(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<>();
        for(int i=0;i<3; i++ )
            frames.add(new TextureRegion(screen.getFBossAtlas().findRegion("CHB"), i *66,2,64,96));
        walkAnimation = new Animation(0.3f,frames);
        for(int i=3;i<5; i++ )
            frames.add(new TextureRegion(screen.getFBossAtlas().findRegion("CHB"), i *66,2,64,96));
        deadAnimation = new Animation(0.3f,frames);
        for(int i=2;i<3; i++ )
            frames.add(new TextureRegion(screen.getFBossAtlas().findRegion("CHB"), i *34,3,34,34));
        idleAnimation = new Animation(0.2f,frames);
        for(int i=3;i<5; i++ )
            frames.add(new TextureRegion(screen.getFBossAtlas().findRegion("CHB"), i *34,3,34,34));
        shootAnimation = new Animation(0.1f,frames);

        stateTime = 0;
        setBounds(getX(),getY(),32/Main.PPM,32/Main.PPM);

        setToDeath = false;
        death = false;



    }
    @Override
    public void update(float dt) {
        stateTime += dt;
        TextureRegion region;
        region=walkAnimation.getKeyFrame(stateTime,true);
        if (setToDeath && !death && count>=7) {
            world.destroyBody(b2body);
            death = true;
            setRegion(deadAnimation.getKeyFrame(stateTime));
            stateTime = 0;
            // setRegion(new TextureRegion(screen.getAtlat().findRegion("Small_Enemy"),34,0,32,32));
            //count =0;
            //adds score and plasma
            Hud.addScore(100);
            Hud.addPlasma(15);
        }

        else if(!death) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        if (getX() < screen.getPlayer().getX() + 2.2f)
            getBody().setActive(true);
    }

    @Override
    protected void defineBody() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;


        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();


        //size of the collision body
        shape.setRadius(18.8f / Main.PPM);



        fdef.filter.maskBits = Main.GROUND_BIT |Main.ENEMY_BIT|Main.ALIEN_BIT|Main.OBJECT_BIT|BULLET_BIT|ENEMY_BIT;

        //setting shape above head
        fdef.shape = shape;


        fdef.filter.categoryBits = Main.BOSS2_BIT;

        b2body.createFixture(fdef).setUserData(this);
    }
    @Override
    public void draw(Batch batch){
        if(!death || stateTime <.5f){
            super.draw(batch);
        }
    }

    @Override
    public void hitByBullet() {
        count++;
        if(count>7) {
            setToDeath = true;

        }


    }


}
