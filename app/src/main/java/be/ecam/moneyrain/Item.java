package be.ecam.moneyrain;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by aurel on 14/04/2016.
 */
public class Item extends Movable {
    private int imageID;

    public Item(Resources res, Point screenSize, Point position, Point speed, int imageID, Point imageSize){
        super(res, screenSize, position, speed);
        this.imageID = imageID;
        this.imageSize = imageSize;
        setImage();
    }

    @Override
    protected void setImage() {
        image = BitmapFactory.decodeResource(res,imageID);
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
}
