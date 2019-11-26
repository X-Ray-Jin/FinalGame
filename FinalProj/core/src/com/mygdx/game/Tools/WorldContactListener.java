package com.mygdx.game.Tools;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Main;
import com.mygdx.game.Sprites.Bullet;
import com.mygdx.game.Sprites.Enemies;
import com.mygdx.game.Sprites.InteractiveObject;
import com.mygdx.game.Sprites.PlasmaBullet;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
    Fixture fixA = contact.getFixtureA();
    Fixture fixB = contact.getFixtureB();

    int collisionDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

    if(fixA.getUserData()=="alien" || fixB.getUserData()=="alien"){
        Fixture head = fixA.getUserData() == "alien" ? fixA:fixB;
        Fixture object = head == fixA ? fixB : fixA;

        if(object.getUserData() !=null && InteractiveObject.class.isAssignableFrom(object.getUserData().getClass())){
            ((InteractiveObject)object.getUserData()).onBodyHit();
        }
    }
    switch(collisionDef){
        case Main.ENEMY_BIT| Main.BULLET_BIT:
            if(fixA.getFilterData().categoryBits == Main.ENEMY_BIT)
                ((Enemies)fixA.getUserData()).hitByBullet();
            else
                ((Enemies)fixB.getUserData()).hitByBullet();
            break;
        case Main.ENEMY_BIT | Main.OBJECT_BIT:
            if(fixA.getFilterData().categoryBits == Main.ENEMY_BIT)
                ((Enemies)fixA.getUserData()).reverseVelocity(true,false);
            else
                ((Enemies)fixB.getUserData()).reverseVelocity(true,false);
            break;
        case Main.ALIEN_BIT | Main.ENEMY_BIT:
            if(fixA.getFilterData().categoryBits == Main.ENEMY_BIT)
                ((Enemies)fixA.getUserData()).reverseVelocity(true,false);
            else
                ((Enemies)fixB.getUserData()).reverseVelocity(true,false);
            break;
        case Main.ENEMY_BIT | Main.ENEMY_BIT:
            ((Enemies)fixA.getUserData()).reverseVelocity(true,false);
            ((Enemies)fixB.getUserData()).reverseVelocity(true,false);
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
