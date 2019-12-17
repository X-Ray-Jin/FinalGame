package com.mygdx.game.Sprites;
import static com.mygdx.game.Main.BULLET_BIT;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;

public class Soldier extends Enemy {
    public enum State {SRUNNING,SDEAD, SIDLE}
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> deadAnimation;
    private Animation<TextureRegion> idleAnimation;
    private Array<TextureRegion> frames;
    private boolean die;
    private boolean dead;
    public State currentState;
    private int count = 0;
    private boolean runningRight;
    private Sound deathSound;

    private float deathDelay;

    public Soldier(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<>();
        for (int i = 3; i < 6; i++)
            frames.add(new TextureRegion(
                            screen.getAtlas().findRegion("Small_Enemy"), i * 34,
                            4, 32, 32));
        walkAnimation = new Animation<>(0.3f, frames);
        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(
                            screen.getAtlas().findRegion("Small_Enemy"), i * 34,
                            4, 32, 32));
        deadAnimation = new Animation<>(0.3f, frames);
        for (int i = 2; i < 3; i++)
            frames.add(new TextureRegion(
                            screen.getAtlas().findRegion("Small_Enemy"), i * 34,
                            4, 32, 32));
        idleAnimation = new Animation<>(0.2f, frames);

        stateTime = 0;
        setBounds(getX(), getY(), 32 / Main.PPM, 32 / Main.PPM);

        die = false;
        dead = false;
        deathSound=Main.manager.get("Audio/death.ogg", Sound.class);
    }
    public void dsound(){
        if (die == true) {
            deathSound.play();
        }
    }

    @Override
    public void update(float dt){
        stateTime += dt;
        TextureRegion region;
        region = walkAnimation.getKeyFrame(stateTime, true);
        if (die) {
            dsound();
            deathDelay = .5f;
            setRegion(deadAnimation.getKeyFrame(stateTime));

            //adds score and plasma
            Hud.addScore(50);
            Hud.addPlasma(3);
            die = false;
            dead = true;
        } else if (!dead) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        } else deathDelay -= dt;


        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        if (getX() < screen.getPlayer().getX() + 2.8f)
            getBody().setActive(true);
        if (getX() < screen.getPlayer().getX() + 2.8f)
            getBody().setActive(true);
        if (dead && deathDelay <= 0f) {
            markForRemoval(true);
        }
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
        shape.setRadius(12.8f / Main.PPM);
        //fdef.filter.categoryBits = Main.SOLDIER_DEATH_BIT;

        fdef.filter.maskBits = Main.GROUND_BIT |Main.ENEMY_BIT|Main.ALIEN_BIT|Main.OBJECT_BIT|BULLET_BIT;

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

    @Override
    public void hitByBullet() {
        count++;
        if (count > 3) {
            die = true;
        }
    }
}
