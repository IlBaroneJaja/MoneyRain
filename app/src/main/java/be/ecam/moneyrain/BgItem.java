package be.ecam.moneyrain;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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

    public void mergeImage(Bitmap image2){
        Bitmap result = Bitmap.createBitmap(image.getWidth(), image.getHeight(), image.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(image, 0, 0, null);
        canvas.drawBitmap(image2, 0, 0, null);
        image = result;
    }

    public Bitmap getImage(){
        return image;
    }
}
