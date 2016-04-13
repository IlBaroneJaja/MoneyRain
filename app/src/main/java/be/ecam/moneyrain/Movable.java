package be.ecam.moneyrain;

import android.content.res.Resources;
import android.graphics.Point;

/**
 * Created by aurel on 12/04/2016.
 */
public abstract class Movable extends Drawable {
    protected Point speed;

    Movable (Resources res, Point position, Point speed){
        super(res, position);
        this.speed = speed;
    }

    public void move(int width, int height){
        checkCollision(width, height);
        position.x += speed.x;
        position.y += speed.y;
    }

    public String checkCollision(int width, int height){
        if(position.x > width-imageSize.x)
            return "right";
        else if(position.x < 0)
            return "left";
        else if(position.y > height-imageSize.y)
            return "bottom";
        else if(position.y < 0)
            return "top";
        else
            return "none";
    }
}
