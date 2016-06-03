package be.ecam.moneyrain;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.*;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.Serializable;

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
    public static final String settings = "sharedSettings";
    public static Resources res;
    private BgItems bgItems;

    public GameView(Context context, AttributeSet aSet) {
        super(context, aSet);
        SharedPreferences sharedSettings = context.getSharedPreferences(settings,0);
        level = sharedSettings.getString("level", "BEGGAR");
        res = getResources();
    }

    private void initElements(Canvas canvas){
        ImagesContainer.initImagesSize(new Point(canvas.getWidth(), canvas.getHeight()), level);
        items = new Items(canvas);
        items.setLevel(level);
        bgItems = new BgItems(canvas, this, level);
        player = new Player(new Point(canvas.getWidth(), canvas.getHeight()), new Point(0, 0), new Point(12, 0));
        player.setLives(5);
        firstLoad = false;
    }

    public void next() {
        if(firstLoad)
            invalidate();
        else {
            player.move();
            items.update(player);
            bgItems.update(score);
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
            //bgItems.draw(canvas);
            items.draw(canvas);
            player.draw(canvas);
        }
    }

    @Override
    public Parcelable onSaveInstanceState(){
        Parcelable superState = super.onSaveInstanceState();

        Bundle state = new Bundle();
        state.putParcelable("GameViewState",superState);
        state.putSerializable("items", (Serializable) items);
        state.putInt("lives",lives);
        state.putInt("score",score);

        return state;
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

    public void setLevel(String level) {
        this.level = level;
    }
}