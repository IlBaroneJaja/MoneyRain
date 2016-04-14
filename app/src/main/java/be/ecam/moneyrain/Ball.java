package be.ecam.moneyrain;

/**
 * Created by lur on 11-04-16.
 */
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

public class Ball extends PointF{
    public float radius;
    private PointF speed;
    public Bitmap image;

    public Ball(float x, float y, float radius, float speedX, float speedY, Bitmap image) {
        super(x,y);

        this.radius = radius;
        speed = new PointF(speedX,speedY);
        this.image = image;
    }

    public void next() {
        x += speed.x;
        y += speed.y;
    }

    public void verticalHit() {
        speed.x = -speed.x;
    }

    public void horizontalHit() {
        speed.y = -speed.y;
    }
}
