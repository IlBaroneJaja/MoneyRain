package be.ecam.moneyrain;

import android.content.res.Resources;
import android.graphics.Point;

/**
 * Created by aurel on 12/04/2016.
 */
public abstract class Movable extends Drawable {
    protected Point speed;

    Movable (Resources res, Point position, int vx, int vy){
        super(res, position);
        this.speed.x = vx;
        this.speed.y = vy;
    }
}
