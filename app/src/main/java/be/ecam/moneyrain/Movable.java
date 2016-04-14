package be.ecam.moneyrain;

import android.content.res.Resources;
import android.graphics.Point;

/**
 * Created by aurel on 12/04/2016.
 */
public abstract class Movable extends Drawable {
    protected Point speed;

    Movable (Resources res, Point screenSize, Point position, Point speed){
        super(res, screenSize, position);
        this.speed = speed;
    }

    public abstract void move();

    protected String checkCollision(){
        if(position.x > screenSize.x-imageSize.x)
            return "right";
        else if(position.x < 0)
            return "left";
        else if(position.y > screenSize.y-imageSize.y)
            return "bottom";
        else if(position.y < 0)
            return "top";
        else
            return "none";
    }
}
