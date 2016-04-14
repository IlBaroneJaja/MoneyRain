package be.ecam.moneyrain;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by aurel on 13/04/2016.
 */
public class Player extends Movable {
    private boolean pushing;

    public Player(Resources res, Point screenSize, Point position,Point speed){
        super(res, screenSize, position, speed);
        imageSize = new Point(79, 94);
        this.position.x = screenSize.x/2;
        this.position.y = screenSize.y-imageSize.y;
        pushing = false;
    }

    @Override
    protected void setImage() {
        image = BitmapFactory.decodeResource(res,R.drawable.persosmall);
    }

    public void move(){
        if (pushing)
            moveRight();
        else
            moveLeft();
    }

    public void moveRight(){
        if(checkCollision() != "right")
            position.x += speed.x;
    }

    public void moveLeft(){
        if(checkCollision() != "left")
            position.x += -speed.x;
    }

    public void setPushing(){
        if(pushing == true)
            pushing = false;
        else
            pushing = true;
    }
}
