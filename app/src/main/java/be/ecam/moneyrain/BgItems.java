package be.ecam.moneyrain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by 11293 on 02-05-16.
 */
public class BgItems {
    private ArrayList<BgItem> list;
    private Point screenSize;
    private String level;
    private int previousScore;
    private int[] bgDelayList = {R.drawable.background1_a,
                           R.drawable.background2_a,
                           R.drawable.background3_a,
                           R.drawable.background4_a};

    public BgItems(Canvas canvas){
        list = new ArrayList<>();
        this.screenSize = new Point(canvas.getWidth(), canvas.getHeight());
        previousScore = 0;
    }

    private int getBgItem(int score){
        switch(level)
        {
            case "BEGGAR":
                if(previousScore < 1000 && score >= 1000) {
                    previousScore = 1000;
                    return R.drawable.background1_a;
                }
                else if(previousScore < 2000 && score >= 2000) {
                    previousScore = 2000;
                    return R.drawable.background1_b;
                }
                else if(previousScore < 3000 && score >= 3000) {
                    previousScore = 3000;
                    return R.drawable.background1_c;
                }
                else
                return -1;
            case "CASHIER":
                if(previousScore < 1000 && score >= 1000) {
                    previousScore = 1000;
                    return R.drawable.background2_a;
                }
                else if(previousScore < 2000 && score >= 2000) {
                    previousScore = 2000;
                    return R.drawable.background2_b;
                }
                else if(previousScore < 3000 && score >= 3000) {
                    previousScore = 3000;
                    return R.drawable.background2_c;
                }
                else
                    return -1;
            case "TRADER":
                if(previousScore < 1000 && score >= 1000) {
                    previousScore = 1000;
                    return R.drawable.background3_a;
                }
                else if(previousScore < 2000 && score >= 2000) {
                    previousScore = 2000;
                    return R.drawable.background3_b;
                }
                else if(previousScore < 3000 && score >= 3000) {
                    previousScore = 3000;
                    return R.drawable.background3_c;
                }
                else
                    return -1;
            case "ILLUMINATI":
                if(previousScore < 1000 && score >= 1000) {
                    previousScore = 1000;
                    return R.drawable.background4_a;
                }
                else if(previousScore < 2000 && score >= 2000) {
                    previousScore = 2000;
                    return R.drawable.background4_b;
                }
                else if(previousScore < 3000 && score >= 3000) {
                    previousScore = 3000;
                    return R.drawable.background4_c;
                }
                else
                    return -1;
            default:
                if(previousScore < 1000 && score >= 1000) {
                    previousScore = 1000;
                    return R.drawable.background1_a;
                }
                else if(previousScore < 2000 && score >= 2000) {
                    previousScore = 2000;
                    return R.drawable.background1_b;
                }
                else if(previousScore < 3000 && score >= 3000) {
                    previousScore = 3000;
                    return R.drawable.background1_c;
                }
                else
                    return -1;
        }
    }

    public void draw(Canvas canvas){
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
    }

    public void update(int score){
        int imageID = getBgItem(score);

        if(imageID != -1) {
            list.add(new BgItem(new Point(screenSize.x, screenSize.y),
                    new Point(0, 0),
                    imageID));
        }
    }

    public void setLevel(String level) {
        this.level = level;
    }
}