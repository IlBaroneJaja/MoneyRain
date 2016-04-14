package be.ecam.moneyrain;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lur on 11-04-16.
 */
public class GameView extends View {

    private Background background;
    Paint mPaint;
    private Ball mBall;
    private Ball mBall2;
    private Player player;
    private boolean pushing = false;

    public GameView(Context context, AttributeSet aSet) {
        super(context, aSet);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics); // donne les dimensions actuelles (dynamique) du device
        mPaint = new Paint();
        background = new Background(getResources());
        mBall = new Ball(150, 150, 0, 2, 2, BitmapFactory.decodeResource(getResources(),R.drawable.piece));
        mBall2 = new Ball(300, 200, 0, -3, -3, BitmapFactory.decodeResource(getResources(),R.drawable.billet));
        player = new Player(getResources(), new Point(displaymetrics.widthPixels/2,300), new Point(5, 0));

    }

    public void next() {
        if(mBall.x+mBall.radius > getWidth()-75 || mBall.x-mBall.radius < 0)
            mBall.verticalHit();
        if(mBall.y+mBall.radius > getHeight()-82 || mBall.y-mBall.radius < 0)
            mBall.horizontalHit();
        mBall.next();
        if(mBall2.x+mBall2.radius > getWidth()-98 || mBall2.x-mBall2.radius < 0)
            mBall2.verticalHit();
        if(mBall2.y+mBall2.radius > getHeight()-45 || mBall2.y-mBall2.radius < 0)
            mBall2.horizontalHit();
        mBall2.next();
        player.move(getWidth(), getHeight());
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN ){
            player.setPushing();
            return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_UP )
            player.setPushing();
        return  super.onTouchEvent(event);
    }

    @Override
    synchronized public void onDraw(Canvas canvas) {
        //background.draw(canvas, getWidth(), getHeight());
        canvas.drawBitmap(mBall.image, mBall.x, mBall.y, null);
        canvas.drawBitmap(mBall2.image, mBall2.x, mBall2.y, null);
        player.draw(canvas);
    }

}
