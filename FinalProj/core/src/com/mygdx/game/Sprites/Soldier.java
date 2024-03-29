package com.mygdx.game.Sprites;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main;
import com.mygdx.game.Screens.PlayScreen;

public class Soldier extends Enemies {
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;

    public Soldier(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i=3;i<6; i++ )
            frames.add(new TextureRegion(screen.getAtlat().findRegion("Small_Enemy"), i *34,4,32,32));
        walkAnimation = new Animation(0.3f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),32/Main.PPM,32/Main.PPM);
    }
    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x-getWidth()/2, b2body.getPosition().y-getHeight()/2);
        setRegion(walkAnimation.getKeyFrame(stateTime,true));

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

        fdef.filter.categoryBits = Main.ENEMY_BIT;
        fdef.filter.maskBits = Main.GROUND_BIT | Main.PLASMA_BIT|Main.ENEMY_BIT|Main.ALIEN_BIT|Main.OBJECT_BIT;

        //setting shape above head
        fdef.shape = shape;

        fdef.filter.categoryBits = Main.SOLDIER_DEATH_BIT;

        b2body.createFixture(fdef);


        b2body.createFixture(fdef).setUserData("soldier");



    }

    @Override
    public void hitByBullet() {

    }
}
