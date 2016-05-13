package be.ecam.moneyrain;

import android.graphics.Point;

/**
 * Created by 11293 on 02-05-16.
 */
public class BgItem extends Drawable {
    public BgItem(Point screenSize, Point position, int imageID) {
        super(screenSize, position);
        this.imageID = imageID;
        setImage(imageID);
    }
}
