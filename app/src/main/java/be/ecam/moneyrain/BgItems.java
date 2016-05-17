package be.ecam.moneyrain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by 11293 on 02-05-16.
 */
public class BgItems {
    private ArrayList<BgItem> list;
    private Point screenSize;
    private int oldScore;

    public BgItems(Canvas canvas){
        list = new ArrayList<>();
        this.screenSize = new Point(canvas.getWidth(), canvas.getHeight());
        oldScore = 0;
    }

    private int getBgItem(int score){
        return 0;
    }

    public void draw(Canvas canvas){
        for (Iterator<BgItem> it = list.iterator(); it.hasNext();) {
            BgItem bgItem = it.next();
            bgItem.draw(canvas);
        }
    }

    public void update(int score){
        int imageID = getBgItem(score);

        list.add(new BgItem(new Point(screenSize.x, screenSize.y),
                new Point(0, 0),
                imageID));

        oldScore = score;
    }
}