package be.ecam.moneyrain;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by aurel on 04/06/2016.
 */
public class Cloud extends Movable{

    public Cloud(Point screenSize, PointF position, PointF speed, int image){
        super(screenSize, position, speed);
        setImage(image);
    }

    @Override
    public void move() {
        position.x += speed.x;
        if(position.x > screenSize.x){
            position.x = -imageSize.x;
        }
    }
}
