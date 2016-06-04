package be.ecam.moneyrain;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by aurel on 14/04/2016.
 */
public class Item extends Movable {
    public Item(Point screenSize, PointF position, PointF speed, int imageID){
        super(screenSize, position, speed);
        this.imageID = imageID;
        setImage(imageID);
    }

    @Override
    public void move() {
        position.y += speed.y;
    }

    public int getImageID(){
        return imageID;
    }
}
