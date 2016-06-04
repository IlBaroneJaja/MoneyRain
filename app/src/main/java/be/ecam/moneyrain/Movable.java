package be.ecam.moneyrain;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by aurel on 12/04/2016.
 */
public abstract class Movable extends Drawable {
    protected PointF speed;

    Movable (Point screenSize, PointF position, PointF speed){
        super(screenSize, position);
        this.speed = speed;
    }

    public abstract void move();

    public String checkCollision(){
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
