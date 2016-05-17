package be.ecam.moneyrain;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by aurel on 14/04/2016.
 */
public class Item extends Movable {
    public Item(Point screenSize, Point position, Point speed, int imageID){
        super(screenSize, position, speed);
        this.imageID = imageID;
        setImage(imageID);
    }

    @Override
    public void move() {
        position.y += speed.y;
    }

    public boolean isOutOfScreen(){
        if(checkCollision() == "bottom")
            return true;
        else
            return false;
    }

    public int getImageID(){
        return imageID;
    }
}
