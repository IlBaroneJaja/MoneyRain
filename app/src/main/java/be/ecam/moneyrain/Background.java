package be.ecam.moneyrain;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by aurel on 12/04/2016.
 */
public class Background extends Drawable {
    private Point dimension;
    private Point scale;

    Background(Resources res){
        super(res, new Point(0,0));
        dimension = new Point(82, 75);
    }

    @Override
    protected void setImage() {
        image = BitmapFactory.decodeResource(res,R.drawable.piece);
    }

    protected void draw(Canvas canvas, int width, int height){
        scale = new Point(width/dimension.x, height/dimension.y);
        int savedState = canvas.save();
        canvas.scale(scale.x, scale.y);
        draw(canvas);
        canvas.restoreToCount(savedState);
    }
}
