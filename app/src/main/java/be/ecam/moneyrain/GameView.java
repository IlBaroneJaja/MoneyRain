package be.ecam.moneyrain;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lur on 11-04-16.
 */
public class GameView extends View {
    private Player player;
    private Items items;
    private int lives;
    private int score;
    private String level;
    private boolean firstLoad = true;
    public static Resources res;

    public GameView(Context context, AttributeSet aSet) {
        super(context, aSet);
        this.res = getResources();
    }

    private void initElements(Canvas canvas){

        items = new Items(canvas);
        items.setLevel(this.getLevel());
        player = new Player(new Point(canvas.getWidth(), canvas.getHeight()), new Point(0, 0), new Point(10, 0));
        player.setLives(5);
        firstLoad = false;
    }

    public void next() {
        if(firstLoad)
            invalidate();
        else {
            player.move();
            items.update(player);
            setLives(player.getLives());
            setScore(player.getScore());
            invalidate();
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        if(!firstLoad) {
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN ||
                    MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_MOVE ||
                    MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_POINTER_UP ) {

                boolean right = false;
                boolean left = false;

                for (int i = 0; i<event.getPointerCount();i++){
                    if(MotionEventCompat.getActionMasked(event) != MotionEvent.ACTION_POINTER_UP || i != event.getActionIndex()){
                        if((int)MotionEventCompat.getX(event, i) > getWidth()/2)
                            right = true;
                        else if ((int)MotionEventCompat.getX(event, i) <= getWidth()/2)
                            left = true;
                    }
                }

                if (right && !left)
                    player.setMove("right");
                else if (left && !right)
                    player.setMove("left");

                return true;

            } else if (event.getAction() == MotionEvent.ACTION_UP)
                player.setMove("none");

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

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }



    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}