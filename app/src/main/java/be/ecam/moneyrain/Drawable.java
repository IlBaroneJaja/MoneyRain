package be.ecam.moneyrain;

import android.content.res.Resources;
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
    protected int[] bgArray = {R.drawable.background1_a,R.drawable.background1_b,R.drawable.background1_c,
                               R.drawable.background2_a,R.drawable.background2_b,R.drawable.background2_c,
                               R.drawable.background3_a,R.drawable.background3_b,R.drawable.background3_c,
                               R.drawable.background4_a,R.drawable.background4_b,R.drawable.background4_c};

    public Drawable(Point screenSize, Point position){
        this.screenSize = screenSize;
        this.position = position;
    }

    protected void setImage(int imageID){
        image = BitmapFactory.decodeResource(GameView.res,imageID);
        for(int i = 0; i < bgArray.length; i++){
            if(bgArray[i] == imageID)
            {
                image = Bitmap.createScaledBitmap(image, screenSize.x, screenSize.y, true);
                break;
            }
        }
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
