package be.ecam.moneyrain;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by aurel on 12/04/2016.
 */
public abstract class Drawable {
    protected Resources res;
    protected Bitmap image;
    protected Point imageSize;
    protected Point screenSize;
    protected Point position;

    public Drawable(Resources res, Point screenSize, Point position){
        this.res = res;
        this.screenSize = screenSize;
        this.position = position;

        setImage();
    }

    abstract protected void setImage();

    protected void draw(Canvas canvas){
        canvas.drawBitmap(image, position.x, position.y, null);
    }
}
