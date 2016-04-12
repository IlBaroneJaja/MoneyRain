package be.ecam.moneyrain;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lur on 11-04-16.
 */
public class GameView extends View {

    private Background background;
    Paint mPaint;
    private Ball mBall;


    public GameView(Context context, AttributeSet aSet) {
        super(context, aSet);

        mPaint = new Paint();
        background = new Background(getResources());
        mBall = new Ball(20, 10, 5, 5, 10, BitmapFactory.decodeResource(getResources(),R.drawable.bombe));
    }

    public void next() {
        if(mBall.x+mBall.radius > getWidth() || mBall.x-mBall.radius < 0)
            mBall.verticalHit();
        if(mBall.y+mBall.radius > getHeight() || mBall.y-mBall.radius < 0)
            mBall.horizontalHit();
        mBall.next();
        invalidate();
    }

    @Override
    synchronized public void onDraw(Canvas canvas) {
        background.draw(canvas, getWidth(), getHeight());
        canvas.drawBitmap(mBall.image, mBall.x, mBall.y, null);
    }


}
