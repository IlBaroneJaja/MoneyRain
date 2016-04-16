package be.ecam.moneyrain;

import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by aurel on 16/04/2016.
 */
public class Items {
    private ArrayList<Item> list;
    private Point screenSize;
    private long newItemNanoTime;

    Items(Canvas canvas){
        list = new ArrayList<>();
        this.screenSize = new Point(canvas.getWidth(), canvas.getHeight());
        newItemNanoTime = System.nanoTime();
    }

    public void update(Player player){
        addItem();
        try{
            for (Iterator<Item> it = list.iterator(); it.hasNext(); ) {
                Item item = it.next();
                if (!player.itemCaught(item)) {
                    if (!(pathFinished(item)))
                        item.move();
                } else
                    it.remove();
            }
        }
        catch (Exception e){
        }
    }

    public void draw(Canvas canvas){
        for (Iterator<Item> it = list.iterator(); it.hasNext();) {
            Item item = it.next();
            item.draw(canvas);
        }
    }

    private void addItem(){
        long currentNanoTime = System.nanoTime();
        if(((currentNanoTime-newItemNanoTime)/1000000) > 1500){
            Random randomPos = new Random();
            Random randomItem = new Random();

            int itemID = randomItem.nextInt(3);
            int imageID;
            Point imageSize;

            switch (itemID){
                case 0:
                    imageID = R.drawable.bombesmall;
                    imageSize = new Point(42, 49);
                    break;
                case 1:
                    imageID = R.drawable.billetsmall;
                    imageSize = new Point(49,23);
                    break;
                case 2:
                    imageID = R.drawable.piecesmall;
                    imageSize = new Point(38, 41);
                    break;
                default:
                    imageID = R.drawable.piecesmall;
                    imageSize = new Point(38, 41);
            }

            newItemNanoTime = currentNanoTime;
            list.add(new Item(new Point(screenSize.x, screenSize.y), new Point(randomPos.nextInt(screenSize.x-imageSize.x),0),new Point(0, 5), imageID, imageSize));
        }
    }

    private boolean pathFinished(Item item){
        if(item.getPosition().y > screenSize.y) {
            list.remove(item);
            return true;
        }
        else
            return false;
    }
}
