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

/**
 * Created by lur on 11-04-16.
 */
public class GameView extends View {
    private Player player;
    private Item billet;
    private boolean firstLoad = true;

    public GameView(Context context, AttributeSet aSet) {
        super(context, aSet);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics); // donne les dimensions actuelles (dynamique) du device
    }

    public void next() {
        if(firstLoad)
            invalidate();
        else {
            player.move();
            if (!billet.isOutOfScreen())
                billet.move();

            invalidate();
        }
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
        if(firstLoad) {
            firstLoad = false;
            billet = new Item(getResources(), new Point(canvas.getWidth(), canvas.getHeight()), new Point(100, 0), new Point(0, 5), R.drawable.billetsmall, new Point(49, 23));
            player = new Player(getResources(), new Point(canvas.getWidth(), canvas.getHeight()), new Point(0, 0), new Point(5, 0));
        }
        else {
            if (!billet.isOutOfScreen())
                billet.draw(canvas);
            player.draw(canvas);
        }
    }
}