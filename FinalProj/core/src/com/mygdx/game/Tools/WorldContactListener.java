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
        int fixACat = fixA.getFilterData().categoryBits;
        int fixBCat = fixB.getFilterData().categoryBits;
        switch(collisionDef){
            case Main.ENEMY_BIT | Main.BULLET_BIT:
            case Main.BOSS1_BIT | Main.BULLET_BIT:
            case Main.BOSS2_BIT | Main.BULLET_BIT:
                if (fixACat == Main.BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).onHit();
                    ((Enemy) fixB.getUserData()).hitByBullet();
                } else {
                    ((Bullet) fixB.getUserData()).onHit();
                    ((Enemy) fixA.getUserData()).hitByBullet();
                }
                break;
            case Main.ENEMY_BIT | Main.OBJECT_BIT:
            case Main.ENEMY_BIT | Main.PLASMA_BIT:
            case Main.BOSS1_BIT | Main.OBJECT_BIT:
            case Main.BOSS1_BIT | Main.PLASMA_BIT:
            case Main.BOSS2_BIT | Main.OBJECT_BIT:
            case Main.BOSS2_BIT | Main.PLASMA_BIT:
                if (fixACat == Main.OBJECT_BIT || fixACat == Main.PLASMA_BIT)
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                else((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                break;
            case Main.ENEMY_BIT | Main.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Main.ALIEN_BIT | Main.PLASMA_BIT:
                if (fixACat == Main.PLASMA_BIT)
                    ((InteractiveObject) fixA.getUserData()).onBodyHit();
                else((InteractiveObject) fixB.getUserData()).onBodyHit();
                break;
            case Main.ALIEN_BIT | Main.ENEMY_BIT:
            case Main.ALIEN_BIT | Main.BOSS1_BIT:
            case Main.ALIEN_BIT | Main.BOSS2_BIT:
                if (fixACat == Main.ALIEN_BIT)
                    ((Alien) fixA.getUserData()).hit();
                else((Alien) fixB.getUserData()).hit();
                break;
            case Main.ALIEN_BIT | Main.GROUND_BIT:
            case Main.ALIEN_BIT | Main.OBJECT_BIT:
                if (fixACat == Main.ALIEN_BIT)
                    ((Alien) fixA.getUserData()).hitGround();
                else((Alien) fixB.getUserData()).hitGround();
                break;
            case Main.ALIEN_BIT | Main.LEVEL_SWITCH_BIT:
                if (fixACat == Main.ALIEN_BIT)
                    ((Alien) fixA.getUserData()).levelSwitch();
                else((Alien) fixB.getUserData()).levelSwitch();
                break;
            case Main.BULLET_BIT | Main.GROUND_BIT:
            case Main.BULLET_BIT | Main.OBJECT_BIT:
                if (fixACat == Main.BULLET_BIT)
                    ((Bullet) fixA.getUserData()).onHit();
                else((Bullet) fixB.getUserData()).onHit();
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