package be.ecam.moneyrain;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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
    private Items items;
    private boolean firstLoad = true;
    public static Resources res;

    public GameView(Context context, AttributeSet aSet) {
        super(context, aSet);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics); // donne les dimensions actuelles (dynamique) du device
        this.res = getResources();
    }

    private void initElements(Canvas canvas){
        items = new Items(canvas);
        player = new Player(new Point(canvas.getWidth(), canvas.getHeight()), new Point(0, 0), new Point(5, 0));
        firstLoad = false;
    }

    public void next() {
        if(firstLoad)
            invalidate();
        else {
            player.move();
            items.update(player);
            invalidate();
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        if(!firstLoad) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                player.setPushing();
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP)
                player.setPushing();
            return super.onTouchEvent(event);
        }
        else
            return super.onTouchEvent(event);
    }

    @Override
    synchronized public void onDraw(Canvas canvas) {
        if(firstLoad) {
            initElements(canvas);
        }
        else {
            items.draw(canvas);
            player.draw(canvas);
        }
    }
}