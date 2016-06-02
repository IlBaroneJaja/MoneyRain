package be.ecam.moneyrain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by aurel on 12/04/2016.
 */
public abstract class Drawable {
    protected Point screenSize;
    protected int imageID;
    protected Bitmap image;
    protected Point imageSize;
    protected Point position;

    public Drawable(Point screenSize, Point position){
        this.screenSize = screenSize;
        this.position = position;
    }

    protected void setImage(int imageID){
        image = ImagesContainer.getImage(imageID);
        imageSize = new Point(image.getWidth(), image.getHeight());
    }

    protected void draw(Canvas canvas){
        canvas.drawBitmap(image, position.x, position.y, null);
    }

    public Point getPosition(){
        return position;
    }

    public Point getImageSize(){
        return imageSize;
    }

    public int getImageID(){
        return imageID;
    }
}
