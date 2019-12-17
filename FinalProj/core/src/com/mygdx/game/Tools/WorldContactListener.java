package com.mygdx.game.Tools;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Main;
import com.mygdx.game.Sprites.Alien;
import com.mygdx.game.Sprites.Bullet;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.InteractiveObject;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int collisionDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch(collisionDef){
            case Main.ALIEN_BIT | Main.PLASMA_BIT:
                if(fixA.getFilterData().categoryBits == Main.PLASMA_BIT)
                    ((InteractiveObject)fixA.getUserData()).onBodyHit();
                else
                    ((InteractiveObject)fixB.getUserData()).onBodyHit();
                break;
            case Main.ENEMY_BIT| Main.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == Main.ENEMY_BIT) {
                    ((Enemy)fixA.getUserData()).hitByBullet();
                    ((Bullet) fixB.getUserData()).onHit();
                } else {
                    ((Enemy)fixB.getUserData()).hitByBullet();
                    ((Bullet) fixA.getUserData()).onHit();
                }
                break;
            case Main.ENEMY_BIT | Main.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Main.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case Main.ALIEN_BIT | Main.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Main.ALIEN_BIT)
                    ((Alien)fixA.getUserData()).hit();
                else
                    ((Alien)fixB.getUserData()).hit();
                break;
            case Main.ENEMY_BIT | Main.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case Main.ENEMY_BIT | Main.PLASMA_BIT:
                if(fixA.getFilterData().categoryBits == Main.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case Main.BOSS1_BIT | Main.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == Main.BOSS1_BIT) {
                    ((Enemy)fixA.getUserData()).hitByBullet();
                    ((Bullet) fixB.getUserData()).onHit();
                } else {
                    ((Enemy)fixB.getUserData()).hitByBullet();
                    ((Bullet) fixA.getUserData()).onHit();
                }
            case Main.BOSS1_BIT | Main.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Main.BOSS1_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case Main.BOSS1_BIT | Main.PLASMA_BIT:
                if(fixA.getFilterData().categoryBits == Main.BOSS1_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case Main.ALIEN_BIT | Main.BOSS1_BIT:
                if(fixA.getFilterData().categoryBits == Main.ALIEN_BIT)
                    ((Alien)fixA.getUserData()).hit();
                else
                    ((Alien)fixB.getUserData()).hit();
                break;
            case Main.ALIEN_BIT | Main.GROUND_BIT:
                if(fixA.getFilterData().categoryBits==Main.ALIEN_BIT)
                    ((Alien)fixA.getUserData()).hitGround();
                else{
                    ((Alien)fixB.getUserData()).hitGround();
                }
                break;
            case Main.ALIEN_BIT | Main.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits==Main.ALIEN_BIT)
                    ((Alien)fixA.getUserData()).hitGround();
                else{
                    ((Alien)fixB.getUserData()).hitGround();
                }
                break;
            case Main.ALIEN_BIT | Main.LEVEL_SWITCH_BIT:
                if(fixA.getFilterData().categoryBits==Main.ALIEN_BIT)
                    ((Alien)fixA.getUserData()).levelSwitch();
                else{
                    ((Alien)fixB.getUserData()).levelSwitch();
                }
                break;
            case Main.BULLET_BIT | Main.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == Main.BULLET_BIT)
                    ((Bullet) fixA.getUserData()).onHit();
                else
                    ((Bullet) fixB.getUserData()).onHit();
                break;
            case Main.BOSS2_BIT | Main.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == Main.BOSS2_BIT) {
                    ((Enemy)fixA.getUserData()).hitByBullet();
                    ((Bullet) fixB.getUserData()).onHit();
                } else {
                    ((Enemy)fixB.getUserData()).hitByBullet();
                    ((Bullet) fixA.getUserData()).onHit();
                }
                break;
            case Main.BOSS2_BIT | Main.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Main.BOSS2_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case Main.BOSS2_BIT | Main.PLASMA_BIT:
                if(fixA.getFilterData().categoryBits == Main.BOSS1_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case Main.ALIEN_BIT | Main.BOSS2_BIT:
                if(fixA.getFilterData().categoryBits == Main.ALIEN_BIT)
                    ((Alien)fixA.getUserData()).hit();
                else
                    ((Alien)fixB.getUserData()).hit();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}