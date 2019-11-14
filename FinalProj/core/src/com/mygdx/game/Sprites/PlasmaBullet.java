package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main;
import com.mygdx.game.Screens.PlayScreen;

import static com.mygdx.game.Main.SOLDIER_DEATH_BIT;

public class PlasmaBullet extends Bullet {
    private float stTime;
    private Animation<TextureRegion> shootAnimation;
    private Array<TextureRegion> frames;

    public PlasmaBullet(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i=0;i<3; i++ )
            frames.add(new TextureRegion(screen.getBulletAtlas().findRegion("Plasma_Ammo"), i *18,2,16,16));
        shootAnimation = new Animation(0.1f,frames);
        stTime = 0;
        setBounds(getX(),getY(),16/ Main.PPM,16/Main.PPM);
    }
    public void update(float dt){
        stTime += dt;
        setPosition(b2body.getPosition().x-getWidth()/2, b2body.getPosition().y-getHeight()/2);
        setRegion(shootAnimation.getKeyFrame(stTime,true));

    }

    @Override
    protected void defineBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX()+.6f, getY()+.6f);
        bdef.type = BodyDef.BodyType.DynamicBody;


        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();


        //size of the collision body
        shape.setRadius(4.5f / Main.PPM);

        fdef.filter.categoryBits = Main.BULLET_BIT;
        fdef.filter.maskBits = Main.GROUND_BIT | Main.PLASMA_BIT|Main.ENEMY_BIT|Main.OBJECT_BIT|SOLDIER_DEATH_BIT;

        //setting shape above head
        fdef.shape = shape;

        fdef.filter.categoryBits = Main.BULLET_BIT;

        b2body.createFixture(fdef);


        b2body.createFixture(fdef).setUserData("Bullet");



    }

    @Override
    public void hitByBullet() {

    }
}


