package be.ecam.moneyrain;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by aurel on 13/04/2016.
 */
public class Player extends Movable {
    private boolean pushing;

    public Player(Resources res, Point point, Point speed){
        super(res, point, speed);
        imageSize = new Point(84, 98);
        pushing = false;
    }

    @Override
    protected void setImage() {
        image = BitmapFactory.decodeResource(res,R.drawable.persoSmall);
    }

    public  void move(int width, int height){
        if (pushing)
            moveRight(width, height);
        else
            moveLeft(width, height);
    }

    public void moveRight(int width, int height){
        if(checkCollision(width, height) != "right")
            position.x += speed.x;
    }

    public void moveLeft(int width, int height){
        if(checkCollision(width, height) != "left")
            position.x += -speed.x;
    }

    public void setPushing(){
        if(pushing == true)
            pushing = false;
        else
            pushing = true;
    }
}
