package be.ecam.moneyrain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by 11293 on 02-05-16.
 */
public class BgItems {
    private GameView gameView;
   // private ArrayList<BgItem> list;
   // private Point screenSize;
    private String level;
    private int previousScore;
    /*private int[] bgDelayList = {R.drawable.background1_a,
                           R.drawable.background2_a,
                           R.drawable.background3_a,
                           R.drawable.background4_a};*/

    public BgItems(Canvas canvas, GameView gameView, String level){
        //list = new ArrayList<>();
        this.level = level;
        this.gameView = gameView;
        //this.screenSize = new Point(canvas.getWidth(), canvas.getHeight());
        previousScore = 0;
        setBackground();
    }

    private boolean needUpdate(int score){
        switch(level)
        {
            case "BEGGAR":
                if(previousScore < 1000 && score >= 1000) {
                    previousScore = 1000;
                    return true;
                }
                else if(previousScore < 2000 && score >= 2000) {
                    previousScore = 2000;
                    return true;                }
                else if(previousScore < 3000 && score >= 3000) {
                    previousScore = 3000;
                    return true;                }
                else
                return false;
            case "CASHIER":
                if(previousScore < 1000 && score >= 1000) {
                    previousScore = 1000;
                    return true;                }
                else if(previousScore < 2000 && score >= 2000) {
                    previousScore = 2000;
                    return true;                }
                else if(previousScore < 3000 && score >= 3000) {
                    previousScore = 3000;
                    return true;                }
                else
                    return false;
            case "TRADER":
                if(previousScore < 1000 && score >= 1000) {
                    previousScore = 1000;
                    return true;                }
                else if(previousScore < 2000 && score >= 2000) {
                    previousScore = 2000;
                    return true;                }
                else if(previousScore < 3000 && score >= 3000) {
                    previousScore = 3000;
                    return true;                }
                else
                    return false;
            case "ILLUMINATI":
                if(previousScore < 1000 && score >= 1000) {
                    previousScore = 1000;
                    return true;                }
                else if(previousScore < 2000 && score >= 2000) {
                    previousScore = 2000;
                    return true;                }
                else if(previousScore < 3000 && score >= 3000) {
                    previousScore = 3000;
                    return true;                }
                else
                    return false;
            default:
                return false;
        }
    }

   /* public void draw(Canvas canvas){
        BgItem bgDelay = null;
        for (Iterator<BgItem> it = list.iterator(); it.hasNext();) {
            BgItem bgItem = it.next();
            for(int i = 0; i < bgDelayList.length; i++ ){
                if(bgDelayList[i] == bgItem.getImageID())
                    bgDelay = bgItem;
                else
                    bgItem.draw(canvas);
            }
        }
        if (bgDelay != null)
            bgDelay.draw(canvas);
    }*/

    public void update(int score){
        if(needUpdate(score)){
            setBackground();
        }
    }

    private void setBackground(){
        Bitmap background = ImagesContainer.getImage(R.drawable.background_default);
        Bitmap result = Bitmap.createBitmap(background.getWidth(), background.getHeight(), background.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(background, 0, 0, null);

        switch(level)
        {
            case "BEGGAR":
                if(previousScore >= 3000) {
                    canvas.drawBitmap(ImagesContainer.getImage(R.drawable.background1_c), 0, 0, null);
                }
                if(previousScore >= 1000) {
                    canvas.drawBitmap(ImagesContainer.getImage(R.drawable.background1_a), 0, 0, null);
                }
                if(previousScore >= 2000) {
                    canvas.drawBitmap(ImagesContainer.getImage(R.drawable.background1_b), 0, 0, null);
                }
                break;
            case "CASHIER":
                if(previousScore >= 3000) {
                    canvas.drawBitmap(ImagesContainer.getImage(R.drawable.background2_c), 0, 0, null);
                }
                if(previousScore >= 1000) {
                    canvas.drawBitmap(ImagesContainer.getImage(R.drawable.background2_a), 0, 0, null);
                }
                if(previousScore >= 2000) {
                    canvas.drawBitmap(ImagesContainer.getImage(R.drawable.background2_b), 0, 0, null);
                }
                break;
            case "TRADER":
                if(previousScore >= 3000) {
                    canvas.drawBitmap(ImagesContainer.getImage(R.drawable.background3_c), 0, 0, null);
                }
                if(previousScore >= 1000) {
                    canvas.drawBitmap(ImagesContainer.getImage(R.drawable.background3_a), 0, 0, null);
                }
                if(previousScore >= 2000) {
                    canvas.drawBitmap(ImagesContainer.getImage(R.drawable.background3_b), 0, 0, null);
                }
                break;
            case "ILLUMINATI":
                if(previousScore >= 3000) {
                    canvas.drawBitmap(ImagesContainer.getImage(R.drawable.background4_c), 0, 0, null);
                }
                if(previousScore >= 1000) {
                    canvas.drawBitmap(ImagesContainer.getImage(R.drawable.background4_a), 0, 0, null);
                }
                if(previousScore >= 2000) {
                    canvas.drawBitmap(ImagesContainer.getImage(R.drawable.background4_b), 0, 0, null);
                }
                break;
        }

        gameView.setBackground(new BitmapDrawable(GameView.res, result));
    }

    public void setLevel(String level) {
        this.level = level;
    }
}